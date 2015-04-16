#inlcude <stdlib.h>
#include <sys/types.h>
#include <sys/ipc>
#include <sys/shm.h>
#include <stdio.h>

#define SHMSZ 27

main()
{
    int shmid;
    key_t key;
    char *shm, *S;
    
    key = 3456;
    
    shmid = shmget(key, SHMSZ, 0666);
    if(shmid < 0)
    {
        perror("shmget");
        exit(1);
    }
}
