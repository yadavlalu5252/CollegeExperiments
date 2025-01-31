import java.io.*;
import java.util.*;

public class Optimization {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String s1, s2;
        String[] code = new String[10];
        
        // Input handling for strings
        System.out.print("Enter string 1: ");
        s1 = br.readLine();
        
        System.out.print("Enter string 2: ");
        s2 = br.readLine();
        
        // String comparison
        if(s1.equals(s2)) {
            System.out.println("Duplicate strings detected!");
            s2 = null;
        } else {
            System.out.println("Strings are unique.");
        }
        
        // Code input handling
        System.out.print("Enter number of code lines (max 10): ");
        int n = Integer.parseInt(br.readLine());
        
        // Validate input
        if(n <= 0 || n > 10) {
            System.out.println("Invalid number of lines!");
            return;
        }
        
        System.out.println("Enter program code:");
        for(int i = 0; i < n; i++) {
            code[i] = br.readLine();
        }
        
        // Dead code detection logic
        for(int i = 0; i < n - 1; i++) {
            String currentLine = code[i].trim();
            String nextLine = code[i + 1].trim();
            
            // Check for variable declaration pattern
            if(currentLine.startsWith("int ")) {
                String[] parts = currentLine.split("=");
                if(parts.length > 0) {
                    String varName = parts[0].replace("int", "").trim().split(" ")[0];
                    
                    // Check if next line uses the same variable
                    if(nextLine.contains(varName + " =")) {
                        System.out.println("Potential dead code detected at line " + (i + 2) + ": " + nextLine);
                    }
                }
            }
        }
    }
}