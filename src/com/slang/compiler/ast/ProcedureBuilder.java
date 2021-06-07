package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;
import com.slang.compiler.parser.TypeInfo;

import java.util.ArrayList;

public class ProcedureBuilder extends AbstractBuilder {
    /**
     * Procedure name ..now it is hard coded
     * to MAIN
     */
    private String proc_name = "";

    /* Compilation context for type analysis */
    COMPILATION_CONTEXT ctx = null;

    /* Procedure does not take any argument.. */
    ArrayList m_formals = null;

    /**
     * Array of Statements
     */
    ArrayList m_statements = new ArrayList();

    /* Return Type of the procedure */
    TypeInfo inf = TypeInfo.TYPE_ILLEGAL;

    /**
     * Constructor
     * @param name
     * @param _ctx
     */
    public ProcedureBuilder(String name, COMPILATION_CONTEXT _ctx) {
        proc_name = name;
        ctx = _ctx;
    }

    /**
     * AddLocal
     * @param info
     */
    public boolean AddLocal(SymbolInfo info) {
        ctx.symbolTable.add(info);
        return true;
    }

    public TypeInfo TypeCheck(Expression e) throws Exception {
        return e.TypeCheck(ctx);
    }

    /**
     * @param statement
     */
    public void AddStatement(Statement statement) {
        m_statements.add(statement);
    }

    public SymbolInfo GetSymbol(String strname) {
        return ctx.symbolTable.get(strname);
    }

    /**
     * Check the function Prototype
     * @param name
     */
    public boolean CheckProto(String name) {
        return true;
    }

    public TypeInfo getType() {
        return inf;
    }

    public void setType(TypeInfo value) {
        inf = value;
    }

    public SymbolTable getSymbolTable() {
        return ctx.symbolTable;
    }

    public COMPILATION_CONTEXT getContext() {
        return ctx;
    }

    public String getName() {
        return proc_name;
    }

    public void setName(String value) {
        proc_name = value;
    }

    public Procedure GetProcedure() {
        Procedure ret = new Procedure(proc_name, m_statements, ctx.symbolTable, inf);
        return ret;
    }
}
