package algoritmoGenetico;

import nsp.Solucao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import nsp.ProblemaNSP;

public class AlgoritmoGenetico {

    private int convergencia;
    private double taxaMutacao;
    private int tamPopulacao;

    public AlgoritmoGenetico(int convergencia, double taxaMutacao, int tamPopulacao) {
        this.convergencia = convergencia;
        this.taxaMutacao = taxaMutacao;
        this.tamPopulacao = tamPopulacao;
    }

    public Solucao resolve(ProblemaNSP problema) {
        int convergenciaInicial = this.convergencia;
        ArrayList<Solucao> populacao = geraPopulacaoInicial(tamPopulacao, problema);
        while (this.convergencia > 0) {
            Collections.sort(populacao);
            int posicaoPais[] = escolhePais(populacao);
            ArrayList<Solucao> filhos = crossOver(posicaoPais, populacao, problema);
            if (mutacao()) {
                realizaMutacao(filhos, problema);
            }
            if (!atualizaPopulacao(filhos, populacao)) {
                this.convergencia--;
            } else {
                this.convergencia = convergenciaInicial;
            }
        }
        Collections.sort(populacao);
        return populacao.get(0);
    }

    private ArrayList<Solucao> geraPopulacaoInicial(int tamPopulacao, ProblemaNSP problema) {
        ArrayList<Solucao> populacao = new ArrayList<>();
        for (int i = 0; i < tamPopulacao; i++) {
            populacao.add(criaSolucaoValida(problema));
        }
        return populacao;
    }

    private Solucao criaSolucaoValida(ProblemaNSP problema) {
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
        int corte = rd.nextInt(problema.getNumeroDeDias());
        if (corte == 0) { //se o corte for 0, significa que os um filho será identico ao pai
            corte = 1;
        }
        int m1[][] = new int[problema.getNumeroDeEnfermeiros()][problema.getNumeroDeDias()];
        int m2[][] = new int[problema.getNumeroDeEnfermeiros()][problema.getNumeroDeDias()];
        for (int i = 0; i < problema.getNumeroDeEnfermeiros(); i++) {
            for (int j = 0; j < problema.getNumeroDeDias(); j++) {
                if (j < corte) {
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

    private int[] escolhePais(ArrayList<Solucao> populacao) {
        /*
         A escolha dos pais ocorre pelo método da roleta
         */
        double totalCustoPop = 0;
        double SomaAcumulada = 0;
        int posicaoPais[] = new int[2];
        
        /*
            Calculando a função fitness:
            - primeiro calcula-se a soma total dos custos;
            - depois, calcula-se a aptidão de cada individuo, inversamente proporcional
            ao seu custo, já que o PEE é um problema de minimização.
        */
        for (Solucao s : populacao) {
            totalCustoPop += s.getCusto();
        }
        
        double fitnessPopulacao[] = new double[populacao.size()];
        for (int i = 0; i < fitnessPopulacao.length; i++) {
            fitnessPopulacao[i] = totalCustoPop - populacao.get(i).getCusto();
        }
        
        /*Fim do calculo da função Fitness*/
        
        Random r = new Random();
        double sorteado = r.nextDouble();
        for (int i = 0; i < populacao.size(); i++) {
            SomaAcumulada += fitnessPopulacao[i];
            if (sorteado <= (SomaAcumulada / totalCustoPop)) {
                posicaoPais[0] = i;
                break;
            }
        }
        do {
            SomaAcumulada = 0;
            sorteado = r.nextDouble();
            for (int i = 0; i < populacao.size(); i++) {
                SomaAcumulada += fitnessPopulacao[i];
                if (sorteado <= (SomaAcumulada / totalCustoPop)) {
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
            this.taxaMutacao -= 0.1;
            return true;
        }
        return false;
    }

    private void realizaMutacao(ArrayList<Solucao> filhos, ProblemaNSP problema) {
        Random rd = new Random();
        for (int i = 0; i < filhos.size(); i++) {
            int coluna1 = rd.nextInt(problema.getNumeroDeDias());
            int coluna2;
            do {
                coluna2 = rd.nextInt(problema.getNumeroDeDias());
            } while (coluna1 == coluna2);
            for (int lin = 0; lin < filhos.get(i).getSolucao().length; lin++) {
                int aux = filhos.get(i).getSolucao()[lin][coluna1];
                filhos.get(i).getSolucao()[lin][coluna1] = filhos.get(i).getSolucao()[lin][coluna2];
                filhos.get(i).getSolucao()[lin][coluna2] = aux;
            }
            filhos.get(i).calculaCusto(problema.getEnfermeiros());
        }
    }
}
