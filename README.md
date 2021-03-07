# TCC | PUC Minas - Sistema Integrado de Gestão e Operação


Este repositório possui os artefatos do TCC de pós-graduação em Arquitetura de Software Distribuído da PUC Minas. Trata-se de um protótipo de uma aplicação que realiza a gestão operacional de processos para uma empresa do setor têxtil. O objetivo deste trabalho é avaliar se a arquitetura proposta é viavel.

## Pré-requisitos
Para executar este projeto, são necessárias as seguintes ferramentas:
- Java (versão 8 no mínimo)
- Node.js
- Docker
- Kubernetes


## Instalação


Para instalar as dependencias do VueJS, execute o seguinte comando:
```sh
cd sigo-app
yarn install
```

Para instalar as dependencias do Java, execute o seguinte comando:
```sh
cd sigo-identity-microservice
mvn install

cd sigo-standard-microservice
mvn install

cd sigo-industry-management-microservice
mvn install

cd sigo-consultancy-microservice
mvn install
```

## Arquitetura
A arquitetura deste protótipo é baseada em microserviços, os quais se comunicam via REST, SOAP e mensageria utilizando RabbitMQ. 
Abaixo é possível verificar o diagrama de componentes da aplicação.

![alt text](https://github.com/Ramonrune/tcc-puc-minas-sigo/blob/main/sigo-docs/Diagrama_componentes.jpg?raw=true)

Abaixo é possível verificar o diagrama de implantação da aplicação.
![alt text](https://github.com/Ramonrune/tcc-puc-minas-sigo/blob/main/sigo-docs/Diagrama_implantacao.jpg?raw=true)


## Microserviços

Os seguintes microserviços são utilizados no backend:

| Microserviço | Descrição |
| ------ | ------ |
| sigo-identity-microservice | Gerenciar usuários, filiais e toda parte de segurança da aplicação|
| sigo-standard-microservice | Gestão de normas técnicas |
| sigo-industry-management-microservice | Gestão de processos industriais|
| sigo-consultancy-microservice | Gestão de consultorias e assessorias |
| sigo-app | Aplicação frontend |


## Tecnologias

A arquitetura utiliza as seguintes tecnologias:

- Java
- Spring boot
- Docker
- Kubernetes
- AWS (S3, RDS, EKS, ECR, AWS Direct Connect)
- REST, SOAP e RabbitMQ
- Redis
- Elasticsearch
- OAUTH e JWT
- MySQL, Spring JPA e Flyway
- Jenkins
- Vuejs
- Quasar framework
- JavaScript




## Implantação

Para implantar a aplicação em Kubernetes, basta executar o seguinte comando em cada projeto:

```sh
sh deploy.sh
```

Este comando irá executar as seguintes instruções:

```sh
echo "Criando build e testes "
./mvnw.cmd package
echo "Minikube"
minikube docker-env
echo "Criando imagem docker "
docker build -t NOME_MICROSERVICO .
echo "Kubernetes deploy"
kubectl apply -f deployment.yaml
```

No caso do frontend, basta executar o mesmo comando.


## Licença

MIT
