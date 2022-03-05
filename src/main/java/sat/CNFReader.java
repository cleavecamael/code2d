package sat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.NegLiteral;
import sat.formula.PosLiteral;

public class CNFReader {
    /** Convert literal in CNF file to literal.
     */
    private static Literal readLiteral(String s){
        char first = s.charAt(0);
        if (first == '-'){
            return NegLiteral.make(s.substring(1));
        }
        return PosLiteral.make(s);
    }
    /** Convert clause in CNF file to clause.
     */
    private static Clause readClause(String s){
        Clause output = new Clause();
        /** Replace newline and tab with whitespace so we can split on whitespace
         */
        String cleanS = s.replaceAll("[\\t\\n]+"," ");
        String[] literals = cleanS.split(" ");
        for (String i: literals){
            output = output.add(readLiteral(i));
        }

        return output;
    }
    /** Takes in a string containing the file location to read, and outputs the formula
     *
     */
    public static Formula readCNF(String fileName)  {
        try {

            File CNFFile = new File(fileName);
            Scanner scan = new Scanner(CNFFile);
            String line;
            while (scan.hasNextLine()){
                line = scan.nextLine();
                if (line.charAt(0) == 'p'){
                    break;
                }
            }
            scan.useDelimiter("\\s0");
            Formula output = new Formula();
            while (scan.hasNext()){
                try {
                    output = output.addClause(readClause(scan.next().trim()));
                } catch (Exception e) {
                    break;
                }
            }
            scan.close();
            return output;

        }catch (FileNotFoundException error){
            System.out.println("Error: File name not found");

            return null;

        }
    }

}
