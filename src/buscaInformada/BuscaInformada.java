package buscaInformada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import nsp.ProblemaNSP;
import nsp.Solucao;

/**
 *
 * @author Filipe Genu
 */
public class BuscaInformada {

    public Solucao resolve(ProblemaNSP problema) {
        Solucao solucao = criaSolucaoInicial(problema);
        int demandaSolucao[][] = calculaDemanda(solucao,problema.getNumeroDeDias(),problema.getNumeroDeTurnos());
        while (!valida(demandaSolucao, problema)) {
            boolean enfermeirosVisitados[] = new boolean[problema.getNumeroDeEnfermeiros()];
            int enfermeirosHeuristica[] = aplicaHeuristica(solucao, demandaSolucao, problema);
            avancaEstado(enfermeirosHeuristica, solucao, demandaSolucao, problema, enfermeirosVisitados);
            demandaSolucao = calculaDemanda(solucao,problema.getNumeroDeDias(),problema.getNumeroDeTurnos());
        }
        solucao.calculaCusto(problema.getEnfermeiros());
        return solucao;
    }

    private Solucao criaSolucaoInicial(ProblemaNSP problema) {
        Solucao solucao = new Solucao(problema.getNumeroDeEnfermeiros(), problema.getNumeroDeDias());
        for (int i = 0; i < problema.getNumeroDeEnfermeiros(); i++) {
            for (int j = 0; j < problema.getNumeroDeDias(); j++) {
                solucao.insereAtribuicao(i, j, new Random().nextInt(problema.getNumeroDeTurnos()));
            }
        }
        return solucao;
    }

    private boolean valida(int[][] demanda, ProblemaNSP problema) {
        for (int i = 0; i < demanda.length; i++) {
            for (int j = 0; j < demanda[i].length; j++) {
                if (demanda[i][j] < problema.getDemanda()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] calculaDemanda(Solucao solucao, int dias, int turnos) {
        int demanda[][] = new int[dias][turnos];
        for (int i = 0; i < solucao.getSolucao().length; i++) {
            for (int j = 0; j < solucao.getSolucao()[0].length; j++) {
                demanda[j][solucao.getSolucao()[i][j]]++;
            }
        }
        return demanda;
    }

    private int[] aplicaHeuristica(Solucao solucao, int[][] demandaSolucao, ProblemaNSP problema) {
        int enfermeirosHeuristica[] = new int[problema.getNumeroDeEnfermeiros()];
        for (int i = 0; i < solucao.getSolucao().length; i++) {
            for (int j = 0; j < solucao.getSolucao()[i].length; j++) {
                int turnoAtribuido = solucao.getSolucao()[i][j];
                if (demandaSolucao[j][turnoAtribuido] < problema.getDemanda()[j][turnoAtribuido]) {
                    enfermeirosHeuristica[i]--;
                } else if (demandaSolucao[j][turnoAtribuido] > problema.getDemanda()[j][turnoAtribuido]) {
                    enfermeirosHeuristica[i]++;
                }
            }
        }
        return enfermeirosHeuristica;
    }

    private void avancaEstado(int[] enfermeirosHeuristica, Solucao solucao, int[][] demandaSolucao, ProblemaNSP problema, boolean[] enfermeirosVisitados) {
        ArrayList<Integer> possiveisTurnosRealocacao = new ArrayList<>();
        boolean ok = false;
        int cont = 0;
        do {
            cont++;
            int posicaoEnfermeiro = melhorH(enfermeirosHeuristica, enfermeirosVisitados);
            for (int dia = 0; dia < solucao.getSolucao()[posicaoEnfermeiro].length; dia++) {
                int turnoAtribuido = solucao.getSolucao()[posicaoEnfermeiro][dia];
                if (demandaSolucao[dia][turnoAtribuido] > problema.getDemanda()[dia][turnoAtribuido]) {
                    possiveisTurnosRealocacao = verificaPossiveisTurnosRealocacao(demandaSolucao, problema.getDemanda(), dia);
                    if (!possiveisTurnosRealocacao.isEmpty()) {
                        ok = true;
                        Collections.shuffle(possiveisTurnosRealocacao);
                        solucao.insereAtribuicao(posicaoEnfermeiro, dia, possiveisTurnosRealocacao.get(0));
                    }
                }
            }
        } while (!ok || cont == problema.getNumeroDeEnfermeiros());
    }

    private int melhorH(int[] enfermeirosHeuristica, boolean[] enfermeirosVisitados) {
        int posicaoMaior = -1;
        int maior = 0;
        for (int i = 0; i < enfermeirosHeuristica.length; i++) {
            if (enfermeirosHeuristica[i] > maior  && !enfermeirosVisitados[i]) {
                maior = enfermeirosHeuristica[i];
                posicaoMaior = i;
            }
        }
        enfermeirosVisitados[posicaoMaior] = true;
        return posicaoMaior;
    }

    private ArrayList<Integer> verificaPossiveisTurnosRealocacao(int[][] demandaSolucao, int[][] demandaProblema, int dia) {
        ArrayList<Integer> turnos = new ArrayList<>();
        for (int turno = 0; turno < demandaProblema[0].length; turno++) {
            if (demandaSolucao[dia][turno] < demandaProblema[dia][turno]) {
                turnos.add(turno);
            }
        }
        return turnos;
    }

}
