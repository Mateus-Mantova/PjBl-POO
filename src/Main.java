import java.util.*;

public class Main {
    public static void main(String[] args) {
        Jogador jogador = new Jogador("Mariana");

        List<Pergunta> perguntas = new ArrayList<>();

        Pergunta vf1 = new PerguntaVerdadeiroFalso(
                "O Sol é uma estrela?", "Astronomia", 2, true);
        Pergunta vf2 = new PerguntaVerdadeiroFalso(
                "A água ferve a 90 graus Celsius ao nível do mar?", "Ciência", 2, false);

        ArrayList<String> opcoes1 = new ArrayList<>(Arrays.asList("Azul", "Verde", "Vermelho", "Amarelo"));
        Pergunta me1 = new PerguntaMultiplaEscolha(
                "Qual é a cor da bandeira do Brasil que representa a floresta?", "Geografia", 3, opcoes1, "B");

        ArrayList<String> opcoes2 = new ArrayList<>(Arrays.asList("3", "4", "5", "6"));
        Pergunta me2 = new PerguntaMultiplaEscolha(
                "Quantos continentes existem?", "Geografia", 3, opcoes2, "C");

        perguntas.add(vf1);
        perguntas.add(me1);
        perguntas.add(vf2);
        perguntas.add(me2);

        Partida partida = new Partida(jogador, perguntas);
        partida.adicionarJogador(jogador);

        partida.iniciar();

        partida.ranking();
    }
}