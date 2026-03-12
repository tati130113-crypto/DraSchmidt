package cenaflix.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String login;
    
    @Column(nullable = false, length = 100)
    private String senha;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, length = 20)
    private String tipo; // ADMIN, OPERADOR, USUARIO
    
    public Usuario() {}
    
    public Usuario(String login, String senha, String nome, String tipo) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.tipo = tipo;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getTipoDescricao() {
        switch(tipo.toUpperCase()) {
            case "ADMIN": return "Administrador";
            case "OPERADOR": return "Operador";
            case "USUARIO": return "Usuário";
            default: return tipo;
        }
    }
    
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(tipo);
    }
    
    public boolean isOperador() {
        return "OPERADOR".equalsIgnoreCase(tipo);
    }
    
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", login=" + login + ", nome=" + nome + ", tipo=" + tipo + '}';
    }
}