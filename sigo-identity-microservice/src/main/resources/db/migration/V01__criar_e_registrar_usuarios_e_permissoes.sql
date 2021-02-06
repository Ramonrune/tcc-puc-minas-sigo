CREATE TABLE usuario(
	codigo BIGINT(20) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(150) NOT NULL,
    codigo_consultoria BIGINT(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE permissao(
	codigo BIGINT(20) PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE usuario_permissao(
	codigo_usuario BIGINT(20) NOT NULL,
    codigo_permissao BIGINT(20) NOT NULL,
    PRIMARY KEY (codigo_usuario, codigo_permissao),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo) ON DELETE CASCADE,
    FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO usuario (codigo, nome, email, senha) values (1, 'Administrador', 'admin@sigo.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario (codigo, nome, email, senha) values (2, 'Ramon Lacava Gutierrez Gonçales', 'ramonrune@gmail.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario (codigo, nome, email, senha, codigo_consultoria) values (3, 'José dos Santos', 'jose_dos_santos@gmail.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.', 1);

INSERT INTO permissao (codigo, descricao) values (1, 'ROLE_ADMIN');
INSERT INTO permissao (codigo, descricao) values (2, 'ROLE_COMMON_USER');
INSERT INTO permissao (codigo, descricao) values (3, 'ROLE_AUDIT_USER');

-- admin
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (1, 1);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (2, 2);
INSERT INTO usuario_permissao (codigo_usuario, codigo_permissao) values (3, 3);

