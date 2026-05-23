import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);

    Menu menu = new Menu(Persistencia.carregarMenu("menu/menu.csv"));

    if(menu.tamanho() == 0) {
      Tela.erro("Cardápio indisponível. Ecerrando...");
      entrada.close();
      return;
    }

    Tela.limparTela();
    System.out.print("Nome do cliente: ");
    String nome = entrada.nextLine().trim();
    Comanda comanda = new Comanda(nome);

    boolean sair = false;
    do {
      Tela.limparTela();
      Tela.menuInicial();
      int escolha = lerEntrada(entrada);

      switch(escolha) {
        case 1: fazerPedido(menu, comanda, entrada); break;
        case 2: sair = verComanda(comanda, entrada); break;
        case 0: sair = confirmarSaida(comanda, entrada); break;
        default:
          Tela.erro("Opção inválida.");
          Tela.pausa(entrada);
      }
    } while (!sair);

    entrada.close();
  }

  static void fazerPedido(Menu menu, Comanda comanda, Scanner entrada) {
    while(true) {
      int indice = Tela.menuCardapio(menu, entrada);

      if(indice == -1) { break; }

      Item item = menu.getItem(indice);
      int quantidade = lerQuantidade(entrada);

      if(quantidade > 0) { comanda.novoPedido(item, quantidade); }
    }
  }

  static boolean verComanda(Comanda comanda, Scanner entrada) {
    while (true) {
      Tela.limparTela();

      if(comanda.isVazia()) {
        Tela.erro("Comanda vazia. Adicione itens primeiro.");
        Tela.pausa(entrada);
        return false;
      }

      Tela.menuComanda(comanda);
      System.out.print("-> ");

      String escolha = entrada.nextLine().trim();

      if(escolha.equalsIgnoreCase("f")) {
        if(!confirmar("Finalizar e salvar o pedido?", entrada)) {
          continue;
        }
        if(Persistencia.salvarPedido(comanda)) {
          Tela.sucesso("Pedido confirmado! Obrigado!");
        } else {
          Tela.erro("Erro ao salvar o pedido.");
        }
        Tela.pausa(entrada);
        return true;
      } else if(escolha.equalsIgnoreCase("r")) {
        System.out.println("Número do Item a remover: ");
        comanda.removerPedido(lerEntrada(entrada) - 1);
      } else if(escolha.equals("0")) {
        return false;
      } else {
        Tela.erro("Opção inválida.");
        Tela.pausa(entrada);
      }
    }
  }

  static boolean confirmar(String pergunta, Scanner entrada) {
    System.out.print(pergunta + " (S/N): ");
    return entrada.nextLine().trim().equalsIgnoreCase("s");
  }

  static boolean confirmarSaida(Comanda comanda, Scanner entrada) {
    if(!comanda.isVazia()) {
      Tela.erro("Você tem itens na comanda que serão cancelados.");
    }
    boolean confirmou = confirmar("Tem certeza que deseja sair?", entrada);

    if(confirmou) { Tela.sucesso("Até logo!");}
    return confirmou;
  }

  static int lerEntrada(Scanner entrada) {
    System.out.print("-> ");
    String linha = entrada.nextLine().trim();

    try {
      return Integer.parseInt(linha);
    } catch (NumberFormatException erro) {
      return -1;
    }
  }

  static int lerQuantidade(Scanner entrada) {
    System.out.print("Quantidade: ");
    int linha = lerEntrada(entrada);

    if(linha > 0) return linha;

    Tela.erro("Quantidade inválida.");
    Tela.pausa(entrada);
    return 0;
  }
}
