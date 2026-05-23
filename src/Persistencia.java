import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Persistencia {
  public static ArrayList<Item> carregarMenu(String caminho) {
    ArrayList<Item> itens = new ArrayList<>();

    try {
      List<String> linhas = Files.readAllLines(Path.of(caminho));

      for(int i = 1; i < linhas.size(); i++) {
        String[] colunas = linhas.get(i).split(",");

        itens.add(Item.criar(
          colunas[0],
          colunas[1],
          Double.parseDouble(colunas[2])
        ));
      }
    } catch (IOException erro) {
      System.out.println("Erro ao ler o menu: " + erro.getMessage());
    }
    return itens;
  }

  public static boolean salvarPedido(Comanda comanda) {
    String carimbo = LocalDateTime.now()
                    .format(DateTimeFormatter
                    .ofPattern("yyyyMMdd-HHmmss"));
    String arquivo = "pedidos/pedido_" +
                    comanda.getCliente().replace(" ", "_") +
                    "_" + carimbo + ".txt";

    StringBuilder texto = new StringBuilder();

    for(String linha : comanda.resumo(30)) {
      texto.append(linha).append("\n");
    }

    try {
      Files.createDirectories(Path.of("pedidos"));
      Files.writeString(Path.of(arquivo), texto.toString());
      return true;
    } catch (IOException erro) {
      System.out.println("Erro ao salvar o pedido: " + erro.getMessage());
      return false;
    }
  }
}
