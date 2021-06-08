package com.slang.compiler.ast.operators;

import com.slang.compiler.ast.COMPILATION_CONTEXT;
import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.ast.constants.RELATION_OPERATOR;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

public class RelationExpression extends Expression {

    /**
     * Which Operator
     */
    RELATION_OPERATOR m_op;

    /**
     * Left and Right Expression
     */
    private Expression ex1, ex2;

    /**
     * Type of this node
     */
     TypeInfo _type;

    /**
     * Operand Types .. if operands are string
     * we need to generate call to String.Compare
     * method...
     */

    TypeInfo _optype;

    public RelationExpression(RELATION_OPERATOR m_op, Expression ex1, Expression ex2) {
        super();
        this.m_op = m_op;
        this.ex1 = ex1;
        this.ex2 = ex2;
    }

    /**
     * The logic of this method is obvious...
     * Evaluate the Left and Right Expression...
     * Query the Type of the expressions and perform
     * appropriate action
     *
     * @param context
     * returns SymbolInfo
     */
    @Override
    public SymbolInfo Evaluate(RUNTIME_CONTEXT context) throws Exception {
        SymbolInfo eval_left = ex1.Evaluate(context);
        SymbolInfo eval_right = ex2.Evaluate(context);
        SymbolInfo ret_val = new SymbolInfo();

        if (eval_left.Type == TypeInfo.TYPE_NUMERIC && eval_right.Type == TypeInfo.TYPE_NUMERIC) {
            ret_val.Type = TypeInfo.TYPE_BOOL;
            ret_val.SymbolName = "";
            if (m_op == RELATION_OPERATOR.TOK_EQUALS)
                ret_val.boolean_val = eval_left.double_val == eval_right.double_val;
            else if (m_op == RELATION_OPERATOR.TOK_NOT_EQUALS)
                ret_val.boolean_val = eval_left.double_val != eval_right.double_val;
            else if (m_op == RELATION_OPERATOR.TOK_GREATER_THAN)
                ret_val.boolean_val = eval_left.double_val > eval_right.double_val;
            else if (m_op == RELATION_OPERATOR.TOK_GREATER_OR_EQUAL)
                ret_val.boolean_val = eval_left.double_val >= eval_right.double_val;
            else if (m_op == RELATION_OPERATOR.TOK_LESS_OR_EQUAL)
                ret_val.boolean_val = eval_left.double_val <= eval_right.double_val;
            else if (m_op == RELATION_OPERATOR.TOK_LESS_THAN)
                ret_val.boolean_val = eval_left.double_val < eval_right.double_val;
            return ret_val;
        } else if (eval_left.Type == TypeInfo.TYPE_STRING && eval_right.Type == TypeInfo.TYPE_STRING) {
            ret_val.Type = TypeInfo.TYPE_BOOL;
            ret_val.SymbolName = "";
            if (m_op == RELATION_OPERATOR.TOK_EQUALS) {
                ret_val.boolean_val = eval_left.string_val.equals(eval_right.string_val);
            } else if (m_op == RELATION_OPERATOR.TOK_NOT_EQUALS) {
                ret_val.boolean_val = !eval_left.string_val.equals(eval_right.string_val);
            } else {
                ret_val.boolean_val = false;
            }
            return ret_val;
        }

        if (eval_left.Type == TypeInfo.TYPE_BOOL && eval_right.Type == TypeInfo.TYPE_BOOL) {
            ret_val.Type = TypeInfo.TYPE_BOOL;
            ret_val.SymbolName = "";
            if (m_op == RELATION_OPERATOR.TOK_EQUALS) {
                ret_val.boolean_val = eval_left.boolean_val == eval_right.boolean_val;
            } else if (m_op == RELATION_OPERATOR.TOK_NOT_EQUALS) {
                ret_val.boolean_val = eval_left.boolean_val != eval_right.boolean_val;
            } else {
                ret_val.boolean_val = false;
            }
            return ret_val;
        }
        return null;
    }

    /**
     * Recursively check the type and bubble up the type
     * information to the top...
     * Query the Type of the expressions and perform
     * appropriate action
     *
     * @param context
     * returns TypeInfo
     */
    @Override
    public TypeInfo TypeCheck(COMPILATION_CONTEXT context) throws Exception {
        TypeInfo eval_left = ex1.TypeCheck(context);
        TypeInfo eval_right = ex2.TypeCheck(context);
        if (eval_left != eval_right) {
            throw new Exception("Wrong Type in expression");
        }

        if (eval_left == TypeInfo.TYPE_STRING && (!(m_op == RELATION_OPERATOR.TOK_EQUALS || m_op == RELATION_OPERATOR.TOK_NOT_EQUALS))) {
            throw new Exception("Only == amd != supported for string type ");
        }

        if (eval_left == TypeInfo.TYPE_BOOL && (!(m_op == RELATION_OPERATOR.TOK_EQUALS || m_op == RELATION_OPERATOR.TOK_NOT_EQUALS))) {
            throw new Exception("Only == amd != supported for boolean type ");
        }
        // store the operand type as well
        _optype = eval_left;
        _type = TypeInfo.TYPE_BOOL;
        return _type;
    }

    @Override
    public TypeInfo get_type() {
        return _type;
    }
}
