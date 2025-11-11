public abstract class Pergunta {
    protected String enunciado;
    protected String categoria;
    protected int valorPontos;

    public Pergunta(String enunciado, String categoria, int valorPontos) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.valorPontos = valorPontos;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getValorPontos() {
        return valorPontos;
    }

    public abstract boolean checarResposta(String respostaJogador);

    public String toString() {
        return "Categoria: " + categoria + "\nPergunta: " + enunciado;
    }
}