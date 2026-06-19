package br.edu.utfpr.zoologico.service;

import br.edu.utfpr.zoologico.dao.AnimalDao;
import br.edu.utfpr.zoologico.exception.ValidacaoException;
import br.edu.utfpr.zoologico.model.Animal;
import br.edu.utfpr.zoologico.model.AnimalFactory;
import br.edu.utfpr.zoologico.model.Especie;
import java.util.List;

public class AnimalService {
    private final AnimalDao animalDao = new AnimalDao();

    public Animal cadastrar(String nome, int idade, double peso, String sexo, Especie especie, String caracteristica) {
        return animalDao.salvar(criar(null, nome, idade, peso, sexo, especie, caracteristica));
    }

    public void atualizar(Long id, String nome, int idade, double peso, String sexo, Especie especie, String caracteristica) {
        animalDao.atualizar(criar(id, nome, idade, peso, sexo, especie, caracteristica));
    }

    public void excluir(Long id) {
        if (id == null) throw new ValidacaoException("Selecione um animal para excluir.");
        animalDao.excluir(id);
    }

    public List<Animal> listar(String filtro) {
        return animalDao.listar(filtro);
    }

    private Animal criar(Long id, String nome, int idade, double peso, String sexo, Especie especie, String caracteristica) {
        if (nome == null || nome.isBlank()) throw new ValidacaoException("Informe o nome do animal.");
        if (idade < 0) throw new ValidacaoException("A idade nao pode ser negativa.");
        if (peso <= 0) throw new ValidacaoException("O peso deve ser maior que zero.");
        if (sexo == null || sexo.isBlank()) throw new ValidacaoException("Informe o sexo do animal.");
        if (especie == null) throw new ValidacaoException("Cadastre e selecione uma especie.");
        if (caracteristica == null || caracteristica.isBlank()) {
            throw new ValidacaoException("Informe a caracteristica especifica do animal.");
        }
        return AnimalFactory.criar(id, nome.trim(), idade, peso, sexo.trim(), especie, caracteristica.trim());
    }
}
