import java.util.List;
import java.util.Scanner;

public class PartidaMultiplayer extends Partida {

    public PartidaMultiplayer(Jogador j1, Jogador j2, List<Pergunta> perguntas) {
        super(j1, perguntas);
        adicionarJogador(j2);
    }

    @Override
    public void iniciar() {
        System.out.println("Iniciando partida multiplayer entre " +
                getJogadores().get(0).getNome() + " e " + getJogadores().get(1).getNome());

        int turno = 0;
        Scanner sc = new Scanner(System.in);

        for (Pergunta p : getPerguntas()) {
            Jogador atual = getJogadores().get(turno % getJogadores().size());

            while (true) {
                System.out.println("\nVez de: " + atual.getNome());
                System.out.println(p);
                System.out.print("Resposta: ");
                String resposta = sc.nextLine();

                try {
                    if (resposta.trim().isEmpty()) {
                        throw new RespostaInvalidaException("⚠️ Resposta inválida. Não pode ser vazia! Tente novamente.");
                    }

                    if (p.checarResposta(resposta)) {
                        atual.somaPonto(p.getPontuacao());
                        System.out.println("✅ Correto! (+" + p.getPontuacao() + " pontos)");
                    } else {
                        System.out.println("❌ Errado!");
                    }
                    break;
                } catch (RespostaInvalidaException e) {
                    System.out.println(e.getMessage());
                }
            }
            turno++;
        }
        ranking();
        exibirVencedor();
    }
}