import java.util.ArrayList;

public class Comanda {
  private String cliente;
  private ArrayList<Pedido> itens;

  public Comanda(String cliente) {
    this.cliente = cliente;
    this.itens = new ArrayList<>();
  }

  public void novoPedido(Item item, int quantidade) {
    itens.add(new Pedido(item, quantidade));
  }

  public double getTotal() {
    double total = 0;
    for (Pedido pedido : itens) {
      total += pedido.getSubtotal();
    }
    return total;
  }

  public boolean isVazia() {
    return itens.isEmpty();
  }

  public String resumo() {
    StringBuilder conta = new StringBuilder();

    conta.append("Cliente: ").append(cliente).append("\n");
    for (Pedido pedido : itens) {
      conta.append(pedido.getItem().descricao()).append(" X ")
           .append(pedido.getQuantidade()).append(" -> ")
           .append(Item.formatarValor(pedido.getSubtotal())).append("\n");
    }
    conta.append("Total: ").append(Item.formatarValor(getTotal())).append("\n");

    return conta.toString();
  }
}
