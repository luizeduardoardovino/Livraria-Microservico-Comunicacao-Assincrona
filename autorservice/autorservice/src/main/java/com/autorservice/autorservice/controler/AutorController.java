package com.autorservice.autorservice.controler;


import com.autorservice.autorservice.model.Autor;
import com.autorservice.autorservice.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public List<Autor> listarTodos() {
        return autorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
        Optional<Autor> autor = autorService.buscarPorId(id);
        return autor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Autor salvar(@RequestBody Autor autor) {
        return autorService.salvar(autor);
    }
    
    @PutMapping("/{id}")  // Método para atualizar um autor existente
    public ResponseEntity<Autor> atualizar(@PathVariable Long id, @RequestBody Autor autorAtualizado) {
        Optional<Autor> autorExistente = autorService.buscarPorId(id);
        if (autorExistente.isPresent()) {
            Autor autor = autorExistente.get();
            autor.setNome(autorAtualizado.getNome());  // Atualizando o nome
            Autor autorSalvo = autorService.salvar(autor);
            return ResponseEntity.ok(autorSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        autorService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
