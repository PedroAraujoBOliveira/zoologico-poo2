package br.edu.utfpr.zoologico.controller;

import br.edu.utfpr.zoologico.model.Especie;
import br.edu.utfpr.zoologico.model.TipoAnimal;
import br.edu.utfpr.zoologico.service.EspecieService;
import java.util.List;

/** Intermedia as acoes da tela de especies e as regras de negocio. */
public class EspecieController {
    private final EspecieService especieService = new EspecieService();

    public Especie cadastrar(String nome, TipoAnimal tipo, String descricao) {
        return especieService.cadastrar(nome, tipo, descricao);
    }

    public List<Especie> listar() {
        return especieService.listar();
    }
}
