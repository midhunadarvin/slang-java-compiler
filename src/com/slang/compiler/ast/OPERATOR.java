package com.slang.compiler.ast;

public enum OPERATOR {
    PLUS('+'),
    MINUS('-'),
    DIV('/'),
    MUL('*');

    final char name;

    OPERATOR(char name) {
        this.name = name;
    }
}
