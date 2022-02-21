bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    format_scanf db "%d", 0
    format_printf db "%d", 0
    a dd 0
    b dd 7FFFFFFFh
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;Se citesc de la tastatura numere (in baza 10) pana cand se introduce cifra 0. Determinaţi şi afişaţi cel mai mic număr dintre cele citite.
        
        loop_citire:
            push a
            push format_scanf
            call [scanf]
            add esp, 4*2
            ; a a primit valoare
            cmp [a], dword 0
            je afisare
            mov EAX, [b]
            cmp [a], EAX
            jge skip_update_lowest
            update_lowest:
                mov EAX, [a]
                mov [b], EAX
            skip_update_lowest:
            
            
        jmp loop_citire
        
            
        afisare:
            push dword [b]
            push format_printf
            call [printf]
            add esp, 4*2
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
