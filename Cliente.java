public class Cliente {
    private String nome;
    private String dataNascimento;
    private String cpf;

    public Cliente(String nome, String dataNascimento, String cpf) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() {
        return nome + "," + dataNascimento + "," + cpf;
    }

    public static Cliente fromString(String linha) {
        String[] partes = linha.split(",");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato da linha inválido: " + linha);
        }
        return new Cliente(partes[0], partes[1], partes[2]);
    }
}
