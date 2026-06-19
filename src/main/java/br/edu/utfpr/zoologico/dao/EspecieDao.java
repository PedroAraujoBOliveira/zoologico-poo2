package br.edu.utfpr.zoologico.dao;

import br.edu.utfpr.zoologico.exception.PersistenciaException;
import br.edu.utfpr.zoologico.infra.ConexaoMySQL;
import br.edu.utfpr.zoologico.model.Especie;
import br.edu.utfpr.zoologico.model.TipoAnimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EspecieDao {
    public Especie salvar(Especie especie) {
        String sql = "INSERT INTO especies (nome, tipo, descricao) VALUES (?, ?, ?)";
        try (Connection conexao = ConexaoMySQL.abrir();
                PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, especie.getNome());
            statement.setString(2, especie.getTipo().name());
            statement.setString(3, especie.getDescricao());
            statement.executeUpdate();
            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) especie.setId(chaves.getLong(1));
            }
            return especie;
        } catch (SQLException exception) {
            throw new PersistenciaException("Nao foi possivel salvar a especie. Verifique se o nome ja existe.", exception);
        }
    }

    public List<Especie> listar() {
        String sql = "SELECT id, nome, tipo, descricao FROM especies ORDER BY tipo, nome";
        List<Especie> especies = new ArrayList<>();
        try (Connection conexao = ConexaoMySQL.abrir(); PreparedStatement statement = conexao.prepareStatement(sql);
                ResultSet resultado = statement.executeQuery()) {
            while (resultado.next()) especies.add(mapear(resultado));
            return especies;
        } catch (SQLException exception) {
            throw new PersistenciaException("Nao foi possivel listar as especies", exception);
        }
    }

    private Especie mapear(ResultSet resultado) throws SQLException {
        return new Especie(resultado.getLong("id"), resultado.getString("nome"),
                TipoAnimal.valueOf(resultado.getString("tipo")), resultado.getString("descricao"));
    }
}
