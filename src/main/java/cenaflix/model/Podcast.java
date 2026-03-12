package cenaflix.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "podcasts")
public class Podcast {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String produtor;
    
    @Column(name = "nome_episodio", nullable = false, length = 200)
    private String nomeEpisodio;
    
    @Column(name = "numero_episodio", nullable = false)
    private Integer numeroEpisodio;
    
    @Column(nullable = false)
    private Integer duracao; // em minutos
    
    @Column(name = "url_repositorio", nullable = false, length = 500)
    private String urlRepositorio;
    
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    
    public Podcast() {
        this.dataCadastro = LocalDate.now();
    }
    
    public Podcast(String produtor, String nomeEpisodio, Integer numeroEpisodio, 
                   Integer duracao, String urlRepositorio) {
        this.produtor = produtor;
        this.nomeEpisodio = nomeEpisodio;
        this.numeroEpisodio = numeroEpisodio;
        this.duracao = duracao;
        this.urlRepositorio = urlRepositorio;
        this.dataCadastro = LocalDate.now();
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getProdutor() { return produtor; }
    public void setProdutor(String produtor) { this.produtor = produtor; }
    
    public String getNomeEpisodio() { return nomeEpisodio; }
    public void setNomeEpisodio(String nomeEpisodio) { this.nomeEpisodio = nomeEpisodio; }
    
    public Integer getNumeroEpisodio() { return numeroEpisodio; }
    public void setNumeroEpisodio(Integer numeroEpisodio) { this.numeroEpisodio = numeroEpisodio; }
    
    public Integer getDuracao() { return duracao; }
    public void setDuracao(Integer duracao) { this.duracao = duracao; }
    
    public String getUrlRepositorio() { return urlRepositorio; }
    public void setUrlRepositorio(String urlRepositorio) { this.urlRepositorio = urlRepositorio; }
    
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    
    public String getDuracaoFormatada() {
        int horas = duracao / 60;
        int minutos = duracao % 60;
        if (horas > 0) {
            return String.format("%dh %02dmin", horas, minutos);
        } else {
            return String.format("%d min", minutos);
        }
    }
    
    public String getDataCadastroFormatada() {
        if (dataCadastro != null) {
            return dataCadastro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "";
    }
    
    @Override
    public String toString() {
        return "Podcast{" + "id=" + id + ", produtor=" + produtor + 
               ", episodio=" + numeroEpisodio + " - " + nomeEpisodio + '}';
    }
}