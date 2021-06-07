package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

import java.util.ArrayList;
/**
 * The class Procedure is to model a FUNCTION. At this point we compile all the statement into a Function.
 * In future , we will support User defined Function.
 */
public class Procedure extends PROC {
    /**
     * Procedure name ..which defaults to Main
     * in the type MainClass
     */
    public String m_name;

    /**
     * Formal parameters...
     */
    public ArrayList m_formals = null;

    /**
     * List of statements which comprises the Procedure
     */
    public ArrayList<Statement> m_statements = null;

    /**
     * Local variables
     */
    public SymbolTable m_locals = null;

    /**
     * return_value.... a hard coded zero at this
     * point of time..
     */
    public SymbolInfo return_value = null;

    /**
     * TYPE_INFO => TYPE_NUMERIC
     */
    public TypeInfo _type = TypeInfo.TYPE_ILLEGAL;

    public Procedure(String m_name, ArrayList m_statements, SymbolTable m_locals, TypeInfo _type) {
        super();
        this.m_name = m_name;
        this.m_statements = m_statements;
        this.m_locals = m_locals;
        this._type = _type;
    }

    @Override
    public SymbolInfo Execute(RUNTIME_CONTEXT context) {
        m_statements.forEach(statement-> {
            try {
                statement.Execute(context);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return null;
    }
}
