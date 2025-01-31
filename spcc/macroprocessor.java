import java.io.*;
// D:\CollegeExperiments\spcc\macroprocessor.java
class macroprocessor {
    public static void main(String[] args) {
        String code[][] = {
            {"ADD", "A", "", "", ""},
            {"MACRO", "ADD1", "&ARG", "", ""},
            {"LOAD", "ARG", "", "", ""},
            {"MEND", "", "", "", ""},
            {"MACRO", "PQR", "&A", "&B", "&C"},
            {"ADD", "B", "", "", ""},
            {"READ", "C", "", "", ""},
            {"READ", "A", "", "", ""},
            {"MEND", "", "", "", ""},
            {"MACRO", "LMN", "", "", ""},
            {"LOAD", "C", "", "", ""},
            {"MEND", "", "", "", ""},
            {"LOAD", "B", "", "", ""},
            {"PQR", "5", "3", "2", ""},
            {"ADD1", "1", "", "", ""},
            {"LMN", "", "", ""},
            {"SUB", "C", "", "", ""},
            {"ENDP", "", "", "", ""}
        };

        String mn[] = new String[3];
        String fpmn[] = new String[4];
        String fp[] = new String[4];
        String pp[] = new String[4];
        int parameter[] = new int[3];
        int c = 0, d = 0, e = 0, l = 0;

        // Step 1: Macro Name Table (MNT) and Parameter Processing
        for (int i = 0; i < 18; i++) {
            if (code[i][0].equals("MACRO")) {
                mn[c] = code[i][1];
                for (int j = 2; j < 5; j++) {
                    if (!code[i][j].equals("")) {
                        fpmn[e] = code[i][1];
                        fp[e] = code[i][j];
                        pp[e++] = "#" + (++d);
                    }
                }
                parameter[c++] = d;
                d = 0;
            }
        }

        // Step 2: Actual Parameter Table (APT)
        String apmn[] = new String[4];
        String ap[] = new String[4];
        String app[] = new String[4];
        c = 1;
        d = 0;
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < mn.length; j++) {
                if (code[i][0].equals(mn[j]) && !code[i][1].equals("")) {
                    while (!code[i][c].equals("")) {
                        apmn[d] = code[i][0];
                        ap[d] = code[i][c];
                        app[d] = "#" + c;
                        c++;
                        d++;
                    }
                    c = 1;
                }
            }
        }

        // Step 3: Display Macro Name Table
        System.out.println("Macro Name Table");
        System.out.println("_________________________");
        System.out.println("Macro Name\tNo. of Parameters");
        System.out.println("_________________________");
        for (int i = 0; i < mn.length; i++) {
            System.out.println(mn[i] + "\t\t" + parameter[i]);
        }

        System.out.println("-----------------------\n");

        // Step 4: Macro Definition Table (MDT)
        System.out.println("Macro Definition Table");
        System.out.println("-----------------------");
        System.out.println("Index\tInstruction");
        System.out.println("---------------------");
        int index = 1, i = 0;
        while (i < 18) {
            if (code[i][0].equals("MACRO")) {
                i++;
                while (!code[i][0].equals("MEND")) {
                    for (int j = 0; j < fp.length; j++) {
                        if (("&" + code[i][1]).equals(fp[j])) {
                            System.out.println((index++) + "\t" + code[i][0] + " " + pp[j]);
                            break;
                        }
                    }
                    i++;
                }
                System.out.println((index++) + "\tMEND");
            } else {
                i++;
            }
        }

        System.out.println("-------------------\n");

        // Step 5: Formal vs Positional Parameters
        System.out.println("Formal vs Positional Parameter List");
        System.out.println("-------------------------------------");
        System.out.println("Macro Name\tFormal Parameter\tPositional Parameter");
        System.out.println("-------------------------------------");
        for (i = 0; i < fpmn.length; i++) {
            System.out.println(fpmn[i] + "\t\t" + fp[i] + "\t\t\t" + pp[i]);
        }

        System.out.println("-------------------------------------");

        // Step 6: Actual vs Positional Parameters
        System.out.println("Actual vs Positional Parameter");
        System.out.println("-------------------------------------");
        System.out.println("Macro Name\tActual Parameter\tPositional Parameter");
        System.out.println("-------------------------------------");
        for (i = 0; i < apmn.length; i++) {
            System.out.println(apmn[i] + "\t\t" + ap[i] + "\t\t\t" + app[i]);
        }

        System.out.println("-------------------------------------\n");

        // Step 7: Expanded Code
        System.out.println("Expanded Code");
        System.out.println("------------------");
        System.out.println("Instruction Code");
        System.out.println("----------------");

        String pvalue[][] = new String[4][2];
        for (i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (fpmn[i].equals(apmn[j]) && pp[i].equals(app[j])) {
                    pvalue[i][0] = fp[i];
                    pvalue[i][1] = ap[j];
                    break;
                }
            }
        }

        i = 0;
        while (i < 18) {
            if (code[i][0].equals("ADD") || code[i][0].equals("SUB") || code[i][0].equals("ENDP") || code[i][0].equals("LOAD")) {
                System.out.println(code[i][0] + " " + code[i][1]);
                i++;
            } else if (code[i][0].equals("MACRO")) {
                i++;
                while (!code[i][0].equals("MEND")) {
                    i++;
                }
                i++;
            } else {
                int k = 0;
                while (k < 18) {
                    if (code[k][1].equals(code[i][0])) {
                        k++;
                        while (!code[k][0].equals("MEND")) {
                            for (l = 0; l < 4; l++) {
                                if (("&" + code[k][l]).equals(pvalue[l][0])) {
                                    System.out.println(code[k][0] + " " + pvalue[l][1]);
                                }
                            }
                            k++;
                        }
                        break;
                    }
                    k++;
                }
                i++;
            }
        }
    }
}
