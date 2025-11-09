import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean continuar = true;
        ArrayList<Jogador> jogadores = new ArrayList<>();

        while (continuar) {

            System.out.print("\nDigite seu nome: ");
            String nome = scanner.nextLine();
            Jogador j = new Jogador(0,nome);

            List<PerguntaTeste> perguntas = new ArrayList<>();
            perguntas.add(new PerguntaTeste("Qual a capital do Brasil?", "1"));
            perguntas.add(new PerguntaTeste("Quanto é 2 + 2?", "2"));
            perguntas.add(new PerguntaTeste("Quem vive no abacaxi?", "3"));



            Partida partida = new Partida(j,perguntas);
            partida.iniciar();

            partida.adicionarJogador(j);

            partida.ranking();

            System.out.print("\nDeseja jogar novamente? (s/n): ");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("n")) {
                continuar = false;
                System.out.println("Obrigado por jogar!");
            }
        }
    }
}



