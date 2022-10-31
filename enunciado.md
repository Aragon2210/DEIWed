# DEIWed: Exercício Prático BL204/2022_IST-ID

O **DEI às Quartas** (ou _Wednesdays @ DEI_) é uma iniciativa do Departamento de Engenharia Informática (DEI) que consiste em palestras semanais sobre temas de interesse geral, visando a divulgação de conhecimento, a partilha perspetivas novas e o diálogo sobre assuntos da atualidade na área da Informática.

Cada sessão do DEI às Quartas acontece numa quarta-feira (não havendo, contudo, necessariamente uma sessão todas as quartas-feiras - apesar de tal ser comum em período de aulas) e é composta por uma palestra (dada por um orador convidado) e por um almoço que precede a palestra, financiado e organizado pelo DEI. Pode ser um orador qualquer pessoa interna ou externa ao DEI e qualquer docente de carreira ou bolseiro do Departamento pode estar presente em sessões do DEI às Quartas (no almoço e/ou na palestra).

De forma a facilitar a gestão das sessões do DEI às Quartas, é necessário o desenvolvimento de uma aplicação web simples para o efeito: o **DEIWed: Wednesdays @ DEI Management System** (o seu exercício).

Esta aplicação deve permitir a gestão de:

-   possíveis participantes em sessões do DEI às Quartas (`Attendees`), guardando, para cada um, pelo menos o seu nome, IST ID, se é docente ou bolseiro, e outros campos que considere relevantes;
-   possíveis pratos a servir no almoço integrado na sessão (`Dishes`; guardados externamente - ver abaixo);
-   sessões do DEI às Quartas (`Sessions`), guardando, para cada uma, pelo menos a data, o orador, o tema da palestra, um poster em formato de imagem (opcionalmente), os pratos servidos ao almoço (opção normal e opção vegetariana), participantes inscritos e outros campos que considere relevantes;

Visto que os pratos não são confecionados no DEI, mas sim fornecidos por uma empresa externa, a base de dados da aplicação não deve conter informação sobre os pratos, estando esta presente no sistema proprietário **_FoodStore_** (que já se encontra desenvolvido). A _FoodStore_ disponibiliza uma API REST que permitirá a integração do **DEIWed** com a mesma de forma transparente ao utilizador.

Deve consultar a documentação da API da _FoodStore_[\[1\]] para obter mais informações sobre a mesma e determinar como a utilizar. Por motivos de segurança, o sistema _FoodStore_ exige que cada aplicação (incluindo o **DEIWed**) se autentique com um _token_ - para efeitos deste exercício, deve utilizar o seu IST ID (por exemplo, `ist199311`) como _token_. A exceção são _endpoints_ de consulta, que não necessitam de autenticação. A aplicação **DEIWed** deve ser tão flexível quanto o sistema _FoodStore_ permitir, no que toca à gestão de pratos.

Note-se que, por razões de segurança, a API da _FoodStore_ só permite atualizar e eliminar pedidos (`Orders`) a quem os criou originalmente (comparando o _token_ enviado com o _token_ original). Para além disso, a manipulação de pratos disponíveis (`Dishes`) é, evidentemente, da responsabilidade da empresa de catering, não sendo possível a aplicações externas, portanto, adicionar, atualizar ou remover pratos através da API da _FoodStore_ (os _endpoints_ para esse fim requerem autenticação via _HTTP Basic Auth_, cujas credenciais não tem - o seu exercício só deve implementar _Bearer Auth_).

Inicialmente, o **DEIWed** será utilizado apenas por funcionários dos Serviços do DEI, estando acessível apenas dentro da rede da Secretaria. Para além disso, não se pretende, por agora, a implementação de mecanismos de auditoria.

## Implementação

O objetivo deste exercício é desenvolver a aplicação **DEIWed** usando Spring Boot[\[2\]] (backend) e Vue.js[\[3\]] com Class Components[\[4\]] (frontend).

A aplicação **DEIWed** deverá permitir, pelo menos:

-   Visualizar todos os participantes numa tabela de consulta rápida, contendo pelo menos nome e IST ID;
-   Adicionar novos participantes;
-   Atualizar e remover participantes existentes;
-   Visualizar todos os pratos disponíveis numa tabela de consulta rápida, contendo pelo menos nome e preço;
-   Visualizar todas as sessões numa tabela de consulta rápida, contendo pelo menos data, orador e tema;
-   Visualizar, na mesma tabela (descrita acima) ou noutra com características semelhantes, todas as sessões a que um participante está inscrito;
-   Visualizar uma sessão em particular numa página dedicada, contendo pelo menos data, orador, tema, poster (se existir), pratos servidos ao almoço e participantes inscritos;
-   Adicionar novas sessões, com um poster opcional (uma imagem);
-   Atualizar e remover sessões existentes;
-   Inscrever e desinscrever participantes em sessões existentes; e
-   Avisar a empresa de _catering_ sobre que pratos necessita de fornecer em que dias, de forma a que estes possam ser preparados e entregues no DEI (deve escolher se esta ação deve ser tomada de forma explícita pelo utilizador ou automaticamente pelo sistema nos momentos indicados).

É permitida (mas não obrigatória) a implementação de funcionalidades adicionais às acima, especialmente se considerar que algum aspeto melhoraria significativamente a _User Experience (UX)_ durante a utilização do sistema.

É-lhe fornecido **código base** que deve analisar e utilizar como ponto de partida, estando algumas das funcionalidades acima já parcial ou integralmente implementadas. Deve seguir as convenções presentes no código base. Não é necessária a implementação de testes.

Deve realizar o exercício de forma modular. Serão valorizadas qualidade e estética do código e da interface web apresentada.

## Submissão

Deve submeter num arquivo comprimido por email a sua solução deste exercício prático e um ficheiro `README` que descreva o procedimento para iniciar um servidor local de testes. Pode incluir nesse ficheiro `README` qualquer informação que considere relevante para a avaliação do seu trabalho, tal como alguma eventual justificação de decisões tomadas. Não submeta artefactos irrelevantes (e.g., pasta `node_modules`) - evite incluir ficheiros listados em `.gitignore`s.

Apesar de não ser obrigatório nem alvo de avaliação de forma direta, é recomendado que utilize um sistema de controlo de versões (especificamente, `git`[\[5\]]) durante o desenvolvimento da sua solução, não devendo excluir os metadados respetivos (nomeadamente, a pasta `.git`) aquando da submissão desta. Desse modo, ser-nos-á possível validar a sua capacidade de utilização da ferramenta, podendo ser-lhe benéfico durante a componente da entrevista.

**Prazo máximo de entrega: domingo, dia 6 de outubro de 2022, às 23h59.**

Não serão aceites quaisquer entregas após o prazo limite, por qualquer razão. Qualquer candidato que não entregue uma solução até ao prazo limite será automaticamente avaliado com a cotação de 0 (zero) valores na componente de seleção correspondente ao exercício prático.

**Boa sorte!**

**NOTA:** Durante os testes de adição de novos pratos à _FoodStore_, pedimos que seja responsável nos dados utilizados, sendo este um sistema partilhado entre todos os candidatos.

## Recursos Potencialmente Úteis

-   https://vuetifyjs.com/en/
-   https://spring.io/guides
-   https://www.javatpoint.com/spring-boot-architecture

[\[1\]]: https://eindhoven.rnl.tecnico.ulisboa.pt/food-store
[\[2\]]: https://spring.io/projects/spring-boot
[\[3\]]: https://vuejs.org/
[\[4\]]: https://class-component.vuejs.org/
[\[5\]]: https://git-scm.com/
