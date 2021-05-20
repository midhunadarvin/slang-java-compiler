package com.slang.compiler;

import com.slang.compiler.ast.*;
import com.slang.compiler.parser.ExpressionBuilder;
import com.slang.compiler.parser.RDParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws IOException {
        if (args == null || args.length != 1) {
            System.out.println("CallSlang <scriptname>\n");
            return;
        }
        TestFileScript(args[0]);
        //------------- Wait for the Key Press
        System.in.read();
    }

    /**
     * Driver routine to call the program script
     */
    static void TestFileScript(String filename) {
        try {
            if (filename == null)
                return;
            // -------------- Read the contents from the file
            String programs2 = readFileAsString(filename);

            //---------------- Creates the Parser Object
            // With Program text as argument
            RDParser pars = null;
            pars = new RDParser(programs2);

            // Create a Compilation Context
            COMPILATION_CONTEXT ctx = new COMPILATION_CONTEXT();

            // Call the top level Parsing Routine with
            // Compilation Context as the Argument
            ArrayList<Statement> statements = pars.Parse(ctx);

            // if we have reached here , the parse process
            // is successful... Create a Run time context and
            // Call Execute statements of each statement...
            RUNTIME_CONTEXT f = new RUNTIME_CONTEXT();
            statements.forEach(statement -> {
                try {
                    statement.Execute(f);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String readFileAsString(String fileName)throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
