package com.slang.compiler.ast;

/**
 * Numeric Constant
 * This class contains a numeric value
 *
 * @author  Midhun A Darvin
 * @version 1.0
 */
public class NumericConstant implements Expression {
    private double _value;

    /**
     * Construction does not do much , just keeps the
     * value assigned to the private variable
     * @param value This is the value of the numeric constant
     * @return void
     */
    public NumericConstant(double value)
    {
        _value = value;
    }

    /**
     * While evaluating a numeric constant , return the _value
     * @param context This is the runtime context
     * @return void
     */
    @Override
    public double Evaluate(RUNTIME_CONTEXT context)
    {
        return _value;
    }
}
