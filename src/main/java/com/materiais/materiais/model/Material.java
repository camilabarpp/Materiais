package com.materiais.materiais.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "Materiais")
public class Material implements Serializable {
    @Id
    private String id;
    private String nome;
    private String marca;
    private Integer quantidade;

    public Material(String id, String nome, String marca, Integer quantidade) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.quantidade = quantidade;
    }
}
