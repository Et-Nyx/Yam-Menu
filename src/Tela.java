import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tela {
  public static final String RESET =      "\u001B[0m";
  public static final String BOLD =       "\u001B[1m";
  public static final String UNDERLINE =  "\u001B[4m";
  public static final String RED =        "\u001B[31m";
  public static final String GREEN =      "\u001B[32m";
  public static final String YELLOW =     "\u001B[33m";
  public static final String BLUE =       "\u001B[34m";
  public static final String MAGENTA =    "\u001B[35m";
  public static final String CYAN =       "\u001B[36m";
  public static final int LARGURA = 29;

  public static void limparTela() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static String centralizar(String texto, int largura) {
    int espaco = Math.max(0, largura - texto.length());
    int oeste = espaco / 2;

    return " ".repeat(oeste) + texto + " ".repeat(espaco - oeste);
  }

  public static void linha(String texto) {
    int faltam = Math.max(0, LARGURA - texto.length());
    System.out.println("│" + texto + " ".repeat(faltam) + "│");
  }

  public static void topo() {
    System.out.println("┌" + "─".repeat(LARGURA) + "┐");
  }
  public static void base() {
    System.out.println("└" + "─".repeat(LARGURA) + "┘");
  }
  public static void div() {
    System.out.println("├" + "─".repeat(LARGURA) + "┤");
  }
  public static void vazia() {
    linha("");
  }

  public static void caixaTitulo(String texto, boolean comSetas) {
    int interno = LARGURA - 2;

    System.out.println("│┌" + "─".repeat(interno) + "┐│");
    System.out.println("││" + " ".repeat(interno) + "││");

    String titulo = comSetas ?
        "<-" + centralizar(texto, interno - 4) +
        "->" : centralizar(texto, interno);
    System.out.println("││" + BOLD + titulo + RESET + "││");

    System.out.println("││" + " ".repeat(interno) + "││");
    System.out.println("│└" + "─".repeat(interno) + "┘│");
  }

  public static void corpo(List<String> escolhas) {
    vazia();

    for(String escolha : escolhas) {
      linha("     " + escolha);
    }
    vazia();
  }

  public static void caixaLegenda(String texto) {
    div();
    linha(" " + texto);
    base();
  }

  public static void desenharTela(
      String titulo,
      String legenda,
      List<String> escolhas,
      boolean comSetas
  ) {
    topo();
    caixaTitulo(titulo, comSetas);
    corpo(escolhas);
    caixaLegenda(legenda);
  }

  public static void menuInicial() {
    List<String> escolhas = new ArrayList<>();
    escolhas.add("1 - Fazer Pedido");
    escolhas.add("2 - Ver Cardápio");
    escolhas.add("0 - Sair");
    desenharTela(
      "YAM MENU",
      "Digite o número da opção",
      escolhas,
      false
    );
  }

  public static int menuCardapio(Menu menu, Scanner entrada) {
    ArrayList<String> tipos = new ArrayList<>();

    for(int i = 0; i < menu.tamanho(); i++) {
      String tipo = menu.getItem(i).getTipo();

      if(!tipos.contains(tipo)) {
        tipos.add(tipo);
      }
    }
    if (tipos.isEmpty()) return -1;

    int pagina = 0;

    while (true) {
      String tipo = tipos.get(pagina);

      List<String> escolhas = new ArrayList<>();
      for(int i = 0; i < menu.tamanho(); i++) {
        Item item = menu.getItem(i);

        if(item.getTipo().equals(tipo)) {
          escolhas.add((i + 1) + " - " +
                   item.getNome() + " " +
                   Item.formatarValor(item.getValor()));
        }
      }
      escolhas.add("0 - Voltar ao Início");

      limparTela();
      String titulo = tipo.toUpperCase() +
                      "  (" + (pagina + 1) +
                      "/" + tipos.size() + ")";
       desenharTela(
         titulo,
         "[P/V] Página  [N°] Escolher ",
         escolhas,
         true
      );

      System.out.print("-> ");
      String escolha = entrada.nextLine().trim();

      if(escolha.equalsIgnoreCase("P")) {
        pagina = (pagina + 1) % tipos.size();
      } else if(escolha.equalsIgnoreCase("V")) {
        pagina = (pagina - 1 + tipos.size()) % tipos.size();
      } else if(escolha.equals("0")) {
        return -1;
      } else {
        try {
          int numero = Integer.parseInt(escolha);

          if(numero >= 1 && numero <= menu.tamanho()) {
            return numero -1;
          }
        } catch (NumberFormatException erro) {}
      }
    }
  }

  public static void pausa(Scanner entrada) {
    System.out.println("\nPressione Enter para continuar");
    entrada.nextLine();
  }

  public static void sucesso(String menssagem) {
    System.out.println(GREEN + menssagem + RESET);
  }
  public static void erro(String menssagem) {
    System.out.println(RED + menssagem + RESET);
  }
}
