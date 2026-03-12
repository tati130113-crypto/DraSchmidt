package cenaflix.view;

import cenaflix.dao.UsuarioDAO;
import cenaflix.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaLogin extends JFrame {
    
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnCancelar;
    
    public TelaLogin() {
        initComponents();
        configurarLayout();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        setTitle("Cenaflix Audio - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        
        txtUsuario = new JTextField(15);
        txtSenha = new JPasswordField(15);
        
        btnLogin = new JButton("Entrar");
        btnCancelar = new JButton("Cancelar");
        
        // Eventos
        btnLogin.addActionListener(e -> fazerLogin());
        btnCancelar.addActionListener(e -> System.exit(0));
        
        // Enter nos campos
        txtUsuario.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtSenha.requestFocus();
                }
            }
        });
        
        txtSenha.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fazerLogin();
                }
            }
        });
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Painel principal
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel titulo = new JLabel("CENAFLIX AUDIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 102, 204));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painel.add(titulo, gbc);
        
        // Subtítulo
        JLabel subtitulo = new JLabel("Sistema de Gestão de Podcasts", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 12));
        
        gbc.gridy = 1;
        painel.add(subtitulo, gbc);
        
        // Espaço
        gbc.gridy = 2;
        painel.add(Box.createVerticalStrut(10), gbc);
        
        // Usuário
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.gridx = 0;
        painel.add(new JLabel("Usuário:"), gbc);
        
        gbc.gridx = 1;
        painel.add(txtUsuario, gbc);
        
        // Senha
        gbc.gridy = 4;
        gbc.gridx = 0;
        painel.add(new JLabel("Senha:"), gbc);
        
        gbc.gridx = 1;
        painel.add(txtSenha, gbc);
        
        // Botões
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnLogin);
        painelBotoes.add(btnCancelar);
        
        painel.add(painelBotoes, gbc);
        
        add(painel, BorderLayout.CENTER);
        
        // Estilo dos botões
        btnLogin.setBackground(new Color(0, 153, 76));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFocusPainted(false);
        
        btnCancelar.setBackground(new Color(204, 0, 0));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
    }
    
    private void fazerLogin() {
        String login = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();
        
        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Preencha usuário e senha!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.autenticar(login, senha);
        dao.close();
        
        if (usuario != null) {
            String mensagem = String.format("Olá %s, sua permissão é de %s. Seja bem-vindo!",
                usuario.getNome(), usuario.getTipoDescricao());
            
            JOptionPane.showMessageDialog(this, mensagem, "Login realizado", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Abrir tela principal
            new TelaPrincipal(usuario).setVisible(true);
            dispose();
            
        } else {
            JOptionPane.showMessageDialog(this, 
                "Usuário ou senha inválidos!", 
                "Erro de autenticação", JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
            txtUsuario.requestFocus();
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(TelaLogin::new);
    }
}