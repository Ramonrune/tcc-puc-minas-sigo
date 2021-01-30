CREATE TABLE processo_industrial(
	codigo BIGINT(20) PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    -- 0 - Não iniciado
    -- 1 - Em andamento
    -- 2 - Falha
    -- 3 - Finalizado
    -- 4 - Cancelado
    status SMALLINT NOT NULL,
    descricao TEXT NOT NULL,
    data_inicio_planejamento DATE NOT NULL,
    data_fim_planejamento DATE NOT NULL,
    codigo_usuario BIGINT(20) NOT NULL,
    codigo_filial BIGINT(20) NOT NULL,
    codigo_externo VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO processo_industrial VALUES(1, 'Fabricação de tecidos de algodão', 1, 'O algodão é uma das matérias-primas mais utilizadas pela indústria têxtil, dando origens a fibras que se destacam pela qualidade e conforto.',
 '2021-01-12', '2021-01-22', 1, 1, '65807130-e1b5-424e-b1d7-99823b61b8ae');


 
 CREATE TABLE processo_industrial_item(
 	codigo BIGINT(20) PRIMARY KEY,
 	codigo_processo_industrial BIGINT(20) NOT NULL,
 	nome VARCHAR(200) NOT NULL,
    status SMALLINT NOT NULL,
  	descricao TEXT NOT NULL,
    data_inicio DATETIME NOT NULL,
    qtd_horas_prevista SMALLINT NOT NULL,
    qtd_horas_realizada SMALLINT NOT NULL,
	codigo_externo_processo_industrial_item VARCHAR(100) NOT NULL,
	FOREIGN KEY (codigo_processo_industrial) REFERENCES processo_industrial(codigo) ON DELETE CASCADE
 );
 
 INSERT INTO processo_industrial_item VALUES(1, 1, 'Armazenamento do algodão', 3, 'A matéria-prima chega à indústria em grandes fardos de até 190kg, a fim de facilitar a sua armazenagem.',
 '2021-01-12 12:00:00', 8, 7,  '65807130-e1b5-424e-b1d7-99823b61b8ae');

 
 
