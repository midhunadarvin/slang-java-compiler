package com.slang.compiler.ast.statements;

import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.ast.Statement;
import com.slang.compiler.parser.SymbolInfo;

/**
 * Implementation of Print Statement
 */
public class PrintStatement extends Statement {

    /**
     * At this point of time , Print will spit the value of an Expression on the screen.
     */
    private Expression _ex;

    /**
     * Constructor just stores the expression passed as parameter
     *
     * @param ex This is expression that will be printed
     * @return void
     */
    public PrintStatement(Expression ex)
    {
        _ex = ex;
    }

    /**
     * Execute method Evaluates the expression and
     * spits the value to the console using
     * System.out.print statement.
     *
     * @param context This is runtime context
     * @return boolean
     */
    @Override
    public SymbolInfo Execute(RUNTIME_CONTEXT context) throws Exception {
        SymbolInfo a = _ex.Evaluate(context);
        System.out.print(a);
        return a;
    }
}
