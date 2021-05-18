package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;

import java.util.HashMap;
import java.util.Map;

public class COMPILATION_CONTEXT {
    /**
     * Symbol Table for this context
     */
    public SymbolTable symbolTable = new SymbolTable();

    public COMPILATION_CONTEXT() {
    }
}
