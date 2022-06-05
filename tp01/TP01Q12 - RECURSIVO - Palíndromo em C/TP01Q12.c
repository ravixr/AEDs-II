#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int isFIM(char s[1000]) {
    return (strlen(s) == 3 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int isPalindrome(char s[1000], int i, int j) {
    //int isPal;
    if (i == j){
        return 1;
    } else if (s[i] == s[j]) {
        return isPalindrome(s, ++i, --j);
    } else return 0;
    //return isPal;
}

void printOut(int isPal) {
    if (isPal) printf("SIM\n");
    else printf("NAO\n");
}

int main() {
    char s[1000];
    while(fgets(s, 1000, stdin) != NULL){
        s[strlen(s) - 1] = 0;
        if (isFIM(s)) break;
        printOut(isPalindrome(s, 0, (strlen(s) - 1)));
    }
    return 0;
}