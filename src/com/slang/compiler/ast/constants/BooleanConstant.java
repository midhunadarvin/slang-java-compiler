package com.slang.compiler.ast.constants;

import com.slang.compiler.ast.COMPILATION_CONTEXT;
import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

/**
 * Boolean Constant ( TRUE, FALSE }
 * This class contains a boolean value
 *
 * @author  Midhun A Darvin
 * @version 1.0
 */
public class BooleanConstant extends Expression {
    /**
     * Info Field
     */
    private SymbolInfo info;

    /**
     * Constructor
     *
     * @param value  This is the value of the boolean constant
     * @return void
     */
    public BooleanConstant(boolean value) {
        info = new SymbolInfo();
        info.SymbolName = null;
        info.boolean_val = value;
        info.Type = TypeInfo.TYPE_BOOL;
    }


    /**
    * Evaluation of boolean will given the value
    */
    @Override
    public SymbolInfo Evaluate(RUNTIME_CONTEXT context) {
        return info;
    }

    @Override
    public TypeInfo TypeCheck(COMPILATION_CONTEXT context) {
        return info.Type;
    }

    @Override
    public TypeInfo get_type() {
        return info.Type;
    }
}

