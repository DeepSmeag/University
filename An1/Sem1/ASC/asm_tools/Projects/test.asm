bits 32; assembling for the 32 bits architecture 

  

; declare the EntryPoint (a label defining the very first instruction of the program) 

global start         

  

; declare external functions needed by our program 

extern exit, printf, scanf, fprintf, fscanf, fopen, fclose, fwrite,fputc, ftell,fseek, fgetpos, fsetpos; tell nasm that exit exists even if we won't be defining it 

import exit msvcrt.dll; exit is a function that ends the calling process. It is defined in msvcrt.dll 

                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions 

import printf msvcrt.dll 
import fprintf msvcrt.dll
import fscanf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll 
import fwrite msvcrt.dll
import fputc msvcrt.dll
import ftell msvcrt.dll
import fseek msvcrt.dll
import fgetpos msvcrt.dll
import fsetpos msvcrt.dll
; our data is declared here (the variables needed by our program) 

segment data use32 class=data 
    
    ok db 0
    
    char resb 1
    
    format_scanf db "%s %c",0
    format_printf db "%s",10,"%c",0
    
    cuvant times 100 db 0
    ; chestii fisiere
    mod_acces_r db "r",0
    mod_acces_w db "w",0
    fr_name times 100 db 0
    fw_name db "cuvinte.txt", 0
    descriptor_r dd 0
    descriptor_w dd 0
    
    format_readf db "%s",0
    format_writef db "%s ",0
    
    back_string db 0
    
    format_back db "%s",0
    
    file_position dd 0
; our code starts here 

segment code use32 class=code 

    start: 

        push dword char
        push dword fr_name
        push dword format_scanf
        call [scanf]
        add esp, 4*3
        
        ; am numele de fisier, acum il deschidem
        ; fopen(nume_fisier, mod_acces)
        push dword mod_acces_r
        push dword fr_name
        
        call [fopen]
        add esp, 4*2
        
        cmp eax, 0
        je .final
        
        mov [descriptor_r], eax
        
        ; fopen(fis_w, mod_w)
        push dword mod_acces_w
        push dword fw_name
        call [fopen]
        add esp, 4*2
        cmp eax,0
        je .final_inchid_r
        mov [descriptor_w], eax 
        
        ; avem deschis fisierul, acum citim din el
        ; fscanf(descriptor, format, loc in care pun)
        .citire_cuv
           
            
            push dword cuvant
            push dword format_readf
            push dword [descriptor_r]
            call [fscanf]
            add esp, 4*3
            
            cmp eax, -1
            je .final_citire
            
            ; luam caracter cu caracter cuvantul si verificam
            mov esi, cuvant
            ;mov byte [ok], 0
            cld
            .loop_verif_char:
                lodsb
                cmp al, 0
                je .citire_cuv
                cmp al, byte [char]
                jne .loop_verif_char
                
                ;.schimb_ok:
                    ;mov byte[ok], 1
                   
            ;cmp byte [ok], 1
            jmp .write_to_file
            .inainte_de_citire_cuv:
            jmp .citire_cuv
            
            
            .write_to_file:
                ; scriu in fisier aia
                ; fprintf(descriptor, format, ceva din format)
                push dword cuvant
                push dword format_writef
                push dword [descriptor_w]
                call [fprintf]
                add esp, 4*3
                
                
                jmp .inainte_de_citire_cuv
            
            
            
        
        .final_citire:
        
        
        
        
        
        
        
        ; push dword [descriptor_w]
        ; call [ftell]
        ; add esp, 4*1
        push dword file_position
        push dword [descriptor_w]
        call [fgetpos]
        add esp, 4*2
        
        ; sub eax, 5
        ; mov dword [file_position], eax
        mov eax, [file_position]
        sub eax, 1
        mov [file_position], eax
        
        push dword file_position
        push dword [descriptor_w]
        call [fsetpos]
        add esp, 4*2
        
        ; push dword [descriptor_w]
        ; push dword 1
        ; push dword 1
        ; push dword format_back
        ; call [fwrite]
        ; add esp, 4*4
        
        ; push dword [file_position]
        ; push dword 1
        ; push dword [descriptor_w]
        ; call [fseek]
        ; add esp, 4*3
        
        push dword back_string
        push dword format_back
        push dword [descriptor_w]
        call [fprintf]
        add esp, 4*2
        
        ; push dword [descriptor_w]
        ; push dword format_back
        ; call [fputc]
        ; add esp, 4*2
        
        ;fclose(descriptor_w)
        push dword [descriptor_w]
        call [fclose]
        add esp, 4
        
        
        .final_inchid_r
        
        ;fclose(descriptor_r)
        push dword [descriptor_r]
        call [fclose]
        add esp, 4
        
        .final:
        
        
        

        ; exit (0) 

        push dword 0; push the parameter for exit onto the stack 

        Call [exit]; call exit to terminate the program 

 