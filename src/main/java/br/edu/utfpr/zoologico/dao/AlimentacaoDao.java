package br.edu.utfpr.zoologico.dao;

import br.edu.utfpr.zoologico.exception.PersistenciaException;
import br.edu.utfpr.zoologico.infra.ConexaoMySQL;
import br.edu.utfpr.zoologico.model.Alimentacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlimentacaoDao {
    public void salvar(Alimentacao alimentacao) {
        String sql = "INSERT INTO alimentacoes (animal_id, alimento, quantidade_kg, data_hora) VALUES (?, ?, ?, ?)";
        try (Connection conexao = ConexaoMySQL.abrir(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, alimentacao.animalId());
            statement.setString(2, alimentacao.alimento());
            statement.setDouble(3, alimentacao.quantidadeKg());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(alimentacao.dataHora()));
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new PersistenciaException("Nao foi possivel registrar a alimentacao", exception);
        }
    }

    public List<Alimentacao> listarPorAnimal(Long animalId) {
        String sql = "SELECT id, animal_id, alimento, quantidade_kg, data_hora "
                + "FROM alimentacoes WHERE animal_id = ? ORDER BY data_hora DESC";
        List<Alimentacao> alimentacoes = new ArrayList<>();
        try (Connection conexao = ConexaoMySQL.abrir(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setLong(1, animalId);
            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    alimentacoes.add(new Alimentacao(resultado.getLong("id"), resultado.getLong("animal_id"),
                            resultado.getString("alimento"), resultado.getDouble("quantidade_kg"),
                            resultado.getObject("data_hora", LocalDateTime.class)));
                }
            }
            return alimentacoes;
        } catch (SQLException exception) {
            throw new PersistenciaException("Nao foi possivel consultar o historico de alimentacao", exception);
        }
    }
}
