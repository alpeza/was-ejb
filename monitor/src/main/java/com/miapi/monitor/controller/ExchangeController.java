package com.miapi.monitor.controller;

import com.miapi.monitor.entities.OrderEntity;
import com.miapi.monitor.entities.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class ExchangeController {

    @PostMapping("/buy")
    public ResponseEntity<OrderEntity> buy(@RequestBody OrderEntity orderEntity) {
        log.info("Orden de compra: " + orderEntity.toString());
        return new ResponseEntity<>(orderEntity, HttpStatus.CREATED);
    }

}
