import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
  public static ArrayList<Item> carregarMenu(String caminho) {
    ArrayList<Item> itens = new ArrayList<>();

    try {
      List<String> linhas = Files.readAllLines(Path.of(caminho));

      for(int i = 1; i < linhas.size(); i++) {
        String[] colunas = linhas.get(i).split(",");

        itens.add(Item.criar(colunas[0], colunas[1], Double.parseDouble(colunas[2])));
      }
    } catch (IOException erro) {
      System.out.println("Erro ao ler o menu: " + erro.getMessage());
    }
    return itens;
  }
}
