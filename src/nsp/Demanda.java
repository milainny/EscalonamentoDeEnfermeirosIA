package nsp;

public class Demanda{
    private final int MANHA= 1;
    private final int TARDE = 2;
    private final int NOITE = 3;
    private final int FOLGA = 4;
    private int[][] demandas;

    public Demanda(int[][] demandas) {
        this.demandas = demandas;
    }

    public int[][] getDemandas() {
        return demandas;
    }

}
