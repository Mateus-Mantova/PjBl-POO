import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PartidaSolo extends Partida {

    public PartidaSolo(Jogador jogador, List<Pergunta> perguntas) {
        super(jogador, perguntas);
    }

    @Override
    public void iniciarSwing(JFrame frame, DefaultTableModel rankingModel, Main main) {
        for (int i = 0; i < perguntas.size(); i++) {
            Pergunta p = perguntas.get(i);
            boolean respondida = false;

            while (!respondida) {
                try {
                    String resposta = mostrarPerguntaDialog(p, "Pergunta " + (i + 1) + "/" + perguntas.size());

                    if (resposta == null) {
                        int confirma = JOptionPane.showConfirmDialog(frame, "Deseja sair da partida?", "Confirmar", JOptionPane.YES_NO_OPTION);
                        if (confirma == JOptionPane.YES_OPTION) {
                            exibirSolo();
                            main.mostrarMenuInicial();
                            return;
                        } else continue;
                    }

                    if (resposta.trim().isEmpty()) {
                        throw new RespostaInvalidaException("⚠️ Resposta inválida. Não pode ser vazia!");
                    }

                    if (p instanceof PerguntaMultiplaEscolha && resposta.trim().length() != 1) {
                        throw new RespostaInvalidaException("⚠️ Resposta inválida. Para Múltipla Escolha, selecione apenas uma letra (A, B, C ou D).");
                    }

                    boolean correta = p.checarResposta(resposta);
                    if (correta) {
                        jogadores.get(0).somaPonto(p.getPontuacao());
                        JOptionPane.showMessageDialog(frame, "✅ Correto! (+" + p.getPontuacao() + " pontos)");
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Errado!");
                    }

                    ranking(rankingModel);

                    respondida = true;
                } catch (RespostaInvalidaException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Resposta Inválida", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        exibirSolo();
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

            if (selecao == JOptionPane.CLOSED_OPTION) return null;

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

            if (selecao == JOptionPane.CLOSED_OPTION) return null;

            return opBotoes[selecao].equalsIgnoreCase("Verdadeiro") ? "v" : "f";
        }
    }

    @Override
    public void iniciar() {
        System.out.println("Use iniciarSwing() para jogar em interface gráfica.");
    }
}
