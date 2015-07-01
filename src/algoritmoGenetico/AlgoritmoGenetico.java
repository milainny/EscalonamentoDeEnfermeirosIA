/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmoGenetico;

import nsp.Solucao;
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

    private int convergencia;

    public void resolve(ProblemaNSP problema, RestricoesNSP restricoes) {
        int tamPopulacao = 10;
        ArrayList<Solucao> populacao = geraPopulacaoInicial(tamPopulacao, problema);
        convergencia = 100;
        while (convergencia > 0) {            
            Collections.sort(populacao);
            System.out.print("Custos: ");
            for (Solucao p : populacao) {
                System.out.print(p.getCusto()+" ");
            }
            System.out.println("");
            ArrayList<Solucao> filhos = crossOver(populacao, problema);
            if (!atualizaPopulacao(filhos, populacao)) {
                convergencia--;
                System.out.println("conv:" + convergencia);
            } else {
                convergencia = 100;
            }
            //verificaDiversidadePopulacao(populacao);
        }
        Collections.sort(populacao);
        populacao.get(0).imprime();
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
        solucao.calculaCusto(problema.getEnfermeiros());
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

    private ArrayList<Solucao> crossOver(ArrayList<Solucao> populacao, ProblemaNSP problema) {
        Random rd = new Random();
        int corte = rd.nextInt(problema.getNumeroDeEnfermeiros());
        if (corte == 0) { //se o corte for 0, significa que os um filho será identico ao pai
            corte = 1;
        }
        int m1[][] = new int[problema.getNumeroDeEnfermeiros()][problema.getNumeroDeDias()];
        int m2[][] = new int[problema.getNumeroDeEnfermeiros()][problema.getNumeroDeDias()];
        for (int i = 0; i < problema.getNumeroDeEnfermeiros(); i++) {
            for (int j = 0; j < problema.getNumeroDeDias(); j++) {
                if (i < corte) {
                    m1[i][j] = populacao.get(0).getSolucao()[i][j];
                    m2[i][j] = populacao.get(1).getSolucao()[i][j];
                } else {
                    m1[i][j] = populacao.get(1).getSolucao()[i][j];
                    m2[i][j] = populacao.get(0).getSolucao()[i][j];
                }
            }
        }
        Solucao filho1 = new Solucao(problema.getNumeroDeEnfermeiros(), problema.getNumeroDeDias());
        Solucao filho2 = new Solucao(problema.getNumeroDeEnfermeiros(), problema.getNumeroDeDias());
        filho1.setSolucao(m1);
        filho1.calculaCusto(problema.getEnfermeiros());
        filho2.setSolucao(m2);
        filho2.calculaCusto(problema.getEnfermeiros());
        ArrayList<Solucao> retorno = new ArrayList<>();
        retorno.add(filho1);
        retorno.add(filho2);
        return retorno;
    }

    private boolean atualizaPopulacao(ArrayList<Solucao> filhos, ArrayList<Solucao> populacao) {
        ArrayList<Solucao> trocas = new ArrayList<>();
        trocas.add(populacao.get(populacao.size() - 1));
        trocas.add(populacao.get(populacao.size() - 2));
        trocas.addAll(filhos);
        Collections.sort(trocas);
        populacao.set(populacao.size() - 1, trocas.get(0));
        populacao.set(populacao.size() - 2, trocas.get(1));
        if (trocas.get(0).equals(filhos.get(0))
                || trocas.get(0).equals(filhos.get(1))
                || trocas.get(1).equals(filhos.get(0))
                || trocas.get(1).equals(filhos.get(1))) {
            return true;
        }
        return false;
    }

    private void verificaDiversidadePopulacao(ArrayList<Solucao> populacao) {
        for (int i = 0; i < populacao.get(0).getSolucao().length; i++) {
            for (int j = 0; j < populacao.get(0).getSolucao()[0].length; j++) {
                if(populacao.get(0).getSolucao()[i][j] != populacao.get(1).getSolucao()[i][j]){
                    System.out.println("DIVERSIDADE");
                    return;
                }                 
            }
            
        }
    }

}
