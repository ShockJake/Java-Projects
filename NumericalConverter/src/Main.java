import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static private FileReader fr = null;
    static private int in_base = null;
    static private int out_base = null;

    public static void main(String[] args) {
        try {
            Scanner str_sc = new Scanner(System.in);
            Scanner int_sc = new Scanner(System.in);
            String str = "";

            System.out.println("### Numeral system converter ###");

            System.out.print("Write input numeral system: ");
            in_base = int_sc.nextInt();
            System.out.print("Write output numeral system: ");
            out_base = int_sc.nextInt();

            while (true) {
                System.out.println(
                        "Choose input mode:\n1) From console\n2) From file\n3) Change numeral systems\n4) Exit");
                int option = int_sc.nextInt();
                if (option == 1) {

                    System.out.println("Write line: ");
                    str = str_sc.nextLine();

                    Converter ct = new Converter(in_base, out_base, str);

                    System.out.println("====Convertation====");
                    ct.writeToConsole();
                    System.out.println("====Convertation====\n\n");
                } else if (option == 2) {
                    System.out.println("Write name of file: ");
                    String name = str_sc.nextLine();

                    fr = new FileReader(name);
                    Converter ct = new Converter(in_base, out_base, fr);

                    System.out.println("====Convertation====");
                    ct.writeToConsole();
                    System.out.println("====Convertation====\n\n");
                    fr.close();
                } else if (option == 3) {

                    System.out.print("Write input numeral system: ");
                    in_base = int_sc.nextInt();
                    System.out.print("Write output numeral system: ");
                    out_base = int_sc.nextInt();
                } else if (option == 4)
                    break;
                else {
                    continue;
                }
            }
            int_sc.close();
            str_sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
