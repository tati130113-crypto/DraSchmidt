package cenaflix.view;

import cenaflix.dao.PodcastDAO;
import cenaflix.model.Podcast;
import cenaflix.model.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCadastroPodcast extends JFrame {
    
    private JTextField txtId;
    private JTextField txtProdutor;
    private JTextField txtNomeEpisodio;
    private JTextField txtNumeroEpisodio;
    private JTextField txtDuracao;
    private JTextField txtUrl;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnVoltar;
    
    private PodcastDAO podcastDAO;
    private Usuario usuarioLogado;
    private Podcast podcastEmEdicao;
    
    public TelaCadastroPodcast(Usuario usuario) {
        this.usuarioLogado = usuario;
        this.podcastDAO = new PodcastDAO();
        initComponents();
        configurarLayout();
        setLocationRelativeTo(null);
    }
    
    public TelaCadastroPodcast(Usuario usuario, Podcast podcast) {
        this(usuario);
        this.podcastEmEdicao = podcast;
        carregarPodcastParaEdicao();
    }
    
    private void initComponents() {
        setTitle("Cenaflix Audio - Cadastro de Podcast");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setResizable(false);
        
        txtId = new JTextField(20);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        
        txtProdutor = new JTextField(20);
        txtNomeEpisodio = new JTextField(20);
        txtNumeroEpisodio = new JTextField(20);
        txtDuracao = new JTextField(20);
        txtDuracao.setToolTipText("Duração em minutos");
        txtUrl = new JTextField(20);
        
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnVoltar = new JButton("Voltar");
        
        btnSalvar.addActionListener(e -> salvarPodcast());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVoltar.addActionListener(e -> voltar());
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("CADASTRO DE PODCAST", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 102, 204));
        painelPrincipal.add(titulo, BorderLayout.NORTH);
        
        // Painel de campos
        JPanel painelCampos = new JPanel(new GridLayout(7, 2, 10, 10));
        
        painelCampos.add(new JLabel("ID:"));
        painelCampos.add(txtId);
        
        painelCampos.add(new JLabel("Produtor:*"));
        painelCampos.add(txtProdutor);
        
        painelCampos.add(new JLabel("Nome do Episódio:*"));
        painelCampos.add(txtNomeEpisodio);
        
        painelCampos.add(new JLabel("Nº do Episódio:*"));
        painelCampos.add(txtNumeroEpisodio);
        
        painelCampos.add(new JLabel("Duração (minutos):*"));
        painelCampos.add(txtDuracao);
        
        painelCampos.add(new JLabel("URL do Repositório:*"));
        painelCampos.add(txtUrl);
        
        // Informação do usuário logado
        JLabel lblUsuario = new JLabel("Operador: " + usuarioLogado.getNome());
        lblUsuario.setFont(new Font("Arial", Font.ITALIC, 11));
        lblUsuario.setForeground(Color.GRAY);
        
        painelCampos.add(new JLabel(""));
        painelCampos.add(lblUsuario);
        
        painelPrincipal.add(painelCampos, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnVoltar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        estilizarBotoes();
    }
    
    private void estilizarBotoes() {
        Dimension tamanhoBotao = new Dimension(120, 35);
        Font fonteBotao = new Font("Arial", Font.BOLD, 12);
        
        btnSalvar.setPreferredSize(tamanhoBotao);
        btnSalvar.setFont(fonteBotao);
        btnSalvar.setBackground(new Color(0, 153, 76));
        btnSalvar.setForeground(Color.BLACK);
        btnSalvar.setFocusPainted(false);
        
        btnLimpar.setPreferredSize(tamanhoBotao);
        btnLimpar.setFont(fonteBotao);
        btnLimpar.setBackground(new Color(255, 153, 0));
        btnLimpar.setForeground(Color.BLACK);
        btnLimpar.setFocusPainted(false);
        
        btnVoltar.setPreferredSize(tamanhoBotao);
        btnVoltar.setFont(fonteBotao);
        btnVoltar.setBackground(new Color(204, 0, 0));
        btnVoltar.setForeground(Color.BLACK);
        btnVoltar.setFocusPainted(false);
    }
    
    private void carregarPodcastParaEdicao() {
        if (podcastEmEdicao != null) {
            txtId.setText(String.valueOf(podcastEmEdicao.getId()));
            txtProdutor.setText(podcastEmEdicao.getProdutor());
            txtNomeEpisodio.setText(podcastEmEdicao.getNomeEpisodio());
            txtNumeroEpisodio.setText(String.valueOf(podcastEmEdicao.getNumeroEpisodio()));
            txtDuracao.setText(String.valueOf(podcastEmEdicao.getDuracao()));
            txtUrl.setText(podcastEmEdicao.getUrlRepositorio());
            
            btnSalvar.setText("Atualizar");
            setTitle("Cenaflix Audio - Edição de Podcast");
        }
    }
    
    private void salvarPodcast() {
        // Validações
        if (txtProdutor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campo Produtor é obrigatório!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            txtProdutor.requestFocus();
            return;
        }
        
        if (txtNomeEpisodio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campo Nome do Episódio é obrigatório!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            txtNomeEpisodio.requestFocus();
            return;
        }
        
        int numeroEpisodio;
        try {
            numeroEpisodio = Integer.parseInt(txtNumeroEpisodio.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número do episódio inválido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            txtNumeroEpisodio.requestFocus();
            return;
        }
        
        int duracao;
        try {
            duracao = Integer.parseInt(txtDuracao.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Duração inválida!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            txtDuracao.requestFocus();
            return;
        }
        
        if (txtUrl.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campo URL é obrigatório!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            txtUrl.requestFocus();
            return;
        }
        
        // Criar ou atualizar podcast
        Podcast podcast;
        if (podcastEmEdicao == null) {
            podcast = new Podcast();
        } else {
            podcast = podcastEmEdicao;
        }
        
        podcast.setProdutor(txtProdutor.getText().trim());
        podcast.setNomeEpisodio(txtNomeEpisodio.getText().trim());
        podcast.setNumeroEpisodio(numeroEpisodio);
        podcast.setDuracao(duracao);
        podcast.setUrlRepositorio(txtUrl.getText().trim());
        
        try {
            if (podcastEmEdicao == null) {
                podcastDAO.inserir(podcast);
                JOptionPane.showMessageDialog(this, "Podcast cadastrado com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                podcastDAO.atualizar(podcast);
                JOptionPane.showMessageDialog(this, "Podcast atualizado com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            
            int resposta = JOptionPane.showConfirmDialog(this, 
                "Deseja voltar para a listagem?", "Continuar", 
                JOptionPane.YES_NO_OPTION);
            
            if (resposta == JOptionPane.YES_OPTION) {
                abrirListagem();
            } else {
                limparCampos();
                podcastEmEdicao = null;
                btnSalvar.setText("Salvar");
                setTitle("Cenaflix Audio - Cadastro de Podcast");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void limparCampos() {
        txtId.setText("");
        txtProdutor.setText("");
        txtNomeEpisodio.setText("");
        txtNumeroEpisodio.setText("");
        txtDuracao.setText("");
        txtUrl.setText("");
        txtProdutor.requestFocus();
    }
    
    private void abrirListagem() {
        TelaListagemPodcast tela = new TelaListagemPodcast(usuarioLogado);
        tela.setVisible(true);
        dispose();
    }
    
    private void voltar() {
        abrirListagem();
    }
}