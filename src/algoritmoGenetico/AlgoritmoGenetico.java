/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmoGenetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import nsp.ProblemaNSP;
import nsp.RestricoesNSP;

/**
 *
 * @author Filipe Genu
 */
public class AlgoritmoGenetico {

    public void resolve(ProblemaNSP problema, RestricoesNSP restricoes) {
        int tamPopulacao = 10;
        ArrayList<Solucao> populacao = geraPopulacaoInicial(tamPopulacao, problema);
        
    }

    private ArrayList<Solucao> geraPopulacaoInicial(int tamPopulacao, ProblemaNSP problema) {
        ArrayList<Solucao> populacao = new ArrayList<>();
        int matrizDeControle[][] = nsp.Utils.copiaProfunda(problema.getDemanda());
        for (int i = 0; i < tamPopulacao; i++) {
            populacao.add(criaSolucaoValida(problema, matrizDeControle));
        }
        return populacao;
    }

    private Solucao criaSolucaoValida(ProblemaNSP problema, int[][] matrizDecontrole) {
        Solucao solucao = new Solucao(problema.getNumeroDeEnfermeiros(), problema.getNumeroDeDias());
        int matrizDaSolucao[][] = new int[problema.getNumeroDeEnfermeiros()][problema.getNumeroDeDias()];
        for (int coluna = 0; coluna < problema.getNumeroDeDias(); coluna++) {
            int demandas[] = new int[problema.getNumeroDeTurnos()];
            System.arraycopy(problema.getDemanda()[coluna], 0, demandas, 0, demandas.length);
            for (int linha = 0; linha < problema.getNumeroDeEnfermeiros(); linha++) {
                matrizDaSolucao[linha][coluna] = atribuiTurno(demandas);
            }
        }
        solucao.setSolucao(matrizDaSolucao);
        
        return solucao;
    }

    private int atribuiTurno(int[] demandas) {        
        ArrayList<Integer> valoresParaSorteio = new ArrayList<>();
        for (int i = 0; i < demandas.length; i++) {
            if (demandas[i] != 0) {
                valoresParaSorteio.add(i);
            }
        }
        int turno;
        if (valoresParaSorteio.isEmpty()) {
            Random rd = new Random();
            turno = rd.nextInt(demandas.length);
        } else {
            Collections.shuffle(valoresParaSorteio);
            turno = valoresParaSorteio.get(0);
            demandas[turno] = demandas[turno] - 1;
        }
        return turno;
    }
}
