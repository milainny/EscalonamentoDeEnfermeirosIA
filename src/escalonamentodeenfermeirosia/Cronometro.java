package escalonamentodeenfermeirosia;

public class Cronometro {
    long inicio;
    long tempo;
    
    public void inicia() {
        inicio = System.nanoTime();
    }

    public void para() {
        tempo = System.nanoTime() - inicio;
    }

    public double getTempoSegundos() {
        return tempo * Math.pow(10, -9);
    }
}