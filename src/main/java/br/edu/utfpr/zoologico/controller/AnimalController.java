package br.edu.utfpr.zoologico.controller;

import br.edu.utfpr.zoologico.model.Animal;
import br.edu.utfpr.zoologico.model.Especie;
import br.edu.utfpr.zoologico.service.AnimalService;
import java.util.List;

/** Intermedia as acoes da tela de animais e as regras de negocio. */
public class AnimalController {
    private final AnimalService animalService = new AnimalService();

    public Animal cadastrar(String nome, int idade, double peso, String sexo, Especie especie, String caracteristica) {
        return animalService.cadastrar(nome, idade, peso, sexo, especie, caracteristica);
    }

    public void atualizar(Long id, String nome, int idade, double peso, String sexo, Especie especie, String caracteristica) {
        animalService.atualizar(id, nome, idade, peso, sexo, especie, caracteristica);
    }

    public void excluir(Long id) {
        animalService.excluir(id);
    }

    public List<Animal> listar(String filtro) {
        return animalService.listar(filtro);
    }
}
