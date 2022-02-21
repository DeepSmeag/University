bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fscanf, printf, fopen         ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fscanf msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    format_fscanf db "%c", 0
    nume_fisier db "fin.txt", 0
    mod_r db "r", 0
    descriptor_fisier dd -1
    format_printf db "Nr. cifre pare: %d", 0
    nr_cif dd 0
    a dd 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de cifre pare si sa se afiseze aceasta valoare. Numele fisierului text este definit in segmentul de date.
        deschidere_fisier:
            push mod_r
            push nume_fisier
            call [fopen]
            add esp, 4*2
            cmp EAX, 0
            je sfarsit
            mov [descriptor_fisier], EAX
            
        
        citire:  
            push a
            push format_fscanf
            push dword [descriptor_fisier]
            call [fscanf]
            add esp, 4*3
            
            test eax, eax
            js afisare
            
            jmp verificare_cifra
            
            
            verificare_par:
                push EAX
                mov EAX, [a]; EAX = a
                mov EDX, 0; pentru ca sunt cifre si consideram oricum fara semn
                mov EBX, 2
                div EBX ; acum edx = restul
                cmp EDX, 0 ; vedem daca restul la impartirea cu 2 e 0 <==> e divizibil cu 2 <==> e par
                jne skip_add_pare
                inc dword [nr_cif]
                skip_add_pare:
                pop EAX
                jmp citire
        
        
        verificare_cifra:
            cmp dword [a], '0'
            jl citire
            cmp dword [a], '9'
            jg citire
            ; convert to cifra
            sub dword [a], '0'
            jmp verificare_par
        
        afisare:
            push dword [nr_cif]
            push format_printf
            call [printf]
            add esp, 4*2
            
            
        sfarsit:
        
            
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
