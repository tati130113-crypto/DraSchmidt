package cenaflix.teste;

import cenaflix.util.JPAUtil;
import javax.persistence.EntityManager;

public class TesteJPA {
    public static void main(String[] args) {
        System.out.println("=== TESTANDO CONEXÃO JPA ===");
        try {
            EntityManager em = JPAUtil.getEntityManager();
            System.out.println("✅ JPA conectou ao banco com sucesso!");
            System.out.println("✅ EntityManager criado: " + em);
            em.close();
            JPAUtil.close();
            System.out.println("✅ Teste concluído!");
        } catch (Exception e) {
            System.err.println("❌ ERRO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}