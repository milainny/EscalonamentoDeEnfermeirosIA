package nsp;

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
}