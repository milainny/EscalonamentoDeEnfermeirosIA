package nsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Utils {
    public static int[][] copiaProfunda(int[][] origem) {
        int destino[][] = new int[origem.length][origem[0].length];
        for (int i = 0; i < origem.length; i++) {
            for (int j = 0; j < origem[i].length; j++) {
                destino[i][j] = origem[i][j];
            }
        }
        return destino;
    }
    
    public static String lerArquivo(String caminho) throws IOException {
        BufferedReader br = criarBufferedReader(caminho);
        StringBuilder sb = new StringBuilder();
        String linha;
        while ((linha = br.readLine()) != null) {
            sb.append(linha);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static BufferedReader criarBufferedReader(String caminho) throws IOException {
        FileReader fr = new FileReader(caminho);
        return new BufferedReader(fr);
    }

    public static int[] stringToInts(String s) {
        String[] valores = s.split("\\s+");
        int[] ints = new int[valores.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(valores[i]);
        }
        return ints;
    }

    public static int[] lerInteiros(String caminho) throws IOException {
        return stringToInts(lerArquivo(caminho));
    }

    public static int[][] criarMatriz(int[] valores, int pos, int linhas, int colunas) {
        int[][] matriz = new int[linhas][colunas];
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                matriz[linha][coluna] = valores[pos++];
            }
        }
        return matriz;
    }

    

    public static int[][] criaEscala(ProblemaNSP problema) {
        int[][] escala = new int[problema.getNumeroDeEnfermeiros()][problema.getNumeroDeDias()];
        Random random = new Random();
        for (int i = 0; i < escala.length; i++) {
            for (int j = 0; j < escala[i].length; j++) {                
                escala[i][j] = random.nextInt(4)+1;
                System.out.print(escala[i][j]+"-");
            }
            System.out.println("");
        }
        return escala;
    }
    static int[][] criaEscalaFixa(ProblemaNSP problema) {
        int[][] escala = new int[problema.getNumeroDeEnfermeiros()][problema.getNumeroDeDias()];
        escala[0][0] = 4 ;
        escala[0][1] = 3;
        escala[0][2] = 2;
        escala[0][3] = 1;
        escala[0][4] = 4;
        escala[0][5] = 3;
        escala[0][6] = 3;
        escala[1][0] = 1 ;
        escala[1][1] = 2;
        escala[1][2] = 4;
        escala[1][3] = 2;
        escala[1][4] = 4;
        escala[1][5] = 4;
        escala[1][6] = 1;
        escala[2][0] = 1 ;
        escala[2][1] = 3;
        escala[2][2] = 2;
        escala[2][3] = 3;
        escala[2][4] = 1;
        escala[2][5] = 4;
        escala[2][6] = 3;
        escala[3][0] = 4 ;
        escala[3][1] = 4;
        escala[3][2] = 1;
        escala[3][3] = 1;
        escala[3][4] = 2;
        escala[3][5] = 3;
        escala[3][6] = 3;
        escala[4][0] = 3 ;
        escala[4][1] = 2;
        escala[4][2] = 4;
        escala[4][3] = 4;
        escala[4][4] = 3;
        escala[4][5] = 4;
        escala[4][6] = 1;
        escala[5][0] = 2 ;
        escala[5][1] = 1;
        escala[5][2] = 4;
        escala[5][3] = 4;
        escala[5][4] = 1;
        escala[5][5] = 1;
        escala[5][6] = 3;
        escala[6][0] = 4 ;
        escala[6][1] = 3;
        escala[6][2] = 1;
        escala[6][3] = 2;
        escala[6][4] = 3;
        escala[6][5] = 1;
        escala[6][6] = 2;
        return escala;
    }

    public static boolean noIntervalo(int n, int min, int max) {
        return min <= n && n <= max;
    }

}