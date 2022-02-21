bits 32 


global start        


extern exit, fscanf, fprintf, fopen, fclose           
import exit msvcrt.dll    
import fscanf msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
                          


segment data use32 class=data

    cuvant times 100 db 0
    numar_cuvinte dd 0
    nr_cifre dd 0

    descriptor1 dd 0
    descriptor2 dd 0
    
    nume1 db "fisier1.txt", 0 ; cel care contine propozitiile
    nume2 db "fisier2.txt", 0 ; cel in care scriem
    mod_1 db "r", 0
    mod_2 db "w", 0
    
    
    
    format_citire db "%s", 0
    format_scriere db "%s %d",10, 0


segment code use32 class=code
    start:
        ;Sa se scrie un program care citeste un fisier text care contine propozitii (propozitiile se termina cu caracterul '?')
        ; si scrie intr-un alt fisier doar ultimul cuvant din fiecare propozitie urmat de numarul cuvintelor din acea propozitie.
        ; Numele celor 2 fisiere se da in segmentul de date.
    
        ;fopen(primul, mod)
        push dword mod_1
        push dword nume1
        call [fopen]
        add esp, 4*2
        cmp eax, 0
        je .final
        
        mov [descriptor1], eax
        
        push dword mod_2
        push dword nume2
        call [fopen]
        add esp, 4*2
        cmp eax,0
        je .pre_final
        mov [descriptor2], eax
        ;avem ambele fisiere deschise, acum incepem sa citim cuvinte din primul
        .citire_cuvinte:
            push dword cuvant
            push dword format_citire
            push dword [descriptor1]
            call [fscanf]
            add esp, 4*3
            cmp eax, -1
            je .final_citire
            ; am citit cuvant, il am in "cuvant"
            inc dword [numar_cuvinte]
            cld
            mov esi, cuvant
            mov dword [nr_cifre], 0
            .verif_char:
                lodsb
                cmp al, '?'
                je .scriere_cuvant
                cmp al, 0
                je .citire_cuvinte
                inc dword [nr_cifre]
                jmp .verif_char
            .scriere_cuvant:
                cmp dword [nr_cifre], 0
                je .citire_cuvinte
                mov edx, [nr_cifre]
                mov byte [cuvant + edx], 0
                ;fprintf(desc, format, cuvant, nr_cuv)
                push dword [numar_cuvinte]
                push dword cuvant
                push dword format_scriere
                push dword [descriptor2]
                call [fprintf]
                add esp, 4*4
                mov dword [numar_cuvinte], 0
                jmp .citire_cuvinte
        .final_citire:
            push dword [descriptor2]
            call [fclose]
            add esp, 4*1
        
        .pre_final:
            push dword [descriptor1]
            call [fclose]
            add esp, 4*1
        
        
        .final:
    
        ; exit(0)
        push    dword 0      
        call    [exit]       
