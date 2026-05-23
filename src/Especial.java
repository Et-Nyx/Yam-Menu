public class Especial extends Item {
  private String detalhe;

  public Especial(String detalhe,String nome, double valor) {
    super(nome, valor);
    this.detalhe = detalhe;
  }

  @Override
  public String getDetalhe() {
    return detalhe;
  }

  @Override
  public String getTipo() {
    return "Especial";
  }
}
