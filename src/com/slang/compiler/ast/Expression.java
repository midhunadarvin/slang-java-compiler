package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

/**
 * Abstract for Expression evaluation
 * Expression is what you evaluate for it's value
 *
 * @author  Midhun A Darvin
 * @version 1.0
 */
public abstract class Expression {
    public abstract SymbolInfo Evaluate(RUNTIME_CONTEXT context) throws Exception;
    public abstract TypeInfo TypeCheck(COMPILATION_CONTEXT context) throws Exception;
    public abstract TypeInfo get_type();
}
