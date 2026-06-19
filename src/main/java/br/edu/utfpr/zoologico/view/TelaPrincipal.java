package br.edu.utfpr.zoologico.view;

import br.edu.utfpr.zoologico.controller.AlimentacaoController;
import br.edu.utfpr.zoologico.controller.AnimalController;
import br.edu.utfpr.zoologico.controller.EspecieController;
import br.edu.utfpr.zoologico.model.Animal;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class TelaPrincipal extends JFrame {
    private final AnimalController animalController = new AnimalController();
    private final EspecieController especieController = new EspecieController();
    private final AlimentacaoController alimentacaoController = new AlimentacaoController();
    private final JTextField campoBusca = new JTextField(24);
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
            new String[] {"ID", "Nome", "Tipo", "Especie", "Idade", "Peso (kg)", "Sexo", "Caracteristica"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };
    private final JTable tabela = new JTable(modeloTabela);
    private List<Animal> animais = new ArrayList<>();

    public TelaPrincipal() {
        setTitle("Sistema de Gestao de Zoologico - POO2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 620);
        setLocationRelativeTo(null);
        construirTela();
        carregarAnimais();
    }

    private void construirTela() {
        JPanel cabecalho = new JPanel(new BorderLayout(12, 0));
        cabecalho.setBorder(BorderFactory.createEmptyBorder(14, 14, 8, 14));
        JLabel titulo = new JLabel("Animais do zoologico");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        cabecalho.add(titulo, BorderLayout.WEST);
        JPanel busca = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        busca.add(new JLabel("Buscar:"));
        busca.add(campoBusca);
        cabecalho.add(busca, BorderLayout.EAST);
        add(cabecalho, BorderLayout.NORTH);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoCreateRowSorter(true);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton novo = new JButton("Novo animal");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");
        JButton alimentar = new JButton("Registrar alimentacao");
        JButton historico = new JButton("Historico de alimentacao");
        JButton especies = new JButton("Especies");
        JButton atualizar = new JButton("Atualizar");
        novo.addActionListener(evento -> new DialogoAnimal(this, especieController, animalController, null, this::carregarAnimais).setVisible(true));
        editar.addActionListener(evento -> editarSelecionado());
        excluir.addActionListener(evento -> excluirSelecionado());
        alimentar.addActionListener(evento -> alimentarSelecionado());
        historico.addActionListener(evento -> abrirHistoricoAlimentacao());
        especies.addActionListener(evento -> new DialogoEspecie(this, especieController).setVisible(true));
        atualizar.addActionListener(evento -> carregarAnimais());

        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        acoes.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        acoes.add(novo); acoes.add(editar); acoes.add(excluir); acoes.add(alimentar); acoes.add(historico); acoes.add(especies); acoes.add(atualizar);
        add(acoes, BorderLayout.SOUTH);

        campoBusca.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent evento) { carregarAnimais(); }
            public void removeUpdate(DocumentEvent evento) { carregarAnimais(); }
            public void changedUpdate(DocumentEvent evento) { carregarAnimais(); }
        });
    }

    private void carregarAnimais() {
        try {
            animais = animalController.listar(campoBusca.getText());
            modeloTabela.setRowCount(0);
            for (Animal animal : animais) {
                modeloTabela.addRow(new Object[] {animal.getId(), animal.getNome(), animal.getTipo(),
                    animal.getEspecie().getNome(), animal.getIdade(), String.format("%.2f", animal.getPeso()),
                    animal.getSexo(), animal.getCaracteristicaEspecifica()});
            }
        } catch (RuntimeException exception) {
            mostrarErro(exception);
        }
    }

    private Animal selecionado() {
        int linhaVisual = tabela.getSelectedRow();
        if (linhaVisual < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um animal na tabela.", "Atencao", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int linhaModelo = tabela.convertRowIndexToModel(linhaVisual);
        Long id = (Long) modeloTabela.getValueAt(linhaModelo, 0);
        return animais.stream().filter(animal -> animal.getId().equals(id)).findFirst().orElse(null);
    }

    private void editarSelecionado() {
        Animal animal = selecionado();
        if (animal != null) new DialogoAnimal(this, especieController, animalController, animal, this::carregarAnimais).setVisible(true);
    }

    private void excluirSelecionado() {
        Animal animal = selecionado();
        if (animal == null) return;
        int resposta = JOptionPane.showConfirmDialog(this, "Excluir " + animal.getNome() + " e suas alimentacoes?",
                "Confirmar exclusao", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                animalController.excluir(animal.getId());
                carregarAnimais();
            } catch (RuntimeException exception) {
                mostrarErro(exception);
            }
        }
    }

    private void alimentarSelecionado() {
        Animal animal = selecionado();
        if (animal != null) new DialogoAlimentacao(this, alimentacaoController, animal).setVisible(true);
    }

    private void abrirHistoricoAlimentacao() {
        Animal animal = selecionado();
        if (animal != null) new DialogoHistoricoAlimentacao(this, alimentacaoController, animal).setVisible(true);
    }

    private void mostrarErro(RuntimeException exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
