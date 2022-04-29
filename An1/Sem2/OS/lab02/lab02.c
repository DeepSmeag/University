/* Citim matrice dintr-un fisier, stocam in alt fisier
 * Ambele nume de fisiere sunt date ca argumente  la linia de comanda
 * Prima linie a fisierului se vor afla nr de linii si coloane ale matricii
 *
 */
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
  if(argc<3) {
    perror("Dati doua fisiere\n");
    exit(1);
  }
  FILE *f_in = fopen(argv[1], "r");
  if(f_in == NULL) {
    perror("Eroare la deschidere fisier in");
    exit(1);
  }
  int n,m;
  fscanf(f_in, "%d%d", &n, &m);
  int **matrice;
  matrice = (int**) malloc( n * sizeof(int*));
  for(int i=0;i<n;i++) {
    matrice[i] = (int*) malloc(m*sizeof(int));
  }
  
  for(int i=0;i<n;i++) {
    for(int j=0;j<m;j++) {
      fscanf(f_in, "%d", &matrice[i][j]);
    }
  }
  fclose(f_in);

  FILE *f_out = fopen(argv[2], "w");
  if(f_out == NULL) {
    perror("Eroare la deschidere fisier out");
    exit(1);
  }
  for(int i=0;i<n;i++) {
    for(int j=0;j<m;j++) {
      fprintf(f_out, "%d ", matrice[i][j]);
    }
    fprintf(f_out, "\n");
  }
  for(int i=0;i<n;i++) {
    free(matrice[i]);
  }
  free(matrice);
  fclose(f_out);
  return 0;
}


