import java.util.List;
import java.util.Scanner;

public class PartidaSolo extends Partida {
    public PartidaSolo(Jogador jogador, List<Pergunta> perguntas) {
        super(jogador, perguntas);
    }

    @Override
    public void iniciar() {
        System.out.println("Iniciando partida solo com o jogador(a): " + jogadores.get(0).getNome());

        Scanner sc = new Scanner(System.in);
        for (Pergunta p : perguntas) {

            while (true) {
                System.out.println(p);
                System.out.print("Resposta: ");
                String resposta = sc.nextLine();

                try {
                    if (resposta.trim().isEmpty()) {
                        throw new RespostaInvalidaException("⚠️ Resposta inválida. Não pode ser vazia! Tente novamente.");
                    }

                    if (p instanceof PerguntaMultiplaEscolha && resposta.trim().length() != 1) {
                        throw new RespostaInvalidaException("⚠️ Resposta inválida. Para Múltipla Escolha, digite apenas uma letra (ex: A, B, C ou D). Tente novamente.");
                    }

                    if (p.checarResposta(resposta)) {
                        jogadores.get(0).somaPonto(p.getPontuacao());
                        System.out.println("✅ Correto! (+" + p.getPontuacao() + " pontos)\n");
                    } else {
                        System.out.println("❌ Errado!\n");
                    }
                    break;
                } catch (RespostaInvalidaException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        ranking();
        exibirVencedor();
    }
}