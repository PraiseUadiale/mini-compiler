import org.python.util.PythonInterpreter;

public class PythonMain {


    public static void main(String[] args) {

        try(PythonInterpreter interpreter = new PythonInterpreter()) {
            interpreter.execfile("python\\main.py");
        } catch (Exception e){
            System.out.println(e.getMessage());

        }

    }
}

