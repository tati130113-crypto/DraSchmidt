package cenaflix.view;

import cenaflix.dao.VideoDAO;
import cenaflix.model.Video;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Tela de listagem e gerenciamento de vídeos.
 * 
 * @author SeuNome
 * @version 1.0
 */
public class TelaListagem extends JFrame {
    
    private JTable tabelaVideos;
    private DefaultTableModel modeloTabela;
    private JTextField txtFiltro;
    private JComboBox<String> comboTipoFiltro;
    private JButton btnFiltrar;
    private JButton btnLimparFiltro;
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;
    private JButton btnVoltar;
    
    private VideoDAO videoDAO;
    private DateTimeFormatter dateFormatter;
    
    public TelaListagem() {
        videoDAO = new VideoDAO();
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        initComponents();
        configurarLayout();
        carregarDadosTabela();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initComponents() {
        setTitle("Cenaflix - Gerenciamento de Vídeos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(true);
        
        modeloTabela = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Data Lançamento", "Categoria"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaVideos = new JTable(modeloTabela);
        tabelaVideos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVideos.getTableHeader().setReorderingAllowed(false);
        
        tabelaVideos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaVideos.getColumnModel().getColumn(1).setPreferredWidth(300);
        tabelaVideos.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabelaVideos.getColumnModel().getColumn(3).setPreferredWidth(150);
        
        txtFiltro = new JTextField(20);
        txtFiltro.setToolTipText("Digite o termo para filtrar...");
        
        String[] tiposFiltro = {"Categoria", "Nome"};
        comboTipoFiltro = new JComboBox<>(tiposFiltro);
        
        btnFiltrar = new JButton("Filtrar");
        btnLimparFiltro = new JButton("Limpar Filtro");
        btnNovo = new JButton("Novo Vídeo");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Lista");
        btnVoltar = new JButton("Voltar");
        
        btnFiltrar.addActionListener(e -> filtrarVideos());
        btnLimparFiltro.addActionListener(e -> limparFiltro());
        btnNovo.addActionListener(e -> abrirTelaCadastro());
        btnEditar.addActionListener(e -> editarVideo());
        btnExcluir.addActionListener(e -> excluirVideo());
        btnAtualizar.addActionListener(e -> carregarDadosTabela());
        btnVoltar.addActionListener(e -> voltar());
        
        txtFiltro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    filtrarVideos();
                }
            }
        });
        
        tabelaVideos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    editarVideo();
                }
            }
        });
        
        estilizarBotoes();
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("GERENCIAMENTO DE VÍDEOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 102, 204));
        painelPrincipal.add(titulo, BorderLayout.NORTH);
        
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFiltro.setBorder(BorderFactory.createTitledBorder("Filtros"));
        
        painelFiltro.add(new JLabel("Filtrar por:"));
        painelFiltro.add(comboTipoFiltro);
        painelFiltro.add(new JLabel("Termo:"));
        painelFiltro.add(txtFiltro);
        painelFiltro.add(btnFiltrar);
        painelFiltro.add(btnLimparFiltro);
        
        JScrollPane scrollPane = new JScrollPane(tabelaVideos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Vídeos Cadastrados"));
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVoltar);
        
        painelPrincipal.add(painelFiltro, BorderLayout.NORTH);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void estilizarBotoes() {
        Dimension tamanhoBotao = new Dimension(120, 35);
        Font fonteBotao = new Font("Arial", Font.BOLD, 12);
        Color textoPreto = Color.BLACK;
        
        JButton[] botoes = {btnFiltrar, btnLimparFiltro, btnNovo, 
                            btnEditar, btnExcluir, btnAtualizar, btnVoltar};
        
        for (JButton botao : botoes) {
            botao.setPreferredSize(tamanhoBotao);
            botao.setFont(fonteBotao);
            botao.setForeground(textoPreto);
            botao.setFocusPainted(false);
            botao.setBorder(BorderFactory.createRaisedBevelBorder());
        }
        
        btnNovo.setBackground(new Color(0, 153, 76));
        btnEditar.setBackground(new Color(255, 153, 0));
        btnExcluir.setBackground(new Color(204, 0, 0));
        btnFiltrar.setBackground(new Color(51, 153, 255));
        btnLimparFiltro.setBackground(new Color(153, 153, 153));
        btnAtualizar.setBackground(new Color(102, 102, 255));
        btnVoltar.setBackground(new Color(255, 102, 102));
    }
    
    private void carregarDadosTabela() {
        modeloTabela.setRowCount(0);
        
        List<Video> videos = videoDAO.listarTodos();
        
        for (Video video : videos) {
            Object[] linha = {
                video.getId(),
                video.getNome(),
                video.getDataLancamento().format(dateFormatter),
                video.getCategoria()
            };
            modeloTabela.addRow(linha);
        }
    }
    
    private void filtrarVideos() {
        String termo = txtFiltro.getText().trim();
        
        if (termo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Digite um termo para filtrar!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modeloTabela.setRowCount(0);
        List<Video> videos;
        
        if (comboTipoFiltro.getSelectedIndex() == 0) {
            videos = videoDAO.buscarPorCategoria(termo);
        } else {
            videos = videoDAO.buscarPorNome(termo);
        }
        
        for (Video video : videos) {
            Object[] linha = {
                video.getId(),
                video.getNome(),
                video.getDataLancamento().format(dateFormatter),
                video.getCategoria()
            };
            modeloTabela.addRow(linha);
        }
        
        if (videos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nenhum vídeo encontrado com o filtro aplicado!",
                "Resultado",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void limparFiltro() {
        txtFiltro.setText("");
        carregarDadosTabela();
    }
    
    private void abrirTelaCadastro() {
        TelaCadastro telaCadastro = new TelaCadastro();
        telaCadastro.setVisible(true);
        dispose();
    }
    
    private void editarVideo() {
        int linhaSelecionada = tabelaVideos.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um vídeo para editar!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        Video video = videoDAO.buscarPorId(id);
        
        if (video != null) {
            TelaCadastro telaCadastro = new TelaCadastro();
            telaCadastro.carregarVideoParaEdicao(video);
            telaCadastro.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar dados do vídeo!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirVideo() {
        int linhaSelecionada = tabelaVideos.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um vídeo para excluir!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Deseja realmente excluir o vídeo \"" + nome + "\"?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean sucesso = videoDAO.excluir(id);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                    "Vídeo excluído com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
                carregarDadosTabela();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Não foi possível excluir o vídeo!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void voltar() {
        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Deseja realmente voltar?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            TelaCadastro telaCadastro = new TelaCadastro();
            telaCadastro.setVisible(true);
            dispose();
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            TelaListagem tela = new TelaListagem();
            tela.setVisible(true);
        });
    }
}