package br.edu.utfpr.zoologico.model;

import java.util.Objects;

public class Especie {
    private Long id;
    private final String nome;
    private final TipoAnimal tipo;
    private final String descricao;

    public Especie(Long id, String nome, TipoAnimal tipo, String descricao) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public TipoAnimal getTipo() { return tipo; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() {
        return nome + " (" + tipo + ")";
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof Especie outra)) return false;
        return id != null && id.equals(outra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
