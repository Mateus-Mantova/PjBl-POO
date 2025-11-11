import java.util.*;

public class Partida {
    private int idPartida;
    private Jogador jogador;
    private List<Pergunta> perguntas;
    private boolean concluida;
    private int indiceAtual;
    private static ArrayList<Jogador> jogadores = new ArrayList<>();

    public Partida(Jogador jogador, List<Pergunta> perguntas) {
        this.idPartida = idPartida;
        this.jogador = jogador;
        this.perguntas = perguntas;
        this.concluida = concluida;
        this.indiceAtual = indiceAtual;
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
        for (Jogador j : jogadores) {
            System.out.println(j.getNome() + " - Pontuação: " + j.getPontos());
        }
        System.out.println("=================================\n");
    }

    public void iniciar() {
        System.out.println("=== Iniciando a partida ===");

        while (!concluida) {

            Scanner scanner = new Scanner(System.in);

            Pergunta atual = perguntas.get(indiceAtual);
            System.out.println("\n" + atual.toString());
            System.out.print("Sua resposta: ");

            String resposta = scanner.nextLine();
            responderPergunta(resposta);
        }

        System.out.println("\n--- Fim de jogo ---");
        System.out.println("Pontuação final de " + jogador.getNome() + ": " + jogador.getPontos() + " pontos!");

    }

    public void responderPergunta(String resposta) {
        Pergunta p = perguntas.get(indiceAtual);
        if (p.checarResposta(resposta)) {
            jogador.somaPonto();
            System.out.println("✅ Correto!");
        } else {
            System.out.println("❌ Errado!");
        }
        indiceAtual++;
        if (indiceAtual >= perguntas.size()) {
            concluida = true;
        }
    }
}