package br.edu.utfpr.zoologico.infra;

import br.edu.utfpr.zoologico.exception.PersistenciaException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConexaoMySQL {
    private static final Properties PROPRIEDADES = carregarPropriedades();

    private ConexaoMySQL() { }

    public static Connection abrir() {
        try {
            String senha = System.getenv("ZOO_DB_PASSWORD");
            if (senha == null || senha.isBlank()) {
                throw new PersistenciaException("A senha do MySQL nao foi configurada", null);
            }
            return DriverManager.getConnection(PROPRIEDADES.getProperty("url"), PROPRIEDADES.getProperty("user"), senha);
        } catch (SQLException exception) {
            throw new PersistenciaException("Nao foi possivel conectar ao MySQL", exception);
        }
    }

    private static Properties carregarPropriedades() {
        try (InputStream arquivo = ConexaoMySQL.class.getResourceAsStream("/database.properties")) {
            Properties propriedades = new Properties();
            if (arquivo == null) {
                throw new PersistenciaException("Arquivo database.properties nao encontrado", null);
            }
            propriedades.load(arquivo);
            return propriedades;
        } catch (IOException exception) {
            throw new PersistenciaException("Nao foi possivel ler a configuracao do banco", exception);
        }
    }
}
