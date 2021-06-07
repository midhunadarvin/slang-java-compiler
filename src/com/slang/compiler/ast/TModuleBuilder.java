package com.slang.compiler.ast;

import java.util.ArrayList;

/**
 * A Builder for Creating a Module
 */
public class TModuleBuilder {
    /**
     * Array of Procedures
     */
    private ArrayList<Procedure> procs;

    /**
     * Array of Function Prototypes
     * not much use as of now...
     */
    private ArrayList protos = null;

    public TModuleBuilder() {
        procs = new ArrayList();
        protos = null;
    }

    /**
     * Add Procedure
     * @param procedure
     */
    public boolean Add(Procedure procedure)
    {
        procs.add(procedure);
        return true;
    }

    /**
     * Create Program
     */
    public TModule GetProgram() {
        return new TModule(procs);
    }

    public Procedure GetProc(String name) {
        for (Procedure proc : procs) {
            if (proc.m_name.equalsIgnoreCase(name)) {
                return proc;
            }
        }
        return null;
    }
}
