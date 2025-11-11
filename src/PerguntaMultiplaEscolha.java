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
        return super.toString()
                + "\n(Tipo: Múltipla Escolha)\nOpções:\n"
                + String.join("\n", opcoes);
    }
}
