package com.slang.compiler.ast;

import com.slang.compiler.parser.SymbolInfo;

import java.util.ArrayList;

/**
 * A CodeModule is a Compilation Unit ..
 * At this point of time ..it is just a bunch
 * of statements...
 */
public class TModule extends CompilationUnit {
    /**
     * A Program is a collection of Procedures...
     * Now , we support only global function...
     */
    private ArrayList<Procedure> m_procs = null;

    /**
     * List of Compiled Procedures....
     * At this point of time..only one procedure will be there....
     */
    private ArrayList compiled_procs = null;

    /**
     * class to generate IL executable...
     */
    // private ExeGenerator _exe = null;

    /**
     * Constructor for the Program ...
     * @param procs
     */
    public TModule(ArrayList<Procedure> procs) {
        m_procs = procs;
    }
    /**
     *
     */
    public boolean CreateExecutable(String name)
    {
        //
        // Create an instance of Exe Generator
        // ExeGenerator takes a TModule and
        // exe name as the Parameter...
        // _exe = new ExeGenerator(this,name);
        // Compile The module...
        //        Compile(null);
        // Save the Executable...
        // _exe.Save();
        return true;
    }

//    public override bool Compile(DNET_EXECUTABLE_GENERATION_CONTEXT cont)
//    {
//        compiled_procs = new ArrayList();
//        foreach (Procedure p in m_procs)
//        {
//            DNET_EXECUTABLE_GENERATION_CONTEXT con = new
//                    DNET_EXECUTABLE_GENERATION_CONTEXT(this,p, _exe.type_bulder);
//            compiled_procs.Add(con);
//            p.Compile(con);
//        }
//        return true;
//    }

    @Override
    public SymbolInfo Execute(RUNTIME_CONTEXT context) {
        Procedure p = Find("Main");
        if (p != null) {
            return p.Execute(context);
        }
        return null;
    }

//    public MethodBuilder _get_entry_point(string _funcname)
//    {
//        foreach (DNET_EXECUTABLE_GENERATION_CONTEXT u in compiled_procs)
//        {
//            if (u.MethodName.Equals(_funcname))
//            {
//                return u.MethodHandle;
//            }
//        }
//        return null;
//    }

    public Procedure Find(String str)
    {
        for (Procedure p : m_procs) {
            String pname = p.m_name;
            if (pname.equalsIgnoreCase(str))
                return p;
        }
        return null;
    }
}

