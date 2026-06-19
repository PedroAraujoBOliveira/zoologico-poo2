package br.edu.utfpr.zoologico.model;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AnimalFactoryTest {
    @Test
    void criaSubclasseConformeTipoDaEspecie() {
        Especie especie = new Especie(1L, "Leao", TipoAnimal.MAMIFERO, "Felino");
        Animal animal = AnimalFactory.criar(null, "Simba", 4, 190.0, "Macho", especie, "Curta");
        assertInstanceOf(Mamifero.class, animal);
    }

    @Test
    void especiesComMesmoIdentificadorSaoIguais() {
        Especie primeira = new Especie(1L, "Leao", TipoAnimal.MAMIFERO, "Felino");
        Especie segunda = new Especie(1L, "Leao Africano", TipoAnimal.MAMIFERO, "Felino");
        assertEquals(primeira, segunda);
    }
}
