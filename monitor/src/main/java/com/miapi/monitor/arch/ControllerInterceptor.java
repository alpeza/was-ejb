package com.miapi.monitor.arch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miapi.monitor.components.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class ControllerInterceptor implements HandlerInterceptor {

    private TXRecord txRecord ;
    @Autowired
    private final MessageSender messageSender;

    public ControllerInterceptor(MessageSender messageSender) {
        this.messageSender = messageSender;
        this.txRecord = new TXRecord();
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("* Arch: " + request.getHeader("h-arch-meta"));

        txRecord.setPath(request.getRequestURI());
        txRecord.setMethod(request.getMethod());
        txRecord.setRequestParams(convertParamsToJson(request.getParameterMap()));
        log.info("Tx: " + txRecord.toString());
        messageSender.sendMessage("DEV.QUEUE.1", txRecord.toString());
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
        if(! txRecord.getState().equals(Literales.KO)){
            txRecord.setState(Literales.OK);
            messageSender.sendMessage("DEV.QUEUE.1", txRecord.toString());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Se ha producido una excepción no controlada: " + e.getMessage());
        txRecord.setState(Literales.KO);
        messageSender.sendMessage("DEV.QUEUE.1", txRecord.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se ha producido una excepción no controlada: " + e.getMessage());
    }
}
