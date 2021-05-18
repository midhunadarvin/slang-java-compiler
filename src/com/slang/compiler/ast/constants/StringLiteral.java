package com.slang.compiler.ast.constants;

import com.slang.compiler.ast.COMPILATION_CONTEXT;
import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

/**
 * To Store Literal string enclosed
 * in Quotes
 */
public class StringLiteral extends Expression {
    /**
     * Info Field
     */
    private SymbolInfo info;

    /**
     * Construction does not do much , just keeps the
     * value assigned to the private variable
     * @param value This is the value of the string literal
     * @return void
     */
    public StringLiteral(String value) {
        info = new SymbolInfo();
        info.SymbolName = null;
        info.string_val = value;
        info.Type = TypeInfo.TYPE_STRING;
    }

    /**
     * Evaluation of string literal will given the value
     */
    @Override
    public SymbolInfo Evaluate(RUNTIME_CONTEXT cont) {
        return info;
    }

    @Override
    public TypeInfo TypeCheck(COMPILATION_CONTEXT cont) {
        return info.Type;
    }

    @Override
    public TypeInfo get_type() {
        return info.Type;
    }
}
