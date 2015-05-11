package nsp;

public class Turno {
    private int nomeTurno;
    private int MinimoMesmosTurnosConsecutivos;
    private int MaximoMesmosTurnosConsecutivos;
    private int MinimoAtribuicoesPorTurno;
    private int MaximoAtribuicoesPorTurno;

    public Turno(int pos, int[] valores,int nomeTurno) {
        this.nomeTurno = nomeTurno;
        
        MinimoMesmosTurnosConsecutivos = valores[pos];
        MaximoMesmosTurnosConsecutivos = valores[++pos];
        MinimoAtribuicoesPorTurno =valores[++pos];
        MaximoAtribuicoesPorTurno= valores[++pos];
    }

    public int getMinimoMesmosTurnosConsecutivos() {
        return MinimoMesmosTurnosConsecutivos;
    }

    public void setMinimoMesmosTurnosConsecutivos(int MinimoMesmosTurnosConsecutivos) {
        this.MinimoMesmosTurnosConsecutivos = MinimoMesmosTurnosConsecutivos;
    }

    public int getMaximoMesmosTurnosConsecutivos() {
        return MaximoMesmosTurnosConsecutivos;
    }

    public void setMaximoMesmosTurnosConsecutivos(int MaximoMesmosTurnosConsecutivos) {
        this.MaximoMesmosTurnosConsecutivos = MaximoMesmosTurnosConsecutivos;
    }

    public int getMinimoAtribuicoesPorTurno() {
        return MinimoAtribuicoesPorTurno;
    }

    public void setMinimoAtribuicoesPorTurno(int MinimoAtribuicoesPorTurno) {
        this.MinimoAtribuicoesPorTurno = MinimoAtribuicoesPorTurno;
    }

    public int getMaximoAtribuicoesPorTurno() {
        return MaximoAtribuicoesPorTurno;
    }

    public void setMaximoAtribuicoesPorTurno(int MaximoAtribuicoesPorTurno) {
        this.MaximoAtribuicoesPorTurno = MaximoAtribuicoesPorTurno;
    }
    
    
}
