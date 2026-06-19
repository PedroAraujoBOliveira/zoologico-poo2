package br.edu.utfpr.zoologico.model;

import java.time.LocalDateTime;

public record Alimentacao(Long id, Long animalId, String alimento, double quantidadeKg, LocalDateTime dataHora) { }
