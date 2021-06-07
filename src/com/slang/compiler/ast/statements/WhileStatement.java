package com.slang.compiler.ast.statements;

import com.slang.compiler.ast.Expression;
import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.ast.Statement;
import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

import java.util.ArrayList;

public class WhileStatement extends Statement {
    private Expression condition;
    private ArrayList<Statement> statements;

    public WhileStatement(Expression condition, ArrayList statements) {
        this.condition = condition;
        this.statements = statements;
    }

    @Override
    public SymbolInfo Execute(RUNTIME_CONTEXT context) throws Exception {
        // label for outer loop
        SymbolInfo m_cond = condition.Evaluate(context);
        if (m_cond == null || m_cond.Type != TypeInfo.TYPE_BOOL)
            return null;
        if (m_cond.boolean_val != true)
            return null;

        SymbolInfo tsp = null;

        while(m_cond.boolean_val == true) {
            for (Statement statement : statements) {
                try {
                    tsp = statement.Execute(context);
                    if (tsp != null) {
                        return tsp;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            m_cond = condition.Evaluate(context);
        }


        return null;
    }
}
