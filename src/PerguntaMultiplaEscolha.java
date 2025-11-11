import java.util.ArrayList;

public class PerguntaMultiplaEscolha extends Pergunta {
    private ArrayList<String> opcoes;
    private String respostaCorreta;

    public PerguntaMultiplaEscolha(String enunciado, String categoria, int valorPontos,
                                   ArrayList<String> opcoes, String respostaCorreta) {
        super(enunciado, categoria, valorPontos);
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta.trim().toLowerCase();
    }

    public ArrayList<String> getOpcoes() {
        return opcoes;
    }

    public boolean checarResposta(String respostaJogador) {
        return respostaJogador.trim().equalsIgnoreCase(respostaCorreta);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(Tipo: Múltipla Escolha)\n");
        sb.append("Categoria: ").append(getCategoria()).append("\n");
        sb.append("Pergunta: ").append(getEnunciado()).append("\n");
        sb.append("Opções:\n");

        for (int i = 0; i < opcoes.size(); i++) {
            char letra = (char) ('A' + i);
            sb.append(letra).append(") ").append(opcoes.get(i)).append("\n");
        }
        return sb.toString();
    }
}
