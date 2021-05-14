package com.slang.compiler.parser;

import com.slang.compiler.ast.*;

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
    private TOKEN Current_Token;

    /**
     * Constructor
     *
     * @param Expr - Expression String
     */
    public RDParser(String Expr) {
        super(Expr);
    }

    /**
     * Call Expression method - This method gets the Expression from the
     * string via lexical analysis and by Recursive Descent parsing
     */
    public Expression CallExpr() throws Exception {
        try {
            Current_Token = this.GetToken();
            return Expr();
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
            while (Current_Token == TOKEN.TOK_PLUS || Current_Token == TOKEN.TOK_SUB) {
                last_token = Current_Token;
                Current_Token = GetToken();
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
            while (Current_Token == TOKEN.TOK_MUL || Current_Token == TOKEN.TOK_DIV) {
                last_token = Current_Token;
                Current_Token = GetToken();
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
            if (Current_Token == TOKEN.TOK_DOUBLE) {
                RetValue = new NumericConstant(GetNumber());
                Current_Token = GetToken();
            } else if (Current_Token == TOKEN.TOK_OPAREN) {
                Current_Token = GetToken();
                RetValue = Expr();
                if (Current_Token != TOKEN.TOK_CPAREN) {
                    System.out.println("Missing Closing Parenthesis\n");
                    throw new Exception();
                }
                Current_Token = GetToken();
            } else if (Current_Token == TOKEN.TOK_PLUS || Current_Token == TOKEN.TOK_SUB) {
                last_token = Current_Token;
                Current_Token = GetToken();
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
