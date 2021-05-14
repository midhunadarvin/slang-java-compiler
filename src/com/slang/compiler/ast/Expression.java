package com.slang.compiler.ast;

public interface Expression {
    double Evaluate(RUNTIME_CONTEXT context);
}
