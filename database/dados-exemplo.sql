USE zoologico_poo2;

INSERT INTO especies (nome, tipo, descricao)
VALUES
    ('Leao', 'MAMIFERO', 'Felino carnivoro de grande porte.'),
    ('Arara-azul', 'AVE', 'Ave brasileira de plumagem azul.'),
    ('Jabuti-piranga', 'REPTIL', 'Quelonio terrestre de casco avermelhado.')
ON DUPLICATE KEY UPDATE descricao = VALUES(descricao);

INSERT INTO animais (nome, idade, peso, sexo, especie_id, caracteristica_especifica)
SELECT 'Simba', 5, 190.00, 'Macho', id, 'Pelagem curta e dourada'
FROM especies
WHERE nome = 'Leao'
  AND NOT EXISTS (SELECT 1 FROM animais WHERE nome = 'Simba');

INSERT INTO animais (nome, idade, peso, sexo, especie_id, caracteristica_especifica)
SELECT 'Jade', 3, 1.30, 'Femea', id, 'Penas azul-cobalto'
FROM especies
WHERE nome = 'Arara-azul'
  AND NOT EXISTS (SELECT 1 FROM animais WHERE nome = 'Jade');

INSERT INTO animais (nome, idade, peso, sexo, especie_id, caracteristica_especifica)
SELECT 'Tito', 12, 5.50, 'Macho', id, 'Escamas escuras e casco avermelhado'
FROM especies
WHERE nome = 'Jabuti-piranga'
  AND NOT EXISTS (SELECT 1 FROM animais WHERE nome = 'Tito');

INSERT INTO alimentacoes (animal_id, alimento, quantidade_kg, data_hora)
SELECT id, 'Carne bovina', 4.00, NOW() FROM animais
WHERE nome = 'Simba'
  AND NOT EXISTS (SELECT 1 FROM alimentacoes WHERE animal_id = animais.id);

INSERT INTO alimentacoes (animal_id, alimento, quantidade_kg, data_hora)
SELECT id, 'Frutas', 0.20, NOW() FROM animais
WHERE nome = 'Jade'
  AND NOT EXISTS (SELECT 1 FROM alimentacoes WHERE animal_id = animais.id);

INSERT INTO alimentacoes (animal_id, alimento, quantidade_kg, data_hora)
SELECT id, 'Folhas', 0.30, NOW() FROM animais
WHERE nome = 'Tito'
  AND NOT EXISTS (SELECT 1 FROM alimentacoes WHERE animal_id = animais.id);
