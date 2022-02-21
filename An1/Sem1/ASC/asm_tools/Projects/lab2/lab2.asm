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
    ; 1,2)Define a,b,c,d as byte
    ; a DB 8
    ; b DB 29
    ; c DB 120
    ; d DB 91
    ; 3)Define a,b,c,d as word
    ; a DW 8
    ; b DW 29
    ; c DW 120
    ; d DW 91
    
    ; 4)Define a,b,c byte--d word
    ; a DB 8
    ; b DB 29
    ; c DB 120
    ; d DW 91
    
    ; 5) Define a,b,c,d bye -- e,f,g word
    a DB 8
    b DB 29
    c DB 120
    d DB 91
    e DW 10
    f DW 123
    g DW 19
; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        ; Exerciții simple
        ; 1)3. 128+128
        
        ; mov AX, 0
        ; mov AX, 128
        ; add AX, 128
        
        ; Adunări, scăderi
        ; a,b,c,d - byte
        ; 2)3. (c+d)-(a+d)+b = 211-99+29 = 141
        
        ; mov AX, 0
        ; mov AX, [c] ; AX = [c]
        ; add AX, [d] ; AX = ([c]+[d])
        
        ; mov BX, 0
        ; mov BX, [a] ; BX = [a]
        ; add BX, [d] ; BX = ([a]+[d])
        ; sub AX, BX ; AX = AX - BX = ([c]+[d]) - ([a]+[d])
        ; add AX, [b] ; AX = ([c]+[d]) - ([a]+[d]) + [b]
        
        
        ; a,b,c,d - word
        ; 3)3. (b+b+d)-(c+a)
        
        ; mov EAX, 0
        ; mov EAX, [b] ; EAX = [b]
        ; add EAX, [b] ; EAX = [b] + [b]
        ; add EAX, [d] ; EAX = [b] + [b] + [d]
        
        ; mov EBX, 0
        ; mov EBX, [c] ; EBX = [c]
        ; add EBX, [a] ; EBX = [c] + [a]
        
        ; sub EAX, EBX ; EAX = EAX - EBX = ([b] + [b] + [d]) - ([c] + [a]) = 149 - 128 = 21
        
        
        
        ; Înmulțiri, împărțiri
        ; a,b,c - byte, d - word
        ; 4)3. [-1+d-2*(b+1)]/a
        
        ; mov EAX, 0
        ; mov AL, [b] ; EAX = [b]
        ; inc AL ; AL = [b] + 1
        ; mov AH, 2 ; AH = 2
        ; mul AH ; AX = AL * AH = 2* ([b] + 1)
    
        ; mov EBX, 0
        ; mov BX, [d] ; BX = [d]
        ; sub BX, AX ; BX = [d] - 2* ([b] + 1)
        ; sub BX, 1 ;  BX = [d] - 2* ([b] + 1) - 1
        
        ; mov AX, BX ; AX = BX
        ; mov BL, [a] ; BL = [a]
        ; div BL ; AL = AX // [a] ; AH = AX % [a] ==> AL = 3; AH = 6
        
        
        ; a,b,c,d-byte, e,f,g,h-word
        ; 5)3. (e+f)*g
        
        mov AX, 0
        mov AX, [e] ; AX = [e]
        add AX, [f] ; AX = [e] + [f]
        mov BX, [g] ; BX = [g]
        mul BX ; DX:AX = AX * BX
        push DX
        push AX
        pop EAX
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
