package escalonamentodeenfermeirosia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import nsp.LeitorProblemaNSP;
import nsp.ProblemaNSP;
import nsp.Solucao;

/**
 *
 * @author Lailla
 */
public class EscalonamentoDeEnfermeirosIA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println("---Algoritmos para resolução do Problema de Escalonamento de Enfermeiros---");
        System.out.println("\nPor favor, escolha qual algoritmo você deseja utilizar para resolver:");
        System.out.println("1) Busca Informada  2) Algoritmo Genético  3)PSR");
        System.out.print("=> ");
        int algoritmo = new Scanner(System.in).nextInt();
        System.out.println("\n Informe o caminho do arquivo de entrada:");
        System.out.print("=> ");
        String caminhoEntrada = new Scanner(System.in).next();
        System.out.println("\n Informe o caminho do arquivo de saida:");
        System.out.print("=> ");
        String caminhoSaida = new Scanner(System.in).next();
        LeitorProblemaNSP leitor = new LeitorProblemaNSP();
        ProblemaNSP problema = null;
        Solucao solucao = null;
        Cronometro cronometro = new Cronometro();
        String infoAlgoritmo = "";
        switch (algoritmo) {
            case 1:
                infoAlgoritmo+="Busca Informada";
                System.out.println("Aguarde, executando algoritmo...");
                cronometro.inicia();
                problema = leitor.ler(caminhoEntrada);
                //solucao = new buscaInformada.BuscaInformada().resolve(problema);
                break;
            case 2:
                System.out.print("Informe a convergencia=> ");
                int convergencia = new Scanner(System.in).nextInt();
                System.out.print("Informe a taxa de mutação inicial=> ");
                int mutacao = new Scanner(System.in).nextInt();
                System.out.print("Informe a população=> ");
                int população = new Scanner(System.in).nextInt();
                System.out.println("Aguarde, executando algoritmo...");
                infoAlgoritmo+="Algoritmo Genético, convergencia "+convergencia+", taxa de mutação "+mutacao+", populaçao "+população+".";
                cronometro.inicia();
                problema = leitor.ler(caminhoEntrada);
                solucao = new algoritmoGenetico.AlgoritmoGenetico(convergencia, mutacao, população).resolve(problema);
                break;
            case 3:
                System.out.println("Aguarde, executando algoritmo...");
                infoAlgoritmo+="PSR";
                cronometro.inicia();
                problema = leitor.ler(caminhoEntrada);
                solucao = new PSR.PSR().resolve(problema);
                break;
        }
        cronometro.para();
        gravaResposta(solucao,caminhoSaida,cronometro,infoAlgoritmo,problema);
        System.out.println("Execução terminada, tempo total: "+cronometro.getTempoSegundos()+" segundos.");
        System.out.println("A solução pode ser visualizada no arquivo: "+caminhoSaida);
    }

    private static void gravaResposta(Solucao solucao, String caminhoSaida, Cronometro cronometro, String infoAlgoritmo, ProblemaNSP problema) throws FileNotFoundException {
        PrintStream saidaPadrao = System.out;
        PrintStream saida = new PrintStream(new FileOutputStream(new File(caminhoSaida)));
        System.setOut(saida);
        System.out.println("Informaçoes do algoritmo utilizado: "+infoAlgoritmo);
        System.out.println("Informações da instancia: "+problema.getNumeroDeEnfermeiros()+" enfermeiros, "+problema.getNumeroDeDias()+" dias e "+problema.getNumeroDeTurnos()+" turnos");
        System.out.println("Tempo total de execução:"+cronometro.getTempoSegundos());
        solucao.imprime();
        System.setOut(saidaPadrao);
    }

}
