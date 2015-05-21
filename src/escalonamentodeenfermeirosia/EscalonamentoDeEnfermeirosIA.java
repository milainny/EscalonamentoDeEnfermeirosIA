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
        //ProblemaNSP problema = leitor.ler("C:\\Users\\Filipe Genu\\Desktop\\Instancias\\1.nsp");
        //ProblemaNSP problema = leitor.ler("D:\\Google Drive\\NspLib\\NSPLib\\N25\\1.nsp");
        //ProblemaNSP problema = leitor.ler("/home/lailla/NetBeansProjects/Instancias/1.nsp");
        //problema.exibeInformacoes();
        LeitorRestricoesNSP leitorRestricoes = new LeitorRestricoesNSP();
        //RestricoesNSP restricoes = leitorRestricoes.ler("C:\\Users\\Filipe Genu\\Desktop\\Instancias\\1.gen");
        //RestricoesNSP restricoes = leitorRestricoes.ler("D:\\Google Drive\\NspLib\\NSPLib\\Cases\\1.gen");
        //RestricoesNSP restricoes = leitorRestricoes.ler("/home/lailla/NetBeansProjects/Instancias/1.gen");
        //restricoes.exibeInformacoes();
        new buscaInformada.BuscaInformada().resolve(problema);
        new algoritmoGenetico.AlgoritmoGenetico().resolve(problema, restricoes);
        new PSR.PSR().resolve(problema, restricoes);
    }
    
}
