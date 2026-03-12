package cenaflix.teste;

import cenaflix.dao.UsuarioDAO;
import cenaflix.model.Usuario;

public class InserirUsuarios {
    public static void main(String[] args) {
        System.out.println("=== CRIANDO USUÁRIOS PADRÃO ===");
        
        UsuarioDAO dao = new UsuarioDAO();
        
        // Verificar se já existem usuários
        if (dao.listarTodos().isEmpty()) {
            // Criar usuários
            Usuario admin = new Usuario("admin", "admin123", "Administrador", "ADMIN");
            Usuario operador = new Usuario("operador", "oper123", "Operador", "OPERADOR");
            Usuario usuario = new Usuario("usuario", "user123", "Usuário", "USUARIO");
            
            // Salvar no banco
            dao.salvar(admin);
            System.out.println("✅ Admin criado: admin / admin123");
            
            dao.salvar(operador);
            System.out.println("✅ Operador criado: operador / oper123");
            
            dao.salvar(usuario);
            System.out.println("✅ Usuário criado: usuario / user123");
            
            System.out.println("✅ TODOS OS USUÁRIOS CRIADOS COM SUCESSO!");
        } else {
            System.out.println("⚠️ Usuários já existem no banco de dados!");
        }
        
        dao.close();
    }
}