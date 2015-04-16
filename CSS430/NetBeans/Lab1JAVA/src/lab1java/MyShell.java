package lab1java;

import java.io.*;
import java.util.ArrayList;
import SysLib.class;

public class MyShell extends Thread
{
    private int counter;
    private String[] args;
    private final char AND_TOKEN = '&', POST_TOKEN = ';';

    public MyShell(String[] args)
    {
        counter = 0;
        this.args = args;
        Kernel.interrupt( Kernel.INTERRUPT_SOFTWARE,
				 Kernel.WRITE, 1, "Shell[" + counter + "]" );
        //SysLib.cout();
        DoSomething();
    }
    
    private void DoSomething()
    {
        SysLib.cout("run start");
        if(this.args.Length == 0)
        {
            return;
        }
        ArrayList<String> argList = new ArrayList<>();
        ArrayList<prog> programs = new ArrayList<>();
        
        for(int j = 0; j < args.length; j++)
        {
            argList.add(args[j].trim());
        }
        
        for(int i = 0 ; ; i++)
        {
            programs.add(new prog());
            argList = SetProgram(programs.get(i), argList);
            if(argList.isEmpty())
            {
                break;
            }
        }
        SysLib.cout(argList);
    }
    
    @Override
    public void run()
    {
        
    }
    
    private ArrayList<String> SetProgram(prog p, ArrayList<String> argList)
    {
        //p.SystemRun = (s[0].trim().charAt(0) == AND_TOKEN)? AND_TOKEN:POST_TOKEN;
        if(argList.get(0).charAt(0) == AND_TOKEN || argList.get(0).charAt(0) == POST_TOKEN)
        {
            p.SystemRun = argList.remove(0).charAt(0);
        }
        else
        {
            p.SystemRun = POST_TOKEN;
        }
        
        p.ProgramName = argList.remove(0);
        
        for (;;)
        {
            if(argList.isEmpty())
            {
                return argList;
            }
            if(argList.get(0).charAt(0) == AND_TOKEN || argList.get(0).charAt(0) == POST_TOKEN)
            {
                return argList;
            }
            else
            {
                p.args.add(argList.remove(0));
            }
        }
    }
    
    private class prog
    {
        char SystemRun;
        String ProgramName;
        ArrayList<String> args = new ArrayList<>();
    }

}
