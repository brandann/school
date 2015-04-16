#include <stdlib.h> //exit
#include <stdio.h> // perror
#include <unistd.h> // fork pipe
#include <sys/wait.h>
#include <iostream>
#include <cstdlib>

using namespace std;

int main(int argc, char** argv) {
    enum {READ, WRITE};
    pid_t pid;
    int pipeFD[2];
    
    int index;
    
    if(pipe(pipeFD) < 0)
    {
        perror("Error in creating pipe");
        exit(EXIT_FAILURE);
    }
    
    pid = fork();
    
    if(pid < 0)
    {
        perror("error in fork");
        exit(EXIT_FAILURE);
    }
    else if(pid == 0)// in child
    {
        close(pipeFD[WRITE]);
        dup2(pipeFD[READ],0);   // stdin is now childs read pipe
        cout << "child" << endl;
        execlp("/bin/ls", "ls", "-l", NULL);
    }
    else //parent
    {
        wait(NULL);
        //char buf[1500];
        //int n = read(pipeFD[READ], buf, 1500);
        //buf[n] = '/0';
        //cout << buf;
        cout << endl << "DONE!" << endl;
    }
    exit(EXIT_SUCCESS);
}