package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;

/**
 * Abstract for Statement execution
 * Statement is what you Execute for it's Effect
 *
 * @author  Midhun A Darvin
 * @version 1.0
 */
public abstract class Statement {
    public abstract SymbolInfo Execute(RUNTIME_CONTEXT context) throws Exception;
}
