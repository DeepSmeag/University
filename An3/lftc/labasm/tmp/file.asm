SYS_READ   equ     0
SYS_WRITE  equ     1
SYS_EXIT   equ     60
STDIN      equ     0
STDOUT     equ     1
NEWLINE    db     10

section .data
x dd 0
b dd 0
a dd 0
section .text
global _start
_start:
mov rdx, 16
mov rsi, b
mov rdi, STDIN
mov rax, SYS_READ
syscall
mov rdx, 16
mov rsi, a
mov rdi, STDIN
mov rax, SYS_READ
syscall
mov eax, [a]
add eax, [b]
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
int 80h
mov eax,1
mov ebx,0
int 80h;
