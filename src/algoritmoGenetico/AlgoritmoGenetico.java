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
    private double taxaMutacao;
    private int tamPopulacao;

    public AlgoritmoGenetico(int convergencia, double taxaMutacao, int tamPopulacao) {
        this.convergencia = convergencia;
        this.taxaMutacao = taxaMutacao;
        this.tamPopulacao = tamPopulacao;
    }

    public void resolve(ProblemaNSP problema, RestricoesNSP restricoes) {
        ArrayList<Solucao> populacao = geraPopulacaoInicial(tamPopulacao, problema);
        int k = 0;
        while (convergencia > 0) {
            Collections.sort(populacao);
            int posicaoPais[] = escolhePais(populacao);
            ArrayList<Solucao> filhos = crossOver(posicaoPais, populacao, problema);
            if (mutacao()) {
                realizaMutacao(filhos, problema);
            }
            if (!atualizaPopulacao(filhos, populacao)) {
                convergencia--;
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

    private ArrayList<Solucao> crossOver(int[] posicaoPais, ArrayList<Solucao> populacao, ProblemaNSP problema) {
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
                    m1[i][j] = populacao.get(posicaoPais[0]).getSolucao()[i][j];
                    m2[i][j] = populacao.get(posicaoPais[1]).getSolucao()[i][j];
                } else {
                    m1[i][j] = populacao.get(posicaoPais[1]).getSolucao()[i][j];
                    m2[i][j] = populacao.get(posicaoPais[0]).getSolucao()[i][j];
                }
            }
        }
        Solucao filho1 = new Solucao(problema.getNumeroDeEnfermeiros(), problema.getNumeroDeDias());
        Solucao filho2 = new Solucao(problema.getNumeroDeEnfermeiros(), problema.getNumeroDeDias());
        filho1.setSolucao(m1);
        filho1.calculaCusto(problema.getEnfermeiros());
        filho2.setSolucao(m2);
        filho2.calculaCusto(problema.getEnfermeiros());
        ArrayList<Solucao> filhos = new ArrayList<>();
        filhos.add(filho1);
        filhos.add(filho2);
        return filhos;
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
                if (populacao.get(0).getSolucao()[i][j] != populacao.get(1).getSolucao()[i][j]) {
                    System.out.println("DIVERSIDADE");
                    return;
                }
            }

        }
    }

    private int[] escolhePais(ArrayList<Solucao> populacao) {
        /*
         A escolha dos pais ocorre pelo método da roleta
         */
        double TotalCustoPop = 0;
        double SomaAcumulada = 0;
        int posicaoPais[] = new int[2];
        for (Solucao s : populacao) {
            TotalCustoPop += s.getCusto();
        }
        Random r = new Random();
        double sorteado = r.nextDouble();
        for (int i = 0; i < populacao.size(); i++) {
            SomaAcumulada += populacao.get(i).getCusto();
            if (sorteado <= (SomaAcumulada / TotalCustoPop)) {
                posicaoPais[0] = i;
                break;
            }
        }
        do {
            SomaAcumulada = 0;
            sorteado = r.nextDouble();
            for (int i = 0; i < populacao.size(); i++) {
                SomaAcumulada += populacao.get(i).getCusto();
                if (sorteado <= (SomaAcumulada / TotalCustoPop)) {
                    //System.out.println("p0: "+posicaoPais[0]+" /p1: "+i);
                    posicaoPais[1] = i;
                    break;
                }
            }
        } while (posicaoPais[0] == posicaoPais[1]);
        return posicaoPais;
    }

    private boolean mutacao() {
        Random r = new Random();
        double taxa = r.nextDouble() * 100;
        if (taxa < this.taxaMutacao) {
            this.taxaMutacao -= 0.5;
            return true;
        }
        return false;
    }

    private void realizaMutacao(ArrayList<Solucao> filhos, ProblemaNSP problema) {
        Random rd = new Random();
        for (int i = 0; i < filhos.size(); i++) {
            int linha1 = rd.nextInt(problema.getNumeroDeEnfermeiros());
            int linha2;
            do {
                linha2 = rd.nextInt(problema.getNumeroDeEnfermeiros());
            } while (linha1 == linha2);
            for (int col = 0; col < filhos.get(i).getSolucao()[0].length; col++) {
                int aux = filhos.get(i).getSolucao()[linha1][col];
                filhos.get(i).getSolucao()[linha1][col] = filhos.get(i).getSolucao()[linha2][col];
                filhos.get(i).getSolucao()[linha2][col] = aux;
            }
            filhos.get(i).calculaCusto(problema.getEnfermeiros());
        }
    }
}
