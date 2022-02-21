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
    ; Adunări, scăderi
    ; 1) a - byte, b - word, c - double word, d - qword - Interpretare fara semn
    ; a db 2
    ; b dw 10
    ; c dd 10
    ; d dq 10
    ; 2) a - byte, b - word, c - double word, d - qword - Interpretare cu semn
    ; a db 2
    ; b dw 100
    ; c dd 10
    ; d dq 50
    ; Înmulțiri, împărțiri - Interpretare fara semn si interpretare cu semn
    ; 3) a,b,c-byte; d-doubleword; e-qword
    a db 2
    b db 8
    c db 1
    d dd 16
    e dq 24
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ; 1) (b+b)+(c-a)+d = 38
        ; mov EAX, 0 ; EAX = 0
        ; mov AX, [b]; EAX = b
        ; add AX, [b]; EAX = b+b
        
        ; mov EBX, [c]; EBX = c
        ; mov EDX, 0; EDX = 0
        ; mov DL, [a]; EDX=a
        ; sub EBX, EDX; EBX = c-a
        
        ; add EAX, EBX ; EAX = (b+b) - (c-a)
        ; mov EDX, 0; EDX:EAX = (b+b) - (c-a)
        ; add EAX, dword [d]
        ; adc EDX, dword [d+4] ; EDX:EAX = (b+b) - (c-a) + d
        
        
        ; 2) (c+b)-a-(d+d) = 8
        ; mov AX, [b]; AX = b
        ; cwde ; EAX = b
        ; add EAX, [c]; EAX = b + c
        
        ; mov EBX, EAX; EBX = b + c
        ; mov AL, [a]; AL = a
        ; cbw ; AX = a
        ; cwde ; EAX = a
        
        ; sub EBX, EAX ; EBX = EBX - EAX = (b+c) - a   
        ; mov EAX, EBX ; EAX = (b+c) - a
        ; cdq ; EDX:EAX = (b+c) - a
        
        ; mov EBX, dword [d]
        ; mov ECX, dword [d+4] ; ECX:EBX = d
        
        ; add EBX, EBX
        ; adc ECX, ECX ; ECX:EBX = d + d
        
        ; sub EDX, ECX
        ; sbb EAX, EBX ; EDX:EAX = (b+c) - a - (d+d)
        
        ; 3) 2/(a+b*c-9)+e-d = 10
        mov AL, [b] ; AL = b
        imul byte [c] ; AX = b * c
        add AL, [a]
        
        adc AX, 0 ; AX = a + b*c
        sub AX, 9 ; AX = a + b*c - 9
        mov BX, AX; BX = AX = a + b*c - 9
        mov AX, 2 ; AX = 2
        mov DX, 0 ; DX:AX = 2
        
        idiv BX ; AX = DX:AX // BX = 2/(a+b*c-9)
        mov BX, AX ; BX = AX
        mov EAX, 0; EAX = 0
        mov AX, BX; EAX = 2/(a+b*c-9)
        cwde ; EAX = 2/(a+b*c-9)
        
        mov EBX, dword [e]
        mov ECX, dword [e+4] ; ECX:EBX = e
        
        cdq ; EDX:EAX = 2/(a+b*c-9)
        ; mov EDX, 0 ; EDX:EAX = 2/(a+b*c-9)
        
        add EAX, EBX
        adc EDX, ECX ; EDX:EAX = 2/(a+b*c-9) + e
        
        mov EBX, [d] ; EBX = d
        
        mov ECX, 0; ECX:EBX = d
        
        sub EDX, ECX
        sbb EAX, EBX ; EDX:EAX = 2/(a+b*c-9) + e - d
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
