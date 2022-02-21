bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    format_printf db "%d + %d = %d",0
    format_scanf db "%d %d", 0
    a dd 0
    b dd 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ; Se dau doua numere naturale a si b (a, b: dword, definite in segmentul de date). Sa se calculeze suma lor si sa se afiseze in urmatorul format: "<a> + <b> = <result>"
        ; Exemplu: "1 + 2 = 3"
        ; Valorile vor fi afisate in format decimal (baza 10) cu semn.
        push b
        push a
        push format_scanf
        call [scanf]
        add esp, 4*3
        
        mov ebx, [b]
        add ebx, [a]
        ; ebx= a+b
        
        push ebx
        push dword [b]
        push dword [a]
        push format_printf
        call [printf]
        add esp, 4*4
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
