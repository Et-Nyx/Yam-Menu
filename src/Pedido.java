public class Pedido {
  private Item item;
  private int quantidade;

  public Pedido(Item item, int quantidade) {
    this.item = item;
    this.quantidade = quantidade;
  }

  public Item getItem() { return item; }
  public int getQuantidade() { return quantidade; }

  public double getSubtotal() {
    return item.getValor() * quantidade;
  }
}
