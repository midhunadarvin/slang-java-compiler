package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    /**
     * private data structure
     */
    private Map<String, SymbolInfo> map = new HashMap<>();

     /**
     * Add a symbol to Symbol Table
     */
    public boolean add(SymbolInfo s)
    {
        map.put(s.SymbolName, s);
        return true;
    }

    /**
     * Retrieve the Symbol
     */
    public SymbolInfo get(String name)
    {
        return map.get(name);
    }

    /**
     * Assign to the Symbol Table
     */
    public void assign(Variable var, SymbolInfo value)
    {
        value.SymbolName = var.getName();
        map.put(var.getName(), value);
    }

    /**
     * Assign to a variable
     */
    public void assign(String var, SymbolInfo value)
    {
        map.put(var, value);
    }
}