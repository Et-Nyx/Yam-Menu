import java.util.ArrayList;

public class Menu {
  private ArrayList<Item> itens;

  public Menu(ArrayList<Item> itens) {
    this.itens = itens;
  }

  public Item getItem(int indice) {
    if(indice < 0 || indice >= itens.size()) {
      return null;
    }
    return itens.get(indice);
  }

  public int tamanho() { return itens.size(); };
}
