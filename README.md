#QUICKLYAPPLICATION

API criada para gerenciar pedidos de produtos ou serviços, proporcionando um controle simples, porém objetivo,
dos pedidos de uma pessoa ou organização.

O projeto está disponibilizado para testes e análises no GitHub e a API está ativa no Heroku:
GitHub: https://github.com/symonnsantos/quickly
Heroku: https://dashboard.heroku.com/apps/quicklyappsenior


::: Passo a passo para clonar o projeto e subi-lo no IntelliJ :::

• No link do projeto, clicar em 'Code' > guia HTTPS e copiar o link que será disponibilizado;
• No Git Bash do seu ambiente local, utilizar o comando git clone https://github.com/symonnsantos/quickly.git;
• Importar o projeto no seu IntelliJ ou IDE de preferência.
OBS: Certifique-se de que a sua IDE está utilizando a versão 1.8 Java. Caso contrário, não será possível realizar o 
build do projeto;

No arquivo 'application.properties' existem dois padrões de configurações diferentes. O primeiro diz respeito às
configurações para acesso à base de dados do Heroku. O segundo diz respeito às configurações para testes internos,
subindo a aplicação pela IDE. Os testes poderão ser realizados via Postman através da Collection disponibilizada também
no projeto, no arquivo Quickly.postman_collection.json.
Para utilizar determinado padrão de configuração, basta comentar (Usando #) as linhas do que não será utilizado.


::: Informações sobre a estrutura do projeto :::

O projeto dispõe de 3 (três) classes diferentes: ItensPedido, Pedido e ProdutoOuServiço. A classe ItensPedido funciona
como uma espécie de 'classe auxiliar' para a classe Pedido.
O usuário poderá realizar cadastros de seus produtos/serviços, especificando através de um ENUM se se trata de um produto
ou de um serviço. Feito isso, poderá realizar o cadastro de seus pedidos.
Algumas observações importantes:
• Um pedido possui um status (Aberto ou Finalizado) e uma lista de Itens. Cada item possui um preço e uma quantidade, com
a possibilidade de aplicar um desconto ao valor de cada item;
• Não é possível aplicar desconto a um pedido aberto, nem a um item do tipo SERVIÇO;
• Não é possível adicionar um produto/serviço que esteja inativo a um pedido;
• Não é possível excluir um produto/serviço caso ele esteja associado a algum pedido;


::: Tecnologias utilizadas no projeto :::

• Java 1.8;
• Spring Framework 2.7.0;
• Spring Data JPA;
• Spring Web;
• Lombok;
• Postgre SQL;