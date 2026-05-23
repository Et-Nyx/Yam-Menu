# Projeto A3 – Algoritmos e Programação Java
## Sistema de Menu de Lanchonete — Yam Menu

Sistema de menu de lanchonete operado pelo terminal. O cliente navega por um
cardápio interativo, monta uma comanda, visualiza o resumo do pedido e, ao
finalizar, o pedido é gravado em um arquivo `.txt`.

### Integrantes do grupo

| Nome | RA |
| --- | --- |
| Manoel Fonseca Pinto | 12826230313 |
| Eduardo Teixeira de Moura Silva | 12826135050 |
| Edson Câmara de Oliveira | 12826123712 |

**Repositório (GitHub):** https://github.com/Et-Nyx/Yam-Menu.git

---

## 1. O que o sistema faz

O Yam Menu cumpre todos os requisitos da atividade:

- ✔ **Exibe um menu de opções** da lanchonete em caixas desenhadas no terminal.
- ✔ **Permite selecionar produtos** e a quantidade de cada um.
- ✔ **Usa `switch case`** para tratar as opções do menu inicial e a criação de itens.
- ✔ **Usa laços de repetição** (`do/while` e `while`) para manter o sistema rodando
  até o encerramento.
- ✔ **Exibe o resumo do pedido** na tela antes de finalizar.
- ✔ **Salva o pedido em um arquivo `.txt`** dentro da pasta `pedidos/`.

Recursos extras implementados além do mínimo pedido:

- Cardápio carregado de um arquivo externo (`menu/menu.csv`), sem valores
  "chumbados" no código.
- Cardápio paginado por tipo de produto (Salgado, Bebida, Doce, Especial).
- Interface em caixas com bordas e cores (códigos ANSI).
- Orientação a objetos com herança e polimorfismo.

---

## 2. Como compilar e executar

O projeto usa apenas a biblioteca padrão do Java. Os comandos abaixo devem ser
executados **a partir da raiz do projeto** (a pasta que contém `src/` e `menu/`),
porque os caminhos dos arquivos (`menu/menu.csv` e `pedidos/`) são relativos a ela.

```bash
# 1. Compilar todas as classes para a pasta out/
javac -d out src/*.java

# 2. Executar a aplicação
java -cp out App
```

> **Atenção aos caminhos:** se você executar de dentro de `src/`, o programa não
> vai encontrar `menu/menu.csv` e exibirá *"Cardápio indisponível"*. Sempre rode
> da raiz do projeto.

A pasta `pedidos/` é criada automaticamente na primeira vez que um pedido é salvo.

---

## 3. Estrutura de pastas

```
Yam-Menu/
├── README.md            Este guia
├── menu/
│   └── menu.csv         Cardápio (fonte de dados do sistema)
├── pedidos/             Pedidos salvos em .txt (criada em tempo de execução)
└── src/
    ├── App.java         Ponto de entrada e laço principal
    ├── Tela.java        Toda a interface de terminal (desenho e cores)
    ├── Menu.java        Lista de itens disponíveis no cardápio
    ├── Comanda.java     Pedido do cliente em construção
    ├── Pedido.java      Um item + quantidade dentro da comanda
    ├── Persistencia.java Leitura do CSV e gravação do .txt
    ├── Item.java        Classe abstrata base dos produtos
    ├── Salgado.java     Subclasse de Item
    ├── Bebida.java      Subclasse de Item
    ├── Doce.java        Subclasse de Item
    └── Especial.java    Subclasse de Item (com detalhe extra)
```

---

## 4. Arquitetura geral

O projeto foi organizado em três camadas, separando *o que é dado*, *o que é
regra* e *o que é interface*:

```
            ┌──────────────────────────────────────────────┐
            │                   App.java                   │
            │ laço principal · switch case · leitura input │
            └───────────────┬───────────────┬──────────────┘
                            │               │
                desenha     │               │   regras de negócio
                a tela      ▼               ▼
                  ┌──────────────┐   ┌───────────────────────────┐
                  │   Tela.java  │   │ Menu · Comanda · Pedido   │
                  │  (interface) │   │ Item (+ subclasses)       │
                  └──────────────┘   └─────────────┬─────────────┘
                                                   │ lê / grava
                                                   ▼
                                        ┌───────────────────────┐
                                        │   Persistencia.java   │
                                        │  menu.csv   ->  txt   │
                                        └───────────────────────┘
```

- **App** coordena o fluxo: mostra o menu, lê a escolha, chama a ação certa e
  repete até o usuário sair.
- **Tela** concentra toda a parte visual. Nenhuma outra classe imprime caixas;
  isso mantém o desenho da interface em um só lugar.
- **Menu, Comanda, Pedido e Item** representam os dados e as regras (preço,
  subtotal, total, tipos de produto).
- **Persistencia** é a única classe que toca em arquivos: lê o cardápio e grava
  o comprovante do pedido.

---

## 5. As classes em detalhe

### 5.1. `Item` (abstrata) e suas subclasses

`Item` é a classe-mãe de todos os produtos. Ela guarda `nome` e `valor` e define
o que todo produto sabe fazer (`descricao()`, `formatarValor()`), deixando para
as subclasses dizerem **qual é o seu tipo** através do método abstrato
`getTipo()`.

```
                 Item (abstrata)
                   getTipo()  ← abstrato
        ┌──────────┬──────────┬───────────┐
     Salgado    Bebida      Doce       Especial
   "Salgado"  "Bebida"    "Doce"      "Especial" (+ detalhe)
```

| Subclasse | `getTipo()` | Observação |
| --- | --- | --- |
| `Salgado` | `"Salgado"` | produto simples |
| `Bebida` | `"Bebida"` | produto simples |
| `Doce` | `"Doce"` | produto simples |
| `Especial` | `"Especial"` | guarda um **detalhe** extra (ex.: "Serve 2 pessoas") |

A criação dos objetos usa o **fábrica com `switch case`** dentro de `Item.criar()`:

```java
public static Item criar(String tipo, String nome, double valor) {
    switch (tipo) {
        case "salgado": return new Salgado(nome, valor);
        case "bebida":  return new Bebida(nome, valor);
        case "doce":    return new Doce(nome, valor);
        default:        return new Especial(tipo, nome, valor);
    }
}
```

> **Detalhe importante do `Especial`:** quando o tipo do CSV **não é**
> `salgado`, `bebida` ou `doce`, ele cai no `default` e vira um `Especial`. Nesse
> caso a primeira coluna do CSV deixa de ser o "tipo" e passa a ser o **detalhe**
> exibido abaixo do item (a linha que começa com `↳`). Por isso, no `menu.csv`,
> os especiais usam textos como `Serve 2 pessoas` ou `Opcao vegana` na primeira
> coluna.

### 5.2. `Menu`

Envolve um `ArrayList<Item>` (o **vetor/lista** de produtos do cardápio).
Oferece `getItem(indice)` com proteção contra índice inválido e `tamanho()`.

### 5.3. `Pedido` e `Comanda`

- **`Pedido`** = um `Item` + uma `quantidade`. Calcula o `getSubtotal()`
  (`valor × quantidade`).
- **`Comanda`** = o pedido do cliente em construção. Guarda o nome do cliente e
  um `ArrayList<Pedido>`. Sabe adicionar item (`novoPedido`), remover
  (`removerPedido`), somar o total (`getTotal`, percorrendo a lista com um
  `for-each`) e montar o `resumo()` formatado e alinhado em colunas.

### 5.4. `Tela`

Responsável por **toda** a saída no terminal. Desenha as caixas com os
caracteres de borda (`┌ ─ ┐ │ └ ┘`), centraliza e alinha textos, aplica cores
ANSI e monta as três telas do sistema:

- `menuInicial()` — o menu principal (Fazer Pedido / Ver Comanda / Sair).
- `menuCardapio()` — o cardápio paginado por tipo, com navegação
  `[P]róximo / [A]nterior`.
- `menuComanda()` — o resumo da comanda com as ações `[F]inalizar / [R]emover`.

### 5.5. `Persistencia`

A camada de arquivos, com dois métodos:

- `carregarMenu(caminho)` — lê o `menu.csv` linha a linha (pulando o cabeçalho),
  faz `split(",")` em cada linha e usa `Item.criar(...)` para transformar cada
  linha em um objeto. Retorna o `ArrayList<Item>`.
- `salvarPedido(comanda)` — monta o texto do resumo e grava em
  `pedidos/pedido_<cliente>_<data-hora>.txt`. Cria a pasta `pedidos/` se ela não
  existir.

---

## 6. Formato do cardápio (`menu/menu.csv`)

O cardápio é um arquivo CSV simples com cabeçalho. Cada linha é um produto:

```
tipo,nome,valor
salgado,Pastel de Carne,5.00
bebida,Limonada,3.50
doce,Brownie,3.25
Serve 2 pessoas,Combo Familia,29.90
```

Regras para editar o arquivo sem quebrar a leitura:

1. **Mantenha o cabeçalho** `tipo,nome,valor` na primeira linha (ela é ignorada
   na leitura).
2. **Três colunas por linha**, separadas por vírgula.
3. **Não use vírgula** dentro do nome ou do valor — a vírgula é o separador.
4. **Use ponto** como separador decimal no valor (`5.00`, não `5,00`).
5. Tipos reconhecidos: `salgado`, `bebida`, `doce`. Qualquer outro texto na
   primeira coluna cria um item **Especial**, e esse texto vira o detalhe
   exibido abaixo do produto.

---

## 7. Fluxo de execução (passo a passo)

```
        início
          │
          ▼
   carrega menu.csv ──► vazio? ──► erro e encerra
          │ (ok)
          ▼
   pergunta o nome do cliente
          │
          ▼
  ┌───────────────────────────────┐
  │    LAÇO PRINCIPAL (do/while)  │
  │  mostra o menu inicial        │
  │  lê a opção                   │
  │ ┌─ switch ──────────────────┐ │
  │ │ 1 → Fazer Pedido          │ │
  │ │ 2 → Ver Comanda           │ │
  │ │ 0 → Confirmar saída       │ │
  │ │ * → "Opção inválida"      │ │
  │ └───────────────────────────┘ │
  └────────────┬──────────────────┘
               │ sair == true
               ▼
       (ao finalizar em "Ver Comanda")
       salva pedido em pedidos/*.txt
               │
               ▼
             fim
```

O programa só termina quando o usuário escolhe **Sair** (e confirma) ou quando
**finaliza** a comanda. Enquanto isso, o laço continua exibindo o menu.

---

## 8. Mapa dos conceitos da disciplina

A tabela liga cada conceito pedido na atividade ao ponto exato do código onde
ele aparece:

| Conceito | Onde está no projeto |
| --- | --- |
| **Estruturas condicionais** | `if/else` em quase todas as classes; validação de índice em `Menu.getItem`; checagens em `App` |
| **`switch case`** | `App.main` (menu inicial: 1 / 2 / 0 / default) e `Item.criar` (escolha da subclasse) |
| **Estruturas de repetição** | `do/while` no laço principal de `App`; `while (true)` na navegação do cardápio e da comanda; `for` / `for-each` em `Comanda`, `Persistencia` e `Tela` |
| **Vetores / coleções** | `ArrayList<Item>` em `Menu`; `ArrayList<Pedido>` em `Comanda`; `String[] colunas` do `split` no CSV |
| **Manipulação de arquivos** | `Persistencia` — `Files.readAllLines` para ler o CSV e `Files.writeString` para gravar o `.txt` |
| **Organização e legibilidade** | separação em classes por responsabilidade; herança em `Item`; nomes em português; interface isolada em `Tela` |

---

## 9. Exemplo de pedido gravado

Ao finalizar, é gerado um arquivo como `pedidos/pedido_Maria_20260606-143015.txt`
com o conteúdo:

```
 Cliente: Maria

 1 - 2x Pastel de Carne     R$10,00
 2 - 1x Limonada             R$3,50
 3 - 1x Brownie              R$3,25
------------------------------
 TOTAL              R$16,75
```
