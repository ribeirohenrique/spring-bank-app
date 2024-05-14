## spring-bank-app
### Descrição
Repositório para armazenamento do código referente ao desafio Accenture de criação de uma aplicação bancária utilizando os conceitos aprendidos durante o curso de Java e Spring Boot

### Solicitação
Faça uma aplicação bancária que permita transferências de valores. O sistema deve conter as seguintes funcionalidades (mínimas):

- Cadastro de conta bancária;
- Numero da conta, numero agencia, cliente, saldo, limite, tipo da conta;
- Depósito;
- Retirada (saque);
- Alteração de limite;
- Transferências;
- Pensem em limitar o valor conforme o horário;
- Exportação de histórico de transações (CSV);
- A aplicação deve persistir informações em banco de dados;
- A documentação da aplicação com Swagger é mandatória;

### Considerações
- Perceba que a descrição do sistema foi feita de forma genérica, propositalmente para encorajar a extensão de funcionalidades conforme a sua necessidade;
- As funcionalidades descritas acima são básicas e mandatórias para o funcionamento e aceite da entrega;
- Utilize o máximo de conceitos abordados durante o curso. boas práticas de nomenclatura, herança, listas, interfaces, trabalho com arquivos, etc;
- Os relacionamentos entre as classes (entidades do sistema) ficam ao seu critério. Utilizem quantas classes e atributos julgarem necessário para a modelagem;
- Sigam o princípio: baixo acoplamento, alta coesão;
- Para estruturar seu código, imagine a aplicação como um entregável que possa ser evoluído sem a necessidade de grande refatoração.
- Pensem que toda operação repetitiva pode ter sua própria classe ou método, como apresentação das informações na tela (ou input), que pode ter dados como parâmetros.

### Modo de utilização
- Para executar, basta ir ao arquivo [SpringBankAppApplication.java](src/main/java/com/course/springbankapp/SpringBankAppApplication.java) e executar o método **Main**;
- O arquivo de exportação é designado por [account_<accountId>_history.csv](account_3_history.csv) sendo mapeado para ser gerado na raiz do projeto;
- Agora o dado é persistente sendo configurado com o banco de dados MariaDB através do serviço [Filess](https://dash.filess.io/);
- O projeto foi atualizado para ser executado dentro de um container Docker e hospedado através do [Render](https://render.com) no endereço [spring-bank-app](https://spring-bank-app.onrender.com);
- O Swagger da aplicação está disponível em [spring-bank-app-swagger](https://spring-bank-app.onrender.com/swagger-ui/index.html);
- Para facilitar o teste, existe uma pasta com a collection do Postman utilizada [Collection](postman_collection/spring-bank-app.postman_collection.json);
- O saldo de toda conta nova, inicia em zero, sendo necessário efetuar um depósito inicial e aumento de limite para realizar transações, visto que existem regras que validam o saldo e limite;
- O projeto foi desenvolvido com algumas regras seguindo modelos de bancos existentes, analisando comportamento de uso cotidiano como, por exemplo:
1. Não permitir a criação de contas com o mesmo número;
3. Ao buscar uma conta, se a mesma não existir, retornar erro;
5. Saque só é efetivado **se** houver saldo disponível **e se** for abaixo do limite de saque da conta;
6. Transferência entre contas só é efetivada **se** as duas contas existirem, **se** houver saldo na conta remetente **e se** for abaixo do limite de saque;
7. Transferência entre contas contam com limitação das 21h às 6h da manhã

### Especificações
1. O formato de armazenamento no arquivo **.csv** seguirá o seguinte padrão:
> **AccountNumber,ClientName,Description,Date**
>
> 41424240,Camila Quintana de Almeida,Conta criada.,2024-05-14 12:38:18.705

### Dificuldades encontradas
- O processo de deploy da aplicação em serviços da web foi trabalhoso. O Heroku não tem mais um pacote gratuito, e até então eu não tinha uma imagem docker, tive que aprender a criar uma com base no vídeo da [Daniele Leão](https://youtu.be/fwWvgk_SW2g?si=xDYU8HGtSFCzg3dp);
- Tive problemas com banco de dados durante o deploy, com erro persistente da URL de acesso, para resolver, decidi deixar persistente e remover apenas a senha, que posteriormente foi passada como variável de ambiente;
- O processo de criação do arquivo foi um pouco diferente do desafio em CLI, foram necessárias adaptações ao código para funcionar corretamente com Header de leitura do CSV;
