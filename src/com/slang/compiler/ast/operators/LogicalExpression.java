package com.slang.compiler.ast.operators;

import com.slang.compiler.ast.COMPILATION_CONTEXT;
import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TOKEN;
import com.slang.compiler.parser.TypeInfo;

public class LogicalExpression extends Expression {
    /**
     * && ( AND ) , || ( OR )
     */
    TOKEN m_op;

    /* Operands */
    private Expression ex1, ex2;

    /* Type of the node... */
    TypeInfo _type;

    public LogicalExpression(TOKEN m_op, Expression ex1, Expression ex2) {
        super();
        this.m_op = m_op;
        this.ex1 = ex1;
        this.ex2 = ex2;
    }


    @Override
    public SymbolInfo Evaluate(RUNTIME_CONTEXT context) throws Exception {
        SymbolInfo eval_left = ex1.Evaluate(context);
        SymbolInfo eval_right = ex2.Evaluate(context);
        if (eval_left.Type == TypeInfo.TYPE_BOOL && eval_right.Type == TypeInfo.TYPE_BOOL) {
            SymbolInfo ret_val = new SymbolInfo();
            ret_val.Type = TypeInfo.TYPE_BOOL;
            ret_val.SymbolName = "";
            if (m_op == TOKEN.TOK_AND) {
                ret_val.boolean_val = (eval_left.boolean_val && eval_right.boolean_val);
            } else if (m_op == TOKEN.TOK_OR) {
                ret_val.boolean_val = (eval_left.boolean_val || eval_right.boolean_val);
            } else {
                return null;
            }
            return ret_val;
        }
        return null;
    }

    @Override
    public TypeInfo TypeCheck(COMPILATION_CONTEXT context) throws Exception {
        TypeInfo eval_left = ex1.TypeCheck(context);
        TypeInfo eval_right = ex2.TypeCheck(context);
        // The Types should be Boolean...
        // Logical Operators only make sense
        // with Boolean Types
        if (eval_left == eval_right &&
                eval_left == TypeInfo.TYPE_BOOL) {
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
