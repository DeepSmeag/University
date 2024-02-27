#include <stdio.h>

__global__ matProd() {
    int row = blockIdx.x * blockDim.x + threadIdx.x;
    printf("Hello World from GPU! Row: %d\n", row);
}

int main() {
    printf("Hello World from CPU!\n");

    matProd <<<2, 20000 >>>();
    cudaDeviceSynchronize();
    return 0;
}
