public class Jogador {
    private int pontos;
    private String nome;

    public Jogador(String nome) {
        this(nome, 0);
    }

    public Jogador(String nome, int pontos) {
        this.nome = nome;
        this.pontos = pontos;
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void somaPonto(int pontuacao) {
        this.pontos += pontuacao;
    }
}
