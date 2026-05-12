public class Pedido {
    String cliente;
    Item item;
    int qtd;

    public Pedido(String c, Item i, int q) {
        cliente = c;
        item = i;
        qtd = q;
    }

    public void check () {
        double total = qtd * item.valor;

        System.out.println ("| " + cliente);
        item.lista();
        System.out.println ("| X" + qtd);
        System.out.println ("| Total a pagar: " + total);
    }
}
