package br.edu.utfpr.zoologico.model;

public abstract class Animal {
    private Long id;
    private final String nome;
    private final int idade;
    private final double peso;
    private final String sexo;
    private final Especie especie;
    private final String caracteristicaEspecifica;

    protected Animal(Long id, String nome, int idade, double peso, String sexo, Especie especie,
            String caracteristicaEspecifica) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.sexo = sexo;
        this.especie = especie;
        this.caracteristicaEspecifica = caracteristicaEspecifica;
    }

    public abstract TipoAnimal getTipo();
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public double getPeso() { return peso; }
    public String getSexo() { return sexo; }
    public Especie getEspecie() { return especie; }
    public String getCaracteristicaEspecifica() { return caracteristicaEspecifica; }
}
