package br.edu.utfpr.zoologico.view;

import br.edu.utfpr.zoologico.controller.AnimalController;
import br.edu.utfpr.zoologico.controller.EspecieController;
import br.edu.utfpr.zoologico.model.Animal;
import br.edu.utfpr.zoologico.model.Especie;
import br.edu.utfpr.zoologico.model.TipoAnimal;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogoAnimal extends JDialog {
    private final EspecieController especieController;
    private final AnimalController animalController;
    private final Animal animalEmEdicao;
    private final Runnable aoSalvar;
    private final JTextField campoNome = new JTextField(22);
    private final JTextField campoIdade = new JTextField(22);
    private final JTextField campoPeso = new JTextField(22);
    private final JComboBox<String> campoSexo = new JComboBox<>(new String[] {"Macho", "Femea", "Nao informado"});
    private final JComboBox<TipoAnimal> campoTipo = new JComboBox<>(TipoAnimal.values());
    private final JComboBox<Especie> campoEspecie = new JComboBox<>();
    private final JLabel rotuloCaracteristica = new JLabel();
    private final JTextField campoCaracteristica = new JTextField(22);
    private List<Especie> especies;

    public DialogoAnimal(Frame parent, EspecieController especieController, AnimalController animalController, Animal animalEmEdicao,
            Runnable aoSalvar) {
        super(parent, animalEmEdicao == null ? "Cadastrar animal" : "Editar animal", true);
        this.especieController = especieController;
        this.animalController = animalController;
        this.animalEmEdicao = animalEmEdicao;
        this.aoSalvar = aoSalvar;
        construirTela();
        carregarEspecies();
        preencherEdicao();
        setLocationRelativeTo(parent);
    }

    private void construirTela() {
        setLayout(new BorderLayout());
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 14, 8, 14));
        adicionar(formulario, new JLabel("Nome:"), campoNome, 0);
        adicionar(formulario, new JLabel("Idade (anos):"), campoIdade, 1);
        adicionar(formulario, new JLabel("Peso (kg):"), campoPeso, 2);
        adicionar(formulario, new JLabel("Sexo:"), campoSexo, 3);
        adicionar(formulario, new JLabel("Classe:"), campoTipo, 4);
        adicionar(formulario, new JLabel("Especie:"), campoEspecie, 5);
        adicionar(formulario, rotuloCaracteristica, campoCaracteristica, 6);
        add(formulario, BorderLayout.CENTER);
        campoTipo.addActionListener(evento -> atualizarEspeciesPorTipo());
        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(evento -> salvar());
        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(evento -> dispose());
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(cancelar); botoes.add(salvar);
        add(botoes, BorderLayout.SOUTH);
        pack();
    }

    private void adicionar(JPanel painel, JLabel rotulo, java.awt.Component componente, int linha) {
        GridBagConstraints restricoes = new GridBagConstraints();
        restricoes.insets = new Insets(5, 5, 5, 5);
        restricoes.anchor = GridBagConstraints.WEST;
        restricoes.gridx = 0; restricoes.gridy = linha;
        painel.add(rotulo, restricoes);
        restricoes.gridx = 1; restricoes.fill = GridBagConstraints.HORIZONTAL; restricoes.weightx = 1;
        painel.add(componente, restricoes);
    }

    private void carregarEspecies() {
        especies = especieController.listar();
        if (animalEmEdicao == null) campoTipo.setSelectedItem(TipoAnimal.AVE);
        atualizarEspeciesPorTipo();
    }

    private void atualizarEspeciesPorTipo() {
        TipoAnimal tipo = (TipoAnimal) campoTipo.getSelectedItem();
        rotuloCaracteristica.setText(tipo.getRotuloCaracteristica() + ":");
        campoEspecie.removeAllItems();
        especies.stream().filter(especie -> especie.getTipo() == tipo).forEach(campoEspecie::addItem);
    }

    private void preencherEdicao() {
        if (animalEmEdicao == null) return;
        campoNome.setText(animalEmEdicao.getNome());
        campoIdade.setText(String.valueOf(animalEmEdicao.getIdade()));
        campoPeso.setText(String.valueOf(animalEmEdicao.getPeso()));
        campoSexo.setSelectedItem(animalEmEdicao.getSexo());
        campoTipo.setSelectedItem(animalEmEdicao.getTipo());
        campoEspecie.setSelectedItem(animalEmEdicao.getEspecie());
        campoCaracteristica.setText(animalEmEdicao.getCaracteristicaEspecifica());
    }

    private void salvar() {
        try {
            int idade = Integer.parseInt(campoIdade.getText().trim());
            double peso = Double.parseDouble(campoPeso.getText().trim().replace(',', '.'));
            Especie especie = (Especie) campoEspecie.getSelectedItem();
            if (animalEmEdicao == null) {
                animalController.cadastrar(campoNome.getText(), idade, peso, (String) campoSexo.getSelectedItem(), especie,
                        campoCaracteristica.getText());
            } else {
                animalController.atualizar(animalEmEdicao.getId(), campoNome.getText(), idade, peso,
                        (String) campoSexo.getSelectedItem(), especie, campoCaracteristica.getText());
            }
            aoSalvar.run();
            dispose();
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Idade deve ser inteira e peso deve ser numerico.", "Dados invalidos",
                    JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
