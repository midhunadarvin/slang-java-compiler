package com.slang.compiler.ast.operators;

import com.slang.compiler.ast.COMPILATION_CONTEXT;
import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

/**
 * This node represents Unary Plus (+) operator
 */
class UnaryMinus extends Expression {
    /**
     * Plus has got a right expression (exp1 )
     * and a Associated type information
     */
    private Expression exp1;
    private TypeInfo _type;

    public UnaryMinus(Expression e1) {
        exp1 = e1;
    }

    @Override
    public SymbolInfo Evaluate(RUNTIME_CONTEXT cont) throws Exception {
        SymbolInfo eval_left = exp1.Evaluate(cont);
        if (eval_left.Type == TypeInfo.TYPE_NUMERIC) {
            SymbolInfo ret_val = new SymbolInfo();
            ret_val.double_val = -eval_left.double_val;
            ret_val.Type = TypeInfo.TYPE_NUMERIC;
            ret_val.SymbolName = "";
            return ret_val;
        } else {
            throw new Exception("Type mismatch");
        }
    }

    public TypeInfo TypeCheck(COMPILATION_CONTEXT cont) throws Exception {
        TypeInfo eval_left = exp1.TypeCheck(cont);
        if (eval_left == TypeInfo.TYPE_NUMERIC) {
            _type = eval_left;
            return _type;
        } else {
            throw new Exception("Type mismatch failure");
        }
    }

    @Override
    public TypeInfo get_type()
    {
        return _type;
    }
}
