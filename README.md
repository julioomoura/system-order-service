# System Order Service - SOS

## Setup para rodar o projeto

### Instalação do Java 11
Para instalar o Java 11 podemos utilizar o sdkman

```
$ curl -s "https://get.sdkman.io" | bash
```

```
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
```

```
$ sdk install java 11.0.10.hs-adpt
```

### Instalação do IntelliJ
Para o desenvolvimento, utilizaremos a IDE IntelliJ IDEA

```
$ sudo snap install intellij-idea-community --classic
```

## Rodando o projeto

### Setup do banco de dados MySQL

#### Subir um container docker com o MySQL
```
$ docker-compose up -d
```

#### Acompanhar os logs do container
```
$ docker-compose logs -f
```

#### Script para criação de esquema
```
$ bash setup-db.sh
```

### Compilar o projeto
```
$ ./mvnw clean compile    
```

### Executar o projeto
```
$ ./mvnw spring-boot:run
```