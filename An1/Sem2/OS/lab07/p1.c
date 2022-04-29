#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


int main(int argc, char ** argv) {
    int p2c[2];
    pipe(p2c);
    if(argc<2){
        printf("Este nevoie de un argument\n");
        exit(1);
    }
    if(fork() == 0) {
        close(p2c[0]);
        FILE* fin = fopen(argv[1], "r");
        if(fin==NULL || fin == 0) {
            printf("Nu a putut fi deschis\n");
            exit(1);
        }   
        int n,sum=0,i,j;
        int **a;
        fscanf(fin,"%d", &n);
        a = (int**)malloc(n * sizeof(int*));
        for(i=0;i<n;i++) {
            a[i] = (int*)malloc(n * sizeof(int));
        }
        for(i=0;i<n;i++) {
        for(j=0;j<n;j++) {
            fscanf(fin,"%d", &a[i][j]);
            }
        }
        for(i=0;i<n;i++) {
            sum+=a[i][i];
        }
        for(i=0;i<n;i++) {
            free(a[i]);
        }
        free(a);
        write(p2c[1], &sum, sizeof(int));
        close(p2c[1]);
        fclose(fin);
        exit(0);
    }   
    int status; 
    close(p2c[1]);
    wait(&status);
    if(status!= 0) {
        printf("Nu s-a putut realiza cu succes\n");
        return 0;
    }
    int sum;
    read(p2c[0], &sum, sizeof(int));
    printf("%d", sum);
    printf("\n");
    close(p2c[0]);
    
    return 0;
}
