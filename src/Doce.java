public class Doce extends Item {
  public Doce(String nome, double valor) {
    super(nome, valor);
  }

  @Override
  public String getTipo() {
    return "Doce";
  }
}
