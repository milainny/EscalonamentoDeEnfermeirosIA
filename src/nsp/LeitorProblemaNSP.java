package nsp;

import java.io.IOException;
import java.util.ArrayList;
import static nsp.Utils.*;

public class LeitorProblemaNSP {
    public ProblemaNSP ler(String arquivo) throws IOException {
        return criarProblema(lerInteiros(arquivo));
    }

    private static ProblemaNSP criarProblema(int[] valores) {
        int numeroDeEnfermeiros = valores[0];
        int numeroDeDias = valores[1];
        int numeroDeTurnos = valores[2];
        int pos = 3;
        int[][] demanda = criarMatriz(valores, pos, numeroDeDias, numeroDeTurnos);
        pos += numeroDeDias * numeroDeTurnos;
        ArrayList<Enfermeiro> enfermeiros = new ArrayList<Enfermeiro>();
        for (int i = 0; i < numeroDeEnfermeiros; i++) {
            int[][] m = criarMatriz(valores, pos, numeroDeDias, numeroDeTurnos);
            enfermeiros.add(new Enfermeiro(m));
            pos += numeroDeDias * numeroDeTurnos;
        }
        return new ProblemaNSP(demanda, enfermeiros);
    }
}
