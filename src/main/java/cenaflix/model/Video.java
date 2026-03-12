package cenaflix.model;

import java.time.LocalDate;

public class Video {
    private int id;
    private String nome;
    private LocalDate dataLancamento;
    private String categoria;
    
    // Construtor vazio
    public Video() {}
    
    // Construtor com parâmetros
    public Video(String nome, LocalDate dataLancamento, String categoria) {
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.categoria = categoria;
    }
    
    // Construtor completo
    public Video(int id, String nome, LocalDate dataLancamento, String categoria) {
        this.id = id;
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.categoria = categoria;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public LocalDate getDataLancamento() {
        return dataLancamento;
    }
    
    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}