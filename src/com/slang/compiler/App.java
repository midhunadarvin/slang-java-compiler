package com.slang.compiler;

import com.slang.compiler.ast.*;
import com.slang.compiler.parser.ExpressionBuilder;
import com.slang.compiler.parser.RDParser;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ExpressionBuilder b = new ExpressionBuilder("-2*(3+3)");
        Expression e = b.GetExpression();
        System.out.println(e.Evaluate(null));

        TestFirstScript();
        TestSecondScript();
    }

    static void TestFirstScript() {
        try {
            String a = "PRINTLINE 2*10;" + "\r\n" + "PRINTLINE 10;\r\n PRINT 2*10;\r\n";
            RDParser p = new RDParser(a);
            ArrayList<Statement> arr = null;
            arr = p.Parse();

            arr.forEach(obj -> {
                obj.Execute(null);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static void TestSecondScript() {
        try {
            String a = "PRINTLINE -2*10;" + "\r\n" + "PRINTLINE -10*-1;\r\n PRINT 2*10;\r\n";
            RDParser p = new RDParser(a);
            ArrayList<Statement> arr = p.Parse();
            arr.forEach(s -> {
                s.Execute(null);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
