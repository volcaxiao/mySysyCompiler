import frontend.lexer.Lexer;
import frontend.lexer.TokenList;

import java.io.*;

public class Compiler {
    public static void main(String[] args) {
        try {
            File inputFile = new File("testfile.txt");
            File outputFile = new File("output.txt");
            FileReader input = new FileReader(inputFile);
            FileWriter output = new FileWriter(outputFile);
            BufferedReader reader = new BufferedReader(input);
            BufferedWriter writer = new BufferedWriter(output);
            TokenList tokenList = Lexer.getLexer(reader).lex();
            writer.write(tokenList.toString());

            //end
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
