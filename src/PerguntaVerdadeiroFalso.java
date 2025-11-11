public class PerguntaVerdadeiroFalso extends Pergunta {
    private boolean respostaCorreta;

    public PerguntaVerdadeiroFalso(String enunciado, String categoria, int valorPontos, boolean respostaCorreta) {
        super(enunciado, categoria, valorPontos);
        this.respostaCorreta = respostaCorreta;
    }

    public boolean checarResposta(String respostaJogador) {
        respostaJogador = respostaJogador.trim().toLowerCase();
        boolean respostaBoolean = respostaJogador.equals("v") || respostaJogador.equals("verdadeiro");
        return respostaBoolean == respostaCorreta;
    }

    public String toString() {
        return super.toString() + "\n(Tipo da questão: Verdadeiro ou Falso)";
    }
}
