package com.livraria.livraria.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;	
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    private Double preco;
    private String isbn;

    // Em vez de referenciar o objeto Autor diretamente, armazenamos apenas o ID do autor
    private Long autorId;
}
