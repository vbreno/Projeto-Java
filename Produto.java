// Classe Produto
class Produto {
    private String nome;
    private String codigo;
    private double preco;

    public Produto(String nome, String codigo, double preco) {
        this.nome = nome;
        this.codigo = codigo;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome + "," + codigo + "," + preco;
    }

    public static Produto fromString(String linha) {
        String[] partes = linha.split(",");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato da linha inválido: " + linha);
        }
        String nome = partes[0];
        String codigo = partes[1];
        double preco = Double.parseDouble(partes[2]);
        return new Produto(nome, codigo, preco);
    }
}
