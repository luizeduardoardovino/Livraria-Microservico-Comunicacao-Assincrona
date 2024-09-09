package com.livraria.livraria.event;

import com.livraria.livraria.model.Autor;
import com.livraria.livraria.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutorEventListener {

    @Autowired
    private AutorRepository autorRepository;

    // Este método será chamado quando uma nova mensagem for recebida
    public void handleAutorMessage(Autor autor) {
        // Salva o autor no banco de dados local
        autorRepository.save(autor);
        System.out.println("Novo autor recebido e salvo: " + autor.getNome());
    }
}
