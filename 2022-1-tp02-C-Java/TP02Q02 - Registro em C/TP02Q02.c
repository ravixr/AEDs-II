#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

struct Date {
    char dateString[11];
    int fullDateInt;
};

struct Keywords {
    char **member;
    int length;
};

struct Movie {
    char name[100];
    char oTitle[100];
    struct Date date;
	int runtime;
	char genre[200];
	char oLang[25];
	char status[25];
	float budget;
    struct Keywords keywords;
};

struct Date emptyDate() {
    struct Date date;
    strcpy(date.dateString, "");
    date.fullDateInt = 0;
    return date;
}

struct Keywords emptyKeywords() {
    struct Keywords keywords;
    keywords.member = malloc(sizeof(char *));
    keywords.member[0] = malloc(2);
    strcpy(keywords.member[0], "");
    keywords.length = 0;
    return keywords;
}

struct Movie emptyMovie() {
    struct Movie m;
    strcpy(m.name, "");
    strcpy(m.oTitle, "");
    m.runtime = 0;
    strcpy(m.genre, "");
    strcpy(m.oLang, "");
    strcpy(m.status, "");
    m.budget = 0.00f;
    return m;
}

char *removeTags (char *str) {
    char *newStr = malloc(strlen(str) + 1);
	bool isTag = false;
    int count = 0;
	for (int i = 0; i < strlen(str); i++) {
		if (str[i] == '<') isTag = true;
		else if (str[i] == '>') isTag = false;
		if (!isTag && str[i] != '<' && str[i] != '>' && str[i] != '\n')
            newStr[count++] = str[i];
	}
    newStr[count] = 0;
	return newStr;
}

char *removeHtmlEntities (char *str) {
    char *newStr = malloc(strlen(str) + 1);
	bool isHtmlEntities = false;
    int count = 0;
	for (int i = 0; i < strlen(str); i++) {
		if (str[i] == '&') isHtmlEntities = true;
		else if (str[i] == ';') isHtmlEntities = false;
		if (!isHtmlEntities && str[i] != '&' && str[i] != ';')
            newStr[count++] = str[i];
	}
    newStr[count] = 0;
	return newStr;
}

char *trimSpace(char *str) {
    char *aux;
    while(isspace((unsigned char)*str)) str++;
    if (*str == 0) return str;
    aux = str + strlen(str) - 1;
    while(aux > str && isspace((unsigned char)*aux)) aux--;
    aux[1] = '\0';
    return str;
}

struct Date parseDate(char *str) {
    struct Date date = emptyDate();
    if (strcmp(str, "") != 0) {
    strcpy(date.dateString, str);
    char aux[8];
        for (int i = 0, j = 0; str[i] != ' '; i++) {
            if (str[i] != '/')
                aux[j++] = str[i];
        }
        date.fullDateInt = atoi(aux);
    }
    return date;
}

int stringToMin(char *str) {
	int result = 0, count = 0, n = strlen(str);
    if (n < 4) {
        if (str[1] == 'h') result += 60 * (str[0] - '0');
        else {
            strncat(str, str, n - 2);
            result += atoi(str);
        }
    } else {
        result += 60 * (str[0] - '0');
        char *aux = malloc(n - 4);
        for (int i = 3, j = 0; i < n - 1; i++)
            aux[j++] = str[i];
        result += atoi(aux);
    }
	return result;
}

char *removeDataName(char *str, char *dataName) {
    int count = 0;
    char *newStr = malloc(strlen(str) + 1);
    for (int i = strlen(dataName); i < strlen(str); i++)
        newStr[count++] = str[i];
    newStr[count] = 0;
    return newStr;
}

float moneyToFloat(char *str) {
    float result = 0.00f;
    if (strcmp("-", str) != 0) {
        char *aux = malloc(strlen(str) + 1);
        int count = 0;
        for (int i = 0; i < strlen(str); i++)
            if (str[i] != '$' && str[i] != ',')
                aux[count++] = str[i];
        aux[count] = 0;
        result = atof(aux);
        free(aux);
    }
    return result;
}

struct Keywords parseKeywords(FILE *f, char *line) {
    for (int i = 0; i < 2; i++) fgets(line, 1000, f);
    struct Keywords keywords = emptyKeywords();
    if (strcmp(trimSpace(removeTags(line)), "Nenhuma palavra-chave foi adicionada.") != 0) {
        char **aux = malloc(sizeof(char *) * 30);
        for (int i = 0; i < 30; i++) aux[i] = malloc(100);
        while (strstr(line, "</ul>") == 0) {
            if (strstr(line, "<li>")) {
                strcpy(aux[keywords.length], removeTags(trimSpace(line)));
                if (strcmp(aux[keywords.length], "") != 0) keywords.length++;
            }
            fgets(line, 1000, f);
        }
        keywords.member = malloc(sizeof(char *) * keywords.length);
        for (int i = 0; i < keywords.length; i++) {
            keywords.member[i] = malloc(strlen(aux[i]) + 1);
            strcpy(keywords.member[i], aux[i]);
        }
        free(aux);
    }
    return keywords;
}

struct Movie readMovie (char fileName[100]) {
    struct Movie m = emptyMovie();
    char *filePath;
    filePath = malloc(sizeof(char) * 1000);
    strcpy(filePath, "/tmp/filmes/");
    strcat(filePath, fileName);
    if (strchr(filePath, 13)) {
        filePath[strlen(filePath) - 1] = 0;
    }
    FILE *f = fopen(filePath, "r");
    char *line = malloc(1000);
    while(fgets(line, 1000, f) != NULL) {
        if (strstr(line, "h2 class")) {
            fgets(line, 1000, f);
            strcpy(m.name, trimSpace(removeTags(line)));
            strcpy(m.oTitle, m.name);
        } else if (strstr(line, "\"release\"")) {
            fgets(line, 1000, f);
            m.date = parseDate(trimSpace(line));
        } else if (strstr(line, "\"genres\"")) {
            for (int i = 0; i < 2; i++) fgets(line, 1000, f);
            strcpy(m.genre, trimSpace(removeHtmlEntities(removeTags(line))));
        } else if (strstr(line, "\"runtime\"")) {
            for (int i = 0; i < 2; i++) fgets(line, 1000, f);
            m.runtime = stringToMin(trimSpace(line));
        } else if (strstr(line, "Título original")) {
            strcpy(m.oTitle, trimSpace(removeDataName(trimSpace(removeTags(line)), "Título original")));
        } else if (strstr(line, "Situação")) {
            strcpy(m.status, trimSpace(removeDataName(trimSpace(removeTags(line)), "Situação")));
        } else if (strstr(line, "Idioma original")) {
            strcpy(m.oLang, trimSpace(removeDataName(trimSpace(removeTags(line)), "Idioma original")));
        } else if (strstr(line, "Orçamento")) {
            m.budget = moneyToFloat(trimSpace(removeDataName(trimSpace(removeTags(line)), "Orçamento")));
        } else if (strstr(line, "Palavras-chave")) {
            m.keywords = parseKeywords(f, line);
        }
    }
    fclose(f);
    free(filePath);
    free(line);
    return m;
}

void printMovie(struct Movie m) {
    printf("%s %s %s %d %s %s %s %g [", m.name, m.oTitle, m.date.dateString, m.runtime, m.genre, m.oLang, m.status, m.budget);
    for (int i = 0; i < m.keywords.length - 1; i++) {
                printf("%s, ", m.keywords.member[i]);
    }
    if (m.keywords.length > 0) printf("%s]\n", m.keywords.member[m.keywords.length - 1]);
    else printf("]\n");
}

int isFIM(char s[1000]) {
    return (strlen(s) <= 4 && s[0] == 'F' && s[1] == 'I' && s[2] == 'M');
}

int main() {
    char **s = malloc(sizeof(char *) * 100);
    int count = 0;
    for (int i = 0; i < 101; i++) {
        s[i] = malloc(1000);
        fgets(s[i], 1000, stdin);
        if (isFIM(s[i])) break;
        s[i][strlen(s[i]) - 1] = 0;
        count++;
    }
    struct Movie *m = malloc(sizeof(struct Movie) * count);
    for (int i = 0; i < count; i++) {
        m[i] = readMovie(s[i]);
        printMovie(m[i]);
    }
    free(s);
    free(m);
    return 0;
}
