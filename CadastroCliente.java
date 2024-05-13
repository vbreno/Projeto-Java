import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CadastroCliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Cliente> listaClientes = new ArrayList<>();

        // Loop para cadastrar e alterar clientes
        while (true) {
            System.out.print("\nDigite 'novo' para adicionar um novo cliente ou 'alterar' para modificar um cliente: ");
            String opcao = scanner.nextLine();

            // Scanner para Cadastrar um novo cliente
            if (opcao.equalsIgnoreCase("novo")) {
                Cliente cliente = cadastrarCliente(scanner);
                listaClientes.add(cliente);
                System.out.println("Cliente cadastrado com sucesso!");
            } // Alterar um cliente existente 
            else if (opcao.equalsIgnoreCase("alterar")) {
                alterarCliente(scanner, listaClientes);
            } else {
                System.out.println("Opção inválida. Por favor, digite 'cadastrar' ou 'alterar'.");
            }

            System.out.print("Deseja continuar?: ");
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

    // Método para cadastrar um novo cliente
    private static Cliente cadastrarCliente(Scanner scanner) {
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a data de nascimento do cliente (DD/MM/AAAA): ");
        String dataNascimento = scanner.nextLine();

        System.out.print("Digite o CPF do cliente (somente números): ");
        String cpf = scanner.nextLine();

        return new Cliente(nome, dataNascimento, cpf);
    }

    // Método para alterar dados de um cliente existente
    private static void alterarCliente(Scanner scanner, List<Cliente> listaClientes) {
        System.out.print("Digite o CPF do cliente que deseja alterar: ");
        String cpf = scanner.nextLine();

        // Procura o cliente na lista pelo CPF
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
                return; // Sai do método após a alteração ser concluída
            }
        }

        // Se o CPF não for encontrado na lista imprime:
        System.out.println("Cliente não encontrado com o CPF fornecido.");
    }
}
