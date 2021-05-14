package com.slang.compiler.parser;

import java.util.Locale;

/**
 * A naive Lexical analyzer which looks for operators , Parenthesis
 * and number. All numbers are treated as IEEE doubles. Only numbers
 * without decimals can be entered. Feel free to modify the code
 * to accommodate LONG and Double values
 *
 * @author Midhun A Darvin
 * @version 1.0
 */
public class Lexer {
    String IExpr;  // Expression string
    int index;     // index into a character
    int length;    // Length of the string
    double number; // Last grabbed number from the stream
    private String variableName;

    /**
     * Constructor
     */
    public Lexer(String Expr) {
        IExpr = Expr;
        length = IExpr.length();
        index = 0;
    }

    private boolean isNotEndOfInput() {
        return index < length;
    }

    /**
     * Grab the next token from the stream
     */
    public TOKEN GetToken() throws Exception {
        TOKEN tok = TOKEN.ILLEGAL_TOKEN;

        /* Skip the white space */
        while (index < length && (IExpr.charAt(index) == ' ' || IExpr.charAt(index) == '\t' || IExpr.charAt(index) == '\r' || IExpr.charAt(index) == '\n'))
            index++;

        /* End of string ? return NULL; */
        if (index == length)
            return TOKEN.TOK_NULL;

        switch (IExpr.charAt(index)) {
            case '+':
                tok = TOKEN.TOK_PLUS;
                index++;
                break;
            case '-':
                tok = TOKEN.TOK_SUB;
                index++;
                break;
            case '/':
                tok = TOKEN.TOK_DIV;
                index++;
                break;
            case '*':
                tok = TOKEN.TOK_MUL;
                index++;
                break;
            case '(':
                tok = TOKEN.TOK_OPAREN;
                index++;
                break;
            case ')':
                tok = TOKEN.TOK_CPAREN;
                index++;
                break;
            case ';':
                tok = TOKEN.TOK_SEMI_COLON;
                index++;
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {
                String str = "";
                while (index < length &&
                        (IExpr.charAt(index) == '0' ||
                                IExpr.charAt(index) == '1' ||
                                IExpr.charAt(index) == '2' ||
                                IExpr.charAt(index) == '3' ||
                                IExpr.charAt(index) == '4' ||
                                IExpr.charAt(index) == '5' ||
                                IExpr.charAt(index) == '6' ||
                                IExpr.charAt(index) == '7' ||
                                IExpr.charAt(index) == '8' ||
                                IExpr.charAt(index) == '9')) {
                    str += IExpr.charAt(index);
                    index++;
                }
                number = Double.parseDouble(str);
                tok = TOKEN.TOK_DOUBLE;
            }
            break;
            default:
                String keyword = readKeyWord();
                TOKEN tempToken = TokenLookup.getToken(keyword.toLowerCase());
                if(TOKEN.UNKNOWN == tempToken) {
                    variableName = keyword;
                    tok = TOKEN.VAR_NAME;
                } else if(TOKEN.VAR == tempToken) {
                    //Handling reserved getType on variable name
                    variableName = keyword;
                    tok = TOKEN.VAR;
                } else {
                    tok = tempToken;
                }
        }
        return tok;
    }

    private String readKeyWord() {
        StringBuilder keyWordBuilder = new StringBuilder();
        //Iterating till end of module
        while (isNotEndOfInput()) {
            char c = IExpr.charAt(index);

            if(Character.isAlphabetic(c) || (keyWordBuilder.length() > 0 && Character.isDigit(c))) {
                keyWordBuilder.append(c);
            } else {
                break;
            }
            index++;
        }
        return keyWordBuilder.toString();
    }

    public double GetNumber() { return number; }
}
