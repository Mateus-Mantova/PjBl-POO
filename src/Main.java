import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private JFrame frame;
    private DefaultTableModel rankingModel;
    private JTable rankingTable;
    private List<Jogador> jogadoresSolo = new ArrayList<>();
    private final String ARQUIVO_RANKING = "ranking_solo.txt";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().iniciarSwing());
    }

    public void iniciarSwing() {
        carregarRanking();
        frame = new JFrame("Quiz POO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
        mostrarMenuInicial();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void mostrarMenuInicial() {
        frame.getContentPane().removeAll();

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.add(painelPrincipal, BorderLayout.CENTER);

        JLabel titulo = new JLabel("BEM-VINDO(A) AO QUIZ");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));


        JPanel rankingPanel = new JPanel(new BorderLayout());
        rankingPanel.setBorder(BorderFactory.createTitledBorder("Ranking Solo"));
        String[] colunas = {"Jogador", "Pontos"};
        rankingModel = new DefaultTableModel(colunas, 0);
        rankingTable = new JTable(rankingModel);
        JScrollPane scroll = new JScrollPane(rankingTable);
        rankingPanel.add(scroll, BorderLayout.CENTER);
        painelPrincipal.add(rankingPanel);

        atualizarRanking();

        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));


        JPanel modoPanel = new JPanel();
        modoPanel.setLayout(new FlowLayout());
        JButton btnSolo = new JButton("Partida Solo");
        JButton btnMulti = new JButton("Multiplayer");
        modoPanel.add(btnSolo);
        modoPanel.add(btnMulti);
        painelPrincipal.add(modoPanel);

        List<Pergunta> perguntas = criarPerguntas();

        btnSolo.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(frame, "Digite seu nome:");
            if (nome != null && !nome.trim().isEmpty()) {
                Jogador jogador = new Jogador(nome);
                jogadoresSolo.add(jogador);
                PartidaSolo partidaSolo = new PartidaSolo(jogador, perguntas);
                partidaSolo.iniciarSwing(frame, rankingModel, this);
                salvarRanking();
            }
        });

        btnMulti.addActionListener(e -> {
            String nome1 = JOptionPane.showInputDialog(frame, "Nome do Jogador 1:");
            String nome2 = JOptionPane.showInputDialog(frame, "Nome do Jogador 2:");
            if (nome1 != null && nome2 != null && !nome1.trim().isEmpty() && !nome2.trim().isEmpty()) {
                Jogador j1 = new Jogador(nome1);
                Jogador j2 = new Jogador(nome2);
                PartidaMultiplayer partidaMulti = new PartidaMultiplayer(j1, j2, perguntas);
                partidaMulti.iniciarSwing(frame, rankingModel, this);
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    private List<Pergunta> criarPerguntas() {
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

    public void atualizarRanking() {
        rankingModel.setRowCount(0);
        jogadoresSolo.sort((a, b) -> b.getPontos() - a.getPontos());
        for (Jogador j : jogadoresSolo) {
            rankingModel.addRow(new Object[]{j.getNome(), j.getPontos()});
        }
    }

    private void carregarRanking() {
        File file = new File(ARQUIVO_RANKING);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    String nome = partes[0];
                    int pontos = Integer.parseInt(partes[1]);
                    jogadoresSolo.add(new Jogador(nome, pontos));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void salvarRanking() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO_RANKING))) {
            for (Jogador j : jogadoresSolo) {
                pw.println(j.getNome() + ";" + j.getPontos());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
