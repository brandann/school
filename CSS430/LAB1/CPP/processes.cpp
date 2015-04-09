#include <stdlib.h> //exit
#include <stdio.h> // perror
#include <unistd.h> // fork pipe
#include <sys/wait.h>
#include <iostream>
#include <cstdlib>

using namespace std;

int main(char** argv) {
    enum {READ, WRITE, ERR};
    pid_t pid1, pid2;
    int FDS1[2], FDS2[2];
    
    if(pipe(FDS1) < 0 || pipe(FDS2) < 0){
        perror("Error in creating pipe");
        exit(EXIT_FAILURE);
    }
    
    pid1 = fork();
    if(pid1 < 0){
        perror("error in fork");
        exit(EXIT_FAILURE);
    }
    else if(pid1 == 0){ //child
        pid2 = fork();
        if(pid2 < 0) {
            perror("error in fork");
            exit(EXIT_FAILURE);
        }
        else if(pid2 == 0){ //grandchild
            dup2(FDS1[WRITE], WRITE);
            close(FDS1[READ]);
            close(FDS1[WRITE]);
            //cout << "Grandchild" << endl;
            execlp("/bin/ps", "ps", "-A", NULL);
        }
        else{ //child
            wait(NULL); //wait for grandchild
            dup2(FDS1[READ], READ);
            dup2(FDS2[WRITE], WRITE);
            close(FDS1[READ]);
            close(FDS1[WRITE]);
            close(FDS2[READ]);
            close(FDS2[WRITE]);
            //cout << "Child" << endl;
            execlp("grep", "grep", argv[1], NULL);
        }
    }
    else{ // in parent
        wait(NULL); //wait for child
        dup2(FDS2[READ], READ);
        close(FDS2[READ]);
        close(FDS2[WRITE]);
        //cout << "Parent" << endl;
        execlp("wc", "wc", "-l", NULL);
    }
    exit(EXIT_SUCCESS);
}

