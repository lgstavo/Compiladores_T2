package br.ufscar.dc.compiladores.compiladorT2;

/**
 *
 * @author jeanf
 */
import br.ufscar.dc.compiladores.compiladorT2.AlgumaLexer;
import br.ufscar.dc.compiladores.compiladorT2.AlgumaParser;
import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class Principal {

    public static void main(String[] parametros) throws RuntimeException, FileNotFoundException, IOException {

        // Valida a quantidade de argumentos passados para o programa.
        // Se for insuficiente, emite mensagem de erro e termina a execução.
        if (parametros.length < 2) {
            System.out.println("Execução interrompida.\nParâmetros insuficientes.");
            System.exit(0);
        }

        // Prepara o lexer e o parser com base no arquivo de entrada.
        AlgumaLexer lexer = new AlgumaLexer(CharStreams.fromFileName(parametros[0]));
        AlgumaParser parser = new AlgumaParser(new CommonTokenStream(lexer));

        parser.removeErrorListeners();
        parser.addErrorListener(GerenciadorErros.INSTANCIA);

        // Tenta abrir o arquivo de saída e processar a análise sintática.
        try (PrintWriter escritor = new PrintWriter(parametros[1])) {

            try {
                parser.programa();
            } catch (ParseCancellationException erro) {
                escritor.println(erro.getMessage());
            }

        } catch (IOException falha) {
            System.out.println("Execução interrompida.\nNão foi possível abrir o arquivo de saída: " + parametros[1] + ".");
        }
    }
}
