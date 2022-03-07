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
     *
     * @return an environment for which the problem evaluates to Bool.true, or
     * null if no such environment exists.
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
     * @param clauses formula in conjunctive normal form
     * @param env     assignment of some or all variables in clauses to true or
     *                false values.
     * @return an environment for which all the clauses evaluate to Bool.true,
     * or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {
        if (clauses.isEmpty()) {
            /** put placeholder for solvable
             *
             */
            return env;
        }
        /** Iterates to find the smallest clause, or returns false if it encounters an empty clause
         */
        Clause minClause = clauses.first();
        int minSize = clauses.first().size();
        for (Clause claus : clauses) {
            if (claus.isEmpty()) {
                return null;
            }
            if (claus.size() < minSize) {
                minSize = claus.size();
                minClause = claus;
            }
        }
        /** Selects the next literal to try to substitute
         */
        Literal next_literal = minClause.chooseLiteral();

        /** Boolean value to assign to the next literal to set it to TRUE
         * Positive literals are assigned TRUE, Negative literals assigned FALSE
         */
        Bool literalBool;

        if (next_literal.getClass().getSimpleName().equals("PosLiteral")) {
            literalBool = Bool.TRUE;
        } else {
            literalBool = Bool.FALSE;
        }

        /** If clause has 1 literal, that literal must be set to TRUE
         */
        if (minSize == 1) {
            return solve(substitute(clauses, next_literal), env.put(next_literal.getVariable(), literalBool));
        }

        /** Otherwise, try setting the literal to TRUE or FALSE and checking if
         * it is satisfiable
         */

        Environment results = solve(substitute(clauses, next_literal), env.put(next_literal.getVariable(), literalBool));
        if (results != null) {
            return results;
        } else {
            return solve(substitute(clauses, next_literal.getNegation()), env.put(next_literal.getVariable(), literalBool.not()));
        }
    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     *
     * @param clauses , a list of clauses
     * @param l       , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses,
                                             Literal l) {
        /** Create a new ImList to store our output.
         * Reduce each clause using the literal given
         * If the clause becomes true, do not add to the output list
         * Finally, return the output list
         */
        ImList<Clause> output = new EmptyImList<Clause>();
        for (Clause claus : clauses) {
            Clause red_claus = claus.reduce(l);
            if (red_claus != null) {
                output = output.add(red_claus);
            }

        }
        return output;
    }

}
