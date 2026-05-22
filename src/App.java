import java.util.Scanner;

public class App {
  public static void main(String[] args) throws Exception {
    Scanner entrada = new Scanner(System.in);

    Item salgado = new Item ("pastel", 5.00);
    Item suco = new Item ("limonada", 3.50);
    Item soda = new Item ("refri", 4.00);
    Item doce = new Item("brownie", 3.25);

    Pedido pedido = new Pedido("", null, 0);

    int opt = 0;
    String se = "";

    do{
      if (se.equals("ticket")) {
        opt = 2;
      } else if (se.equals("checkout")) {
        System.out.println ("\033[H\033[2J\n\n");
        System.out.println ("|---> Menu Lanche <---|");
        System.out.println ("| Deseja continuar? (y/n)|");
        pedido.check();

        entrada.nextLine();
        se = entrada.nextLine();

        if (se.equals("y")) {
          System.out.println("Aproveite o seu lanche!");
          opt = 0;
        } else if (se.equals("n")) {
          System.out.println("Obrigado, volte sempre.");
          pedido.cliente = "";
          pedido.item = null;
          pedido.qtd = 0;

          se = "";
          break;
        }
      } else {
        System.out.println ("\033[H\033[2J\n\n");
        System.out.println ("|---> Menu Lanche <---|");
        System.out.println ("|---------------------|");
        System.out.println ("|1 - Fazer pedido     |");
        System.out.println ("|2 - Ver pratos       |");
        System.out.println ("|0 - Sair             |");
        System.out.println ("|---------------------|");
        System.out.println ("|----> Selecione <----|");

        opt = entrada.nextInt();
      }

      switch (opt) {
        case 1:
        System.out.println ("\033[H\033[2J\n\n");
        System.out.println ("|---> Menu Lanche <---|");
        System.out.println ("|---------------------|");
        System.out.println ("|   Insira seu nome   |");
        System.out.println ("|---------------------|");

        entrada.nextLine();
        System.out.printf ("| Nome: ");
        pedido.cliente = entrada.nextLine();

        se = "ticket";

        break;
        case 2:
        System.out.println ("\033[H\033[2J\n\n");
        System.out.println ("|---> Menu Lanche <---|");
        System.out.println ("|---------------------|");

        System.out.printf ("| 1- ");
        salgado.lista();
        System.out.printf ("| 2- ");
        doce.lista();
        System.out.printf ("| 3- ");
        soda.lista();
        System.out.printf ("| 4- ");
        suco.lista();
        System.out.println ("|---------------------|");

        if (se.equals("ticket")) {
          System.out.println ("|----> Selecione <----|");

          opt = entrada.nextInt();
          se = "checkout";

          switch (opt) {
            case 1:
            pedido.item = salgado;
            System.out.printf ("| Quantidade: ");
            pedido.qtd = entrada.nextInt();
            break;
            case 2:
            pedido.item = doce;
            System.out.printf ("| Quantidade: ");
            pedido.qtd = entrada.nextInt();
            break;
            case 3:
            pedido.item = soda;
            System.out.printf ("| Quantidade: ");
            pedido.qtd = entrada.nextInt();
            break;
            case 4:
            pedido.item = suco;
            System.out.printf ("| Quantidade: ");
            pedido.qtd = entrada.nextInt();
            break;
            default:
            System.out.println ("|  Item indisponĩvel  |");
            se = "ticket";

            entrada.nextLine();
            entrada.nextLine();
          }
        } else {
          System.out.println ("|-----> Voltar <------|");
          entrada.nextLine();
          entrada.nextLine();
        }

        break;

        default:
      }

    }while(opt != 0);

    entrada.close();
  }
}
