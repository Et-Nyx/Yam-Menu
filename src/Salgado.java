public class Salgado extends Item {
  public Salgado(String nome, double valor) {
    super(nome, valor);
  }

  @Override
  public String getTipo() {
    return "Salgado";
  }
}
