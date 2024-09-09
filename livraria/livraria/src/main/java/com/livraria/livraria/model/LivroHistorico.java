package com.livraria.livraria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Double preco;
    private String isbn;
    private Long autorId;  // Em vez de objeto Autor, armazenamos apenas o autorId
    private LocalDateTime dataModificacao;

    public LivroHistorico(Livro livro) {
        this.titulo = livro.getTitulo();
        this.preco = livro.getPreco();
        this.isbn = livro.getIsbn();
        this.autorId = livro.getAutorId();  // Mantemos apenas o ID do autor no histórico
        this.dataModificacao = LocalDateTime.now();  // Grava a data da modificação
    }
}
