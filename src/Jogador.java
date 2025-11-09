public class Jogador {
   private int pontos;
   private String nome;


    public Jogador(int pontos, String nome) {
        this.pontos = 0;
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public void somaPonto(){
        pontos+=1;
    }

}
