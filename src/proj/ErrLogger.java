package proj;

import java.text.MessageFormat;

public class ErrLogger 
{
    private int numErrors = 0;
    
    public void error(int lineNum, String msg, Object ... args)
    {
        numErrors++;
        String fmt = MessageFormat.format(msg, args);
        System.err.println("ERROR (line " + lineNum + "): " + fmt);
    }
    
    public void warning(int lineNum, String msg, Object ... args)
    {
        String fmt = MessageFormat.format(msg, args);
        System.err.println("WARNING (line " + lineNum + "): " + fmt);
    }
    
    public boolean hasErrors()
    {
        return numErrors > 0;
    }
}
