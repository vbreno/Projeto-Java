import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String CLIENTES_FILE_PATH = "clientes.txt";
    private static final String PRODUTOS_FILE_PATH = "produtos.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = carregarClientesDoArquivo();
        List<Produto> listaProdutos = carregarProdutosDoArquivo();

        // Cadastro de clientes
        while (true) {
            System.out.print("\nDigite 'novo' para adicionar um novo cliente, 'alterar' para modificar um cliente, 'remover' para excluir um cliente ou 'sair' para continuar para pedidos: ");
            String opcao = scanner.nextLine();

            if (opcao.equalsIgnoreCase("novo")) {
                Cliente cliente = cadastrarCliente(scanner);
                listaClientes.add(cliente);
                salvarClientesNoArquivo(listaClientes);
                System.out.println("Cliente cadastrado com sucesso!");
            } else if (opcao.equalsIgnoreCase("alterar")) {
                alterarCliente(scanner, listaClientes);
                salvarClientesNoArquivo(listaClientes);
            } else if (opcao.equalsIgnoreCase("remover")) {
                removerCliente(scanner, listaClientes);
                salvarClientesNoArquivo(listaClientes);
            } else if (opcao.equalsIgnoreCase("sair")) {
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }

        // Processamento de pedidos
        while (true) {
            System.out.print("\nDigite 'pedido' para registrar um pedido, 'sair' para finalizar ou 'alerta' para verificar alertas de estoque: ");
            String opcao = scanner.nextLine();

            if (opcao.equalsIgnoreCase("pedido")) {
                processarPedido(scanner, listaProdutos);
                salvarProdutosNoArquivo(listaProdutos);
            } else if (opcao.equalsIgnoreCase("sair")) {
                break;
            } else if (opcao.equalsIgnoreCase("alerta")) {
                verificarAlertasEstoque(listaProdutos);
            } else {
                System.out.println("Opção inválida.");
            }
        }

        // Exibe todos os produtos cadastrados
        System.out.println("\nLista de Produtos Atualizados:");
        for (Produto produto : listaProdutos) {
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Código: " + produto.getCodigo());
            System.out.println("Preço: " + produto.getPreco());
            if (produto instanceof ProdutoEstoque) {
                ProdutoEstoque produtoEstoque = (ProdutoEstoque) produto;
                System.out.println("Quantidade: " + produtoEstoque.getQuantidade());
                System.out.println("Quantidade Mínima: " + produtoEstoque.getQuantidadeMinima());
                if (produtoEstoque.precisaReporEstoque()) {
                    System.out.println("Alerta: É necessário repor o estoque!");
                }
            }
            System.out.println();
        }

        // Fecha o scanner
        scanner.close();
    }

    private static Cliente cadastrarCliente(Scanner scanner) {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a data de nascimento do cliente (DD/MM/AAAA): ");
        String dataNascimento = scanner.nextLine();

        System.out.print("Digite o CPF do cliente (somente números): ");
        String cpf = scanner.nextLine();

        return new Cliente(nome, dataNascimento, cpf);
    }

    private static void alterarCliente(Scanner scanner, List<Cliente> listaClientes) {
        System.out.print("Digite o CPF do cliente que deseja alterar: ");
        String cpf = scanner.nextLine();

        for (Cliente cliente : listaClientes) {
            if (cliente.getCpf().equals(cpf)) {
                System.out.println("Digite os novos dados do cliente:");

                System.out.print("Novo nome: ");
                cliente.setNome(scanner.nextLine());

                System.out.print("Nova data de nascimento (DD/MM/AAAA): ");
                cliente.setDataNascimento(scanner.nextLine());

                System.out.println("Cliente alterado com sucesso!");
                return;
            }
        }
        System.out.println("Cliente não encontrado com o CPF fornecido.");
    }

    private static void removerCliente(Scanner scanner, List<Cliente> listaClientes) {
        System.out.print("Digite o CPF do cliente que deseja remover: ");
        String cpf = scanner.nextLine();

        Iterator<Cliente> iterator = listaClientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            if (cliente.getCpf().equals(cpf)) {
                iterator.remove();
                System.out.println("Cliente removido com sucesso!");
                return;
            }
        }
        System.out.println("Cliente não encontrado com o CPF fornecido.");
    }

    private static void processarPedido(Scanner scanner, List<Produto> listaProdutos) {
        System.out.print("Digite o código do produto que deseja comprar: ");
        String codigo = scanner.nextLine();

        System.out.print("Digite a quantidade desejada: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        for (Produto produto : listaProdutos) {
            if (produto.getCodigo().equals(codigo)) {
                if (produto instanceof ProdutoEstoque) {
                    ProdutoEstoque produtoEstoque = (ProdutoEstoque) produto;
                    if (produtoEstoque.getQuantidade() >= quantidade) {
                        produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() - quantidade);
                        System.out.println("Pedido realizado com sucesso!");
                    } else {
                        System.out.println("Quantidade em estoque insuficiente!");
                    }
                }
                return;
            }
        }
        System.out.println("Produto não encontrado com o código fornecido.");
    }

    private static void verificarAlertasEstoque(List<Produto> listaProdutos) {
        System.out.println("\nAlertas de Estoque:");
        for (Produto produto : listaProdutos) {
            if (produto instanceof ProdutoEstoque) {
                ProdutoEstoque produtoEstoque = (ProdutoEstoque) produto;
                if (produtoEstoque.precisaReporEstoque()) {
                    System.out.println("Produto: " + produtoEstoque.getNome() + " - Estoque abaixo do limite!");
                }
            }
        }
    }

    // Métodos para carregar e salvar dados nos arquivos

    private static List<Cliente> carregarClientesDoArquivo() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CLIENTES_FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                clientes.add(Cliente.fromString(linha));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    private static List<Produto> carregarProdutosDoArquivo() {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUTOS_FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 5) {
                    produtos.add(ProdutoEstoque.fromString(linha));
                } else {
                    produtos.add(Produto.fromString(linha));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    private static void salvarClientesNoArquivo(List<Cliente> clientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLIENTES_FILE_PATH))) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void salvarProdutosNoArquivo(List<Produto> produtos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUTOS_FILE_PATH))) {
            for (Produto produto : produtos) {
                writer.write(produto.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}