CREATE TABLE empresa_consultoria(
	codigo BIGINT(20) PRIMARY KEY,
   	nome VARCHAR(100) NOT NULL,
   	cnpj VARCHAR(14) NOT NULL,
   	telefone VARCHAR(10) NOT NULL,
    email VARCHAR(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE consultoria(
	codigo BIGINT(20) PRIMARY KEY,
    codigo_empresa_consultoria BIGINT(20) NOT NULL,
	setor VARCHAR(200) NOT NULL,
    motivo TEXT NOT NULL,
    data_contratacao DATE NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
	codigo_filial BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_empresa_consultoria) REFERENCES empresa_consultoria(codigo) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE anexo(
	codigo BIGINT(20) PRIMARY KEY,
    codigo_consultoria BIGINT(20) NOT NULL,
	titulo VARCHAR(200) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    data DATE NOT NULL,
    origem SMALLINT NOT NULL,
	FOREIGN KEY (codigo_consultoria) REFERENCES consultoria(codigo) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 