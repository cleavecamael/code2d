package com.example.code2d;
import com.sun.tools.doclint.Env;

import java.io.FileNotFoundException;

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


public class Main {
    public static void main(String[] args) {
        System.out.println(SATSolver.solve(CNFReader.readCNF("code2d/cnf/largeSat.cnf")));
        }
    }

