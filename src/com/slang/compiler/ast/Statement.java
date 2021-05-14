package com.slang.compiler.ast;

/**
 * Abstract for Statement execution
 * Statement is what you Execute for it's Effect
 *
 * @author  Midhun A Darvin
 * @version 1.0
 */
public abstract class Statement {
    public abstract boolean Execute(RUNTIME_CONTEXT context);
}
