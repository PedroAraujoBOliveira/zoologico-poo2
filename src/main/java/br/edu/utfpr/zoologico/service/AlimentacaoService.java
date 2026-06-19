package br.edu.utfpr.zoologico.service;

import br.edu.utfpr.zoologico.dao.AlimentacaoDao;
import br.edu.utfpr.zoologico.exception.ValidacaoException;
import br.edu.utfpr.zoologico.model.Alimentacao;
import java.time.LocalDateTime;
import java.util.List;

public class AlimentacaoService {
    private final AlimentacaoDao alimentacaoDao = new AlimentacaoDao();

    public void registrar(Long animalId, String alimento, double quantidadeKg) {
        if (animalId == null) throw new ValidacaoException("Selecione um animal.");
        if (alimento == null || alimento.isBlank()) throw new ValidacaoException("Informe o alimento.");
        if (quantidadeKg <= 0) throw new ValidacaoException("A quantidade deve ser maior que zero.");
        alimentacaoDao.salvar(new Alimentacao(null, animalId, alimento.trim(), quantidadeKg, LocalDateTime.now()));
    }

    public List<Alimentacao> listarPorAnimal(Long animalId) {
        if (animalId == null) throw new ValidacaoException("Selecione um animal.");
        return alimentacaoDao.listarPorAnimal(animalId);
    }
}
