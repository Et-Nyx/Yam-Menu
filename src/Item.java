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
  public String precoFormat() {
    return String.format(Locale.forLanguageTag("pt-BR"), "R$%.2f", valor);
  }
  public String descricao() {
      return getTipo() + " - " + nome + ": " + precoFormat();
  }
}
