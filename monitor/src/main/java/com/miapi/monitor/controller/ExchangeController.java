package com.miapi.monitor.controller;

import com.miapi.monitor.entities.OrderEntity;
import com.miapi.monitor.entities.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class ExchangeController {

    @PostMapping("/buy")
    public ResponseEntity<OrderEntity> buy(
            @RequestParam String fromid,
            @RequestParam String toid,
            @RequestParam double amount,
            @RequestParam String currency) {

        OrderEntity orderEntity = new OrderEntity(fromid, toid, amount, currency);
        log.info("Orden de compra: " + orderEntity.toString());
        return new ResponseEntity<>(orderEntity, HttpStatus.CREATED);
    }

}
