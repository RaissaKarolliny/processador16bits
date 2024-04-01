import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Tradutor {
    // essa primeira linha é obrigatória para o logisim pode ler o arquivo
    // resultante
    String instrucao = "v2.0 raw\n";
    int contador;
    int line;

    public void leitura() {
        // lendo o arquivo de entrada de dados, são as instruções do usuário
        String arquivoCSV = "prompt.csv";
        try {
            File arquivo = new File(arquivoCSV);
            Scanner scanner = new Scanner(arquivo);
            this.line = 0;
            // lê linha por linha
            while (scanner.hasNextLine()) {
                contador = 0;
                String linha = scanner.nextLine();
                String[] dados = linha.split(" ");
                this.line++;

                // verifica se alinha é vazia
                if (linha.isEmpty()) {
                    continue;
                }

                // verificando qual é a opção. ex: se opbção fo soma, o binário recebe 0000 que
                // é o código logisim para a soma
                switch (dados[0]) {
                    case "soma": // soma
                        this.instrucao += "5";
                        contador +=1;
                        break;
                    case "dimo": // subtração
                        this.instrucao += "1";
                        contador +=1;
                        break;
                    case "vezes": // multiplicação
                        this.instrucao += "2";
                        contador +=1;
                        break;
                    case "cm": // pegar da memoria
                        this.instrucao += "3";
                        contador +=1;
                        break;
                    case "gm": // guarda na memoria
                        this.instrucao += "40";
                        contador +=2;
                        break;
                    case "ci": // colocar um int no resgistrador
                        this.instrucao += "6";
                        contador +=1;
                        break;
                    case "igual": //
                        this.instrucao += "70";
                        contador +=2;
                        break;
                    case "somaInt": // soma com inteiro
                        this.instrucao += "8";
                        contador +=1;
                        break;
                    case "salte": // salte para
                        this.instrucao += "9000";
                        break;
                    default:
                        System.out.println("Instrução inválida [linha: " + line + "]");
                        this.line = -1;
                        return;
                }
                if (dados[0].equals("salte")) {
                    int valor = Integer.parseInt(dados[1]);
                    valor = valor * 4;
                    String hexadecimal = Integer.toHexString(valor);
                    int completa = 4 - hexadecimal.length();
                    for(int i =0; i != completa; i++){
                            this.instrucao += "0";
                        }
                    this.instrucao += hexadecimal;

                } else if (dados[0].equals("igual")) {
                    // verifica quais registradores estamos usando
                    for (int i = 1; i < dados.length-1; i++) {
                        String resultado = dados[i].replace("{", "").replace("}", "");
                        int valor = Integer.parseInt(resultado);
                        String hexadecimal = Integer.toHexString(valor);
                        this.instrucao += hexadecimal;
                    }
                            int valor = Integer.parseInt(dados[3]);
                            valor = valor * 4;
                            String hexadecimal = Integer.toHexString(valor);
                            int completa = 4 - hexadecimal.length();
                            for(int i =0; i != completa; i++){
                                 this.instrucao += "0";
                                 }
                               this.instrucao += hexadecimal; 

                } else if (dados[0].equals("gm")) {
                    // verifica quais registradores estamos usando
                    for (int i = 1; i < dados.length; i++) {
                        String resultado = dados[i].replace("{", "").replace("}", "");
                        int valor = Integer.parseInt(resultado);
                        String hexadecimal = Integer.toHexString(valor);
                        this.instrucao += hexadecimal;
                        contador += hexadecimal.length();
                    }
                    while(contador!=8){
                        this.instrucao += "0";
                        contador +=1;
                    }
                              
                } else if (dados[0].equals("ci")) {
                    // verifica quais registradores estamos usando
                    for (int i = 1; i < dados.length; i++) {
                        String resultado = dados[i].replace("{", "").replace("}", "");
                        int valor = Integer.parseInt(resultado);
                        String hexadecimal = Integer.toHexString(valor);
                        this.instrucao += hexadecimal;
                        contador += hexadecimal.length();
                        if(i==dados.length-1){
                            this.instrucao += hexadecimal;
                            contador += hexadecimal.length();
                        }
                        
                    
                    }
                    
                    while(contador!=8){
                        this.instrucao += "0";
                        contador +=1;
                    }
                              
                }
                else {
                    // verifica quais registradores estamos usando
                    for (int i = 1; i < dados.length; i++) {
                        String resultado = dados[i].replace("{", "").replace("}", "");
                        int valor = Integer.parseInt(resultado);
                        String hexadecimal = Integer.toHexString(valor);
                        this.instrucao += hexadecimal;
                        contador += hexadecimal.length();
                    }
                    // se não usarmos todos os espaços, completamos com 0
                    while(contador!=8){
                        this.instrucao += "0";
                        contador +=1;
                    }
                }

                this.instrucao += " "; // damos um espaço para a proxima instrução
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
            this.line = -1;
            return;
        }
        if (line > 0) {
            escreverBinario();
        }
    }

    public void escreverBinario() {
        String arquivoBinarioCSV = "binario.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoBinarioCSV))) {
            // Escreve o conteúdo do atributo binario no arquivo CSV
            writer.println(this.instrucao);
            System.out.println("Conteúdo binário foi escrito com sucesso no arquivo binario.csv.");
            System.out.println(this.instrucao);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo binario.csv: " + e.getMessage());
        }
    }
}
