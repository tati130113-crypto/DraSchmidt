package cenaflix.view;

import cenaflix.dao.PodcastDAO;
import cenaflix.model.Podcast;
import cenaflix.model.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagemPodcast extends JFrame {
    
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtFiltro;
    private JButton btnFiltrar;
    private JButton btnLimparFiltro;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;
    private JButton btnVoltar;
    
    private PodcastDAO podcastDAO;
    private Usuario usuarioLogado;
    
    public TelaListagemPodcast(Usuario usuario) {
        this.usuarioLogado = usuario;
        this.podcastDAO = new PodcastDAO();
        initComponents();
        configurarLayout();
        carregarDados();
        configurarPermissoes();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Cenaflix Audio - Listagem de Podcasts");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        
        // Modelo da tabela
        modeloTabela = new DefaultTableModel(
            new Object[]{"ID", "Produtor", "Episódio", "Nº", "Duração", "URL"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);
        
        // Configurar largura das colunas
        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(250);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(250);
        
        // Componentes de filtro
        txtFiltro = new JTextField(20);
        txtFiltro.setToolTipText("Digite o nome do produtor para filtrar...");
        
        btnFiltrar = new JButton("Filtrar");
        btnLimparFiltro = new JButton("Limpar");
        btnNovo = new JButton("Novo Podcast");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar");
        btnVoltar = new JButton("Voltar");
        
        // Eventos
        btnFiltrar.addActionListener(e -> filtrar());
        btnLimparFiltro.addActionListener(e -> limparFiltro());
        btnNovo.addActionListener(e -> abrirCadastro());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnAtualizar.addActionListener(e -> carregarDados());
        btnVoltar.addActionListener(e -> voltar());
        
        // Duplo clique na tabela
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editar();
                }
            }
        });
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("LISTAGEM DE PODCASTS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 102, 204));
        painelPrincipal.add(titulo, BorderLayout.NORTH);
        
        // Painel de filtro
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.setBorder(BorderFactory.createTitledBorder("Filtro por Produtor"));
        
        painelFiltro.add(new JLabel("Produtor:"));
        painelFiltro.add(txtFiltro);
        painelFiltro.add(btnFiltrar);
        painelFiltro.add(btnLimparFiltro);
        
        // Tabela com scroll
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Podcasts Cadastrados"));
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVoltar);
        
        // Informação do usuário
        JLabel lblUsuario = new JLabel("Usuário: " + usuarioLogado.getNome() + 
            " (" + usuarioLogado.getTipoDescricao() + ")");
        lblUsuario.setFont(new Font("Arial", Font.ITALIC, 11));
        lblUsuario.setForeground(Color.GRAY);
        
        JPanel painelRodape = new JPanel(new BorderLayout());
        painelRodape.add(lblUsuario, BorderLayout.WEST);
        painelRodape.add(painelBotoes, BorderLayout.CENTER);
        
        painelPrincipal.add(painelFiltro, BorderLayout.NORTH);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        estilizarBotoes();
    }
    
    private void estilizarBotoes() {
        Dimension tamanhoBotao = new Dimension(120, 35);
        Font fonteBotao = new Font("Arial", Font.BOLD, 12);
        
        JButton[] botoes = {btnFiltrar, btnLimparFiltro, btnNovo, btnEditar, 
                            btnExcluir, btnAtualizar, btnVoltar};
        
        for (JButton botao : botoes) {
            botao.setPreferredSize(tamanhoBotao);
            botao.setFont(fonteBotao);
            botao.setForeground(Color.BLACK);
            botao.setFocusPainted(false);
        }
        
        btnNovo.setBackground(new Color(0, 153, 76));
        btnEditar.setBackground(new Color(255, 153, 0));
        btnExcluir.setBackground(new Color(204, 0, 0));
        btnFiltrar.setBackground(new Color(51, 153, 255));
        btnLimparFiltro.setBackground(Color.LIGHT_GRAY);
        btnAtualizar.setBackground(new Color(102, 102, 255));
        btnVoltar.setBackground(new Color(255, 102, 102));
    }
    
    private void configurarPermissoes() {
        if (!usuarioLogado.isAdmin()) {
            // Usuário não-admin não pode excluir
            btnExcluir.setEnabled(false);
            btnExcluir.setToolTipText("Apenas administradores podem excluir");
            
            if (usuarioLogado.getTipo().equalsIgnoreCase("USUARIO")) {
                btnNovo.setEnabled(false);
                btnNovo.setToolTipText("Apenas administradores e operadores podem cadastrar");
            }
        }
    }
    
    private void carregarDados() {
        modeloTabela.setRowCount(0);
        List<Podcast> podcasts = podcastDAO.listarTodos();
        
        for (Podcast p : podcasts) {
            Object[] linha = {
                p.getId(),
                p.getProdutor(),
                p.getNomeEpisodio(),
                p.getNumeroEpisodio(),
                p.getDuracaoFormatada(),
                p.getUrlRepositorio()
            };
            modeloTabela.addRow(linha);
        }
        
        JOptionPane.showMessageDialog(this, 
            "Total de podcasts: " + podcasts.size(), 
            "Informação", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void filtrar() {
        String filtro = txtFiltro.getText().trim();
        
        if (filtro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome de produtor para filtrar!",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modeloTabela.setRowCount(0);
        List<Podcast> podcasts = podcastDAO.filtrarPorProdutor(filtro);
        
        for (Podcast p : podcasts) {
            Object[] linha = {
                p.getId(),
                p.getProdutor(),
                p.getNomeEpisodio(),
                p.getNumeroEpisodio(),
                p.getDuracaoFormatada(),
                p.getUrlRepositorio()
            };
            modeloTabela.addRow(linha);
        }
        
        if (podcasts.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Nenhum podcast encontrado para o produtor: " + filtro,
                "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void limparFiltro() {
        txtFiltro.setText("");
        carregarDados();
    }
    
    private void abrirCadastro() {
        new TelaCadastroPodcast(usuarioLogado).setVisible(true);
        dispose();
    }
    
    private void editar() {
        int linha = tabela.getSelectedRow();
        
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um podcast para editar!",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long id = (Long) modeloTabela.getValueAt(linha, 0);
        Podcast podcast = podcastDAO.buscarPorId(id);
        
        if (podcast != null) {
            new TelaCadastroPodcast(usuarioLogado, podcast).setVisible(true);
            dispose();
        }
    }
    
    private void excluir() {
        if (!usuarioLogado.isAdmin()) {
            JOptionPane.showMessageDialog(this, 
                "Você não tem permissão para excluir podcasts!",
                "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int linha = tabela.getSelectedRow();
        
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um podcast para excluir!",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Long id = (Long) modeloTabela.getValueAt(linha, 0);
        String nome = (String) modeloTabela.getValueAt(linha, 2);
        
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Deseja realmente excluir o episódio \"" + nome + "\"?",
            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                podcastDAO.excluir(id);
                JOptionPane.showMessageDialog(this, "Podcast excluído com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void voltar() {
        new TelaPrincipal(usuarioLogado).setVisible(true);
        dispose();
    }
}