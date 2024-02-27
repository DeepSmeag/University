SYS_READ   equ     0
SYS_WRITE  equ     1
SYS_EXIT   equ     60
STDIN      equ     0
STDOUT     equ     1
NEWLINE    db     10

section .data
x dd 0
section .text
global _start
_start:
mov rdx, 16
mov rsi, x
mov rdi, STDIN
mov rax, SYS_READ
syscall
mov eax, [x]
add eax, 1
sub eax, 3
mov [x], eax
mov rdx, 1
mov rsi, x
mov rdi, STDOUT
mov rax, SYS_WRITE
syscall
mov rdx, 1
mov rsi, NEWLINE
mov rdi, STDOUT
mov rax, SYS_WRITE
syscall
mov eax, [x]
xor edx, edx
mov ebx, 2
mul ebx
mov [x], eax
mov rdx, 1
mov rsi, x
mov rdi, STDOUT
mov rax, SYS_WRITE
syscall
mov rdx, 1
mov rsi, NEWLINE
mov rdi, STDOUT
mov rax, SYS_WRITE
syscall
mov eax, [x]
xor edx, edx
mov ebx, 3
div ebx
mov [x], eax
mov rdx, 1
mov rsi, x
mov rdi, STDOUT
mov rax, SYS_WRITE
syscall
mov rdx, 1
mov rsi, NEWLINE
mov rdi, STDOUT
mov rax, SYS_WRITE
syscall
int 80h
mov eax,1
mov ebx,0
int 80h;
