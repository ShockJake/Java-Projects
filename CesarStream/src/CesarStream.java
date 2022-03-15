import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// Class that implements encoder of Cesar Cipher
class CesarOutputStream {
    private final int SIZEOFASCII = 128;
    private FileInputStream fis;
    private int shift;
    private String outputFileName;

    CesarOutputStream(FileInputStream fis, String outputFileName, int shift) throws IOException {
        this.fis = fis;
        this.shift = shift;
        this.outputFileName = outputFileName;
    }

    public void encode() throws IOException {
        int Char;
        FileOutputStream fos = new FileOutputStream(outputFileName);
        while ((Char = fis.read()) != -1) {
            System.out.print(((Char + shift) % SIZEOFASCII));
            fos.write((Char + shift) % SIZEOFASCII);
        }
        fos.close();
    }

    public void close() throws IOException {
        fis.close();
    }
}

// Class that implements a decoder of Cesar Cipher
class CesarInputStream {
    private final int SIZEOFASCII = 128;
    private FileInputStream fis;
    private int shift;

    CesarInputStream(FileInputStream fis, int shift) throws IOException {
        this.fis = fis;
        this.shift = shift;
    }

    public void decode() throws IOException {
        int Char;
        while ((Char = fis.read()) != -1) {
            if (Char > 0) {
                System.out.print((char) ((Char - shift) % SIZEOFASCII));
            } else {
                System.out.print((char) ((Char - shift - SIZEOFASCII)));
            }
        }
    }

    public void close() throws IOException {
        fis.close();
    }
}

public class CesarStream {
    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        System.out.print("Write name of file that you want to work with: ");

        // Main loop
        while (true) {
            
            // Getting file name
            String name = scan.nextLine();
            File fl = new File(name);
            
            if (fl.exists()) {
                System.out
                        .println("File exists, what do you want to do with it:\n1) Decode\n2) Encode\n3) End program");
                int x = scan.nextInt();
                if (x == 1) {
                    FileInputStream fis = new FileInputStream(name);
                    System.out.print("What shift do you want to use: ");
                    int shift = scan.nextInt();
                    CesarInputStream cis = new CesarInputStream(fis, shift);
                    cis.decode();
                    cis.close();
                } else if (x == 2) {
                    FileInputStream fis = new FileInputStream(name);
                    System.out.print("What shift do you want to use: ");
                    int shift = scan.nextInt();
                    CesarOutputStream cos = new CesarOutputStream(fis, "output.txt", shift);
                    cos.encode();
                    cos.close();
                } else if (x == 3) {
                    break;
                } else {
                    System.out.println("\n");
                    continue;
                }
                break;
            } else {
                System.out.println("File does not exists, try again...");
            }
        }
        scan.close();
    }
}
