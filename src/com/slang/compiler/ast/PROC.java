package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;

/**
 * Abstract base class for Procedure
 * All the statements in a Program ( Compilation unit )
 * will be compiled into a PROC
 */
public abstract class PROC {
        public abstract SymbolInfo Execute(RUNTIME_CONTEXT cont);
//        public abstract boolean Compile(DNET_EXECUTABLE_GENERATION_CONTEXT cont);
}
