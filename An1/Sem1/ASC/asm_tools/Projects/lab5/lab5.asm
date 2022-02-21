bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    s1 db 1, 2, 3, 4
    l1 equ $-s1
    s2 db 5, 6, 7
    l2 equ $-s2
    d times l1+l2 db 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;3.Se dau doua siruri de octeti S1 si S2. Sa se construiasca sirul D prin concatenarea
        ;elementelor din sirul S1 luate de la stanga spre dreapta si a elementelor din sirul S2 luate de la dreapta spre stanga. 
        
        mov ECX, l1 ;punem lungimea primului sir in ECX pentru bucla loop
        mov ESI, 0
        jecxz sfarsit1 ;ne asiguram ca nu intram cu ECX=0 in loop
        Repeta1:
            mov AL, [s1+ESI] ;Puneam in AL elementele sirului s1 in ordine
            mov [d+ESI], AL ;Punem in sirul D elementele sirului s1 in ordine
            inc ESI
        loop Repeta1
        sfarsit1:
        
        mov ECX, l2   ;puneam lungimea celui de al doilea sir in ECX pentru bucla loop
        mov ESI, 0
        jecxz sfarsit2 ;ne asiguram ca nu intram cu ECX=0 in loop
        Repeta2:
            mov AL, [s2+ECX-1] ;Puneam in AL elementele sirului s2 in ordine inversa 
            mov [d+l1+ESI], AL ;Punem in sirul D elementele sirului s2 in ordine inversa
            inc ESI
        loop Repeta2
        sfarsit2:
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
