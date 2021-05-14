package com.slang.compiler.ast;

/**
 * Abstract for Expression evaluation
 * Expression is what you evaluate for it's value
 *
 * @author  Midhun A Darvin
 * @version 1.0
 */
public abstract class Expression {
    public abstract double Evaluate(RUNTIME_CONTEXT context);
}
