package cenaflix.view;

import cenaflix.model.Usuario;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    
    private Usuario usuarioLogado;
    private JButton btnCadastrarPodcast;
    private JButton btnListarPodcasts;
    private JButton btnSair;
    
    public TelaPrincipal(Usuario usuario) {
        this.usuarioLogado = usuario;
        initComponents();
        configurarPermissoes();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Cenaflix Audio - Painel Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setResizable(false);
        
        setLayout(new BorderLayout(10, 10));
        
        // Painel de boas-vindas
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel lblBoasVindas = new JLabel("Bem-vindo, " + usuarioLogado.getNome() + "!");
        lblBoasVindas.setFont(new Font("Arial", Font.BOLD, 18));
        
        JLabel lblPermissao = new JLabel("Permissão: " + usuarioLogado.getTipoDescricao());
        lblPermissao.setFont(new Font("Arial", Font.ITALIC, 14));
        lblPermissao.setForeground(new Color(0, 102, 204));
        
        painelTopo.add(lblBoasVindas, BorderLayout.NORTH);
        painelTopo.add(lblPermissao, BorderLayout.SOUTH);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 15, 15));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        
        btnCadastrarPodcast = new JButton("🎙️ Cadastrar Podcast");
        btnListarPodcasts = new JButton("📋 Listar Podcasts");
        btnSair = new JButton("🚪 Sair");
        
        // Estilo dos botões
        Dimension tamanhoBotao = new Dimension(250, 50);
        Font fonteBotao = new Font("Arial", Font.BOLD, 14);
        
        btnCadastrarPodcast.setPreferredSize(tamanhoBotao);
        btnCadastrarPodcast.setFont(fonteBotao);
        btnCadastrarPodcast.setBackground(new Color(0, 153, 76));
        btnCadastrarPodcast.setForeground(Color.BLACK);
        btnCadastrarPodcast.setFocusPainted(false);
        
        btnListarPodcasts.setPreferredSize(tamanhoBotao);
        btnListarPodcasts.setFont(fonteBotao);
        btnListarPodcasts.setBackground(new Color(51, 153, 255));
        btnListarPodcasts.setForeground(Color.BLACK);
        btnListarPodcasts.setFocusPainted(false);
        
        btnSair.setPreferredSize(tamanhoBotao);
        btnSair.setFont(fonteBotao);
        btnSair.setBackground(new Color(204, 0, 0));
        btnSair.setForeground(Color.BLACK);
        btnSair.setFocusPainted(false);
        
        painelBotoes.add(btnCadastrarPodcast);
        painelBotoes.add(btnListarPodcasts);
        painelBotoes.add(btnSair);
        
        add(painelTopo, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);
        
        // Eventos
        btnCadastrarPodcast.addActionListener(e -> abrirCadastroPodcast());
        btnListarPodcasts.addActionListener(e -> abrirListagemPodcast());
        btnSair.addActionListener(e -> sair());
    }
    
    private void configurarPermissoes() {
        switch(usuarioLogado.getTipo().toUpperCase()) {
            case "ADMIN":
                // Tudo habilitado
                break;
            case "OPERADOR":
                // Tudo exceto exclusão (será controlado na listagem)
                break;
            case "USUARIO":
                btnCadastrarPodcast.setEnabled(false);
                btnCadastrarPodcast.setToolTipText("Acesso restrito a Administradores e Operadores");
                break;
        }
    }
    
    private void abrirCadastroPodcast() {
        TelaCadastroPodcast tela = new TelaCadastroPodcast(usuarioLogado);
        tela.setVisible(true);
        dispose();
    }
    
    private void abrirListagemPodcast() {
        TelaListagemPodcast tela = new TelaListagemPodcast(usuarioLogado);
        tela.setVisible(true);
        dispose();
    }
    
    private void sair() {
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente sair?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            new TelaLogin().setVisible(true);
            dispose();
        }
    }
}