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

                        if ("SAIR".equals(resposta)) {
                            main.mostrarMenuInicial();
                            return;
                        }

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

            String[] opcoesBotoes = new String[me.getOpcoes().size()];
            for (int i = 0; i < me.getOpcoes().size(); i++) {
                opcoesBotoes[i] = String.valueOf((char) ('A' + i));
            }

            StringBuilder mensagem = new StringBuilder();
            mensagem.append(p.getEnunciado()).append("\n\n");
            for (int i = 0; i < me.getOpcoes().size(); i++) {
                char letra = (char) ('A' + i);
                mensagem.append(letra).append(") ").append(me.getOpcoes().get(i)).append("\n");
            }

            int selecao = JOptionPane.showOptionDialog(
                    null,
                    mensagem.toString(),
                    titulo,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoesBotoes,
                    opcoesBotoes[0]
            );

            if (selecao == JOptionPane.CLOSED_OPTION) {
                int sair = JOptionPane.showConfirmDialog(null, "Deseja sair da partida?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (sair == JOptionPane.YES_OPTION) return "SAIR";
                else return mostrarPerguntaDialog(p, titulo);
            }

            return opcoesBotoes[selecao].toLowerCase();

        } else {
            String[] opBotoes = {"Verdadeiro", "Falso"};

            int selecao = JOptionPane.showOptionDialog(
                    null,
                    p.getEnunciado(),
                    titulo,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opBotoes,
                    opBotoes[0]
            );

            if (selecao == JOptionPane.CLOSED_OPTION) {
                int sair = JOptionPane.showConfirmDialog(null, "Deseja sair da partida?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (sair == JOptionPane.YES_OPTION) return "SAIR";
                else return mostrarPerguntaDialog(p, titulo);
            }

            return opBotoes[selecao].equalsIgnoreCase("Verdadeiro") ? "v" : "f";
        }
    }

    @Override
    public void iniciar() {
        System.out.println("Use iniciarSwing() para jogar em interface gráfica.");
    }
}
