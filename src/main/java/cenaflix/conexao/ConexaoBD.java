package cenaflix.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    
    // ALTERE ESTAS CONFIGURAÇÕES CONFORME SEU BANCO
    private static final String URL = "jdbc:mysql://localhost:3306/cenaflix?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root"; // Seu usuário do MySQL
    private static final String SENHA = "M@ia2505"; // Sua senha do MySQL (deixe vazio se não tiver senha)
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão com banco de dados estabelecida!");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do MySQL não encontrado", e);
        }
    }
}