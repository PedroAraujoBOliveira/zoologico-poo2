package br.edu.utfpr.zoologico;

import br.edu.utfpr.zoologico.exception.PersistenciaException;
import br.edu.utfpr.zoologico.infra.DatabaseInitializer;
import br.edu.utfpr.zoologico.view.TelaPrincipal;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                DatabaseInitializer.criarEstrutura();
                new TelaPrincipal().setVisible(true);
            } catch (PersistenciaException exception) {
                JOptionPane.showMessageDialog(null,
                        exception.getMessage() + "\n\nDefina a variavel ZOO_DB_PASSWORD no terminal antes de executar.",
                        "Falha na conexao com o banco", JOptionPane.ERROR_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
