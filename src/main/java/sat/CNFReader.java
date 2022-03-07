package sat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
        Clause bufferClause;
        /** Contains list of literals whose negation also appears in the strings
         * and are thus redundant and can be removed
         */
        Set<Literal> redLiterals = new HashSet<Literal>();

        for (String i: literals){
                bufferClause = output.add(readLiteral(i));
                if (bufferClause != null){
                    output = bufferClause;
                }
                else{
                    redLiterals.add(readLiteral(i));
                }

        }
        for (Literal l: redLiterals){
            output = output.reduce(l);
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
            /** We can now start reading the clauses, using a space/tab/new line followed by
             * 0 as a delimiter
             */
            scan.useDelimiter("[\\t\\n\\s]0");

            /** Since each token is delimited by 0, the token after the last 0 is not valid,
             * so we only want to read until the second last token.
             */
            String prevLine = null;
            String currLine = scan.next().trim();
            Formula output = new Formula();

            while (scan.hasNext()){
                    prevLine = currLine;
                    currLine = scan.next().trim();
                    output = output.addClause(readClause(prevLine));
            }
            scan.close();
            return output;

        }catch (FileNotFoundException error){
            error.printStackTrace();
            return null;

        }
    }

}
