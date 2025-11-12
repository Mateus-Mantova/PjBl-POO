import java.util.*;

public abstract class Partida {
    private int idPartida;
    private Jogador jogador;
    protected List<Pergunta> perguntas;
    private boolean concluida;
    protected int indiceAtual;
    protected ArrayList<Jogador> jogadores = new ArrayList<>();

    public Partida(Jogador jogador, List<Pergunta> perguntas) {
        this.idPartida = 0;
        this.jogador = jogador;
        this.perguntas = perguntas;
        this.concluida = false;
        this.indiceAtual = 0;
        this.jogadores = new ArrayList<>();
        this.jogadores.add(jogador);
    }

    public int getIdPartida() {
        return idPartida;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public List<Pergunta> getPerguntas() {
        return perguntas;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public void adicionarJogador(Jogador j) {
        jogadores.add(j);
    }

    public void ranking() {
        System.out.println("\n===== RANKING DOS JOGADORES =====");
        jogadores.sort(Comparator.comparingInt(Jogador::getPontos).reversed());

        for (Jogador j : jogadores) {
            System.out.println(j.getNome() + " - Pontuação: " + j.getPontos());
        }
        System.out.println("=================================\n");
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public abstract void iniciar();

    public void exibirVencedor() {
        if (jogadores.isEmpty()) return;

        Jogador vencedor = jogadores.get(0);
        for (Jogador j : jogadores) {
            if (j.getPontos() > vencedor.getPontos()) {
                vencedor = j;
            }
        }
        System.out.println("🏆 O vencedor foi: " + vencedor.getNome() +
                " com " + vencedor.getPontos() + " pontos!");
    }
}