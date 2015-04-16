#include <iostream> //cout cin
#include <unistd.h> // for fork, pipe
#include <stdlib.h>
#include <sys/wait.h>
#include <cstdlib>
    
using namespace std;

int main(int argc, char** argv)
{
    int status;
    
    // neg is error
    // 0 is child process
    // pos is parent process
    int pid = fork(); // process id
    
    if(pid < 0)
    {
        cout << "fork error" << endl;
        exit(EXIT_FAILURE);
    }
    else if(pid == 0) //child
    {
        cout << "child sleep" << endl;
        sleep(10);
        int rc = execlp("/bin/ls", "-l", (char *) 0);
        exit(EXIT_SUCCESS);
    }
    else //parent code
    {
        cout << "Parent waiting on child" << endl;
        wait(&status);
        cout << "Parent sleep" << endl;
        sleep(3);
        exit(EXIT_SUCCESS);
    }
    return 0;
}