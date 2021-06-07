package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;

/**
 * A bunch of statement is called a Compilation
 * unit at this point of time... STEP 5
 * In future , a Collection of Procedures will be
 * called a Compilation unit
 *
 * Added in the STEP 5
 */
public abstract class CompilationUnit {
    public abstract SymbolInfo Execute(RUNTIME_CONTEXT cont);
//  public abstract boolean Compile(DNET_EXECUTABLE_GENERATION_CONTEXT cont);
}
