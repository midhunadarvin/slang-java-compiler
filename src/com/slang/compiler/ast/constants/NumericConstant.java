package com.slang.compiler.ast.constants;

import com.slang.compiler.ast.COMPILATION_CONTEXT;
import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

/**
 * Numeric Constant
 * This class contains a numeric value
 *
 * @author  Midhun A Darvin
 * @version 1.0
 */
public class NumericConstant extends Expression {
    /**
     * Info Field
     */
    private SymbolInfo info;
    private double _value;

    /**
     * Construction does not do much , just keeps the
     * value assigned to the private variable
     * @param value This is the value of the numeric constant
     * @return void
     */
    public NumericConstant(double value)
    {
        _value = value;
        info = new SymbolInfo();
        info.SymbolName = null;
        info.double_val = value;
        info.Type = TypeInfo.TYPE_NUMERIC;
    }

    /**
     * While evaluating a numeric constant , return the _value
     * @param context This is the runtime context
     * @return void
     */
    @Override
    public SymbolInfo Evaluate(RUNTIME_CONTEXT context)
    {
        return info;
    }

    @Override
    public  TypeInfo TypeCheck(COMPILATION_CONTEXT context)
    {
        return info.Type;
    }

    @Override
    public TypeInfo get_type()
    {
        return info.Type;
    }
}
