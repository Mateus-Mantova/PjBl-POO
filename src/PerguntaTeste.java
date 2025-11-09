public class PerguntaTeste {
    private String enunciado;
    private String respostaCorreta;

    public PerguntaTeste(String enunciado, String respostaCorreta) {
        this.enunciado = enunciado;
        this.respostaCorreta = respostaCorreta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public boolean checarResposta(String resposta) {
        return resposta.equalsIgnoreCase(respostaCorreta);
    }
}
