import java.io.FileReader;
import java.io.IOException;

// Class that implments a converter from one numerical system to enother
public class Converter {
    private String buff = ""; // Variable to store the input string.
    private int base1; // Input numeral system.
    private int base2; // Output numeral system.
    private int number; // Number to convert.

    Converter(int in_base, int out_base, FileReader fr) throws IOException { // Cunstructor for files
        base1 = in_base;                           
        base2 = out_base;                           
        int data = fr.read();                       //
        while (data != -1) {                        //
            buff += String.valueOf((char) data);    //
            data = fr.read();                       // Reading file section.
        }                                           //
        buff += " ";                                //
    }

    Converter(int in_base, int out_base, String str) {  // Constructor for console input
        base1 = in_base;
        base2 = out_base;
        buff = str + " ";
    }

    private char[] convert(int key) {
        String key_str = String.valueOf(key);  // Prepearing number to convert it do decimal number.
        int parsed_key = Integer.parseInt(key_str, this.base1); // Converting number to decimal number from string.
        return Integer.toString(parsed_key, this.base2).toCharArray(); // Converting number from decimal to the numeral system  
    }                                                                  // that was written by the user.

    public void writeToConsole() {
        for (int i = 0; i < buff.length(); i++) {
            char c = buff.charAt(i);
            if (Character.isDigit(c)) {
                number = 10 * number + Character.digit(c, 10);
            } else {
                if (number != 0) {
                    char[] cNumber = this.convert(number);
                    System.out.print(cNumber);
                    this.number = 0;
                }
                System.out.print(c);
            }
        }
        System.out.println("");
    }
}
