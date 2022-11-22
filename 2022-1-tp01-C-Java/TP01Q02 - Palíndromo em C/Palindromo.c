#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int isFIM(char s[1000]) {
    return (strlen(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int isPalindrome(char s[1000]) {
    int bl = 1;
    if (strlen(s) == 0)
        bl = 0;
    else {

        /*if (strlen(s) % 2) {
            memmove(&s[(int)(strlen(s) / 2)], &s[(int)(strlen(s) / 2) + 1], strlen(s) - (int)(strlen(s) / 2));
        }*/

        for (int i = 0, j = strlen(s) - 1; i < (strlen(s) / 2); i++, j--)
            if (s[i] != s[j]) {
                bl = 0;
            }

    }
    return bl;
}

void printOut(int bl/*, FILE *f*/) {
    if (bl) printf(/*f,*/"SIM\n");
    else printf(/*f,*/ "NAO\n");
}

int main() {
    /*FILE *in, *out;
    in = fopen("pub.in", "r");
    out = fopen("my.out", "w");*/
    char s[1000];
    while(fgets(s, 1000, stdin) != NULL){
        s[strlen(s) - 1] = 0;
        if (isFIM(s)) break;
        printOut(isPalindrome(s)/*, out*/);
    }
    /*fclose(in);
    fclose(out);*/
    return 0;
}