package com.slang.compiler.parser;

import com.slang.compiler.ast.*;

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
        } catch(Exception exception) {
            throw exception;
        }
    }


    /**
     * Parses the Expression String and gets the statements.
     */
    public ArrayList Parse() throws Exception {
        try {
            // Get the Next Token
            getNextToken();
            //
            // Parse all the statements
            //
            return StatementList();
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }

    }

    /**
     * Call Expression method - This method gets the Expression from the
     * string via lexical analysis and by Recursive Descent parsing
     */
    public Expression CallExpr() throws Exception {
        try {
            getNextToken();
            return Expr();
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    private ArrayList StatementList() throws Exception {
        try {
            ArrayList arr = new ArrayList();
            while (currentToken != TOKEN.TOK_NULL)
            {
                Statement temp = Statement();
                if (temp != null)
                {
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
     *  This Routine Queries Statement Type
     *  to take the appropriate Branch...
     *  Currently , only Print and PrintLine statement
     *  are supported..
     *
     *  if a line does not start with Print or PrintLine ..
     *  an exception is thrown
     */
    private Statement Statement() throws Exception {
        try {
            Statement returnVal = null;
            switch (currentToken)
            {
                case TOK_PRINT:
                    returnVal = ParsePrintStatement();
                    getNextToken();
                    break;
                case TOK_PRINTLN:
                    returnVal = ParsePrintLNStatement();
                    getNextToken();
                    break;
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
     *  Parse the Print Statement .. The grammar is
     *  PRINT <expr> ;
     *  Once you are in this subroutine , we are expecting
     *  a valid expression ( which will be compiled ) and a
     *  semi colon to terminate the line..
     *  Once Parse Process is successful , we create a PrintStatement
     *  Object..
     *
     *  if a line does not start with Print or PrintLine ..
     *  an exception is thrown
     */
    private Statement ParsePrintStatement() throws Exception {
        try {
            getNextToken();
            Expression a = Expr();
            if (currentToken != TOKEN.TOK_SEMI_COLON)
            {
                throw new Exception("; is expected");
            }
            return new PrintStatement(a);
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }

    }

    /**
     *  Parse the PrintLine Statement .. The grammar is
     *  PRINTLINE <expr> ;
     *  Once you are in this subroutine , we are expecting
     *  a valid expression ( which will be compiled ) and a
     *  semi colon to terminate the line..
     *  Once Parse Process is successful , we create a PrintLineStatement
     *  Object..
     *
     *  if a line does not start with Print or PrintLine ..
     *  an exception is thrown
     */
    private Statement ParsePrintLNStatement() throws Exception {
        try {
            getNextToken();
            Expression a = Expr();
            if (currentToken != TOKEN.TOK_SEMI_COLON)
            {
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
     */
    private Expression Expr() throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = Term();
            while (currentToken == TOKEN.TOK_PLUS || currentToken == TOKEN.TOK_SUB) {
                last_token = currentToken;
                currentToken = GetToken();
                Expression e1 = Expr();
                RetValue = new BinaryExpression(RetValue, e1, last_token == TOKEN.TOK_PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);
            }
            return RetValue;
        } catch (Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

    /**
     * Gets the Term
     * <Term> ::= <Factor> { * | / } <Term>
     */
    private Expression Term() throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = Factor();
            while (currentToken == TOKEN.TOK_MUL || currentToken == TOKEN.TOK_DIV) {
                last_token = currentToken;
                getNextToken();
                Expression e1 = Term();
                RetValue = new BinaryExpression(RetValue, e1, last_token == TOKEN.TOK_MUL ? OPERATOR.MUL : OPERATOR.DIV);
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
    public Expression Factor() throws Exception {
        try {
            TOKEN last_token;
            Expression RetValue = null;
            if (currentToken == TOKEN.TOK_DOUBLE) {
                RetValue = new NumericConstant(GetNumber());
                getNextToken();
            } else if (currentToken == TOKEN.TOK_OPAREN) {
                getNextToken();
                RetValue = Expr();
                if (currentToken != TOKEN.TOK_CPAREN) {
                    System.out.println("Missing Closing Parenthesis\n");
                    throw new Exception();
                }
                getNextToken();
            } else if (currentToken == TOKEN.TOK_PLUS || currentToken == TOKEN.TOK_SUB) {
                last_token = currentToken;
                getNextToken();
                RetValue = Factor();
                RetValue = new UnaryExpression(RetValue,
                        last_token == TOKEN.TOK_PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);
            } else {
                System.out.println("Illegal Token");
                throw new Exception();
            }
            return RetValue;
        } catch(Exception exception) {
            System.out.println(exception);
            throw exception;
        }
    }

}
