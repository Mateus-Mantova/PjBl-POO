import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    private JFrame frame;
    private DefaultTableModel rankingModel;
    private JTable rankingTable;
    private List<Jogador> jogadoresSolo = new ArrayList<>();
    private final String ARQUIVO_RANKING = "ranking_solo.txt";
    private final String ARQUIVO_PERGUNTAS = "perguntas.txt";
    private List<Pergunta> todasAsPerguntas;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().iniciarSwing());
    }

    public void iniciarSwing() {
        carregarRanking();

        this.todasAsPerguntas = carregarPerguntasDoArquivo();

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

        JLabel titulo = new JLabel("BEM-VINDO(A) AO QUIZ POO!");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnSolo = new JButton("Partida Solo");
        btnSolo.setFont(new Font("Arial", Font.PLAIN, 16));
        btnSolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSolo.setMaximumSize(new Dimension(300, 50));

        JButton btnMulti = new JButton("Partida Multiplayer");
        btnMulti.setFont(new Font("Arial", Font.PLAIN, 16));
        btnMulti.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMulti.setMaximumSize(new Dimension(300, 50));

        btnSolo.addActionListener(e -> {
            if (todasAsPerguntas.size() < 10) {
                JOptionPane.showMessageDialog(frame, "É necessário pelo menos 10 perguntas no arquivo para o modo Solo.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Pergunta> perguntasSolo = todasAsPerguntas.subList(0, 10);

            String nome = JOptionPane.showInputDialog(frame, "Digite seu nome:");
            if (nome != null && !nome.trim().isEmpty()) {
                Jogador jogador = new Jogador(nome);
                jogadoresSolo.add(jogador);
                PartidaSolo partidaSolo = new PartidaSolo(jogador, perguntasSolo);
                partidaSolo.iniciarSwing(frame, rankingModel, this);
                salvarRanking();
            }
        });

        btnMulti.addActionListener(e -> {
            if (todasAsPerguntas.size() < 10) {
                JOptionPane.showMessageDialog(frame, "É necessário pelo menos 10 perguntas no arquivo para o modo Multiplayer.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Pergunta> perguntasMulti = new ArrayList<>(todasAsPerguntas);
            Collections.shuffle(perguntasMulti);
            perguntasMulti = perguntasMulti.subList(0, 10);

            String nome1 = JOptionPane.showInputDialog(frame, "Nome do Jogador 1:");
            String nome2 = JOptionPane.showInputDialog(frame, "Nome do Jogador 2:");
            if (nome1 != null && nome2 != null && !nome1.trim().isEmpty() && !nome2.trim().isEmpty()) {
                Jogador j1 = new Jogador(nome1);
                Jogador j2 = new Jogador(nome2);
                PartidaMultiplayer partidaMulti = new PartidaMultiplayer(j1, j2, perguntasMulti);
                partidaMulti.iniciarSwing(frame, rankingModel, this);
            }
        });

        rankingModel = new DefaultTableModel(new Object[]{"Jogador", "Pontos"}, 0);
        rankingTable = new JTable(rankingModel);
        JScrollPane scrollPane = new JScrollPane(rankingTable);
        scrollPane.setMaximumSize(new Dimension(300, 200));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRanking = new JLabel("Ranking Solo:");
        lblRanking.setFont(new Font("Arial", Font.BOLD, 18));
        lblRanking.setAlignmentX(Component.CENTER_ALIGNMENT);

        atualizarRanking();

        painelPrincipal.add(titulo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));
        painelPrincipal.add(btnSolo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        painelPrincipal.add(btnMulti);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));
        painelPrincipal.add(lblRanking);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
        painelPrincipal.add(scrollPane);

        frame.revalidate();
        frame.repaint();
    }
    private List<Pergunta> criarPerguntas() {
        return todasAsPerguntas;
    }

    private List<Pergunta> carregarPerguntasDoArquivo() {
        List<Pergunta> perguntas = new ArrayList<>();
        File file = new File(ARQUIVO_PERGUNTAS);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split("\\|");

                try {
                    String tipo = partes[0].trim();
                    String categoria = partes[1].trim();
                    int pontuacao = Integer.parseInt(partes[2].trim());
                    String enunciado = partes[3].trim();

                    if (tipo.equalsIgnoreCase("VF")) {
                        boolean resposta = Boolean.parseBoolean(partes[4].trim());
                        perguntas.add(new PerguntaVerdadeiroFalso(enunciado, categoria, pontuacao, resposta));

                    } else if (tipo.equalsIgnoreCase("ME")) {
                        ArrayList<String> opcoes = new ArrayList<>();
                        opcoes.add(partes[4].trim());
                        opcoes.add(partes[5].trim());
                        opcoes.add(partes[6].trim());
                        opcoes.add(partes[7].trim());
                        String respostaCorreta = partes[8].trim();

                        perguntas.add(new PerguntaMultiplaEscolha(enunciado, categoria, pontuacao, opcoes, respostaCorreta));
                    }
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    System.err.println("Erro ao processar linha do arquivo de perguntas: " + linha);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar perguntas: " + e.getMessage() + "\nVerifique se o arquivo 'perguntas.txt' existe.", "Erro de IO", JOptionPane.ERROR_MESSAGE);
        }

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