package br.edu.utfpr.zoologico.model;

public enum TipoAnimal {
    AVE("Ave", "Cor das penas"),
    MAMIFERO("Mamifero", "Tipo de pelagem"),
    REPTIL("Reptil", "Tipo de escamas");

    private final String descricao;
    private final String rotuloCaracteristica;

    TipoAnimal(String descricao, String rotuloCaracteristica) {
        this.descricao = descricao;
        this.rotuloCaracteristica = rotuloCaracteristica;
    }

    public String getRotuloCaracteristica() {
        return rotuloCaracteristica;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
