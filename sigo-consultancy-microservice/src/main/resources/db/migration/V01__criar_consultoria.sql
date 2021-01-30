CREATE TABLE consultoria_assessoria(
	codigo BIGINT(20) PRIMARY KEY,
   	nome VARCHAR(100) NOT NULL,
   	cnpj VARCHAR(14) NOT NULL,
   	telefone VARCHAR(10) NOT NULL,
    email VARCHAR(200) NOT NULL
    -- data_contratacao DATE NOT NULL,
    -- data_inicio DATE NOT NULL,
    -- data_fim DATE NOT NULL,
    -- quantidade_meses SMALLINT NOT NULL,
    -- status_andamento SMALLINT NOT NULL,
    -- ativa SMALLINT NOT NULL,
    -- setor VARCHAR(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;