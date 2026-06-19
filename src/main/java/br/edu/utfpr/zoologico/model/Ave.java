package br.edu.utfpr.zoologico.model;

public class Ave extends Animal {
    public Ave(Long id, String nome, int idade, double peso, String sexo, Especie especie, String corDasPenas) {
        super(id, nome, idade, peso, sexo, especie, corDasPenas);
    }

    @Override
    public TipoAnimal getTipo() { return TipoAnimal.AVE; }
}
