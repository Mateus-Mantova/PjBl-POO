import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PartidaMultiplayer extends Partida {

    private Jogador jogador2;

    public PartidaMultiplayer(Jogador j1, Jogador j2, List<Pergunta> perguntas) {
        super(j1, perguntas);
        this.jogador2 = j2;
        jogadores.add(j2);
    }

    @Override
    public void iniciarSwing(JFrame frame, DefaultTableModel rankingModel, Main main) {
        for (int i = 0; i < perguntas.size(); i++) {
            Pergunta p = perguntas.get(i);
            for (Jogador j : jogadores) {
                boolean respondida = false;
                while (!respondida) {
                    try {
                        String resposta = mostrarPerguntaDialog(p, "Jogador(a): " + j.getNome());
                        if (resposta == null) continue;

                        if (resposta.trim().isEmpty()) {
                            throw new RespostaInvalidaException("⚠️ Resposta inválida!");
                        }

                        if (p instanceof PerguntaMultiplaEscolha && resposta.trim().length() != 1) {
                            throw new RespostaInvalidaException("⚠️ Resposta inválida!");
                        }

                        if (p.checarResposta(resposta)) {
                            j.somaPonto(p.getPontuacao());
                            JOptionPane.showMessageDialog(frame, j.getNome() + ": ✅ Correto!");
                        } else {
                            JOptionPane.showMessageDialog(frame, j.getNome() + ": ❌ Errado!");
                        }

                        respondida = true;
                    } catch (RespostaInvalidaException ex) {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Resposta Inválida", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
        exibirVencedor();
        main.mostrarMenuInicial();
    }

    private String mostrarPerguntaDialog(Pergunta p, String titulo) {
        if (p instanceof PerguntaMultiplaEscolha) {
            PerguntaMultiplaEscolha me = (PerguntaMultiplaEscolha) p;
            String[] opcoes = new String[me.getOpcoes().size()];
            for (int i = 0; i < me.getOpcoes().size(); i++) {
                char letra = (char) ('A' + i);
                opcoes[i] = letra + ") " + me.getOpcoes().get(i);
            }

            String resp = (String) JOptionPane.showInputDialog(
                    null,
                    p.getEnunciado(),
                    titulo,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            if (resp == null) return null;
            return resp.substring(0, 1).toLowerCase();
        } else {
            String[] op = {"Verdadeiro", "Falso"};
            String resp = (String) JOptionPane.showInputDialog(
                    null,
                    p.getEnunciado(),
                    titulo,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    op,
                    op[0]
            );

            if (resp == null) return null;
            return resp.equalsIgnoreCase("Verdadeiro") ? "v" : "f";
        }
    }

    @Override
    public void iniciar() {
        System.out.println("Use iniciarSwing() para jogar em interface gráfica.");
    }
}
