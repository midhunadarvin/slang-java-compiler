package com.slang.compiler.ast.statements;

import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.ast.Statement;
import com.slang.compiler.ast.Variable;
import com.slang.compiler.parser.SymbolInfo;

/**
 * Assignment Statement
 */
public class AssignmentStatement extends Statement {
    private Variable variable;
    private Expression exp1;

    public AssignmentStatement(Variable var, Expression e) {
        variable = var;
        exp1 = e;
    }

    public AssignmentStatement(SymbolInfo var, Expression e) {
        variable = new Variable(var);
        exp1 = e;
    }

    @Override
    public SymbolInfo Execute(RUNTIME_CONTEXT context) throws Exception {
        SymbolInfo val = exp1.Evaluate(context);
        context.symbolTable.assign(variable, val);
        return null;
    }
}
