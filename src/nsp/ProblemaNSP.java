package nsp;
import java.util.ArrayList;

public class ProblemaNSP {
    private int numeroDeEnfermeiros;
    private int numeroDeDias;
    private int numeroDeTurnos;
    private Demanda demandas;
    private ArrayList<Enfermeiro> enfermeiros;

    public ProblemaNSP(int[][] demanda, ArrayList<Enfermeiro> enfermeiros) {
        this.numeroDeEnfermeiros = enfermeiros.size();
        this.numeroDeDias = demanda.length;
        this.numeroDeTurnos = demanda[0].length;
        this.demandas = new Demanda(demanda);
        this.enfermeiros = enfermeiros;
    }    

    public int getNumeroDeEnfermeiros() {
        return numeroDeEnfermeiros;
    }

    public int getNumeroDeDias() {
        return numeroDeDias;
    }

    public int getNumeroDeTurnos() {
        return numeroDeTurnos;
    }

    public int[][] getDemanda() {
        return demandas.getDemandas();
    }

    public ArrayList<Enfermeiro> getEnfermeiros() {
        return enfermeiros;
    }
    
    public void exibeInformacoes() {        
        System.out.println("-----PROBLEMA NPSLIB-------");
        System.out.println("Numero de Enfermeiros: "+this.numeroDeEnfermeiros);
        System.out.println("Numero de Dias: "+this.numeroDeDias);
        System.out.println("Numero de Turnos: "+this.numeroDeTurnos);
        System.out.println("");
        System.out.println("MATRIZ DE DEMANDA");
        for (int i = 0; i < this.numeroDeDias; i++) {
            for (int j = 0; j < this.numeroDeTurnos; j++) {                
                System.out.print(this.demandas.getDemandas()[i][j]);
                if (j== this.numeroDeTurnos-1){
                    System.out.println("");
                }
            }
        }
        System.out.println("");
        System.out.println("PREFERENCIA DE ENFERMEIROS");
        for (int i = 0; i < this.enfermeiros.size(); i++) {
            System.out.println("Enfermeiro "+(i+1)+":");            
            for (int linha = 0; linha < this.numeroDeDias ; linha++) {
                for (int coluna = 0; coluna < this.numeroDeTurnos; coluna++) {
                    System.out.println("Dia "+(linha+1)+", Turno "+(coluna+1)+": "+this.enfermeiros.get(i).getPreferencias()[linha][coluna]);                  
                }
            }
        }
        System.out.println("");
    }   
}
