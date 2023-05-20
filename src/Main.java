import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.python.util.PythonInterpreter;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;

import static org.antlr.v4.runtime.CharStreams.fromFileName;

public class Main {

    /**
     * Uncomment the input file you want to use
     */
   // private static final String INPUT_TXT = "input\\input_factorial.txt";

     private static final String INPUT_TXT = "input\\input_fibonacci.txt";
    //private static final String INPUT_TXT = "input\\input_odd_even.txt";


    private static final String PYTHON_OUTPUT = "python\\main.py";

    public static void main(String[] args) throws IOException {
        CharStream cs = fromFileName(INPUT_TXT);
        ExprLexer lexer = new ExprLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
        ErrorListener errorListener = ErrorListener.INSTANCE;
        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);

        ParseTree tree = parser.program();

        String visitor = new TranslateVisitor().visit(tree);
        System.out.println("Pretty print:");
        System.out.println(visitor);
        FileOutputStream outputStream = new FileOutputStream(PYTHON_OUTPUT);
        byte[] strToBytes = visitor.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();

        try (PythonInterpreter interpreter = new PythonInterpreter()) {
            System.out.println("Result:");

            interpreter.execfile(PYTHON_OUTPUT);
            renderParseTree(parser, tree);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }


    }

    private static void renderParseTree(Parser parser, ParseTree tree) {
        JFrame frame = new JFrame("Expr AST");
        JPanel panel = new JPanel();

        TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);

        panel.add(viewer);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}