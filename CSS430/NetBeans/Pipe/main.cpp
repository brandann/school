/* 
 * File:   main.cpp
 * Author: brandan
 *
 * Created on April 1, 2015, 4:57 PM
 */

#include <stdlib.h> //exit
#include <stdio.h> // perror
#include <unistd.h> // fork pipe
#include <sys/wait.h>
#include <iostream>
#include <cstdlib>

using namespace std;

/*
 * 
 */
int main(int argc, char** argv) {
    enum {READ, WRITE};
    pid_t pid;
    int pipeFD[2];
    
    if(pipe(pipeFD) < 0)
    {
        perror("Error in creatting pipe");
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
        char buf[256];
        int n = read(pipeFD[READ], buf, 256);
        buf[n] = '\0';
        cout << buf << endl << "Child read the above" << endl;
        sleep(5);
    }
    else if(pid > 0)
    {
        close(pipeFD[READ]);
        dup2(pipeFD[WRITE], 2);
        cerr << "Hello, do your homework" << endl;
        wait(NULL);
        cout << "Parent exiting" << endl;
    }
    exit(EXIT_SUCCESS);
}

