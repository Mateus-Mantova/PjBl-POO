public abstract class Pergunta {
    protected String enunciado;
    protected String categoria;
    protected int pontuacao;

    public Pergunta(String enunciado, String categoria, int pontuacao) {
        this.enunciado = enunciado;
        this.categoria = categoria;
        this.pontuacao = pontuacao;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public abstract boolean checarResposta(String respostaJogador);

    public String toString() {
        return "Categoria: " + categoria + "\nPergunta: " + enunciado;
    }
}