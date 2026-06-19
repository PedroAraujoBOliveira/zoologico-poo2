package br.edu.utfpr.zoologico.view;

import br.edu.utfpr.zoologico.controller.EspecieController;
import br.edu.utfpr.zoologico.model.TipoAnimal;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogoEspecie extends JDialog {
    private final EspecieController especieController;
    private final JTextField campoNome = new JTextField(22);
    private final JComboBox<TipoAnimal> campoTipo = new JComboBox<>(TipoAnimal.values());
    private final JTextField campoDescricao = new JTextField(22);

    public DialogoEspecie(Frame parent, EspecieController especieController) {
        super(parent, "Cadastrar especie", true);
        this.especieController = especieController;
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 8, 14));
        adicionar(formulario, "Nome:", campoNome, 0);
        adicionar(formulario, "Classe:", campoTipo, 1);
        adicionar(formulario, "Descricao:", campoDescricao, 2);
        add(formulario, BorderLayout.CENTER);
        JButton salvar = new JButton("Salvar");
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
            especieController.cadastrar(campoNome.getText(), (TipoAnimal) campoTipo.getSelectedItem(), campoDescricao.getText());
            JOptionPane.showMessageDialog(this, "Especie cadastrada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (RuntimeException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
