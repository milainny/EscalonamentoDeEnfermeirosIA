package PSR;
import nsp.Enfermeiro;
import nsp.ProblemaNSP;
import nsp.Solucao;
import nsp.Utils;

public class PSR {

    public Solucao resolve(ProblemaNSP problema) {
        int[][] demanda = copiaDemanda(problema.getDemanda());
        Solucao solucao = new Solucao(problema.getNumeroDeEnfermeiros(), problema.getNumeroDeDias());
        int[][] s = new int[problema.getNumeroDeEnfermeiros()][problema.getNumeroDeDias()];
        s = inicializaSolucao(s);
        int custo;
        custo = preencheSolucaoInicial(s, problema, demanda);
        custo += preencheRestoEnfermeiros(s, problema);
        solucao.setCusto(custo);
        solucao.setSolucao(s);
        return solucao;
    }

    private int[][] copiaDemanda(int[][] demanda) {
        int[][] d = Utils.copiaProfunda(demanda);
        return d;
    }

    private int preencheSolucaoInicial(int[][] s, ProblemaNSP problema, int[][] demanda) {
        int enf = 0;
        int custo = 0;
        int menorPreferencia = 1;
        while (menorPreferencia < 5) {
            for (Enfermeiro e : problema.getEnfermeiros()) {
                for (int i = 0; i < problema.getNumeroDeDias(); i++) {
                    for (int j = 0; j < problema.getNumeroDeTurnos(); j++) {
                        if ((e.getPreferencia(i, j + 1) == menorPreferencia) && !((demanda[i][j]) == 0)) {
                            if (s[enf][i] != 0) {
                                s[enf][i] = j + 1;
                                demanda[i][j]--;
                                custo++;
                            }
                        }
                    }
                }
                enf++;
            }
            if (verificaSeDemandaAtendida(demanda)) {
                break;
            } else {
                if (menorPreferencia == 4){
                    System.out.println("ATENÇÃO - Solução Invalida - Demanda não atendida!");
                    break;
                }
                menorPreferencia++;
                enf = 0;
            }

        }
        return custo;
    }

    private int[][] inicializaSolucao(int[][] s) {
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[0].length; j++) {
                s[i][j] = -1;

            }

        }
        return s;
    }

    private int preencheRestoEnfermeiros(int[][] s, ProblemaNSP problema) {
        int enf = 0;
        int custo = 0;
        for (Enfermeiro e : problema.getEnfermeiros()) {
            for (int i = 0; i < problema.getNumeroDeDias(); i++) {
                    if ((s[enf][i]) == -1) {
                        s[enf][i] = e.getMenorPreferenciaDia(i, problema.getNumeroDeTurnos()) + 1;
                        custo = custo + e.getPreferencia(i, s[enf][i]);
                }
            }
            enf++;
        }
        return custo;
    }

    private boolean verificaSeDemandaAtendida(int[][] demanda) {
        for (int i = 0; i < demanda.length; i++) {
            for (int j = 0; j < demanda[0].length; j++) {
                if (demanda[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
