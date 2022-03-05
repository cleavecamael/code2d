package sat;

import sat.env.Environment;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;

/** outputs the environment to a txt file
 * As the bindings attribute is private, and we do not have knowledge on the variables
 * (assuming they are not restricted to numbers), we use the toString() method and
 * convert to the specified format in the 2D documentation. This does mean that the entire
 * string is formatted and written into the file at once, so this function may not work
 * for very large environments.
 */
public class writeEnv {
    public static String convFormat(String envString){
        /** Remove the "Environment: " and square brackets in the toString of env
         */
        String output = envString.substring(13,envString.length() - 1);
        /** Replace comma delimiter with newline, "->" with ":"
         */
        output = output.replaceAll(", ", "\n");
        output = output.replaceAll("->", ":");
        return output;
    }

    /** Takes an Environment, converts it to the output format specified in 2D documentation
     * and writes it to the specified file location.
     * @param env
     *              The Environment to output.
     * @param fileName
     *              The file location to write the Environment in.
     */

    public static void writeEnvFile(Environment env, String fileName){
        try{
            File newFile = new File(fileName);
            newFile.createNewFile();
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(convFormat(env.toString()));
            fileWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
