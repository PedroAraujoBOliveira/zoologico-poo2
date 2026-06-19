package br.edu.utfpr.zoologico.model;

public class Reptil extends Animal {
    public Reptil(Long id, String nome, int idade, double peso, String sexo, Especie especie, String tipoEscamas) {
        super(id, nome, idade, peso, sexo, especie, tipoEscamas);
    }

    @Override
    public TipoAnimal getTipo() { return TipoAnimal.REPTIL; }
}
