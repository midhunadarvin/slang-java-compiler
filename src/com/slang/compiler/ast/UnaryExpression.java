package com.slang.compiler.ast;

/**
 * Unary Expression
 * This class supports Unary Operators like + , - , / , *
 *
 * @author Midhun A Darvin
 * @version 1.0
 */
public class UnaryExpression implements Expression {

    private Expression _ex1;
    private OPERATOR _op;


    /**
     * While evaluating apply the unary operator after evaluating the operand.
     *
     * @param a  This is the expression to evaluate
     * @param op This is the operator for the expression
     * @return void
     */
    public UnaryExpression(Expression a, OPERATOR op) {
        _ex1 = a;
        _op = op;
    }

    /**
     * While evaluating apply the unary operator after evaluating the operand.
     *
     * @param context This is the runtime context
     * @return double This evaluated unary expression
     */
    @Override
    public double Evaluate(RUNTIME_CONTEXT context) {
        switch (_op) {
            case PLUS:
                return _ex1.Evaluate(context);
            case MINUS:
                return -_ex1.Evaluate(context);
        }
        return Double.NaN;
    }
}
