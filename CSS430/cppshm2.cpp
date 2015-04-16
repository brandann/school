#inlcude <stdlib.h>
#include <sys/types.h>
#include <sys/ipc>
#include <sys/shm.h>
#include <stdio.h>

#define SHMSZ   27

int main()
{
    char c;
    int shmid;
    key_t key;
    char *shm, *S;
    
    key = 3456;
    
    shmid = shmget(key, SHMSZ, IPC_CREAT | 0666);
    if(shmid < 0)
    {
        perror("shmget");
        exit(1);
    }
    
    shm = (char *) shmat(shmid, NULL, 0);
    
    S = shm;
    
    for(c = 'a'; c <= 'z'; c++)
    {
        *S++ = c;
    }
    *S = 0;
    
    while(*shm != '*')
    {
    
    }
}
