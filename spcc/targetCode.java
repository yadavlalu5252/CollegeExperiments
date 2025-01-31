import java.io.*;

public class targetCode {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Enter the equation");
        String stmt = br.readLine();
        
        StringBuffer ans = new StringBuffer("");
        int reg = 0;
        int parenCount = 0;
        
        // First pass: Process parentheses and generate basic instructions
        for(int i = 0; i < stmt.length(); i++) {
            char c = stmt.charAt(i);
            
            switch(c) {
                case '(':
                    parenCount++;
                    break;
                case ')':
                    parenCount--;
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    if(parenCount > 0 && i > 0 && i < stmt.length()-1) {
                        char leftOperand = stmt.charAt(i-1);
                        char rightOperand = stmt.charAt(i+1);
                        
                        System.out.println("MOV " + leftOperand + ", R" + reg);
                        switch(c) {
                            case '+':
                                System.out.println("ADD " + rightOperand + ", R" + reg);
                                break;
                            case '-':
                                System.out.println("SUB " + rightOperand + ", R" + reg);
                                break;
                            case '*':
                                System.out.println("MUL " + rightOperand + ", R" + reg);
                                break;
                            case '/':
                                System.out.println("DIV " + rightOperand + ", R" + reg);
                                break;
                        }
                        ans.append("R" + reg);
                        reg++;
                        i++; // Skip processed right operand
                    } else {
                        ans.append(c);
                    }
                    break;
                default:
                    if(parenCount == 0) {
                        ans.append(c);
                    }
                    break;
            }
        }
        
        // Second pass: Process remaining operations
        String ans1 = ans.toString();
        System.out.println("\nOptimized code:");
        for(int i = 0; i < ans1.length(); i++) {
            char c = ans1.charAt(i);
            if("+-*/".indexOf(c) != -1 && i > 0 && i < ans1.length()-1) {
                String left = ans1.substring(i-1, i);
                String right = ans1.substring(i+1, i+2);
                
                System.out.println("OPR " + c + " " + left + " " + right);
            }
        }
    }
}
