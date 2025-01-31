#include <stdio.h>
#include <string.h>

// Action Table (axn) and Goto Table (gotot) kept as original
int axn[][6][2] = {
    {{100,5},{-1,-1},{-1,-1},{100,4},{-1,-1},{-1,-1}},
    {{-1,-1},{100,6},{-1,-1},{-1,-1},{-1,-1},{102,102}},
    {{-1,-1},{101,2},{100,7},{-1,-1},{101,2},{101,2}},
    {{-1,-1},{101,4},{101,4},{-1,-1},{101,4},{101,4}},
    {{100,5},{-1,-1},{-1,-1},{100,4},{-1,-1},{-1,-1}},
    {{100,5},{101,6},{101,6},{-1,-1},{101,6},{101,6}},
    {{100,5},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1}},
    {{100,5},{-1,-1},{-1,-1},{100,4},{-1,-1},{-1,-1}},
    {{-1,-1},{100,6},{-1,-1},{-1,-1},{100,11},{-1,-1}},
    {{-1,-1},{101,1},{100,7},{-1,-1},{101,1},{101,1}},
    {{-1,-1},{101,3},{101,3},{-1,-1},{101,3},{101,3}},
    {{-1,-1},{101,5},{101,5},{-1,-1},{101,5},{101,5}}
};

int gotot[12][3] = {
    {1,2,3}, {-1,-1,-1}, {-1,-1,-1}, {-1,-1,-1},
    {8,2,3}, {-1,-1,-1}, {-1,9,3}, {-1,-1,10},
    {-1,-1,-1}, {-1,-1,-1}, {-1,-1,-1}, {-1,-1,-1}
};

int a[10];
char b[10];
int top = -1, btop = -1;

void push(int k) {
    if(top < 9) a[++top] = k;
}

void pushb(char k) {
    if(btop < 9) b[++btop] = k;
}

int TOS() {
    return a[top];
}

void pop() {
    if(top >= 0) top--;
}

void popb() {
    if(btop >= 0) b[btop--] = '\0';
}

void display() {
    for(int i = 0; i <= top; i++)
        printf("%d%c", a[i], b[i]);
}

void display1(char p[], int m) {
    printf("\t\t");
    for(int l = m; p[l] != '\0'; l++)
        printf("%c", p[l]);
    printf("\n");
}

void error() {
    printf("\n\nSyntax Error");
}

void reduce(int p) {
    char *dest = "";
    char src;
    
    switch(p) {
        case 1: dest = "E+T"; src = 'E'; break;
        case 2: dest = "T";   src = 'E'; break;
        case 3: dest = "T*F"; src = 'T'; break;
        case 4: dest = "F";   src = 'T'; break;
        case 5: dest = "(E)"; src = 'F'; break;
        case 6: dest = "i";   src = 'F'; break;
        default: dest = "";   src = '\0'; break;
    }
    
    // Pop operations with bounds checking
    int len = strlen(dest);
    while(len-- > 0 && top >= 0 && btop >= 0) {
        pop();
        popb();
    }
    
    pushb(src);
    
    // Goto table lookup with bounds checking
    int ad;
    switch(src) {
        case 'E': ad = 0; break;
        case 'T': ad = 1; break;
        case 'F': ad = 2; break;
        default:  ad = -1; break;
    }
    
    if(ad != -1 && TOS() >= 0 && TOS() < 12 && ad < 3) {
        push(gotot[TOS()][ad]);
    }
}

int main() {
    char ip[20];
    printf("Enter any String :- ");
    fgets(ip, 20, stdin);  // Safer alternative to gets()
    ip[strcspn(ip, "\n")] = '\0'; // Remove newline
    
    push(0);
    display();
    printf("\t%s\n", ip);
    
    for(int j = 0; ip[j] != '\0';) {
        int st = TOS();
        char an = ip[j];
        int ic = -1;
        
        // Fixed logical operators (&& instead of &)
        if(an >= 'a' && an <= 'z') ic = 0;
        else if(an == '+') ic = 1;
        else if(an == '*') ic = 2;
        else if(an == '(') ic = 3;
        else if(an == ')') ic = 4;
        else if(an == '$') ic = 5;
        
        if(ic == -1) {
            error();
            break;
        }
        
        if(axn[st][ic][0] == 100) {
            pushb(an);
            push(axn[st][ic][1]);
            display();
            j++;
            display1(ip, j);
        }
        else if(axn[st][ic][0] == 101) {
            reduce(axn[st][ic][1]);
            display();
            display1(ip, j);
        }
        else if(axn[st][ic][1] == 102) {
            printf("Given String is Accepted");
            break;
        }
        else {
            error();
            break;
        }
    }
    return 0;
}