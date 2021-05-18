package com.slang.compiler.ast.statements;

import com.slang.compiler.ast.RUNTIME_CONTEXT;
import com.slang.compiler.ast.Statement;
import com.slang.compiler.ast.Variable;
import com.slang.compiler.parser.SymbolInfo;

/**
 * Compile the Variable Declaration statements
 */
public class VariableDeclarationStatement extends Statement {

    SymbolInfo m_inf = null;
    Variable var = null;

    public VariableDeclarationStatement(SymbolInfo inf) {
        m_inf = inf;
    }

    @Override
    public SymbolInfo Execute(RUNTIME_CONTEXT context) {
        context.symbolTable.put(m_inf.SymbolName, m_inf);
        var = new Variable(m_inf);
        return m_inf;
    }
}
