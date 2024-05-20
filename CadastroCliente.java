import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CadastroCliente {
    private static final String FILE_PATH = "clientes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = carregarClientesDoArquivo();

        // Loop para cadastrar, alterar e remover clientes
        while (true) {
            System.out.print("\nDigite 'novo' para adicionar um novo cliente, 'alterar' para modificar um cliente, ou 'remover' para excluir um cliente: ");
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
            } else {
                System.out.println("Opção inválida. Por favor, digite 'novo', 'alterar', ou 'remover'.");
            }

            System.out.print("Deseja continuar? (sim/não): ");
            String continuar = scanner.nextLine();
            if (!continuar.equalsIgnoreCase("sim")) {
                break;
            }
        }

        // Exibe todos os clientes cadastrados
        System.out.println("\nLista de Clientes Cadastrados:");
        for (Cliente cliente : listaClientes) {
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Data de Nascimento: " + cliente.getDataNascimento());
            System.out.println("CPF: " + cliente.getCpf() + "\n");
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
                System.out.println("Cliente encontrado. Digite os novos dados:");

                System.out.print("Novo nome: ");
                String novoNome = scanner.nextLine();
                cliente.setNome(novoNome);

                System.out.print("Nova data de nascimento (DD/MM/AAAA): ");
                String novaDataNascimento = scanner.nextLine();
                cliente.setDataNascimento(novaDataNascimento);

                System.out.println("Dados do cliente atualizados com sucesso!");
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

    private static List<Cliente> carregarClientesDoArquivo() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                clientes.add(Cliente.fromString(linha));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    private static void salvarClientesNoArquivo(List<Cliente> clientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
