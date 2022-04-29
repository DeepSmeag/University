#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv) {
  // sfsefe
  if (argc<2)
  {
    printf("Please provide a name\n");
    exit(1);
  }
  printf("Hello %s\n", argv[1]);
  return 0;
}
