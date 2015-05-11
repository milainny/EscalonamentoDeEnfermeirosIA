package escalonamentodeenfermeirosia;

import java.io.IOException;
import nsp.LeitorProblemaNSP;
import nsp.LeitorRestricoesNSP;
import nsp.ProblemaNSP;
import nsp.RestricoesNSP;

/**
 *
 * @author Lailla
 */
public class EscalonamentoDeEnfermeirosIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        LeitorProblemaNSP leitor = new LeitorProblemaNSP();
        ProblemaNSP problema = leitor.ler("C:\\Users\\Lailla\\Documents\\NetBeansProjects\\nsplib\\trunk\\NspLib\\instancias\\N25\\1.nsp");
       // problema.exibeInformacoes();
        LeitorRestricoesNSP leitorRestricoes = new LeitorRestricoesNSP();
        RestricoesNSP restricoes = leitorRestricoes.ler("C:\\Users\\Lailla\\Documents\\NetBeansProjects\\nsplib\\trunk\\NspLib\\instancias\\Cases\\1.gen");
        restricoes.exibeInformacoes();
    }
    
}
