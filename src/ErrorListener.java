import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class ErrorListener extends BaseErrorListener {
    public static final ErrorListener INSTANCE = new ErrorListener();

    private ErrorListener() {


    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String message, RecognitionException e) {
        System.err.println("Error at line " + line + ", column " + charPositionInLine + ": " + message);
        System.err.println(" ".repeat(charPositionInLine) + "^");
        System.exit(1);
    }
}
