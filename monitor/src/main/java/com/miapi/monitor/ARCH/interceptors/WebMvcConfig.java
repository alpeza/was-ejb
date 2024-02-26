package com.miapi.monitor.ARCH.interceptors;

import com.miapi.monitor.ARCH.components.MessageSender;
import com.miapi.monitor.ARCH.config.ChannelProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private final MessageSender messageSender;

    @Autowired
    private final ChannelProperties channelProperties;

    public WebMvcConfig(MessageSender messageSender,ChannelProperties channelProperties) {

        this.messageSender = messageSender;
        this.channelProperties = channelProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ControllerInterceptor(messageSender, channelProperties));
    }
}
