package com.slang.compiler.ast.operators;

import com.slang.compiler.ast.COMPILATION_CONTEXT;
import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

/**
 * This node represents Binary Plus (+) operator
 */
public class BinaryPlus extends Expression {
    /**
     * Plus has got a left expression (exp1 )
     * and a right expression...
     * and a Associated type information
     */
    private Expression exp1, exp2;
    TypeInfo _type;


    public BinaryPlus(Expression e1, Expression e2) {
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public SymbolInfo Evaluate(RUNTIME_CONTEXT cont) throws Exception {
        SymbolInfo eval_left = exp1.Evaluate(cont);
        SymbolInfo eval_right = exp2.Evaluate(cont);
        if (eval_left.Type == TypeInfo.TYPE_STRING && eval_right.Type == TypeInfo.TYPE_STRING) {
            SymbolInfo ret_val = new SymbolInfo();
            ret_val.string_val = eval_left.string_val + eval_right.string_val;
            ret_val.Type = TypeInfo.TYPE_STRING;
            ret_val.SymbolName = "";
            return ret_val;
        } else if (eval_left.Type == TypeInfo.TYPE_NUMERIC && eval_right.Type == TypeInfo.TYPE_NUMERIC) {
            SymbolInfo ret_val = new SymbolInfo();
            ret_val.double_val = eval_left.double_val + eval_right.double_val;
            ret_val.Type = TypeInfo.TYPE_NUMERIC;
            ret_val.SymbolName = "";
            return ret_val;
        } else {
            throw new Exception("Type mismatch");
        }
    }

    public TypeInfo TypeCheck(COMPILATION_CONTEXT cont) throws Exception {
        TypeInfo eval_left = exp1.TypeCheck(cont);
        TypeInfo eval_right = exp2.TypeCheck(cont);
        if (eval_left == eval_right && eval_left != TypeInfo.TYPE_BOOL) {
            _type = eval_left;
            return _type;
        } else {
            throw new Exception("Type mismatch failure");
        }
    }

    @Override
    public TypeInfo get_type() {
        return _type;
    }
}