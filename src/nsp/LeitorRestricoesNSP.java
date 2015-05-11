package nsp;

import java.io.IOException;
import java.util.ArrayList;
import static nsp.Utils.*;

public class LeitorRestricoesNSP {
    

    public RestricoesNSP ler(String arquivo) throws IOException {
        return criarRestricoes(lerInteiros(arquivo));
    }

    private static RestricoesNSP criarRestricoes(int[] valores) {
        ArrayList<Turno> turnos = new ArrayList<Turno>();
        int qtdeTurnos = valores[1];
        int pos = 6;
        int nomeTurnos = 1;
        for (int i = 0; i < qtdeTurnos; i++) {
                      turnos.add(lerTurno(pos, valores,nomeTurnos));
                      pos+= 4;
                      nomeTurnos++;
        }
        
        return new RestricoesNSP(valores, turnos);
        
    }
    
    private static Turno lerTurno(int pos, int[] valores, int nomeTurnos) {
        return new Turno(pos, valores,nomeTurnos);
    }
}
