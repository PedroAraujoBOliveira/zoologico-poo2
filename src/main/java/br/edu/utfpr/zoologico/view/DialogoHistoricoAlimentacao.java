package br.edu.utfpr.zoologico.view;

import br.edu.utfpr.zoologico.controller.AlimentacaoController;
import br.edu.utfpr.zoologico.model.Alimentacao;
import br.edu.utfpr.zoologico.model.Animal;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DialogoHistoricoAlimentacao extends JDialog {
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final Animal animal;
    private final AlimentacaoController alimentacaoController;
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
            new String[] {"Data e hora", "Alimento", "Quantidade (kg)"}, 0) {
        @Override public boolean isCellEditable(int linha, int coluna) { return false; }
    };

    public DialogoHistoricoAlimentacao(Frame parent, AlimentacaoController alimentacaoController, Animal animal) {
        super(parent, "Historico de alimentacao", true);
        this.animal = animal;
        this.alimentacaoController = alimentacaoController;
        setLayout(new BorderLayout(8, 8));
        JLabel titulo = new JLabel("Historico de alimentacao - " + animal.getNome());
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 0, 12));
        add(titulo, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(modeloTabela)), BorderLayout.CENTER);
        setSize(520, 300);
        setLocationRelativeTo(parent);
        carregarHistorico();
    }

    private void carregarHistorico() {
        try {
            for (Alimentacao alimentacao : alimentacaoController.listarPorAnimal(animal.getId())) {
                modeloTabela.addRow(new Object[] {FORMATO_DATA.format(alimentacao.dataHora()), alimentacao.alimento(),
                    String.format("%.2f", alimentacao.quantidadeKg())});
            }
        } catch (RuntimeException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
