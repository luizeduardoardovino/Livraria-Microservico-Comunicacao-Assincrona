package com.autorservice.autorservice.service;


import com.autorservice.autorservice.model.Autor;
import com.autorservice.autorservice.repository.AutorRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;  // Injetando o RabbitTemplate para enviar mensagens


    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    public Autor salvar(Autor autor) {
        Autor autorSalvo = autorRepository.save(autor);
        
        // Envia o autor criado para o RabbitMQ
        rabbitTemplate.convertAndSend("autoresExchange", "", autorSalvo);
        
        return autorSalvo;
    }

    public void deletarPorId(Long id) {
        autorRepository.deleteById(id);
    }
}
