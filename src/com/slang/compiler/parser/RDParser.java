package com.slang.compiler.parser;

import com.slang.compiler.ast.*;
import com.slang.compiler.ast.constants.BooleanConstant;
import com.slang.compiler.ast.constants.NumericConstant;
import com.slang.compiler.ast.constants.StringLiteral;
import com.slang.compiler.ast.operators.*;
import com.slang.compiler.ast.statements.AssignmentStatement;
import com.slang.compiler.ast.statements.PrintLineStatement;
import com.slang.compiler.ast.statements.PrintStatement;
import com.slang.compiler.ast.statements.VariableDeclarationStatement;

import java.util.ArrayList;

/**
 * The class RDParser is derived from the Lexer class. By using an algorithm by the name Recursive
 * descent parsing , we will evaluate the expression.A recursive descent parser is a top-down parser built
 * from a set of mutually-recursive procedures where each such procedure usually implements one of the
 * production rules of the grammar. Thus the structure of the resulting program closely mirrors that
 * of the grammar it recognizes.
 *
 * @author Midhun A Darvin
 * @version 1.0
 */
public class RDParser extends Lexer {
    private TOKEN currentToken;
    private TOKEN previousToken;

    /**
     * Constructor
     *
     * @param Expr - Expression String
     */
    public RDParser(String Expr) {
        super(Expr);
    }

    private void getNextToken() throws Exception {
        try {
            previousToken = currentToken;
            currentToken = GetToken();
        } catch (Exception exception) {
            throw exception;
        }
    }


    /**
     * Parses the Expression String and gets the statements.
     */
    public ArrayList Parse(COMPILATION_CONTEXT context) throws Exception {
        try {
            // Get the Next Token
            getNextToken();
            //
            // Parse all the statements
            //
            return StatementList(context);
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }

    }

    /**
     * Call Expression method - This method gets the Expression from the
     * string via lexical analysis and by Recursive Descent parsing
     */
    public Expression CallExpr(COMPILATION_CONTEXT context) throws Exception {
        try {
            getNextToken();
            return Expr(context);
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }


    /**
     * The Grammar is
     *
     * <stmtlist> := { <statement> }+
     *
     * <statement> := <printstmt> | <printlinestmt>
     * <printstmt> := print <expr >;
     * <vardeclstmt> := STRING <varname>; | NUMERIC <varname>; | BOOLEAN <varname>;
     * <printlinestmt>:= printline <expr>;
     *
     * <Expr> ::= <Term> | <Term> { + | - } <Expr>
     * <Term> ::= <Factor> | <Factor> {*|/} <Term>
     * <Factor>::= <number> | ( <expr> ) | {+|-} <factor> | <variable> | TRUE | FALSE
     */

    private ArrayList StatementList(COMPILATION_CONTEXT context) throws Exception {
        try {
            ArrayList arr = new ArrayList();
            while (currentToken != TOKEN.TOK_NULL) {
                Statement temp = Statement(context);
                if (temp != null) {
                    arr.add(temp);
                }
            }
            return arr;
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * This Routine Queries Statement Type
     * to take the appropriate Branch...
     * Currently , only Print and PrintLine statement
     * are supported..
     *
     * if a line does not start with Print or PrintLine ..
     * an exception is thrown
     */
    private Statement Statement(COMPILATION_CONTEXT context) throws Exception {
        try {
            Statement returnVal = null;
            switch (currentToken) {
                case TOK_VAR_STRING:
                case TOK_VAR_NUMBER:
                case TOK_VAR_BOOL:
                    returnVal = ParseVariableDeclarationStatement(context);
                    getNextToken();
                    return returnVal;
                case TOK_PRINT:
                    returnVal = ParsePrintStatement(context);
                    getNextToken();
                    break;
                case TOK_PRINTLN:
                    returnVal = ParsePrintLNStatement(context);
                    getNextToken();
                    break;
                case TOK_UNQUOTED_STRING:
                    returnVal = ParseAssignmentStatement(context);
                    getNextToken();
                    return returnVal;
                default:
                    throw new Exception("Invalid statement");
            }
            return returnVal;
        } catch (Exception exception) {
            System.out.println(exception);
            throw new Exception("Invalid statement");
        }
    }

    /**
     * Parse Variable declaration statement
     */
    public Statement ParseVariableDeclarationStatement(COMPILATION_CONTEXT context) throws Exception {
        try {
            //--- Save the Data type
            TOKEN tok = currentToken;
            // --- Skip to the next token , the token ought
            // to be a Variable name ( UnQuoted String )
            getNextToken();
            if (currentToken == TOKEN.TOK_UNQUOTED_STRING) {
                SymbolInfo symbol = new SymbolInfo();
                symbol.SymbolName = this.last_string;
                symbol.Type = (tok == TOKEN.TOK_VAR_BOOL) ?
                        TypeInfo.TYPE_BOOL : (tok == TOKEN.TOK_VAR_NUMBER) ?
                        TypeInfo.TYPE_NUMERIC : TypeInfo.TYPE_STRING;
                //---------- Skip to Expect the SemiColon
                getNextToken();
                if (currentToken == TOKEN.TOK_SEMI_COLON) {
                    // ----------- Add to the Symbol Table
                    // for type analysis
                    context.symbolTable.add(symbol);
                    // --------- return the Object of type
                    // --------- VariableDeclStatement
                    // This will just store the Variable name
                    // to be looked up in the above table
                    return new VariableDeclarationStatement(symbol);
                } else {
                    throw new Exception("; expected");
                }
            } else {
                throw new Exception("invalid variable declaration");
            }
        } catch(Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * Parse the Assignment Statement
     * <variable> = <expr>
     */
    public Statement ParseAssignmentStatement(COMPILATION_CONTEXT context) throws Exception {
        //
        // Retrieve the variable and look it up in
        // the symbol table ..if not found throw exception
        //
        String variable = this.last_string;
        SymbolInfo symbol = context.symbolTable.get(variable);
        if (symbol == null) {
            throw new Exception("Variable not found " + last_string);
        }
        //------------ The next token ought to be an assignment
        // expression....
        getNextToken();
        if (currentToken != TOKEN.TOK_ASSIGN) {
            throw new Exception("= expected");
        }
        //-------- Skip the token to start the expression
        // parsing on the RHS
        getNextToken();
        Expression exp = Expr(context);
        //------------ Do the type analysis ...
        if (exp.TypeCheck(context) != symbol.Type) {
            throw new Exception("Type mismatch in assignment");
        }
        // -------------- End of statement ( ; ) is expected
        if (currentToken != TOKEN.TOK_SEMI_COLON) {
            throw new Exception("; expected");
        }
        // return an instance of AssignmentStatement node..
        // s => Symbol info associated with variable
        // exp => to evaluated and assigned to symbol_info
        return new AssignmentStatement(symbol, exp);
    }

    /**
     * Parse the Print Statement .. The grammar is
     * PRINT <expr> ;
     * Once you are in this subroutine , we are expecting
     * a valid expression ( which will be compiled ) and a
     * semi colon to terminate the line..
     * Once Parse Process is successful , we create a PrintStatement
     * Object..
     * <p>
     * if a line does not start with Print or PrintLine ..
     * an exception is thrown
     */
    private Statement ParsePrintStatement(COMPILATION_CONTEXT context) throws Exception {
        try {
            getNextToken();
            Expression a = Expr(context);
            if (currentToken != TOKEN.TOK_SEMI_COLON) {
                throw new Exception("; is expected");
            }
            return new PrintStatement(a);
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * Parse the PrintLine Statement .. The grammar is
     * PRINTLINE <expr> ;
     * Once you are in this subroutine , we are expecting
     * a valid expression ( which will be compiled ) and a
     * semi colon to terminate the line..
     * Once Parse Process is successful , we create a PrintLineStatement
     * Object..
     *
     * if a line does not start with Print or PrintLine ..
     * an exception is thrown
     */
    private Statement ParsePrintLNStatement(COMPILATION_CONTEXT context) throws Exception {
        try {
            getNextToken();
            Expression a = Expr(context);
            if (currentToken != TOKEN.TOK_SEMI_COLON) {
                throw new Exception("; is expected");
            }
            return new PrintLineStatement(a);
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * Gets the Expression
     * <Expr> ::= <Term> { + | - } <Expr>
     * @param context
     */
    private Expression Expr(COMPILATION_CONTEXT context) throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = Term(context);
            while (currentToken == TOKEN.TOK_PLUS || currentToken == TOKEN.TOK_SUB) {
                last_token = currentToken;
                currentToken = GetToken();
                Expression e1 = Expr(context);
                if (last_token == TOKEN.TOK_PLUS)
                    RetValue = new BinaryPlus(RetValue, e1);
                else
                    RetValue = new BinaryMinus(RetValue, e1);
            }
            return RetValue;
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * Gets the Term
     * <Term> ::= <Factor> | <Factor> {*|/} <Term>
     */
    private Expression Term(COMPILATION_CONTEXT context) throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = Factor(context);
            while (currentToken == TOKEN.TOK_MUL || currentToken == TOKEN.TOK_DIV) {
                last_token = currentToken;
                getNextToken();
                Expression e1 = Term(context);
                if (last_token == TOKEN.TOK_MUL)
                    RetValue = new BinaryMultiply(RetValue, e1);
                else
                    RetValue = new BinaryDivide(RetValue, e1);
            }
            return RetValue;
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * Gets the Term
     * <Factor> ::= <TOK_DOUBLE> | ( <Exp> ) | { + | - } <Factor>
     */
    public Expression Factor(COMPILATION_CONTEXT context) throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = null;
            if (currentToken == TOKEN.TOK_NUMERIC) {
                RetValue = new NumericConstant(GetNumber());
                getNextToken();
            }
            else if (currentToken == TOKEN.TOK_STRING) {
                RetValue = new StringLiteral(last_string);
                getNextToken();
            }
            else if (currentToken == TOKEN.TOK_BOOL_FALSE || currentToken == TOKEN.TOK_BOOL_TRUE) {
                RetValue = new BooleanConstant(currentToken == TOKEN.TOK_BOOL_TRUE ? true : false);
                getNextToken();
            } else if (currentToken == TOKEN.TOK_OPAREN) {
                getNextToken();
                RetValue = Expr(context); // Recurse
                if (currentToken != TOKEN.TOK_CPAREN) {
                    System.out.println("Missing Closing Parenthesis\n");
                    throw new Exception();
                }
                getNextToken();
            } else if (currentToken == TOKEN.TOK_PLUS || currentToken == TOKEN.TOK_SUB) {
                last_token = currentToken;
                getNextToken();
                RetValue = Factor(context);
                if (last_token == TOKEN.TOK_PLUS)
                    RetValue = new UnaryPlus(RetValue);
                else
                    RetValue = new UnaryMinus(RetValue);
            } else if (currentToken == TOKEN.TOK_UNQUOTED_STRING) {
                ///
                /// Variables
                ///
                String str = this.last_string;
                SymbolInfo inf = context.symbolTable.get(str);
                if (inf == null)
                    throw new Exception("Undefined symbol");
                getNextToken();
                RetValue = new Variable(inf);
            } else {
                System.out.println("Illegal Token");
                throw new Exception();
            }
            return RetValue;
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

}
