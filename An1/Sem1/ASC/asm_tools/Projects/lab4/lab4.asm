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
    a dw 1010101010101010b
    b dw 0101010101010101b
    c dd 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;3. Se dau cuvintele A si B. Sa se obtina dublucuvantul C:
        ; bitii 0-2 ai lui C coincid cu bitii 12-14 ai lui A
        ; bitii 3-8 ai lui C coincid cu bitii 0-5 ai lui B
        ; bitii 9-15 ai lui C coincid cu bitii 3-9 ai lui A
        ; bitii 16-31 ai lui C coincid cu bitii lui A
        
        mov AX, [a] ; AX = a
        shl EAX, 16; EAX are bitii 16-31 drept bitii lui A
        and AX, 0000000000000000b; AX = 0 acum
        ; Acum lucram doar pe AX
        mov BX, [a] ; BX =a
        and BX, 0000001111111000b ; izolam bitii 3-9 ai lui A
        shl BX, 6 ; Aducem bitii pe pozitiile 9-15
        or AX, BX ; AX va avea bitii lui A pe pozitiile 9-15
        
        mov BX, [b] ; BX = b
        and BX, 0000000000111111b ; am izolat bitii 0-5 din B
        shl BX, 3 ; am mutat bitii in pozitiile corecte
        or AX, BX ; Acum AX are pe pozitiile 3-8 bitii 0-5 ai lui b
        
        mov BX, [a]
        and BX, 0111000000000000b ; izolam bitii 12-14 ai lui A
        shr BX, 12 ; mutam pe pozitiile corecte
        or AX, BX ; AX are bitii ceruti pe pozitiile cerute
        ;EAX = 1010 1010 1010 1010 1010 1010 1010 1010b 
        mov [c], EAX ; c primeste EAX cu cerintele impuse
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
