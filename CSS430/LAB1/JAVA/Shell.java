import java.io.*;
import java.util.*;
import java.util.ArrayList;

class Shell extends Thread
{
    private final char AND_TOKEN = '&';
    private final char POST_TOKEN = ';';
    
    public void run()
    {
        for(int c = 1;;c++)
        {
            ArrayList<String> argList = GetCMD(c);
            
            if(argList == null)
            {
                SysLib.exit();
                return;
            }
            else
            {
                int runningCount = 0;
                for(int ac = 0; ac < argList.size(); ac++)
                {
                    String[] args = SysLib.stringToArgs(argList.get(ac));
                    
                    SysLib.exec(args);
                    if(args[args.length -1].contains(";"))
                    {
                        SysLib.join();
                        runningCount++;
                    }
                }
                while(runningCount <= argList.size() - 1)
                {
                    SysLib.join();
                    runningCount++;
                }
            }
        }
    }
    
    private ArrayList<String> GetCMD(int counter)
    {
        String inStream = "";
        ArrayList<String> argList = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        SysLib.cerr("\nshell[" + counter + "]% ");
        SysLib.cin(sb);
        inStream = (sb).toString();
        
        if(inStream.equals("exit"))
        {
            return null;
        }
        
        int start = 0, end = 0;
        for(; end < inStream.length(); end++)
        {
            char at = inStream.charAt(end);
            if(at == AND_TOKEN || at == POST_TOKEN)
            {
                argList.add(inStream.substring(start, end + 1));
                start = end + 1;
            }
        }
        
        if(start < end)
        {
            inStream = inStream + " ;";
            argList.add(inStream.substring(start, end + 1));
        }
        return argList;
    }
}
