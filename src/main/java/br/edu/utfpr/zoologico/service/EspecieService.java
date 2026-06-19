package br.edu.utfpr.zoologico.service;

import br.edu.utfpr.zoologico.dao.EspecieDao;
import br.edu.utfpr.zoologico.exception.ValidacaoException;
import br.edu.utfpr.zoologico.model.Especie;
import br.edu.utfpr.zoologico.model.TipoAnimal;
import java.util.List;

public class EspecieService {
    private final EspecieDao especieDao = new EspecieDao();

    public Especie cadastrar(String nome, TipoAnimal tipo, String descricao) {
        if (nome == null || nome.isBlank()) throw new ValidacaoException("Informe o nome da especie.");
        if (tipo == null) throw new ValidacaoException("Selecione a classe da especie.");
        return especieDao.salvar(new Especie(null, nome.trim(), tipo, descricao == null ? "" : descricao.trim()));
    }

    public List<Especie> listar() {
        return especieDao.listar();
    }
}
