import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CadastroProduto {
    private static final String FILE_PATH = "produtos.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Produto> listaProdutos = carregarProdutosDoArquivo();

        // Loop para cadastrar, alterar e remover produtos
        while (true) {
            System.out.print("\nDigite 'novo' para adicionar um novo produto, 'alterar' para modificar um produto, ou 'remover' para excluir um produto: ");
            String opcao = scanner.nextLine();

            if (opcao.equalsIgnoreCase("novo")) {
                Produto produto = cadastrarProduto(scanner);
                listaProdutos.add(produto);
                salvarProdutosNoArquivo(listaProdutos);
                System.out.println("Produto cadastrado com sucesso!");
            } else if (opcao.equalsIgnoreCase("alterar")) {
                alterarProduto(scanner, listaProdutos);
                salvarProdutosNoArquivo(listaProdutos);
            } else if (opcao.equalsIgnoreCase("remover")) {
                removerProduto(scanner, listaProdutos);
                salvarProdutosNoArquivo(listaProdutos);
            } else {
                System.out.println("Opção inválida. Por favor, digite 'novo', 'alterar', ou 'remover'.");
            }

            System.out.print("Deseja continuar? (sim/não): ");
            String continuar = scanner.nextLine();
            if (!continuar.equalsIgnoreCase("sim")) {
                break;
            }
        }

        // Exibe todos os produtos cadastrados
        System.out.println("\nLista de Produtos Cadastrados:");
        for (Produto produto : listaProdutos) {
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Código: " + produto.getCodigo());
            System.out.println("Preço: " + produto.getPreco() + "\n");
        }

        // Fecha o scanner
        scanner.close();
    }

    private static Produto cadastrarProduto(Scanner scanner) {
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();

        System.out.print("Digite o preço do produto: ");
        double preco = Double.parseDouble(scanner.nextLine());

        return new Produto(nome, codigo, preco);
    }

    private static void alterarProduto(Scanner scanner, List<Produto> listaProdutos) {
        System.out.print("Digite o código do produto que deseja alterar: ");
        String codigo = scanner.nextLine();

        for (Produto produto : listaProdutos) {
            if (produto.getCodigo().equals(codigo)) {
                System.out.println("Produto encontrado. Digite os novos dados:");

                System.out.print("Novo nome: ");
                String novoNome = scanner.nextLine();
                produto.setNome(novoNome);

                System.out.print("Novo preço: ");
                double novoPreco = Double.parseDouble(scanner.nextLine());
                produto.setPreco(novoPreco);

                System.out.println("Dados do produto atualizados com sucesso!");
                return;
            }
        }
        System.out.println("Produto não encontrado com o código fornecido.");
    }

    private static void removerProduto(Scanner scanner, List<Produto> listaProdutos) {
        System.out.print("Digite o código do produto que deseja remover: ");
        String codigo = scanner.nextLine();

        Iterator<Produto> iterator = listaProdutos.iterator();
        while (iterator.hasNext()) {
            Produto produto = iterator.next();
            if (produto.getCodigo().equals(codigo)) {
                iterator.remove();
                System.out.println("Produto removido com sucesso!");
                return;
            }
        }
        System.out.println("Produto não encontrado com o código fornecido.");
    }

    private static List<Produto> carregarProdutosDoArquivo() {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                produtos.add(Produto.fromString(linha));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    private static void salvarProdutosNoArquivo(List<Produto> produtos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Produto produto : produtos) {
                writer.write(produto.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
