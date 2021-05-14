package com.slang.compiler;

import com.slang.compiler.ast.*;
import com.slang.compiler.parser.ExpressionBuilder;

public class App {
    public static void main(String[] args) {
        ExpressionBuilder b = new ExpressionBuilder("-2*(3+3)");
        Expression e = b.GetExpression();
        System.out.println(e.Evaluate(null));
    }
}
