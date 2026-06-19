package br.edu.utfpr.zoologico.controller;

import br.edu.utfpr.zoologico.service.AlimentacaoService;
import br.edu.utfpr.zoologico.model.Alimentacao;
import java.util.List;

/** Intermedia o registro de alimentacao solicitado pela interface. */
public class AlimentacaoController {
    private final AlimentacaoService alimentacaoService = new AlimentacaoService();

    public void registrar(Long animalId, String alimento, double quantidadeKg) {
        alimentacaoService.registrar(animalId, alimento, quantidadeKg);
    }

    public List<Alimentacao> listarPorAnimal(Long animalId) {
        return alimentacaoService.listarPorAnimal(animalId);
    }
}
