package br.edu.utfpr.zoologico.model;

public class Mamifero extends Animal {
    public Mamifero(Long id, String nome, int idade, double peso, String sexo, Especie especie, String tipoPelagem) {
        super(id, nome, idade, peso, sexo, especie, tipoPelagem);
    }

    @Override
    public TipoAnimal getTipo() { return TipoAnimal.MAMIFERO; }
}
