package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;

import java.util.HashMap;
import java.util.Map;

/**
* A Context is necessary for Variable scope...will be used later
*/
public class RUNTIME_CONTEXT {
    /**
     * Symbol Table for this context
     */
    public SymbolTable symbolTable = new SymbolTable();

    public RUNTIME_CONTEXT() {
    }

    public RUNTIME_CONTEXT(TModule program) {
    }
}
