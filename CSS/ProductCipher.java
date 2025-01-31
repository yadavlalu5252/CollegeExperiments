import java.util.*;

class ProductCipher {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        // Input for substitution encryption
        System.out.println("Enter the input to be encrypted:");
        String substitutionInput = scanner.nextLine();

        // Input for transposition encryption
        System.out.println("Enter a number for transposition:");
        int n = scanner.nextInt();

        // Substitution encryption
        StringBuffer substitutionOutput = new StringBuffer();
        for (int i = 0; i < substitutionInput.length(); i++) {
            char c = substitutionInput.charAt(i);
            substitutionOutput.append((char) (c + 5)); // Shift each character by 5
        }
        System.out.println("\nSubstituted text:");
        System.out.println(substitutionOutput);

        // Transposition encryption
        String transpositionInput = substitutionOutput.toString();
        int modulus = transpositionInput.length() % n;
        if (modulus != 0) {
            modulus = n - modulus; // Calculate padding needed
            for (; modulus != 0; modulus--) {
                transpositionInput += "X"; // Add padding character 'X'
            }
        }
        StringBuffer transpositionOutput = new StringBuffer();
        System.out.println("\nTransposition Matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < transpositionInput.length() / n; j++) {
                char c = transpositionInput.charAt(i + (j * n));
                System.out.print(c); // Print matrix row-wise
                transpositionOutput.append(c);
            }
            System.out.println();
        }
        System.out.println("\nFinal encrypted text:");
        System.out.println(transpositionOutput);

        // Transposition decryption
        String transpositionEncrypted = transpositionOutput.toString();
        int rows = transpositionEncrypted.length() / n;
        StringBuffer transpositionPlaintext = new StringBuffer();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < n; j++) {
                char c = transpositionEncrypted.charAt(i + (j * rows));
                transpositionPlaintext.append(c);
            }
        }

        // Remove padding
        while (transpositionPlaintext.charAt(transpositionPlaintext.length() - 1) == 'X') {
            transpositionPlaintext.deleteCharAt(transpositionPlaintext.length() - 1);
        }

        // Substitution decryption
        StringBuffer plaintext = new StringBuffer();
        for (int i = 0; i < transpositionPlaintext.length(); i++) {
            char c = transpositionPlaintext.charAt(i);
            plaintext.append((char) (c - 5)); // Reverse shift by 5
        }

        System.out.println("\nDecrypted Plaintext:");
        System.out.println(plaintext);

        scanner.close();
    }
}
