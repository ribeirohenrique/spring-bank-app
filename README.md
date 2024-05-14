## desafio-aplicacao-bancaria
### Descrição
Repositório para armazenamento do código referente ao desafio Accenture de criação de uma aplicação bancária utilizando os conceitos aprendidos durante o curso de Java

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
- Para estruturar seu código, imagine a aplicação como um entregável que possa ser evoluído sem a necessidade de grande refatoração. Ex: não tenho um banco de dados hoje ou uma API Rest para acesso às operações, mas posso construir um módulo sem afetar O DOMÍNIO do sistema (classes de negócio e entidades);
- Pensem que toda operação repetitiva pode ter sua própria classe ou método, como apresentação das informações na tela (ou input), que pode ter dados como parâmetros.

### Modo de utilização
- Para executar, basta ir ao arquivo [SpringBankAppApplication.java](src/main/java/com/course/springbankapp/SpringBankAppApplication.java) e executar o método **Main**;
- O arquivo de exportação é designado por [account_<accountId>_history.csv](account_3_history.csv) sendo mapeado para ser gerado na raiz do projeto;
- Agora o dado é persistente e foi configurado com o banco de dados MariaDB através do serviço [Filess](https://dash.filess.io/);
- O saldo de toda conta nova, inicia em zero, sendo necessário efetuar um depósito inicial para realizar transações, visto que existem regras que validam o saldo;
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

2. Diagrama de classes
> [![](https://mermaid.ink/img/pako:eNqVU01vwjAM_StRToyPw649TIJt0g6MC9VOvZjEFGttUiUpVYXgty80qShiaOyU-OXZfnbsAxdaIk-4KMDaN4LcQJmpzmJzIXStHDtkirEZWztDKmeiIFRuBSUGeEnWnVIDyoJwpNWJuYvx4R-1aQOTfCzIUYl2VZcbNAGVut4UyCAkW1JJbkAP6H3-AgpQAq8Uxqe0rTp8woRBcLjCJlY0iuc0iI_W6amvOHhtScnegf4i7wmbqOVX8l6TDCINCm3koGGBH7RfiBNmYY_pTStT_br-Gt2yJVbakrsSHFp1V8uENeR20kDzP6_uf7doFugaRBUpNng_FkLsQOU4H_z5A7mP_WAOutIP551hiRMh_fdfA2iFoeocoYub9VWwjD9nnI1nsxd_vZ1kNvavw_yZ4lNeoimBpN-jTo533KHfD574qwTznXGfxfOgdnrdKsETZ2qc8ro6K4trdw2-S_L5eLKFwnoQO_MzLuv5OP4AHflURg?type=png)](https://mermaid.live/edit#pako:eNqVU01vwjAM_StRToyPw649TIJt0g6MC9VOvZjEFGttUiUpVYXgty80qShiaOyU-OXZfnbsAxdaIk-4KMDaN4LcQJmpzmJzIXStHDtkirEZWztDKmeiIFRuBSUGeEnWnVIDyoJwpNWJuYvx4R-1aQOTfCzIUYl2VZcbNAGVut4UyCAkW1JJbkAP6H3-AgpQAq8Uxqe0rTp8woRBcLjCJlY0iuc0iI_W6amvOHhtScnegf4i7wmbqOVX8l6TDCINCm3koGGBH7RfiBNmYY_pTStT_br-Gt2yJVbakrsSHFp1V8uENeR20kDzP6_uf7doFugaRBUpNng_FkLsQOU4H_z5A7mP_WAOutIP551hiRMh_fdfA2iFoeocoYub9VWwjD9nnI1nsxd_vZ1kNvavw_yZ4lNeoimBpN-jTo533KHfD574qwTznXGfxfOgdnrdKsETZ2qc8ro6K4trdw2-S_L5eLKFwnoQO_MzLuv5OP4AHflURg)

3. Diagrama de sequencia
> [![](https://mermaid.ink/img/pako:eNq1UsFqQjEQ_JWwpwr2B3IQWtpjS0GPuSzJqqF9m-dmw6OI_258L4pg8dDSnLIzszOHnT34FAgsZNoVYk8vETeCnWNTX4-i0cceWc2HpJ-JJ-9TYb0lVoKc0WtM7Hiim4l5XCzOe9Z4IVR6p6EhD7NJ3MZRfOVljZBPEq6g88adkEB9ylH_FHHHfoi6DYLDf_nrSbkmeSYdiLjh-Tc5MIeOpMMY6tn3p30HuqWOHNj6DSifDhwfqg6LpuU3e7AqheZQ-lAv1SoCdo1f-YK-hqhJLiCN49tUrrFjhyMIidSN?type=png)](https://mermaid.live/edit#pako:eNq1UsFqQjEQ_JWwpwr2B3IQWtpjS0GPuSzJqqF9m-dmw6OI_258L4pg8dDSnLIzszOHnT34FAgsZNoVYk8vETeCnWNTX4-i0cceWc2HpJ-JJ-9TYb0lVoKc0WtM7Hiim4l5XCzOe9Z4IVR6p6EhD7NJ3MZRfOVljZBPEq6g88adkEB9ylH_FHHHfoi6DYLDf_nrSbkmeSYdiLjh-Tc5MIeOpMMY6tn3p30HuqWOHNj6DSifDhwfqg6LpuU3e7AqheZQ-lAv1SoCdo1f-YK-hqhJLiCN49tUrrFjhyMIidSN)

4. Swagger das APIs expostas
> [Localhost](http://localhost:8080/swagger-ui.html)