package nsp;

import java.util.ArrayList;

public class Solucao implements Comparable<Solucao>{

    private int[][] solucao;
    private int custo;

    public Solucao(int numeroEnfermeiros, int numeroDias) {
        solucao = new int[numeroEnfermeiros][numeroDias];
        custo = 0;
    }

    public int[][] getSolucao() {
        return solucao;
    }

    public void setSolucao(int[][] solucao) {
        this.solucao = solucao;
    }
    

    public int getCusto() {
        return custo;
    }
    
    public void setCusto(int custo) {
        this.custo = custo;
    }

    public void imprime() {
        System.out.println("--- SOLUÇÃO (Enfermeiros x Dia) ---");
        System.out.println("\ncusto total: "+this.custo);
        System.out.println("");
        for (int i = 0; i < solucao.length; i++) {
            for (int j = 0; j < solucao[0].length; j++) {
                System.out.print(this.solucao[i][j] + "\t");
            }
            System.out.println("");
        }
    }

    public void calculaCusto(ArrayList<Enfermeiro> enfermeiros) {
        for (int i = 0; i < solucao.length; i++) {
            for (int j = 0; j < solucao[i].length; j++) {
                this.custo += enfermeiros.get(i).getPreferencia(j,solucao[i][j]+1);
            }
        }
    }
    
    @Override
    public int compareTo(Solucao s) {
         if (this.custo < s.custo) {
            return -1;
        }
        if (this.custo > s.custo) {
            return 1;
        }
        return 0;
    }
}
