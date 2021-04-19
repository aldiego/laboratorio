O Banco de dados esta sendo gerado pela aplicação, depois de subir
é bom colocar a propriedade ddl-auto como none.

#[Swagger link](https://www.jetbrains.com)

Para rodar os comandos abaixo precisa estar dentro da pasta do projeto.

Para rodar os teste integrados precisar rodar o comando:
```
./gradlew integrationTest
``` 

Comando para gerar a imagem.
```
./gradlew clean build && docker build -t laboratorio-crud:latest .
```

Para iniciar os serviços precisa rodar os comanda:
```
./gradlew clean build && docker build -t laboratorio-crud:latest . && docker-compose up
```