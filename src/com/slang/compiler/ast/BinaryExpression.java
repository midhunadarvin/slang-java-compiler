package com.slang.compiler.ast;

/**
 * Binary Expression
 * This class supports Binary Operators like + , - , / , *
 *
 * @author Midhun A Darvin
 * @version 1.0
 */
public class BinaryExpression extends Expression {
    private Expression _ex1, _ex2;
    private OPERATOR _op;

    /**
     * Constructor
     *
     * @param a  This is the first expression to evaluate
     * @param b  This is the second expression to evaluate
     * @param op This is the operator for the expression
     * @return void
     */
    public BinaryExpression(Expression a, Expression b, OPERATOR op) {
        _ex1 = a;
        _ex2 = b;
        _op = op;
    }

    /**
     * While evaluating apply the operator after evaluating the left and right operands
     *
     * @param context This is the runtime context
     * @return double
     */
    @Override
    public double Evaluate(RUNTIME_CONTEXT context) {
        switch (_op) {
            case PLUS:
                return _ex1.Evaluate(context) + _ex2.Evaluate(context);
            case MINUS:
                return _ex1.Evaluate(context) - _ex2.Evaluate(context);
            case DIV:
                return _ex1.Evaluate(context) / _ex2.Evaluate(context);
            case MUL:
                return _ex1.Evaluate(context) * _ex2.Evaluate(context);
        }
        return Double.NaN;
    }
}


