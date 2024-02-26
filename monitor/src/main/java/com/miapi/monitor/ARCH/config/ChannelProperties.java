package com.miapi.monitor.ARCH.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Data
@ConfigurationProperties(prefix = "arch")
@Slf4j
public class ChannelProperties {

    private List<Canal> canales;

    // Getter y Setter
    public List<Canal> getCanales() {
        return canales;
    }

    public String getColaMQ(String id) {
        List<Canal> canales = this.getCanales();
        for (Canal canal : canales) {
            if (canal.getId().equals(id)) {
                return canal.getMq();
            }
        }
        throw new RuntimeException("ID de canal no encontrado: " + id);
    }

    // Clase anidada para representar cada elemento de la lista
    public static class Canal {
        private String id;
        private String mq;

        // Getters y Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMq() {
            return mq;
        }

        public void setMq(String mq) {
            this.mq = mq;
        }
    }
}
