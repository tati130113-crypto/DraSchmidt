package cenaflix.dao;

import cenaflix.conexao.ConexaoBD;
import cenaflix.model.Video;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade Video.
 */
public class VideoDAO {
    
    // ==================== MÉTODOS CRUD ====================
    
    /**
     * Insere um novo vídeo no banco de dados.
     */
    public boolean inserir(Video video) {
        String sql = "INSERT INTO videos (nome, data_lancamento, categoria) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            System.out.println("Inserindo vídeo: " + video.getNome());
            
            stmt.setString(1, video.getNome());
            stmt.setDate(2, Date.valueOf(video.getDataLancamento()));
            stmt.setString(3, video.getCategoria());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir vídeo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Atualiza um vídeo existente.
     */
    public boolean atualizar(Video video) {
        String sql = "UPDATE videos SET nome = ?, data_lancamento = ?, categoria = ? WHERE id = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            System.out.println("Atualizando vídeo ID: " + video.getId());
            
            stmt.setString(1, video.getNome());
            stmt.setDate(2, Date.valueOf(video.getDataLancamento()));
            stmt.setString(3, video.getCategoria());
            stmt.setInt(4, video.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar vídeo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Exclui um vídeo pelo ID.
     */
    public boolean excluir(int id) {
        String sql = "DELETE FROM videos WHERE id = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            System.out.println("Excluindo vídeo ID: " + id);
            
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir vídeo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // ==================== MÉTODOS DE CONSULTA ====================
    
    /**
     * Lista todos os vídeos.
     */
    public List<Video> listarTodos() {
        List<Video> videos = new ArrayList<>();
        String sql = "SELECT * FROM videos ORDER BY id";
        
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Video video = new Video();
                video.setId(rs.getInt("id"));
                video.setNome(rs.getString("nome"));
                video.setDataLancamento(rs.getDate("data_lancamento").toLocalDate());
                video.setCategoria(rs.getString("categoria"));
                videos.add(video);
            }
            
            System.out.println("Total de vídeos listados: " + videos.size());
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar vídeos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return videos;
    }
    
    /**
     * Busca vídeos por categoria (filtro dinâmico).
     */
    public List<Video> buscarPorCategoria(String categoria) {
        List<Video> videos = new ArrayList<>();
        String sql = "SELECT * FROM videos WHERE LOWER(categoria) LIKE LOWER(?) ORDER BY nome";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + categoria + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Video video = new Video();
                video.setId(rs.getInt("id"));
                video.setNome(rs.getString("nome"));
                video.setDataLancamento(rs.getDate("data_lancamento").toLocalDate());
                video.setCategoria(rs.getString("categoria"));
                videos.add(video);
            }
            
            System.out.println("Vídeos encontrados na categoria '" + categoria + "': " + videos.size());
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por categoria: " + e.getMessage());
            e.printStackTrace();
        }
        
        return videos;
    }
    
    /**
     * Busca vídeos por nome (filtro dinâmico).
     */
    public List<Video> buscarPorNome(String nome) {
        List<Video> videos = new ArrayList<>();
        String sql = "SELECT * FROM videos WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Video video = new Video();
                video.setId(rs.getInt("id"));
                video.setNome(rs.getString("nome"));
                video.setDataLancamento(rs.getDate("data_lancamento").toLocalDate());
                video.setCategoria(rs.getString("categoria"));
                videos.add(video);
            }
            
            System.out.println("Vídeos encontrados com nome '" + nome + "': " + videos.size());
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por nome: " + e.getMessage());
            e.printStackTrace();
        }
        
        return videos;
    }
    
    /**
     * Busca um vídeo específico pelo ID.
     */
    public Video buscarPorId(int id) {
        String sql = "SELECT * FROM videos WHERE id = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Video video = new Video();
                video.setId(rs.getInt("id"));
                video.setNome(rs.getString("nome"));
                video.setDataLancamento(rs.getDate("data_lancamento").toLocalDate());
                video.setCategoria(rs.getString("categoria"));
                return video;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vídeo por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    /**
     * Conta o total de vídeos cadastrados.
     */
    public int contarVideos() {
        String sql = "SELECT COUNT(*) FROM videos";
        
        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao contar vídeos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Verifica se um vídeo existe pelo ID.
     */
    public boolean existe(int id) {
        String sql = "SELECT COUNT(*) FROM videos WHERE id = ?";
        
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}