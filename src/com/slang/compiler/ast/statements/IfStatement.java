package com.slang.compiler.ast.statements;

import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.ast.Statement;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

import java.util.ArrayList;

public class IfStatement extends Statement {
    /**
     * Condition expression
     * the type ought to be boolean
     */
    private Expression condition;

    /**
     * List of statements to be
     * executed if condition is true
     */
    private ArrayList<Statement> true_part;

    /**
     * List of statements to be
     * executed if condition is false
     */
    private ArrayList<Statement> false_part;

    @Override
    public SymbolInfo Execute(RUNTIME_CONTEXT context) throws Exception {

        // Evaluate the Condition
        SymbolInfo m_cond = condition.Evaluate(context);

        // if cond is not boolean..or evaluation failed
        if (m_cond == null || m_cond.Type != TypeInfo.TYPE_BOOL)
            return null;

        if (m_cond.boolean_val == true) {
            // if cond is true
            true_part.forEach(statement -> {
                try {
                    statement.Execute(context);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        } else {
            // if cond is false
            false_part.forEach(statement -> {
                try {
                    statement.Execute(context);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        }
        return null;
    }
}
