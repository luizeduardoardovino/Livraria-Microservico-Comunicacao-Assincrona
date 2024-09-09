package com.livraria.livraria.consumer;

import com.livraria.livraria.model.Autor;
import com.livraria.livraria.repository.AutorRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutorMessageConsumer {

    @Autowired
    private AutorRepository autorRepository;

    // Escuta a fila "autorQueue" e converte automaticamente a mensagem recebida para o objeto Autor
    @RabbitListener(queues = "autorQueue")
    public void receiveMessage(Autor autor) {
        // Salva o autor recebido no banco de dados local
        autorRepository.save(autor);
        System.out.println("Autor recebido e salvo: " + autor.getNome());
    }
}
