import java.util.ArrayList;
import java.util.List;

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

  public void removerPedido(int indice) {
    if(indice >= 0 && indice < itens.size()) {
      itens.remove(indice);
    }
  }

  public String getCliente() {
    return cliente;
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

  public List<String> resumo(int largura) {
    List<String> linhas = new ArrayList<>();

    linhas.add(" Cliente: " + cliente);
    linhas.add(" ");

    for(int i = 0; i < itens.size(); i++) {
      Pedido pedido = itens.get(i);

      String oeste = " " + (i + 1) + " - " +
                    pedido.getQuantidade() + "x " +
                    pedido.getItem().getNome();
      String leste = Item.formatarValor(pedido.getSubtotal()) + " ";

      linhas.add(alinhar(oeste, leste, largura));
    }
    linhas.add("-".repeat(largura));
    linhas.add(alinhar(" TOTAL", Item.formatarValor(getTotal()) + " ", largura));

    return linhas;
  }

  public String alinhar(String oeste, String leste, int largura) {
    int meio = Math.max(1, largura - oeste.length() - leste.length());

   return oeste + " ".repeat(meio) + leste;
  }
}
