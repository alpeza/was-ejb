package com.miapi.monitor.controller;

import com.miapi.monitor.service.CryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.miapi.monitor.entities.CryptoEntity;

@RestController
@RequestMapping("/crypto")
@Slf4j
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping
    public ResponseEntity<List<CryptoEntity>> getAllCryptos() {
        log.info("Listando todas las cryptos");
        List<CryptoEntity> cryptos = cryptoService.getAllCryptos();
        return new ResponseEntity<>(cryptos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CryptoEntity> getCryptoById(@PathVariable("id") Long id) {
        Optional<CryptoEntity> crypto = cryptoService.getCryptoById(id);
        return crypto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CryptoEntity> createCrypto(@RequestBody CryptoEntity cryptoEntity) {
        CryptoEntity createdCrypto = cryptoService.createOrUpdateCrypto(cryptoEntity);
        return new ResponseEntity<>(createdCrypto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CryptoEntity> updateCrypto(@PathVariable("id") Long id, @RequestBody CryptoEntity cryptoEntity) {
        Optional<CryptoEntity> existingCrypto = cryptoService.getCryptoById(id);
        if (existingCrypto.isPresent()) {
            cryptoEntity.setCryptoId(id);
            CryptoEntity updatedCrypto = cryptoService.createOrUpdateCrypto(cryptoEntity);
            return new ResponseEntity<>(updatedCrypto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrypto(@PathVariable("id") Long id) {
        Optional<CryptoEntity> crypto = cryptoService.getCryptoById(id);
        if (crypto.isPresent()) {
            cryptoService.deleteCrypto(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
