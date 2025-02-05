class symtab {
    int index;
    String name;
    int addr;
    symtab(int i, String s, int a){
        index = i;
        name = s;
        addr = a;
    }
}

class littab {
    int index;
    String name;
    int addr;

    littab(int i, String s, int a){
        index = i;
        name = s;
        addr = a;
    }
    
    void setaddr (int a){
        addr = a;
    }
}

class pooltab {
    int p_index;
    int l_index;
    pooltab(int i, int a){
        p_index = i;
        l_index = a;
    }
}

public class pass1 {
    public static void main(String args[]){
        String input[][] = {
            {null, "START","100",null},
            {null, "MOVER","AREG","A"},
            {"AGAIN","ADD","AREG","=2"},
            {null,"ADD","AREG","B"},
            {"AGAIN","ADD","AREG","=3"},
            {null, "LTORG",null,null},
            {"AGAIN2","ADD","AREG","BREG"},
            {"AGAIN2","ADD","AREG","CREG"},
            {null, "DC","B","3"},
            {"LOOP","DS","A","1"},
            {null, "END",null, null}
        };

        symtab s[] = new symtab[20];
        littab l[] = new littab[20];
        pooltab p[] = new pooltab[20];

        int loc=0, i=0;
        String m, op1, op2;
        int sn=0, ln=0, pn=0, lnc = 0;

        loc = Integer.parseInt(input[0][2]);
        i = 1;

        while(!input[i][1].equals("END")){
            m = input[i][1];

            if(check(m) == 1){
                if(input[i][0] == null){
                    op1 = input[i][2];
                    op2 = input[i][3];
                    if(comp(op2, s, sn) == 1){
                        s[sn] = new symtab(sn, op2, 0);
                        sn++;
                    }
                    else if(comp(op2, s, sn) == 2){
                        l[ln] = new littab(ln, op2, 0);
                        ln++;
                    }
                    loc++;
                } else {
                    op1 = input[i][0];
                    s[sn] = new symtab(sn, op1, loc);
                    sn++;

                    op1 = input[i][2];
                    op2 = input[i][3];
                    if(comp(op2, s, sn) == 1){
                        s[sn] = new symtab(sn, op2, 0);
                        sn++;
                    }
                    else if(comp(op2, s, sn) == 2){
                        l[ln] = new littab(ln, op2, 0);
                        ln++;
                    }
                    loc++;
                }
            }
            i++;
        }

        System.out.println("Symbol Table\nIndex\tSymbol\tAddress\n");
        for(i = 0; i < sn; i++){
            System.out.println(s[i].index + "\t" + s[i].name + "\t" + s[i].addr);
        }

        System.out.println("\nLiteral Table\nIndex\tLiteral\tAddress\n");
        for(i = 0; i < ln; i++){
            System.out.println(l[i].index + "\t" + l[i].name + "\t" + l[i].addr);
        }

        System.out.println("\nPool Table\nPool Index\tLiteral Index\n");
        for(i = 0; i < pn; i++){
            System.out.println("\t" + p[i].p_index + "\t\t" + p[i].l_index);
        }
    }

    static int check(String m){
        if(m.equals("MOVER") || m.equals("ADD")) return 1;
        else if(m.equals("DS")) return 2;
        else if(m.equals("DC")) return 3;
        else if(m.equals("LTORG")) return 4;
        return -1;
    }

    static int comp(String m, symtab s[], int sn){
        if(m.equals("AREG") || m.equals("BREG") || m.equals("CREG")) return 0;
        else if(m.charAt(0) == '=') return 2;
        else if(comps(m, s, sn) == 99) return 1;
        return 0;
    }

    static int comps(String m, symtab s[], int sn){
        for(int i = 0; i < sn; i++){
            if(m.equals(s[i].name)){
                return s[i].index;
            }
        }
        return 99;
    }

    static String ic(String m){
        if(m.equals("START")) return "(AD,01)";
        else if(m.equals("END")) return "(AD,02)";
        else if(m.equals("ORIGIN")) return "(AD,03)";
        else if(m.equals("EQU")) return "(AD,04)";
        else if(m.equals("LTORG")) return "(DL,02)";
        else if(m.equals("ADD")) return "(IS,01)";
        else if(m.equals("SUB")) return "(IS,02)";
        else if(m.equals("MOVER")) return "(IS,04)";
        else if(m.equals("MOVEM")) return "(AD,05)";
        else if(m.equals("AREG")) return "(RG,01)";
        else if(m.equals("BREG")) return "(RG,02)";
        else if(m.equals("CREG")) return "(RG,03)";
        else if(m.equals("DS")) return "(DL,01)";
        else if(m.equals("DC")) return "(DL,02)";
        else if(m.charAt(0) == '=') return ("(C," + m.charAt(2) + ")");
        else return ("(C," + m + ")");
    }
}
