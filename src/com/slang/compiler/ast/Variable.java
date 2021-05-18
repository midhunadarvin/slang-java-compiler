package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;


/**
 *  Node to store Variables
 *  The data types supported are
 *  NUMERIC
 *  STRING
 *  BOOLEAN
 *  The node store only the variable name , the
 *  associated data will be found in the
 *  Symbol Table attached to the COMPILATION_CONTEXT
 */
public class Variable extends Expression {
    private String var_name;
    private TypeInfo _type;

    /**
     * Constructor stores the variable name
     * @return void
     */
    public Variable(SymbolInfo inf) {
        var_name = inf.SymbolName;
    }

    /**
     * Creates a new symbol and puts into the symbol table
     * and stores the key ( variable name )
     * @param compilationContext
     * @param name
     * @param _value
     * @return void
     */
    public Variable(COMPILATION_CONTEXT compilationContext, String name, double _value) {
        SymbolInfo s =new SymbolInfo();
        s.SymbolName = name;
        s.Type = TypeInfo.TYPE_NUMERIC;
        s.double_val = _value;
        var_name=name;
    }

    /**
     * Creates a new symbol and puts into the symbol table
     * and stores the key ( variable name )
     * @param compilationContext
     * @param name
     * @param _value
     * @return void
     */
    public Variable(COMPILATION_CONTEXT compilationContext, String name, boolean _value) {
        SymbolInfo s = new SymbolInfo();
        s.SymbolName=name;
        s.Type=TypeInfo.TYPE_BOOL;
        s.boolean_val=_value;
        compilationContext.symbolTable.add(s);
        var_name=name;
    }

    /**
     * Creates a new symbol and puts into the symbol table
     * and stores the key ( variable name )
     * @param compilationContext
     * @param name
     * @param _value
     * @return void
     */
    public Variable(COMPILATION_CONTEXT compilationContext, String name, String _value) {
        SymbolInfo s = new SymbolInfo();
        s.SymbolName = name;
        s.Type= TypeInfo.TYPE_STRING;
        s.string_val = _value;
        compilationContext.symbolTable.add(s);
        var_name=name;
    }

    /**
     * Retrieves the name of the Variable ( method version )
     * @return var_name
     */
    public String getName() {
        return var_name;
    }

    /**
     * Set the name of the Variable ( method version )
     * @return var_name
     */
    public void setName(String value) {
        var_name = value;
    }

    /**
     * To Evaluate a variable , we just need to do a lookup
     * in the Symbol table ( of RUNTIME_CONTEXT )
     * @param runtimeContext
     * @return symbolInfo
     */
    @Override
    public SymbolInfo Evaluate(RUNTIME_CONTEXT runtimeContext) {
        if (runtimeContext.symbolTable == null) {
            return null;
        } else {
            SymbolInfo a = runtimeContext.symbolTable.get(var_name);
            return a;
        }
    }

    /**
     * Look it up in the Symbol Table and
     * return the type
     * @param context
     * @return symbolInfo
     */
    @Override
    public TypeInfo TypeCheck(COMPILATION_CONTEXT context) {
        if (context.symbolTable != null) {
            SymbolInfo a = context.symbolTable.get(var_name);
            if (a != null) {
                _type = a.Type;
                return _type;
            }
        }
        return TypeInfo.TYPE_ILLEGAL;
    }

    /**
     * This should only be called after the TypeCheck method
     * has been invoked on AST
     * @return _type
     */
    @Override
    public TypeInfo get_type() {
        return _type;
    }
}
