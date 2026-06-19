package br.edu.utfpr.zoologico.model;

public final class AnimalFactory {
    private AnimalFactory() { }

    public static Animal criar(Long id, String nome, int idade, double peso, String sexo, Especie especie,
            String caracteristica) {
        return switch (especie.getTipo()) {
            case AVE -> new Ave(id, nome, idade, peso, sexo, especie, caracteristica);
            case MAMIFERO -> new Mamifero(id, nome, idade, peso, sexo, especie, caracteristica);
            case REPTIL -> new Reptil(id, nome, idade, peso, sexo, especie, caracteristica);
        };
    }
}
