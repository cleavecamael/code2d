package sat;

import com.sun.tools.doclint.Env;

import immutable.ImList;
import immutable.EmptyImList;
import sat.env.Bool;
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */

public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     * 
     * @return an environment for which the problem evaluates to Bool.true, or
     *         null if no such environment exists.
     *

     *
     * Simplify:
     * For each clause:
     * if clause.contains(): list.remove(clause)
     * if clause.contains(): remove the neg a
     *
     * return clauses
     */
    public static Environment solve(Formula formula) {

        Environment start_env = new Environment();
        ImList<Clause> start_clauses = formula.getClauses();
        return solve(start_clauses, start_env);
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     * 
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.true,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {
        if (clauses.isEmpty()){
            /** put placeholder for solvable
             *
             */
            return env;
        }
        /** Iterates to find the smallest clause, or returns false if it encounters an empty clause
         */
        Clause min_clause = new Clause();
        int min_size  = Integer.MAX_VALUE;
        for (Clause claus:clauses){
            if (claus.isEmpty()){
                return null;
            }
            if (claus.size() < min_size){
                min_size = claus.size();
                min_clause = claus;
            }
        }
        /** Selects the next literal to try to substitute
         */
        Literal next_literal = min_clause.chooseLiteral();
        /** Boolean value that when assigned to literal value would evaluate to true
         */
        Bool literalBool;

        if (next_literal.getClass().getSimpleName().equals("PosLiteral")){
            literalBool = Bool.TRUE;
        }
        else{
            literalBool = Bool.FALSE;
        }

        Environment results = solve(substitute(clauses, next_literal), env.put(next_literal.getVariable(), literalBool));
        if (results != null){
            return results;
        }
        else{
            return solve(substitute(clauses, next_literal.getNegation()), env.put(next_literal.getVariable(), literalBool.not()));
        }
    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     * 
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {
        ImList<Clause> output = new EmptyImList<Clause>();
        for (Clause claus:clauses){
            Clause red_claus = claus.reduce(l);
            if (red_claus != null){
                output = output.add(red_claus);
            }

        }
       return output;
    }

}
