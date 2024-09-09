Este projeto é uma evolução do CRUD básico da Livraria, refatorado para uma arquitetura orientada a eventos, utilizando RabbitMQ como message broker. Com essa abordagem, o sistema torna-se mais escalável e resiliente, promovendo o desacoplamento entre microserviços através da comunicação baseada em eventos.

No contexto desta aplicação, ao cadastrar um autor no microserviço `autorservice`, uma mensagem é enviada para uma fila do RabbitMQ, que é então consumida pelo serviço `livraria`, atualizando o banco de dados da livraria com os dados do novo autor. Esse padrão de arquitetura orientada a eventos facilita a integração entre microserviços de forma assíncrona e eficiente.

### Tecnologias Utilizadas:

- **Spring Boot**: Framework principal para a construção dos microserviços.
- **RabbitMQ**: Message broker para comunicação assíncrona entre os serviços.
- **Spring AMQP**: Abstração de integração entre Spring Boot e RabbitMQ.
- **Spring Data JPA**: Persistência de dados com JPA (Java Persistence API).
- **H2 Database**: Banco de dados em memória para testes e desenvolvimento.
- **RestTemplate**: Para comunicação REST entre os microserviços.

---

### Como Testar:

**Passo a Passo para Testar as APIs:**

1. Inicie ambos os microserviços (`autorservice` e `livraria`).
2. Acesse as interfaces de cadastro para inserir novos autores e livros.

- **Para cadastrar um autor**:  
  Acesse `localhost:8080/cadastro-autor.html` no seu navegador.  
  Ao cadastrar um autor, o serviço `autorservice` grava o autor no seu próprio banco e emite um evento através do RabbitMQ.  
  O serviço `livraria` então consome esse evento e salva o autor no banco de dados da livraria.

- **Para cadastrar um livro**:  
  Acesse `localhost:8080/cadastro-livro.html`.  
  Nessa interface, ao cadastrar um livro, o sistema associa o livro ao autor previamente registrado (cadastrado no banco de dados da livraria via RabbitMQ).

**Diferença entre a versão anterior e a atual:**
- Na versão anterior, os autores eram gerenciados separadamente no microserviço `autorservice`, sem comunicação direta com a `livraria`.
- Agora, com RabbitMQ, além de salvar o autor no banco do `autorservice`, o autor é automaticamente cadastrado no banco de dados da `livraria` de forma assíncrona via eventos.

---

Esse modelo orientado a eventos torna a arquitetura mais flexível e preparada para escalar, permitindo que múltiplos serviços se comuniquem eficientemente sem a necessidade de forte acoplamento entre eles.
