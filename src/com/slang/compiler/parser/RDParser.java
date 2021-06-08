package com.slang.compiler.parser;

import com.slang.compiler.ast.*;
import com.slang.compiler.ast.constants.BooleanConstant;
import com.slang.compiler.ast.constants.NumericConstant;
import com.slang.compiler.ast.constants.RELATION_OPERATOR;
import com.slang.compiler.ast.constants.StringLiteral;
import com.slang.compiler.ast.operators.*;
import com.slang.compiler.ast.statements.*;

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
    private TModuleBuilder tModuleBuilder;

    /**
     * Constructor
     *
     * @param Expr - Expression String
     */
    public RDParser(String Expr) {
        super(Expr);
        tModuleBuilder = new TModuleBuilder();
    }

    private void getNextToken() throws Exception {
        try {
            previousToken = currentToken;
            currentToken = GetToken();
        } catch (Exception exception) {
            throw exception;
        }
    }


    public TModule DoParse() throws Exception {
        ProcedureBuilder pb = new ProcedureBuilder("MAIN", new COMPILATION_CONTEXT());
        ArrayList<Statement> statements = Parse(pb);

        for (Statement statement : statements) {
            pb.AddStatement(statement);
        }

        Procedure procedure = pb.GetProcedure();
        tModuleBuilder.Add(procedure);
        return tModuleBuilder.GetProgram();
    }

    /**
     * Parses the Expression String and gets the statements.
     *
     * @param context
     */
    public ArrayList Parse(ProcedureBuilder context) throws Exception {
        try {
            // Get the Next Token
            getNextToken();

            // Parse all the statements
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
    public Expression CallExpr(ProcedureBuilder context) throws Exception {
        try {
            getNextToken();
            return BooleanExpr(context);
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
     * <statement> := <vardeclstmt> | <printstmt> | <printlinestmt> | <assignmentstmt> | <ifstmt> | <whilestmt>
     *
     * <vardeclstmt> := STRING <varname>; | NUMERIC <varname>; | BOOLEAN <varname>;
     * <printstmt> := PRINT <expr>;
     * <printlinestmt>:= printline <expr>;
     * <assignmentstmt>:= <variable> = value;
     * <ifstmt>::= IF <BooleanExpr> THEN <stmts> [ ELSE <stmts> ] ENDIF
     * <whilestmt>::= WHILE <BooleanExpr> <stmts> WEND
     * <type> := NUMERIC | STRING | BOOLEAN
     *
     * <BooleanExpr> ::= <BooleanExpr>
     * <BooleanExpr> ::= <LogicalExpr> LOGIC_OP <BooleanExpr>
     * <LogicalExpr> ::= <RelationalExpr> REL_OP <LogicalExpr>
     * <RelationalExpr> ::= <Term> ADD_OP <RelationalExpr>
     * <Term> ::= <Factor> MUL_OP <Term>
     * <Factor> ::= <Numeric> | <String> | TRUE | FALSE | <variable> | ‘(‘ <expr> ‘)’ | {+|-|!} <Factor>
     * <LOGIC_OP> := '&&' | ‘||’
     * <REL_OP> := '>' |' < '|' >=' |' <=' |' <>' |' =='
     * <ADD_OP> := '+' | '-'
     * <MUL_OP> := '*' | '/'
     */
    private ArrayList StatementList(ProcedureBuilder context) throws Exception {
        try {
            ArrayList arr = new ArrayList();
            while (
                    (currentToken != TOKEN.TOK_ELSE)
                            && (currentToken != TOKEN.TOK_END_IF)
                            && (currentToken != TOKEN.TOK_WHILE_END)
                            && (currentToken != TOKEN.TOK_NULL)
            ) {
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
     * Boolean Expression
     * <BooleanExpr> ::= <BooleanExpr>
     * <BooleanExpr> ::= <LogicalExpr> LOGIC_OP <BooleanExpr>
     *
     * @param context
     */
    private Expression BooleanExpr(ProcedureBuilder context) throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = LogicalExpr(context);
            while (currentToken == TOKEN.TOK_AND || currentToken == TOKEN.TOK_OR) {
                last_token = currentToken;
                getNextToken();
                Expression e1 = BooleanExpr(context);
                if (last_token == TOKEN.TOK_AND)
                    RetValue = new LogicalExpression(TOKEN.TOK_AND, RetValue, e1);
                else
                    RetValue = new LogicalExpression(TOKEN.TOK_OR, RetValue, e1);
            }
            return RetValue;
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * Logical Expression
     * <LogicalExpr> ::= <RelationalExpr> REL_OP <LogicalExpr>
     *
     * @param context
     */
    private Expression LogicalExpr(ProcedureBuilder context) throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = RelationalExpr(context);
            while (
                    currentToken == TOKEN.TOK_EQUALS
                            || currentToken == TOKEN.TOK_NOT_EQUALS
                            || currentToken == TOKEN.TOK_GREATER_THAN
                            || currentToken == TOKEN.TOK_LESS_THAN
                            || currentToken == TOKEN.TOK_GREATER_OR_EQUAL
                            || currentToken == TOKEN.TOK_LESS_OR_EQUAL
            ) {
                last_token = currentToken;
                getNextToken();
                Expression e1 = LogicalExpr(context);

                switch (last_token) {
                    case TOK_EQUALS: {
                        RetValue = new RelationExpression(RELATION_OPERATOR.TOK_EQUALS, RetValue, e1);
                        break;
                    }
                    case TOK_NOT_EQUALS: {
                        RetValue = new RelationExpression(RELATION_OPERATOR.TOK_NOT_EQUALS, RetValue, e1);
                        break;
                    }
                    case TOK_GREATER_THAN: {
                        RetValue = new RelationExpression(RELATION_OPERATOR.TOK_GREATER_THAN, RetValue, e1);
                        break;
                    }
                    case TOK_GREATER_OR_EQUAL: {
                        RetValue = new RelationExpression(RELATION_OPERATOR.TOK_GREATER_OR_EQUAL, RetValue, e1);
                        break;
                    }
                    case TOK_LESS_THAN: {
                        RetValue = new RelationExpression(RELATION_OPERATOR.TOK_LESS_THAN, RetValue, e1);
                        break;
                    }
                    case TOK_LESS_OR_EQUAL: {
                        RetValue = new RelationExpression(RELATION_OPERATOR.TOK_LESS_OR_EQUAL, RetValue, e1);
                        break;
                    }
                }
            }
            return RetValue;
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * Relational Expression
     * <RelationalExpr> ::= <Term> ADD_OP <RelationalExpr>
     *
     * @param context
     */
    private Expression RelationalExpr(ProcedureBuilder context) throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = Term(context);
            while (
                    currentToken == TOKEN.TOK_PLUS || currentToken == TOKEN.TOK_SUB
            ) {
                last_token = currentToken;
                getNextToken();
                Expression e1 = RelationalExpr(context);
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
     *
     * @param context
     */
    private Expression Term(ProcedureBuilder context) throws Exception {
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
     *
     * @param context
     */
    public Expression Factor(ProcedureBuilder context) throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = null;
            if (currentToken == TOKEN.TOK_NUMERIC) {
                RetValue = new NumericConstant(GetNumber());
                getNextToken();
            } else if (currentToken == TOKEN.TOK_STRING) {
                RetValue = new StringLiteral(last_string);
                getNextToken();
            } else if (currentToken == TOKEN.TOK_BOOL_FALSE || currentToken == TOKEN.TOK_BOOL_TRUE) {
                RetValue = new BooleanConstant(currentToken == TOKEN.TOK_BOOL_TRUE ? true : false);
                getNextToken();
            } else if (currentToken == TOKEN.TOK_OPAREN) {
                getNextToken();
                RetValue = BooleanExpr(context); // Recurse
                if (currentToken != TOKEN.TOK_CPAREN) {
                    System.out.println("Missing Closing Parenthesis\n");
                    throw new Exception();
                }
                getNextToken();
            } else if (currentToken == TOKEN.TOK_NOT) {
                getNextToken();
                RetValue = BooleanExpr(context);
                RetValue = new LogicalNotExpression(RetValue);
                return RetValue;
            } else if (currentToken == TOKEN.TOK_PLUS || currentToken == TOKEN.TOK_SUB) {
                last_token = currentToken;
                getNextToken();
                RetValue = Factor(context);
                if (last_token == TOKEN.TOK_PLUS)
                    RetValue = new UnaryPlus(RetValue);
                else if (last_token == TOKEN.TOK_SUB)
                    RetValue = new UnaryMinus(RetValue);
                getNextToken();
            } else if (currentToken == TOKEN.TOK_UNQUOTED_STRING) {
                ///
                /// Variables
                ///
                String str = this.last_string;
                SymbolInfo inf = context.getSymbolTable().get(str);
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

    /**
     * This Routine Queries Statement Type
     * to take the appropriate Branch...
     * Currently , only Print and PrintLine statement
     * are supported..
     * <p>
     * if a line does not start with Print or PrintLine ..
     * an exception is thrown
     *
     * @param context
     */
    private Statement Statement(ProcedureBuilder context) throws Exception {
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
                case TOK_IF:
                    returnVal = ParseIfStatement(context);
                    getNextToken();
                    return returnVal;
                case TOK_WHILE:
                    returnVal = ParseWhileStatement(context);
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
     *
     * @param context
     */
    public Statement ParseVariableDeclarationStatement(ProcedureBuilder context) throws Exception {
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
                    context.getSymbolTable().add(symbol);
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
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * Parse the Assignment Statement
     * <variable> = <expr>
     *
     * @param context
     */
    public Statement ParseAssignmentStatement(ProcedureBuilder context) throws Exception {
        //
        // Retrieve the variable and look it up in
        // the symbol table ..if not found throw exception
        //
        String variable = this.last_string;
        SymbolInfo symbol = context.getSymbolTable().get(variable);
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
        Expression exp = BooleanExpr(context);
        //------------ Do the type analysis ...
        if (exp.TypeCheck(context.getContext()) != symbol.Type) {
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
     *
     * @param context
     */
    private Statement ParsePrintStatement(ProcedureBuilder context) throws Exception {
        try {
            getNextToken();
            Expression a = BooleanExpr(context);
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
     * <p>
     * if a line does not start with Print or PrintLine ..
     * an exception is thrown
     *
     * @param context
     */
    private Statement ParsePrintLNStatement(ProcedureBuilder context) throws Exception {
        try {
            getNextToken();
            Expression a = BooleanExpr(context);
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
     * Parse the If Statement .. The grammar is
     * <ifstmt>::= IF <expr> THEN <stmts> [ ELSE <stmts> ] ENDIF
     * <param name="pb"></param>
     */
    public Statement ParseIfStatement(ProcedureBuilder pb) throws Exception {
        try {
            getNextToken();
            ArrayList<Statement> true_part = null;
            ArrayList<Statement> false_part = null;

            Expression exp = BooleanExpr(pb); // Evaluate Expression

            if (pb.TypeCheck(exp) != TypeInfo.TYPE_BOOL) {
                throw new Exception("Expects a boolean expression");
            }

            if (currentToken != TOKEN.TOK_THEN) {
                System.out.println(" Then Expected");
                throw new Exception("Then Expected");
            }
            getNextToken();

            true_part = StatementList(pb);

            if (currentToken == TOKEN.TOK_END_IF) {
                return new IfStatement(exp, true_part, false_part);
            }
            if (currentToken != TOKEN.TOK_ELSE) {
                throw new Exception("ELSE expected");
            }
            getNextToken();

            false_part = StatementList(pb);

            if (currentToken != TOKEN.TOK_END_IF) {
                throw new Exception("END IF EXPECTED");
            }
            return new IfStatement(exp, true_part, false_part);
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    public Statement ParseWhileStatement(ProcedureBuilder pb) throws Exception {
        try {
            getNextToken();
            Expression exp = BooleanExpr(pb);

            if (pb.TypeCheck(exp) != TypeInfo.TYPE_BOOL) {
                throw new Exception("Expects a boolean expression");
            }

            ArrayList body = StatementList(pb);

            if ((currentToken != TOKEN.TOK_WHILE_END)) {
                System.out.println("While end Expected");
                throw new Exception("Wend Expected");
            }

            return new WhileStatement(exp, body);
        } catch (Exception exception) {
            throw exception;
        }
    }

}
