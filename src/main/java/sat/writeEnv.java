package sat;

import sat.env.Environment;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
public class writeEnv {
    public static String convFormat(String envString){
        String output = envString.substring(13,envString.length() - 1);
       output = output.replaceAll(", ", "\n");
        output = output.replaceAll("->", ":");
        return output;
    }
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
