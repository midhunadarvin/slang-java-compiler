package com.slang.compiler.ast;

/**
 * Implementation of Print Statement
 */
public class PrintLineStatement extends Statement {

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
    public PrintLineStatement(Expression ex)
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
    public boolean Execute(RUNTIME_CONTEXT context) {
        double a = _ex.Evaluate(context);
        System.out.println(a);
        return true;
    }
}
