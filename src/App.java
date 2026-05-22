import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    Scanner entrada = new Scanner(System.in);

    Menu menu = new Menu(Persistencia.carregarMenu("menu/menu.csv"));
    Comanda comanda = new Comanda("João");
    comanda.novoPedido(menu.getItem(0), 2);   // 2 pastéis
    comanda.novoPedido(menu.getItem(2), 1);   // 1 refri
    System.out.println(comanda.resumo());

    entrada.close();
  }
}
