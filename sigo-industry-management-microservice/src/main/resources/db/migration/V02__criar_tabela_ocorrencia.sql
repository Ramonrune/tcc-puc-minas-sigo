  CREATE TABLE ocorrencia(
 	codigo BIGINT(20) PRIMARY KEY,
 	codigo_processo_industrial BIGINT(20) NOT NULL,
	codigo_usuario BIGINT(20) NOT NULL,
    codigo_filial BIGINT(20) NOT NULL,
 	nome VARCHAR(200) NOT NULL,
    status SMALLINT NOT NULL,
  	descricao TEXT NOT NULL,
    data_ocorrencia DATETIME NOT NULL,
	FOREIGN KEY (codigo_processo_industrial) REFERENCES processo_industrial(codigo) ON DELETE CASCADE
 );
 
 
 
