CREATE TABLE norma(
	codigo BIGINT(20) PRIMARY KEY,
    orgao VARCHAR(100) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    resumo VARCHAR(200) NOT NULL,
    descricao TEXT NOT NULL,
    versao VARCHAR(10) NOT NULL,
    numeroEdicao VARCHAR(10) NOT NULL,
    dataEdicao DATE NOT NULL,
    inicioValidade DATE NOT NULL,
    categoria VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO norma VALUES(1, 'ABNT', 'NBR ISO 9001', 'Sistemas de gestão da qualidade -
Requisitos', 'Convém que a adoção de um sistema de gestão da qualidade seja uma decisão estratégica de uma organização. O projeto
e a implementação de um sistema de gestão da qualidade de uma organização são influenciados por várias necessidades,
objetivos específicos, produtos fornecidos, os processos empregados e o tamanho e estrutura da organização. Não é intenção
desta Norma impor uniformidade na estrutura de sistemas de gestão da qualidade ou uniformidade da documentação.
Os requisitos do sistema de gestão da qualidade especificados nesta Norma são complementares aos requisitos para produtos.
As informações identificadas como "NOTA" se destinam a orientar o entendimento ou esclarecer o requisito
associado.
Esta Norma pode ser usada por partes internas ou externas, incluindo organismos de certificação, para avaliar a capacidade
da organização de atender aos requisitos do cliente, os regulamentares e os da própria organização.', '2000', '1', '2000-12-01', '2001-01-29', 'QUALIDADE')



INSERT INTO norma VALUES(2, 'ABNT', 'ABNT NBR 16064', 'Produtos têxteis para saúde', 'A transmissão de agentes infecciosos durante procedimentos cirúrgicos invasivos pode ocorrer
de várias maneiras (ver Anexo B).
Campos cirúrgicos estéreis, aventais cirúrgicos estéreis e roupas estéreis para sala limpa são
utilizados para minimizar a disseminação de agentes infecciosos das, e para as, incisões cirúrgicas
nos pacientes, ajudando assim a prevenir infecções pós-operatórias nas feridas (ver Anexo B).
O desempenho necessário das coberturas para paciente, equipe cirúrgica e equipamento varia
de acordo com, por exemplo, o tipo e a duração do procedimento, o grau de umidade do campo
de operação, o grau de tensão mecânica em materiais e da suscetibilidade do paciente à infecção.
O uso de aventais cirúrgicos com resistência à penetração de líquidos pode também diminuir o risco
à saúde da equipe de cirurgia, devido aos agentes infecciosos transportados no sangue ou outros
fluidos corporais.', '2016', '3', '2016-10-26', '2016-10-26', 'TECNICA')



INSERT INTO norma VALUES(3, 'ABNT', 'ABNT NBR ISO 13688', 'Vestimentas de proteção ― Requisitos gerais', 'Esta Norma especifica os requisitos gerais de desempenho para ergonomia, inocuidade, designações
para tamanhos, envelhecimento, compatibilidade e marcação da vestimenta de proteção, além das
informações a serem fornecidas pelo fabricante em relação a vestimenta de proteção.
Esta Norma é aplicável somente em combinação com outras normas que contenham requisitos
específicos para o desempenho de proteção e não aplicada como uma Norma única.', '2017', '1', '2017-04-19', '2017-05-19', 'SEGURANCA')




INSERT INTO norma VALUES(4, 'ABNT', 'ABNT NBR 16360', 'Proteção ocular pessoal — Protetor ocular e
facial tipo tela — Requisitos', 'Esta Norma estabelece os requisitos para protetores oculares e facial tipo tela quanto aos materiais,
design, desempenho métodos de ensaio e marcação.
Esta Norma não se aplica aos protetores faciais e oculares para uso contra respingo de líquidos
(incluindo metais derretidos), riscos de sólidos quentes, perigos elétricos, radiação de infravermelho
e ultravioleta, bem como para uso em esportes, como hóquei e esgrima.', '2015', '1', '2015-04-07', '2015-05-07', 'SEGURANCA')




INSERT INTO norma VALUES(5, 'ABNT', 'ABNT NBR ISO 26000', 'Diretrizes sobre responsabilidade social', 'Esta Norma fornece orientações sobre os princípios subjacentes à responsabilidade social, reconhecendo a
responsabilidade social e o engajamento das partes interessadas, os temas centrais e as questões pertinentes à
responsabilidade social (ver Tabela 2) e formas de integrar o comportamento socialmente responsável na
organização (ver Figura 1). Esta Norma salienta a importância de resultados e melhorias no desempenho em
responsabilidade social.', '2010', '1', '2010-11-01', '2010-12-01', 'RESPONSABILIDADE SOCIAL')


INSERT INTO norma VALUES(6, 'ABNT', 'ABNT NBR 15599', 'Acessibilidade - Comunicação na prestação
de serviços', 'Esta Norma fornece diretrizes que promovem a acessibilidade na prestação de sewiços, contornando as barreiras
de comunicação existentes, por meio do princípio da redundância. Destina-se ao uso pelos prestadores de serviço
que buscam o atendimento a demanda das pessoas com dificuldades na comunicação, potencial mercado,
e a legislação pertinente em vigor.', '2008', '1', '2008-08-25', '2008-09-25', 'ACESSIBILIDADE')