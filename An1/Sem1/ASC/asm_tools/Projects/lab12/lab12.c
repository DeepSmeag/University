#include <stdio.h>


void asm_concatenare(char s1[], char s2[], char rez[]);

int main(){
    
    //3. Se dau doua siruri continand caractere. Sa se calculeze si sa se afiseze rezultatul concatenarii tuturor caracterelor tip cifra zecimala din cel de-al doilea sir dupa cele din primul sir si invers, rezultatul concatenarii primului sir dupa al doilea.
        
    char sir1[101] = "absaka12456akdf12";
    char sir2[101] = "fkaf819kfa98r";
    
    char rez1[201] ="",rez2[201]="";
    
    asm_concatenare(sir1,sir2,rez1);
    asm_concatenare(sir2,sir1,rez2);
    
    printf("%s\n", rez1);
    printf("%s\n", rez2);
    
    
    
    
    
    
    return 0;
}