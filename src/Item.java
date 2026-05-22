public class Item {
  String prato;
  double valor;

  public Item (String p, double v) {
    prato = p;
    valor = v;
  }

  public void lista() {
    System.out.println ( "| " + prato + ": R$" + valor );
  }
}
