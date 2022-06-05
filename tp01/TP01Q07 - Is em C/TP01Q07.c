#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int isFIM(char s[1000]) {
    return (strlen(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

   int onlyVowel (char s[1000]) {
      int ans = 1;
      if (strlen(s) == 0) ans = 0;
      else
         // testa se há algum caractere diferente de uma vogal
         for (int i = 0; i < strlen(s); i++) {
            if (s[i] != 'a' && s[i] != 'e' && s[i] != 'i' && s[i] != 'o' && s[i] != 'u' &&
            s[i] != 'A' && s[i] != 'E' && s[i] != 'I' && s[i] != 'O' && s[i] != 'U') {
               ans = 0;
               break;
            }
         }
      return ans;
   }

   int onlyCons (char s[1000]) {
      int ans = 1;
      if (strlen(s) == 0) ans = 0;
      else
         // testa se há algum caractere igual a uma vogal
         for (int i = 0; i < strlen(s); i++) {
            if ((s[i] < 'a' || s[i] > 'z') && (s[i] < 'A' || s[i] > 'Z') || 
            (s[i] == 'a' || s[i] == 'e' || s[i] == 'i' || s[i] == 'o' || s[i] == 'u' ||
            s[i] == 'A' || s[i] == 'E' || s[i] == 'I' || s[i] == 'O' || s[i] == 'U')) {
             ans = 0;
               break;
            }
         }
      return ans;
   }

   int isInt (char s[1000]) {
      int ans = 1;
      if (strlen(s) == 0) ans = 0;
      else
         // testa se há algum caractere diferente de um dígito
         for (int i = 0; i < strlen(s); i++) {
            if (s[i] < '0' || s[i] > '9') {
             ans = 0;
               break;
            }
         }
      return ans;
   }

   int isFloat (char s[1000]) {
      int ans = 1;
      int dot = 0;
      if (strlen(s) == 0) ans = 0;
      else {
         for (int i = 0; i < strlen(s); i++) {
            // testa se há algum caractere diferente de um dígito
            if (s[i] < '0' || s[i] > '9') {
               // se sim testa se é uma virgula ou ponto
               if (s[i] == ',' || s[i] == '.') {
                  dot++;
               } else {
                 ans = 0;
                  break;
               }
            }
         }
      }
      // se possuir apenas numeros porém mais de uma virgula e/ou ponto logo não é um número real
      if (ans && dot > 1) ans = 0;
      return ans;
   }

   void printMyOutput (char s[1000]){
      char text[25];
      if (onlyVowel(s)) {
         strcpy(text, "SIM NAO NAO NAO\n");
      } else if (onlyCons(s)) {
         strcpy(text, "NAO SIM NAO NAO\n");
      } else if (isInt(s)) {
         if (isFloat(s)) strcpy(text, "NAO NAO SIM SIM\n");
         else strcpy(text, "NAO NAO SIM NAO\n");
      } else if (isFloat(s)) {
         strcpy(text, "NAO NAO NAO SIM\n");
      } else {
         strcpy(text, "NAO NAO NAO NAO\n");
      }
      printf("%s", text);
   }

int main() {
    char s[1000];
    while(fgets(s, 1000, stdin) != NULL){
        s[strlen(s) - 1] = 0;
        if (isFIM(s)) break;
        printMyOutput(s);
    }
    return 0;
}