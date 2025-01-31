#include <stdio.h>
#include <string.h>

int main() {
    char *code[9][4] = {
        {"PRG1", "START", "", ""},
        {"", "USING", "*", "15"},
        {"", "L", "", ""},
        {"", "A", "", ""},
        {"", "ST", "", ""},
        {"FOUR", "DC", "F", ""},
        {"FIVE", "DC", "F", ""},
        {"TEMP", "DS", "F", ""},
        {"", "END", "", ""}
    };

    char av[3] = {'\0'};
    char avail[15] = {'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N'};
    int i, j, k, count[3] = {0}, lc[9] = {0}, loc = 0;

    printf("------------------------------\n");
    printf("LABEL\t\tOPCODE\n");
    printf("------------------------------\n\n");

    for (i = 0; i < 9; i++) {
        for (j = 0; j < 4; j++) {
            printf("%s\t\t", code[i][j]);
        }
        printf("\n");
    }

    printf("------------------------------\n");
    printf("VALUES FOR LC:\n\n");

    for (j = 0; j < 9; j++) {
        if ((strcmp(code[j][1], "START") != 0) && (strcmp(code[j][1], "USING") != 0) && (strcmp(code[j][1], "L") != 0)) {
            lc[j] = lc[j - 1] + 4;
        }
        printf("%d\t", lc[j]);
    }

    printf("\n\nSYMBOL TABLE:\n-----------------------------\n");
    printf("SYMBOL\t\tVALUE\t\tLENGTH\t\tR/A");
    printf("\n-------------------------\n");

    loc = 0;
    for (i = 0; i < 9; i++) {
        if (strcmp(code[i][1], "START") == 0) {
            printf("%s\t\t%d\t\t%d\t\t%c\n", code[i][0], loc, 4, 'R');
        } else if (strcmp(code[i][0], "") != 0) {
            printf("%s\t\t%d\t\t%d\t\t%c\n", code[i][0], loc, 4, 'R');
            loc += 4;
        } else if (strcmp(code[i][1], "USING") == 0) {
            continue;
        } else {
            loc += 4;
        }
    }

    printf("-----------------------------------\n");
    printf("\nBASE TABLE:\n-----------------------------\n");
    printf("REG NO\t\tAVAILABILITY\n");
    printf("------------------------------------\n");

    for (j = 0; j < 9; j++) {
        if (strcmp(code[j][1], "USING") == 0) {
            strcpy(av, code[j][3]);
        }
    }

    count[0] = av[0] - '0'; // Convert character to integer
    count[1] = av[1] - '0'; // Convert character to integer (if applicable)
    count[2] = count[0] * 10 + count[1];
    avail[count[2] - 1] = 'Y';

    for (k = 0; k < 15; k++) {
        printf("%d\t\t%c\n", k + 1, avail[k]);
    }

    printf("----------------------------------\n");
    printf("PASS2 TABLE:\n\n");
    printf("LABEL\t\tOP1\t\tLC\t\t");
    printf("\n---------------------\n");

    for (i = 0; i < 9; i++) {
        for (j = 0; j < 4; j++) {
            printf("%s\t\t", code[i][j]);
        }
        printf("\n");
    }

    printf("------------------------------\n");
    return 0;
}
