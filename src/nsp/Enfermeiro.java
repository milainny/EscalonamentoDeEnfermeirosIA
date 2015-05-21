package nsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Enfermeiro {
    private int[][] preferencias;

    public Enfermeiro(int[][] preferencias) {
        this.preferencias = preferencias;
    }

    public int[][] getPreferencias() {
        return preferencias;
    }
    
    public int getPreferencia(int dia, int turno) {
        return preferencias[dia][turno-1];
    }
    
    public int getMenorPreferenciaDia(int dia, int qtdTurnos){
        ArrayList<Integer> preferencias1 = new ArrayList<>();
        int menor = qtdTurnos;
        for (int i = 0; i < qtdTurnos; i++) {
            if (preferencias[dia][i] < menor){
                menor = preferencias[dia][i];
            }
            
        }
        for (int i = 0; i < qtdTurnos; i++) {
            if (preferencias[dia][i] == menor){
                preferencias1.add(i);
            }
            
        }
        Collections.shuffle(preferencias1);
        return preferencias1.get(0);
    }
}