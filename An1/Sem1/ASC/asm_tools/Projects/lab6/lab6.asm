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


    s1 dd 0702090Ah, 0B0C0304h, 05060108h
    l1 equ ($-s1)/2
    ; se va obtine:
    ; 72h, 9Ah, BCh,  34h, 56h, 18h
    ; care se va ordona crescator:
    ; 9Ah, 0BCh, 18h, 34h, 56h, 72h
    s2 times l1 db 0
    
segment code use32 class=code
    start:
        ; ...
        ;3. Se da un sir de 3 dublucuvinte, fiecare dublucuvant continand 2 valori pe cuvant (despachetate, deci fiecare cifra hexa e precedata de un 0). Sa se creeze un sir de octeti care sa contina acele valori (impachetate deci pe un singur octet), ordonate crescator in memorie, acestea fiind considerate numere cu semn.
        
        mov ecx, l1
        jecxz sfarsit
        
        mov esi, s1
        mov edi, s2
        cld
        repeta:
            lodsw
            push ecx
            mov cl, 4
            shl AX, cl
            shr AH, cl
            shr AX, cl ; AL = byte-ul fara 0 dintre worduri
            stosb
            pop ecx
        loop repeta
        
        mov esi, 0 ; i=0
        
        for_i:
            mov edi, esi ; j=i
            inc edi ; j =i+1
            cmp edi, l1
            jge sfarsit_for_j
            
            for_j:
                mov al, [s2+esi] ; a[i]
                mov bl, [s2+edi] ; a[j]
                cmp al, bl
                jg swap
                jmp next_item
                swap:
                    mov [s2+esi], bl
                    mov [s2+edi], al
                    
                next_item:
                inc edi
            cmp edi, l1
            jl for_j
            sfarsit_for_j:
            inc esi
            
            cmp esi, l1-1
            jl for_i
         
        ; for(i=0;i<n-1;i++)
            ; for(j=i+1;j<n;j++)
                ; if(a[i]>a[j])
                    ; swap(a[i],a[j])
        
        
        sfarsit:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
