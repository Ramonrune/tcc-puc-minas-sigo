CREATE TABLE filial(
	codigo BIGINT(20) PRIMARY KEY,
   	nome VARCHAR(100) NOT NULL,
   	cnpj VARCHAR(14) NOT NULL,
   	telefone VARCHAR(10) NOT NULL,
    logradouro VARCHAR(200) NOT NULL,
    numero VARCHAR(30) NOT NULL,
    complemento VARCHAR(30),
    bairro VARCHAR(30) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE usuario_filial(
	codigo_usuario BIGINT(20) NOT NULL,
    codigo_filial BIGINT(20) NOT NULL,
    PRIMARY KEY (codigo_usuario, codigo_filial),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_filial) REFERENCES filial(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




INSERT INTO filial values (1, 'IND TEXTIL BRA FILIAL MATRIZ', '11253910000100', '1936459991',  'Rua do Abacaxi', '10', null, 'Vila uberlandia', '15076610', 'Uberlândia', 'MG');

INSERT INTO filial values (2, 'IND TEXTIL BRA FILIAL FILIAL SP', '09469704000182', '1936459992', 'Rua Joaquim', '165', null, 'Vila Santos', '13495014', 'Santa Barbara Do Oeste', 'SP');

INSERT INTO filial values (3, 'IND TEXTIL BRA FILIAL FILIAL MT', '03401886000173', '1936459993', 'Rua Cruzeiro', '98', null, 'Vila aparecida', '13455542', 'Cuiaba', 'MT');


-- admin
INSERT INTO usuario_filial (codigo_usuario, codigo_filial) values (1, 1);
INSERT INTO usuario_filial (codigo_usuario, codigo_filial) values (1, 2);

-- common user
INSERT INTO usuario_filial (codigo_usuario, codigo_filial) values (2, 2);

-- audit user
INSERT INTO usuario_filial (codigo_usuario, codigo_filial) values (3, 3);

