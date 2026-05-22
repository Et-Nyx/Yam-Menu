public class Bebida extends Item {
  public Bebida(String nome, double valor) {
    super(nome, valor);
  }

  @Override
  public String getTipo() {
    return "Bebida";
  }
}
