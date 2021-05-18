package com.slang.compiler.parser;

/**
 * Symbol Table entry for variable
 * using Attributes , one can optimize the
 * storage by simulating C/C++ union.
 */

public class SymbolInfo {
    public String SymbolName; // Symbol Name
    public TypeInfo Type; // Data type
    public String string_val; // memory to hold string
    public double double_val; // memory to hold double
    public boolean boolean_val; // memory to hold boolean
}
