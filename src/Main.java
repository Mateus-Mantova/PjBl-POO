//TESTE

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("          BEM-VINDO(A) AO QUIZ");
        System.out.println("==========================================");

        int modo = 0;
        while (modo != 1 && modo != 2) {
            System.out.println("\nEscolha o modo de jogo:");
            System.out.println("1 - Partida Solo");
            System.out.println("2 - Partida Multiplayer (2 Jogadores)");
            System.out.print("Digite o número (1 ou 2): ");

            try {
                String input = scanner.nextLine();
                modo = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Entrada inválida. Por favor, digite 1 ou 2.");
            }
        }

        List<Pergunta> perguntasDoJogo = criarPerguntas();

        if (modo == 1) {
            System.out.print("\nDigite seu nome de Jogador: ");
            String nomeSolo = scanner.nextLine();
            Jogador jogadorSolo = new Jogador(nomeSolo);

            PartidaSolo partidaSolo = new PartidaSolo(jogadorSolo, perguntasDoJogo);
            partidaSolo.iniciar();

        } else if (modo == 2) {
            System.out.print("\nDigite o nome do Jogador 1: ");
            String nomeJ1 = scanner.nextLine();
            Jogador jogador1 = new Jogador(nomeJ1);

            System.out.print("Digite o nome do Jogador 2: ");
            String nomeJ2 = scanner.nextLine();
            Jogador jogador2 = new Jogador(nomeJ2);

            PartidaMultiplayer partidaMulti = new PartidaMultiplayer(jogador1, jogador2, perguntasDoJogo);
            partidaMulti.iniciar();
        }

        scanner.close();
    }

    private static List<Pergunta> criarPerguntas() {
        List<Pergunta> perguntas = new ArrayList<>();

        Pergunta vf1 = new PerguntaVerdadeiroFalso(
                "O Sol é uma estrela?", "Astronomia", 1, true);

        ArrayList<String> opcoes1 = new ArrayList<>(Arrays.asList("Azul", "Verde", "Vermelho", "Amarelo"));
        Pergunta me1 = new PerguntaMultiplaEscolha(
                "Qual é a cor da bandeira do Brasil que representa a floresta?", "Geografia", 1, opcoes1, "B");

        Pergunta vf2 = new PerguntaVerdadeiroFalso(
                "A água ferve a 90 graus Celsius ao nível do mar?", "Ciência", 1, false);

        ArrayList<String> opcoes2 = new ArrayList<>(Arrays.asList("4", "5", "6", "7"));
        Pergunta me2 = new PerguntaMultiplaEscolha(
                "Quantos continentes são geralmente reconhecidos?", "Geografia", 1, opcoes2, "D");

        perguntas.add(vf1);
        perguntas.add(me1);
        perguntas.add(vf2);
        perguntas.add(me2);

        return perguntas;
    }
}