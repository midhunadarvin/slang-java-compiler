package com.slang.compiler.parser;

import com.slang.compiler.ast.Expression;

/**
 * Expression Builder
 *
 * @author Midhun A Darvin
 * @version 1.0
 */
public class ExpressionBuilder {
    public String _expr_string;

    public ExpressionBuilder(String expr)
    {
        _expr_string = expr;
    }

    public Expression GetExpression()
    {
        try {
            RDParser p = new RDParser(_expr_string);
            return p.CallExpr();
        } catch (Exception exception) {
            return null;
        }
    }
}
