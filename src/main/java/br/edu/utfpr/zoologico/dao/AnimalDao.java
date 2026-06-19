package br.edu.utfpr.zoologico.dao;

import br.edu.utfpr.zoologico.exception.PersistenciaException;
import br.edu.utfpr.zoologico.infra.ConexaoMySQL;
import br.edu.utfpr.zoologico.model.Animal;
import br.edu.utfpr.zoologico.model.AnimalFactory;
import br.edu.utfpr.zoologico.model.Especie;
import br.edu.utfpr.zoologico.model.TipoAnimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AnimalDao {
    private static final String COLUNAS = "a.id, a.nome, a.idade, a.peso, a.sexo, a.caracteristica_especifica, "
            + "e.id AS especie_id, e.nome AS especie_nome, e.tipo AS especie_tipo, e.descricao AS especie_descricao ";

    public Animal salvar(Animal animal) {
        String sql = "INSERT INTO animais (nome, idade, peso, sexo, especie_id, caracteristica_especifica) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = ConexaoMySQL.abrir();
                PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencher(statement, animal);
            statement.executeUpdate();
            try (ResultSet chaves = statement.getGeneratedKeys()) {
                if (chaves.next()) animal.setId(chaves.getLong(1));
            }
            return animal;
        } catch (SQLException exception) {
            throw new PersistenciaException("Nao foi possivel salvar o animal", exception);
        }
    }

    public void atualizar(Animal animal) {
        String sql = "UPDATE animais SET nome = ?, idade = ?, peso = ?, sexo = ?, especie_id = ?, caracteristica_especifica = ? WHERE id = ?";
        try (Connection conexao = ConexaoMySQL.abrir(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            preencher(statement, animal);
            statement.setLong(7, animal.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new PersistenciaException("Nao foi possivel atualizar o animal", exception);
        }
    }

    public void excluir(Long id) {
        try (Connection conexao = ConexaoMySQL.abrir()) {
            conexao.setAutoCommit(false);
            try (PreparedStatement alimentacoes = conexao.prepareStatement("DELETE FROM alimentacoes WHERE animal_id = ?");
                    PreparedStatement animal = conexao.prepareStatement("DELETE FROM animais WHERE id = ?")) {
                alimentacoes.setLong(1, id);
                alimentacoes.executeUpdate();
                animal.setLong(1, id);
                animal.executeUpdate();
                conexao.commit();
            } catch (SQLException exception) {
                conexao.rollback();
                throw exception;
            }
        } catch (SQLException exception) {
            throw new PersistenciaException("Nao foi possivel excluir o animal", exception);
        }
    }

    public List<Animal> listar(String filtro) {
        String sql = "SELECT " + COLUNAS + "FROM animais a JOIN especies e ON e.id = a.especie_id "
                + "WHERE LOWER(a.nome) LIKE ? OR LOWER(e.nome) LIKE ? ORDER BY a.nome";
        List<Animal> animais = new ArrayList<>();
        String termo = "%" + (filtro == null ? "" : filtro.trim().toLowerCase()) + "%";
        try (Connection conexao = ConexaoMySQL.abrir(); PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, termo);
            statement.setString(2, termo);
            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) animais.add(mapear(resultado));
            }
            return animais;
        } catch (SQLException exception) {
            throw new PersistenciaException("Nao foi possivel listar os animais", exception);
        }
    }

    private void preencher(PreparedStatement statement, Animal animal) throws SQLException {
        statement.setString(1, animal.getNome());
        statement.setInt(2, animal.getIdade());
        statement.setDouble(3, animal.getPeso());
        statement.setString(4, animal.getSexo());
        statement.setLong(5, animal.getEspecie().getId());
        statement.setString(6, animal.getCaracteristicaEspecifica());
    }

    private Animal mapear(ResultSet resultado) throws SQLException {
        Especie especie = new Especie(resultado.getLong("especie_id"), resultado.getString("especie_nome"),
                TipoAnimal.valueOf(resultado.getString("especie_tipo")), resultado.getString("especie_descricao"));
        return AnimalFactory.criar(resultado.getLong("id"), resultado.getString("nome"), resultado.getInt("idade"),
                resultado.getDouble("peso"), resultado.getString("sexo"), especie,
                resultado.getString("caracteristica_especifica"));
    }
}
