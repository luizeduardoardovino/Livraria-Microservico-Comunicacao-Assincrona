package com.livraria.livraria.controller;

import com.livraria.livraria.dto.AutorDTO;  // Para lidar com os dados do autor vindos do microsserviço
import com.livraria.livraria.model.Livro;
import com.livraria.livraria.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> listarLivros() {
        return livroService.listarTodos();
    }

    @GetMapping("/titulo")
    public List<Livro> buscarPorTitulo(@RequestParam String titulo) {
        return livroService.buscarPorTitulo(titulo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivro(@PathVariable Long id) {
        return livroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@RequestBody Livro livro) {
        try {
            Livro livroSalvo = livroService.salvar(livro);  // Salva o livro
            return ResponseEntity.ok(livroSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();  // Trata erros (ex: se o autor não for encontrado)
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        return livroService.buscarPorId(id)
                .map(livro -> {
                    livro.setTitulo(livroAtualizado.getTitulo());
                    livro.setPreco(livroAtualizado.getPreco());
                    livro.setIsbn(livroAtualizado.getIsbn());

                    // Agora só armazenamos o ID do autor
                    livro.setAutorId(livroAtualizado.getAutorId());
                    Livro livroSalvo = livroService.salvar(livro);
                    return ResponseEntity.ok(livroSalvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para testar a busca de autores diretamente do microsserviço
    @GetMapping("/autor/{id}")
    public ResponseEntity<AutorDTO> buscarAutor(@PathVariable Long id) {
        AutorDTO autor = livroService.buscarAutorPorId(id);  // Busca o autor via microsserviço
        return ResponseEntity.ok(autor);
    }
}
