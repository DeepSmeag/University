#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
int main(int argc, char **argv) {
    if(argc < 2) {
        printf("Nu am primit parametru\n");
        exit(1);
    }
    int c2p[2];
    if(pipe(c2p) != 0){
        printf("Nu a putut fi creat pipe\n");
        exit(0);
    }

    int proc = fork();
   
    if(proc == -1) {
        // eroare la crearea fiului
        printf("Nu a putut fi creat un nou proces\n");
        exit(1);
    }
   
    else if(proc==0) {
        // Procesul fiu
        close(c2p[0]);
        int fin = open(argv[1], O_RDONLY);
        if(fin < 0) {
            printf("Nu a putut fi deschis fisierul\n");
            exit(1);
        }   
        char s[129]="";
        int indCur=-1;
        char c[2]="";
        int punctGasit=0;
        while(0< read(fin, &c, 1)){
            s[++indCur] = c[0];
            if(c[0] == '.'){    
                punctGasit =1;   
            }
            else if(c[0] != '.' && c[0] != ' ' && (c[0] < 'a' || c[0] > 'z')){
                punctGasit = 0;
            }
            else if(punctGasit ==1 && c[0]>='a' && c[0]<='z'){
                s[indCur] = (char)(c[0] - 'a' + 'A');
                punctGasit=0;
            }
            if(indCur > 120){
                write(c2p[1], &indCur, sizeof(int));
                write(c2p[1], &s, indCur);
            }
        }
        write(c2p[1], &indCur, sizeof(int));
        write(c2p[1], &s, indCur);
        close(fin);
        close(c2p[1]);
        exit(0);
    }
    close(c2p[1]);
    int signal;
    int nochars;
    char p[129];
    while(0<read(c2p[0], &nochars, sizeof(int))) {
        if(0<read(c2p[0], &p, nochars)) {
            p[nochars]='\0';
            printf("%s", p);
        }
    }
    printf("\n");
    wait(&signal);
    if(signal != 0) {
        printf("A aparut o eroare\n");
        printf("Status: %d", signal);
        exit(0);
    }
    return 0;
    // prelucrari citiri etc;
}
