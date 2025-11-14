import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Partida {
    protected List<Pergunta> perguntas;
    protected ArrayList<Jogador> jogadores = new ArrayList<>();

    public Partida(Jogador jogador, List<Pergunta> perguntas) {
        this.perguntas = perguntas;
        this.jogadores.add(jogador);
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void ranking(DefaultTableModel model) {
        jogadores.sort(Comparator.comparingInt(Jogador::getPontos).reversed());
        model.setRowCount(0);
        for (Jogador j : jogadores) {
            model.addRow(new Object[]{j.getNome(), j.getPontos()});
        }
    }

    public void exibirVencedor() {
        if (jogadores.isEmpty()) return;
        Jogador vencedor = jogadores.get(0);
        for (Jogador j : jogadores) {
            if (j.getPontos() > vencedor.getPontos()) vencedor = j;
        }
        javax.swing.JOptionPane.showMessageDialog(null,
                "🏆 O vencedor foi: " + vencedor.getNome() + " com " + vencedor.getPontos() + " pontos!");
    }

    public void exibirSolo(){
        Jogador vencedor = jogadores.get(0);
        javax.swing.JOptionPane.showMessageDialog(null,
                 vencedor.getNome() + " sua pontuação foi de " + vencedor.getPontos() + " pontos!");
    }


    public abstract void iniciar();
    public abstract void iniciarSwing(JFrame frame, DefaultTableModel rankingModel, Main main);
}
