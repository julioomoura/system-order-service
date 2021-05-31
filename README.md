# System Order Service - SOS
Este é um projeto realizado para a matéria estágio supervisionado no curso de Ciência da Computação no CEUB. É uma aplicação responsável pelo gerenciamento de ordens de serviço. O fluxo básico da aplicação é:

1. Um **cliente** cria uma ordem de serviço

2. Um **administrador** visualizar ordens de serviço e distribui para desenvolvedores atenderem o serviço, até certo 
   prazo de dias.
   
3. O desenvolvedor visualiza as ordens de serviço atribuídas a ele. Realiza o trabalho e finaliza a ordem de serviço.
   
## Setup para rodar o projeto

### Usando o docker para rodar a aplicação completa(Backend Server e MySQL Server)

Clonando o repositório

```
$ git clone git@github.com:julioomoura/system-order-service.git
```

Rodando a aplicação

```
$ docker-compose up --build
```

Só esperar o build e a execução do projeto. Por padrão a aplicação está sendo exposto na porta **8080**.

### Rodando a aplicação no terminal
Para instalar o Java 11 podemos utilizar o sdkman

```
$ curl -s "https://get.sdkman.io" | bash
```

```
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
```

Usar a versão 11 do Java
```
$ sdk env
```
Rodando a aplicação utilizando o Maven
```
$ ./mvnw spring-boot:run
```
