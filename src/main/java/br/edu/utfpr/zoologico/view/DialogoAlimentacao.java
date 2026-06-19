package br.edu.utfpr.zoologico.view;

import br.edu.utfpr.zoologico.controller.AlimentacaoController;
import br.edu.utfpr.zoologico.model.Animal;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogoAlimentacao extends JDialog {
    private final Animal animal;
    private final AlimentacaoController alimentacaoController;
    private final JTextField campoAlimento = new JTextField(22);
    private final JTextField campoQuantidade = new JTextField(22);

    public DialogoAlimentacao(Frame parent, AlimentacaoController alimentacaoController, Animal animal) {
        super(parent, "Registrar alimentacao", true);
        this.animal = animal;
        this.alimentacaoController = alimentacaoController;
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 8, 14));
        adicionar(formulario, "Animal:", new JLabel(animal.getNome()), 0);
        adicionar(formulario, "Alimento:", campoAlimento, 1);
        adicionar(formulario, "Quantidade (kg):", campoQuantidade, 2);
        add(formulario, BorderLayout.CENTER);
        JButton salvar = new JButton("Registrar");
        salvar.addActionListener(evento -> salvar());
        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(evento -> dispose());
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(cancelar); botoes.add(salvar);
        add(botoes, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }

    private void adicionar(JPanel painel, String rotulo, java.awt.Component componente, int linha) {
        GridBagConstraints restricoes = new GridBagConstraints();
        restricoes.insets = new Insets(5, 5, 5, 5);
        restricoes.anchor = GridBagConstraints.WEST;
        restricoes.gridx = 0; restricoes.gridy = linha;
        painel.add(new JLabel(rotulo), restricoes);
        restricoes.gridx = 1; restricoes.fill = GridBagConstraints.HORIZONTAL; restricoes.weightx = 1;
        painel.add(componente, restricoes);
    }

    private void salvar() {
        try {
            double quantidade = Double.parseDouble(campoQuantidade.getText().trim().replace(',', '.'));
            alimentacaoController.registrar(animal.getId(), campoAlimento.getText(), quantidade);
            JOptionPane.showMessageDialog(this, "Alimentacao registrada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "A quantidade deve ser numerica.", "Dados invalidos", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
