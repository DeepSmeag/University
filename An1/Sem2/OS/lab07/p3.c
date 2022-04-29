#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char **argv) {
    int p2c[2], c2p[2];
    pipe(p2c);
    pipe(c2p);
    if(fork()==0) {
        close(p2c[1]);
        close(c2p[0]);
        int fcmmdc(int a, int b) {
            while(a != b){
                if(a > b)
                    a -= b;
                else
                    b -= a;
            }
            return a;
        }
        int cmmdc=0;
        int num, status=1;
        while(read(p2c[0], &num, sizeof(int))) {
            status = 0;
            if(cmmdc==0) {
                cmmdc = num;
            }
            cmmdc = fcmmdc(cmmdc,num);
        }
        close(p2c[0]);
        write(c2p[1], &cmmdc, sizeof(int));
        close(c2p[1]);
        exit(status);
    }
    close(c2p[1]);
    close(p2c[0]);
    int num;
    scanf("%d", &num);
    while(num !=0) {
        write(p2c[1], &num, sizeof(int));
        scanf("%d", &num);
    }
    close(p2c[1]);
    int status;
    wait(&status);
    if(status!=0) {
        printf("A fost citit de la inceput 0 \n");
        return 0;
    }
    int cmmdc=0;

    read(c2p[0], &cmmdc, sizeof(int));
    close(c2p[0]);
    printf("%d\n", cmmdc);




    return 0;

}
