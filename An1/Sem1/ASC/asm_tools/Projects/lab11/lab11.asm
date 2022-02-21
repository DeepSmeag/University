bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)

extern concatenare

segment data use32 class=data
    ; ...
    L equ 100
    sir1 db "abcde1234jg",0
    sir2 db "fka1295kf12",0

    rez1 resb (2*L+1)
    rez2 resb (2*L+1)
    
    format_print db "%s",10,0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ; 3. Se dau doua siruri continand caractere. Sa se calculeze si sa se afiseze rezultatul concatenarii tuturor caracterelor tip cifra zecimala din cel de-al doilea sir dupa cele din primul sir si invers, rezultatul concatenarii primului sir dupa al doilea.
        
        
        ;concatenare 1
        push dword rez1
        push dword sir2
        push dword sir1
        call concatenare
        add ESP, 4*3
        
        ;concatenare 2
        push dword rez2
        push dword sir1
        push dword sir2
        call concatenare
        add ESP, 4*3
        
        ;afisare
        push rez1
        push format_print
        call [printf]
        add ESP, 4*1
        
        push rez2
        push format_print
        call [printf]
        add ESP, 4*1
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
