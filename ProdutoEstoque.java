// Classe ProdutoEstoque
class ProdutoEstoque extends Produto {
    private int quantidade;
    private int quantidadeMinima;

    public ProdutoEstoque(String nome, String codigo, double preco, int quantidade, int quantidadeMinima) {
        super(nome, codigo, preco);
        this.quantidade = quantidade;
        this.quantidadeMinima = quantidadeMinima;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public boolean precisaReporEstoque() {
        return quantidade <= (quantidadeMinima * 0.1);
    }

    @Override
    public String toString() {
        return super.toString() + "," + quantidade + "," + quantidadeMinima;
    }

    public static ProdutoEstoque fromString(String linha) {
        String[] partes = linha.split(",");
        if (partes.length != 5) {
            throw new IllegalArgumentException("Formato da linha inválido: " + linha);
        }
        String nome = partes[0];
        String codigo = partes[1];
        double preco = Double.parseDouble(partes[2]);
        int quantidade = Integer.parseInt(partes[3]);
        int quantidadeMinima = Integer.parseInt(partes[4]);
        return new ProdutoEstoque(nome, codigo, preco, quantidade, quantidadeMinima);
    }
}