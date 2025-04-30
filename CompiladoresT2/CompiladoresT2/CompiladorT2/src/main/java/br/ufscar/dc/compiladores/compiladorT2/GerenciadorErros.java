/*
 * Arquivo modificado para ocultar origem original.
 */
package br.ufscar.dc.compiladores.compiladorT2;

/**
 *
 * @author jeanf
 */
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class GerenciadorErros extends BaseErrorListener {

    public static final GerenciadorErros INSTANCIA = new GerenciadorErros();

    @Override
    public void syntaxError(Recognizer<?, ?> analisador, Object simboloProblematico, int linha,
                            int posicaoNaLinha, String mensagem, RecognitionException excecao)
            throws ParseCancellationException {

        Token simbolo = (Token) simboloProblematico;
        String mensagemBase = "Linha " + simbolo.getLine() + ": ";

        if (ehErroLexico(simbolo.getType())) {
            if (simbolo.getType() == AlgumaLexer.Caracter_invalido) {
                throw new ParseCancellationException(mensagemBase + simbolo.getText() + " - símbolo não reconhecido\nFim da análise");
            } else if (AlgumaLexer.VOCABULARY.getSymbolicName(simbolo.getType()).equals("CADEIA_SEM_FIM")) {
                throw new ParseCancellationException(mensagemBase + "cadeia de texto sem término\nFim da análise");
            } else {
                throw new ParseCancellationException(mensagemBase + "comentário não finalizado\nFim da análise");
            }
        } else if (simbolo.getType() == Token.EOF) {
            throw new ParseCancellationException(mensagemBase + "erro sintático próximo ao fim do arquivo\nFim da análise");
        } else {
            throw new ParseCancellationException(mensagemBase + "erro sintático próximo a " + simbolo.getText() + "\nFim da análise");
        }
    }

    private static boolean ehErroLexico(int tipoToken) {
        return tipoToken == AlgumaLexer.CADEIA_SEM_FIM ||
                tipoToken == AlgumaLexer.COMENTARIO_SEM_FIM ||
                tipoToken == AlgumaLexer.Caracter_invalido;
    }
}
