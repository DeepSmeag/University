#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(int argc, char **argv) {
    if(argc < 2){
        printf("Ceva");
        exit(1);
    }
    int c2p[2];
    if(pipe(c2p) !=0){
        printf("Eroare pipe");
        exit(0);
    }

    int proc = fork();
    if(proc==-1) {
        printf("Esuat la creare proces");
        exit(1);
    }
       if(proc == 0){
        printf("child:");
        close(c2p[0]);
        int fin = open(argv[1], O_RDONLY);
        if(fin < 0){
            printf("Fisier problemme");
            exit(1);
        }
        int c;
        while(0< read(fin, &c, 1)){
    //        printf("Child scrie...%c", c);
            write(c2p[1], &c, sizeof(char));
            printf("\n");
        }
        close(c2p[1]);
        close(fin);
        return 0;
    }
    close(c2p[1]);
    char cP;
    while(0<read(c2p[0], &cP, sizeof(char))) {
        printf("%c", cP);
    }
    int status;
    wait(&status);
    if(status!=0) {
        printf("Status: %d", status);
    }
    return 0;
}
