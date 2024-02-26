package com.miapi.monitor.ARCH.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miapi.monitor.ARCH.config.ChannelProperties;
import com.miapi.monitor.ARCH.headers.HeaderArchMeta;
import com.miapi.monitor.ARCH.common.Literales;
import com.miapi.monitor.ARCH.data.TXMQRecord;
import com.miapi.monitor.ARCH.components.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import com.google.gson.Gson;

@Slf4j
@ControllerAdvice
public class ControllerInterceptor implements HandlerInterceptor {


    @Autowired
    private final MessageSender messageSender;

    @Autowired
    private final ChannelProperties channelProperties;

    public ControllerInterceptor(MessageSender messageSender, ChannelProperties channelProperties) {
        this.messageSender = messageSender;
        this.channelProperties = channelProperties;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TXMQRecord txRecord = new TXMQRecord();
        //1.- Obtenemos la cabecera de arquitectura.
        String headerValue = request.getHeader("H-Arch-Meta");
        if (headerValue != null) {
            HeaderArchMeta headerArchMeta = new Gson().fromJson(headerValue, HeaderArchMeta.class);
            log.info("H-Arch-Meta: " + headerArchMeta);
            // Si la TX está informada supondremos que este simplemente es un nodo de una transacción ya iniciada.
            if( headerArchMeta.getTxid() != null){
                //Sumaremos 1 a la profundidad. Es decir que supondremos que esta tx es hija de otra.
                txRecord.setTier(txRecord.getTier()+1);
                txRecord.setTxid(headerArchMeta.getTxid());
            }else{
                txRecord.setTier(1);
            }
            // Informamos del canal
            String canal = headerArchMeta.getChannel();
            if(canal != null){
                txRecord.setChannel(headerArchMeta.getChannel());
            }else{
                log.warn("El canal no se ha informado en la cabecer. Se tomará el canal por defecto");
                txRecord.setChannel(Literales.CANAL_POR_DEFECTO);
            }
        }else {
            log.warn("Cabecera de arquitectura no informada.");
            txRecord.setChannel(Literales.CANAL_POR_DEFECTO);
        }

        //2.- Completamos el resto de la información de la transacción
        txRecord.setPath(request.getRequestURI());
        txRecord.setMethod(request.getMethod());
        txRecord.setRequestParams(convertParamsToJson(request.getParameterMap()));

        //3.- Grabamos el mensaje en MQ
        Gson gson = new Gson();
        String txRecordJson = gson.toJson(txRecord);
        String colaMQ = channelProperties.getColaMQ(txRecord.getChannel().toLowerCase());
        log.info("TX-IN: " + txRecordJson);
        log.info("Cola MQ: " + colaMQ);
        request.setAttribute("txRecord", txRecord);
        messageSender.sendMessage(colaMQ, txRecordJson);
        return true;
    }

    private HeaderArchMeta loadHArchMeta(String jsonString) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HeaderArchMeta headerArchMeta = objectMapper.readValue(jsonString, HeaderArchMeta.class);
            return headerArchMeta;
        }catch (JsonProcessingException ex){
            log.error(ex.getMessage());
        }
        return new HeaderArchMeta();
    }

    private String convertParamsToJson(Map<String, String[]> parameterMap) {
        if(parameterMap != null){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(parameterMap);
            } catch (Exception e) {
                log.error("Error al convertir los parámetros a JSON", e);
                return "";
            }
        }
        return "";
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TXMQRecord txRecord = (TXMQRecord) request.getAttribute("txRecord");
        if(!txRecord.getState().equals(Literales.KO))
            txRecord.setState(Literales.OK);

        String colaMQ = channelProperties.getColaMQ(txRecord.getChannel().toLowerCase());
        log.info("TX-OUT: " + txRecord.getASJson());
        log.info("Cola MQ: " + colaMQ);
        messageSender.sendMessage(colaMQ, txRecord.getASJson());
        log.info(" TX: " + txRecord.getTxid() + " completada: " + txRecord.getState());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        e.printStackTrace();
        log.error("Se ha producido una excepción no controlada. Revise los logs: " + e.getMessage());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        TXMQRecord txRecord = (TXMQRecord) request.getAttribute("txRecord");
        txRecord.setState(Literales.KO);
        messageSender.sendMessage("DEV.QUEUE.1", txRecord.getASJson());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se ha producido una excepción no controlada: " + e.getMessage());
    }
}
