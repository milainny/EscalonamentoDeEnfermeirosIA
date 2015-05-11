package nsp;
import java.util.ArrayList;

public class RestricoesNSP {
    private int qtdeDias;
    private int qtdeTurnos;
    private int minAtribuicoes;
    private int maxAtribuicoes;
    private ArrayList<Turno> turnos;

    public RestricoesNSP(int[] valores, ArrayList<Turno> turnos) {
        qtdeDias = valores[0];
        qtdeTurnos = turnos.size();
        this.turnos = turnos;
        minAtribuicoes = valores[2];
        maxAtribuicoes = valores[3];
    }
    
    

    public int getQtdeDias() {
        return qtdeDias;
    }

    public int getQtdeTurnos() {
        return qtdeTurnos;
    }

    public ArrayList<Turno> getTurnos() {
        return turnos;
    }
    
    
    public void exibeInformacoes() {        
        System.out.println("-----RESTRICOES-------");
        System.out.println("Numero de Dias: "+this.qtdeDias);
        System.out.println("Numero de Turnos: "+this.qtdeTurnos);
        System.out.println("Numero minimo de Atribuicoes: "+this.minAtribuicoes);
        System.out.println("Numero maximo de Atribuicoes: "+this.maxAtribuicoes);
        System.out.println("");
        System.out.println("TURNOS");
        for (int i = 0; i < this.turnos.size(); i++) {
            System.out.println("Turnos "+(i+1)+":");            
            System.out.println("Minimo mesmo Turno consecutivos: "+turnos.get(i).getMinimoMesmosTurnosConsecutivos());
            System.out.println("Maximo mesmo Turno consecutivos: "+turnos.get(i).getMaximoMesmosTurnosConsecutivos());
            System.out.println("Minimo de Atribuicoes por turno: "+turnos.get(i).getMinimoAtribuicoesPorTurno());
            System.out.println("Maximo de Atribuicoes por turno: "+turnos.get(i).getMaximoAtribuicoesPorTurno());
        }
        System.out.println("");
    }       
}
