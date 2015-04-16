#include <stdlib.h> //exit
#include <stdio.h> // perror
#include <unistd.h> // fork pipe
#include <sys/wait.h>
#include <iostream>
#include <cstdlib>

using namespace std;

int main(int i, char** argv) 
{
    enum {READ, WRITE, ERR};
    pid_t pid1, pid2;
    int FDS1[2], FDS2[2];
    int status1, status2;
    
    if(pipe(FDS1) < 0)
    {
        perror("Error in creating pipe 1");
        exit(EXIT_FAILURE);
    }
    
    if(pipe(FDS2) < 0)
	{
	    perror("Error in creating pipe");
	    exit(EXIT_FAILURE);
	}
    
    pid1 = fork();
    if(pid1 < 0)
    {
        perror("error in fork: 1");
        exit(EXIT_FAILURE);
    }
    else if(pid1 == 0)
    {
        
        
		
        pid2 = fork();
        if(pid2 < 0) 
        {
            perror("error in fork: 2");
            exit(EXIT_FAILURE);
        }
        else if(pid2 == 0)
        { //grandchild
            dup2(FDS1[WRITE], WRITE);
            
            close(FDS1[READ]);
            close(FDS1[WRITE]);
            
            execlp("ps", "ps", "-A", NULL);
        }
        else
        { //child
            wait(NULL); //wait for grandchild
            
            dup2(FDS1[WRITE], READ);
	        dup2(FDS2[WRITE], WRITE);
	        
            close(FDS1[WRITE]);
            close(FDS1[READ]);
            close(FDS2[READ]);
            close(FDS2[WRITE]);
            
	        execlp("grep", "grep", "unity", NULL);
        }
    }
    else
    { //parent
        wait(NULL); //wait for child
        wait(NULL);
        
        dup2(FDS2[WRITE], READ);
        dup(0);
        
        close(FDS1[WRITE]);
        close(FDS1[READ]);
        close(FDS2[READ]);
        close(FDS2[WRITE]);
        
    	execlp("wc", "wc", "-l", NULL);
    }
    exit(EXIT_SUCCESS);
}

