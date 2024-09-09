package com.livraria.livraria.service;

import com.livraria.livraria.model.Livro;
import com.livraria.livraria.model.LivroHistorico;
import com.livraria.livraria.repository.LivroHistoricoRepository;
import com.livraria.livraria.repository.LivroRepository;
import com.livraria.livraria.dto.AutorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroHistoricoRepository livroHistoricoRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String autorServiceUrl = "http://localhost:8081/api/autores";

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Livro salvar(Livro livro) {
        // Verifica se o autor existe no microsserviço
        Long autorId = livro.getAutorId();
        AutorDTO autor = buscarAutorPorId(autorId);  // Busca o autor no microsserviço

        // Salva o histórico se o livro já existe
        if (livro.getId() != null) {
            Optional<Livro> livroExistente = livroRepository.findById(livro.getId());
            livroExistente.ifPresent(livroOriginal -> {
                LivroHistorico historico = new LivroHistorico(livroOriginal);
                livroHistoricoRepository.save(historico);  // Salva no histórico
            });
        }

        // Salva o livro no banco de dados
        return livroRepository.save(livro);
    }

    public void deletarPorId(Long id) {
        livroRepository.deleteById(id);
    }

    public AutorDTO buscarAutorPorId(Long id) {
        return restTemplate.getForObject(autorServiceUrl + "/" + id, AutorDTO.class);
    }
}
