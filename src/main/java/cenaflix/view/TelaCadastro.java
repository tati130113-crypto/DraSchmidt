package cenaflix.view;

import cenaflix.dao.VideoDAO;
import cenaflix.model.Video;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaCadastro extends JFrame {
    
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtDataLancamento;
    private JComboBox<String> comboCategoria;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnSair;
    
    private VideoDAO videoDAO;
    private Video videoEmEdicao;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public TelaCadastro() {
        videoDAO = new VideoDAO();
        initComponents();
        configurarLayout();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        setTitle("Cenaflix - Cadastro de Vídeos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setResizable(false);
        
        txtId = new JTextField(20);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        
        txtNome = new JTextField(20);
        txtDataLancamento = new JTextField(20);
        txtDataLancamento.setToolTipText("Formato: dd/MM/yyyy");
        
        String[] categorias = {"Ação", "Comédia", "Drama", "Ficção Científica", "Terror", "Romance", "Documentário"};
        comboCategoria = new JComboBox<>(categorias);
        
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");
        
        btnSalvar.addActionListener(e -> salvarVideo());
        btnLimpar.addActionListener(e -> limparCampos());
        btnSair.addActionListener(e -> sair());
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("CADASTRO DE VÍDEOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 102, 204));
        painelPrincipal.add(titulo, BorderLayout.NORTH);
        
        JPanel painelCampos = new JPanel(new GridLayout(4, 2, 10, 15));
        
        painelCampos.add(new JLabel("ID:"));
        painelCampos.add(txtId);
        painelCampos.add(new JLabel("Nome do Filme:*"));
        painelCampos.add(txtNome);
        painelCampos.add(new JLabel("Data de Lançamento:*"));
        painelCampos.add(txtDataLancamento);
        painelCampos.add(new JLabel("Categoria:*"));
        painelCampos.add(comboCategoria);
        
        painelPrincipal.add(painelCampos, BorderLayout.CENTER);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        add(painelPrincipal);
        
        estilizarBotoes();
    }
    
    private void estilizarBotoes() {
        Dimension tamanhoBotao = new Dimension(100, 35);
        Font fonteBotao = new Font("Arial", Font.BOLD, 14);
        Color textoPreto = Color.BLACK;
        
        btnSalvar.setPreferredSize(tamanhoBotao);
        btnSalvar.setFont(fonteBotao);
        btnSalvar.setForeground(textoPreto);
        btnSalvar.setBackground(new Color(0, 153, 76));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(BorderFactory.createRaisedBevelBorder());
        
        btnLimpar.setPreferredSize(tamanhoBotao);
        btnLimpar.setFont(fonteBotao);
        btnLimpar.setForeground(textoPreto);
        btnLimpar.setBackground(new Color(255, 153, 0));
        btnLimpar.setFocusPainted(false);
        btnLimpar.setBorder(BorderFactory.createRaisedBevelBorder());
        
        btnSair.setPreferredSize(tamanhoBotao);
        btnSair.setFont(fonteBotao);
        btnSair.setForeground(textoPreto);
        btnSair.setBackground(new Color(204, 0, 0));
        btnSair.setFocusPainted(false);
        btnSair.setBorder(BorderFactory.createRaisedBevelBorder());
    }
    
    private void salvarVideo() {
        // Validar campos
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtNome.requestFocus();
            return;
        }
        
        if (txtDataLancamento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Data é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtDataLancamento.requestFocus();
            return;
        }
        
        LocalDate dataLancamento;
        try {
            dataLancamento = LocalDate.parse(txtDataLancamento.getText().trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida! Use dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
            txtDataLancamento.requestFocus();
            return;
        }
        
        Video video;
        if (videoEmEdicao == null) {
            video = new Video();
        } else {
            video = videoEmEdicao;
        }
        
        video.setNome(txtNome.getText().trim());
        video.setDataLancamento(dataLancamento);
        video.setCategoria(comboCategoria.getSelectedItem().toString());
        
        boolean sucesso;
        if (videoEmEdicao == null) {
            sucesso = videoDAO.inserir(video);
        } else {
            sucesso = videoDAO.atualizar(video);
        }
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this, 
                videoEmEdicao == null ? "Cadastrado com sucesso!" : "Atualizado com sucesso!", 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            int resposta = JOptionPane.showConfirmDialog(this,
                "Voltar para listagem?", "Continuar", JOptionPane.YES_NO_OPTION);
            
            if (resposta == JOptionPane.YES_OPTION) {
                abrirTelaListagem();
            } else {
                limparCampos();
                videoEmEdicao = null;
                btnSalvar.setText("Salvar");
                setTitle("Cenaflix - Cadastro de Vídeos");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtDataLancamento.setText("");
        comboCategoria.setSelectedIndex(0);
        txtNome.requestFocus();
    }
    
    private void sair() {
        if (JOptionPane.showConfirmDialog(this, "Deseja sair?", "Confirmar", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    public void carregarVideoParaEdicao(Video video) {
        this.videoEmEdicao = video;
        txtId.setText(String.valueOf(video.getId()));
        txtNome.setText(video.getNome());
        txtDataLancamento.setText(video.getDataLancamento().format(DATE_FORMATTER));
        comboCategoria.setSelectedItem(video.getCategoria());
        btnSalvar.setText("Atualizar");
        setTitle("Cenaflix - Edição de Vídeo");
    }
    
    private void abrirTelaListagem() {
        new TelaListagem().setVisible(true);
        dispose();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(TelaCadastro::new);
    }
}