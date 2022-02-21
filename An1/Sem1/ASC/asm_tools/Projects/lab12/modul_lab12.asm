bits 32              
                          
global _asm_concatenare
segment data public data use32

    adresaSir1 dd 0
    adresaSir2 dd 0 
    adresaRez dd 0
segment code public code use32
    ; 3. Se dau doua siruri continand caractere. Sa se calculeze si sa se afiseze rezultatul concatenarii tuturor caracterelor tip cifra zecimala din cel de-al doilea sir dupa cele din primul sir si invers, rezultatul concatenarii primului sir dupa al doilea.
    _asm_concatenare:
        push ebp
        mov ebp, esp ; adresa de return e acum in ebp
        
        ; sub esp, 4*3; rezervam pe stiva spatiu pentru variabilele locale
        
        ; la [ebp] se afla valoarea ebp pentru apelant
        ; la [ebp+4] se afla adresa de return (valoarea din EIP la momentul apelarii)
        
        ; [ebp+8] - adresa sir param1
        ; [ebp+12] - adresa sir param2
        ; [ebp+16] - adresa sir rez
        
        mov eax, [ebp+8]
        mov [adresaSir1], eax
        
        mov eax, [ebp+12]
        mov [adresaSir2], eax
        
        mov eax, [ebp+16]
        mov [adresaRez], eax
        
        ;acum avem adresele sirurilor si le putem modifica
        
        mov esi, [adresaSir1]
        mov edi, [adresaRez]
            
        cld
            
        concat:
            lodsb
            cmp al,0
            je final1
            ;verificare daca e caracter cifra
            cmp al, '0'
            jb skip_concat
            cmp al, '9'
            ja skip_concat
            stosb
            skip_concat:
                
            jmp concat
            
        final1:
        
        mov esi, [adresaSir2]
        
        
        cld
        
        concat2:
            lodsb
            cmp al,0
            je final2
            ;verificare daca e caracter cifra
            cmp al, '0'
            jb skip_concat2
            cmp al, '9'
            ja skip_concat2
            stosb
            skip_concat2:
                
            jmp concat2
            
        final2:
        
        
        
        
        mov esp, ebp
        pop ebp
            
    ret
