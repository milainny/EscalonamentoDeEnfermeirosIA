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
        //ProblemaNSP problema = leitor.ler("D:\\Google Drive\\NspLib\\NSPLib\\problemaTeste.nsp");
        //ProblemaNSP problema = leitor.ler("C:\\Users\\Lailla\\Documents\\NetBeansProjects\\Instancias\\instanciamedia.nsp");
        //problema.exibeInformacoes();
        LeitorRestricoesNSP leitorRestricoes = new LeitorRestricoesNSP();
        //RestricoesNSP restricoes = leitorRestricoes.ler("C:\\Users\\Filipe Genu\\Desktop\\Instancias\\1.gen");
        //RestricoesNSP restricoes = leitorRestricoes.ler("D:\\Google Drive\\NspLib\\NSPLib\\Cases\\1.gen");
        //RestricoesNSP restricoes = leitorRestricoes.ler("/home/lailla/NetBeansProjects/Instancias/1.gen");
        //restricoes.exibeInformacoes();
        //new buscaInformada.BuscaInformada().resolve(problema);
        //new algoritmoGenetico.AlgoritmoGenetico(100, 5, 50).resolve(problema);
        //new PSR.PSR().resolve(problema);
    }
    
}
