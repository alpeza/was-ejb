package com.miapi.monitor.service;

import com.miapi.monitor.components.MessageSender;
import com.miapi.monitor.repositories.CryptoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miapi.monitor.entities.CryptoEntity;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CryptoService {

    private final MessageSender messageSender;

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    public CryptoService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public List<CryptoEntity> getAllCryptos() {
        log.info("Registrando transacción");
        messageSender.sendMessage("DEV.QUEUE.1", "holi");
        return cryptoRepository.findAll();
    }

    public Optional<CryptoEntity> getCryptoById(Long id) {
        return cryptoRepository.findById(id);
    }

    public CryptoEntity createOrUpdateCrypto(CryptoEntity cryptoEntity) {
        return cryptoRepository.save(cryptoEntity);
    }

    public void deleteCrypto(Long id) {
        cryptoRepository.deleteById(id);
    }
}
