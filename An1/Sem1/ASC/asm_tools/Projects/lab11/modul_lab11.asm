bits 32 

global concatenare        

segment code use32 class=code public
    concatenare:
        ; ...
        ; va primi 3 parametri adrese si va face concatenare param2 la param1, pe care il pune in param3
        ; [ESP] - adresa de return
        ; [ESP+4] - adresa param1
        ; [ESP+8] - adresa param2
        ; [ESP+12] - adresa param3
        ; concatenare (param1, param2, param3)
        ; param2 concatenat la param1, rezultat pus in param3
        mov esi, [esp+4]
        mov edi, [esp+12]
        
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
        
        mov esi, [esp+8]
        
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
        
        
    ret
