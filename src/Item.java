import java.util.Locale;

public abstract class Item {
  protected String nome;
  protected double valor;

  public Item(String nome, double valor) {
    this.nome = nome;
    this.valor = valor;
  }

  public String getNome() { return nome; }
  public double getValor() { return valor; }

  public abstract String getTipo();
  public static String formatarValor(double valor) {
    return String.format(
      Locale.forLanguageTag("pt-BR"),
      "R$%.2f", valor
    );
  }
  public String descricao() {
    return getTipo() + " - " +
           nome + ": " +
           formatarValor(valor);
  }

  public static Item criar(String tipo, String nome, double valor) {
    switch (tipo) {
      case "salgado": return new Salgado(nome, valor);
      case "bebida":  return new Bebida(nome, valor);
      case "doce":    return new Doce(nome, valor);
      default:        return null;
    }
  }
}
