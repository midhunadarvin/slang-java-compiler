package com.slang.compiler.ast.operators;

import com.slang.compiler.ast.COMPILATION_CONTEXT;
import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

public class LogicalNotExpression extends Expression {

    private Expression ex1;
    TypeInfo _type;

    public LogicalNotExpression(Expression e1) {
        ex1 = e1;
    }

    @Override
    public SymbolInfo Evaluate(RUNTIME_CONTEXT context) throws Exception {
        SymbolInfo eval_left = ex1.Evaluate(context);
        if (eval_left.Type == TypeInfo.TYPE_BOOL) {
            SymbolInfo ret_val = new SymbolInfo();
            ret_val.Type = TypeInfo.TYPE_BOOL;
            ret_val.SymbolName = "";
            ret_val.boolean_val = !eval_left.boolean_val;
            return ret_val;
        } else {
            return null;
        }
    }

    @Override
    public TypeInfo TypeCheck(COMPILATION_CONTEXT context) throws Exception {
        TypeInfo eval_left = ex1.TypeCheck(context);
        if (eval_left == TypeInfo.TYPE_BOOL) {
            _type = TypeInfo.TYPE_BOOL;
            return _type;
        } else {
            throw new Exception("Wrong Type in expression");
        }
    }

    @Override
    public TypeInfo get_type() {
        return _type;
    }
}
