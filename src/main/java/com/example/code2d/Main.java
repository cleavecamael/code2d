package com.example.code2d;
import com.sun.tools.doclint.Env;

import java.io.FileNotFoundException;

import javax.naming.Binding;

import immutable.ImList;
import immutable.EmptyImList;
import sat.CNFReader;
import sat.SATSolver;
import sat.env.Bool;
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.NegLiteral;
import sat.formula.PosLiteral;
import sat.env.Variable;
import sat.WriteEnv;


public class Main {
    public static void main(String[] args) {
        String filename = args[0];
        Environment output = SATSolver.solve(CNFReader.readCNF(filename));
        if (output != null){
            WriteEnv.writeEnvFile(output,"BoolAssignment.txt");
            System.out.println("Satisfiable");
        }
        else{
            System.out.println("Unsatisfiable");
        }

    }
}

