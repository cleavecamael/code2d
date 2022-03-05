package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import sat.env.*;
import sat.formula.*;


public class SATSolverTest {
    public static void main(String[] args) {
        String filename = args[0];
        Environment output = SATSolver.solve(CNFReader.readCNF(filename));
        if (output != null) {
            writeEnv.writeEnvFile(output, "BoolAssignment.txt");
            System.out.println("Satisfiable");
        } else {
            System.out.println("Unsatisfiable");
        }

    }

}