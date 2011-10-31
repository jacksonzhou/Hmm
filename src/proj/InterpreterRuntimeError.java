package proj;

import java.text.MessageFormat;

/**
 * This exception is thrown when the Interpreter encounters
 * the runtime error.
 */
@SuppressWarnings("serial")
public class InterpreterRuntimeError extends Exception {
    public InterpreterRuntimeError(int lineNum, String msg, Object ... args)
    {
        super("RUNTIME ERROR (line " + lineNum + "): " + MessageFormat.format(msg, args));
    }
}
