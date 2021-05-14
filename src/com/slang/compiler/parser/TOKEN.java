package com.slang.compiler.parser;

public enum TOKEN {
    ILLEGAL_TOKEN, // Not a Token
    TOK_PLUS, // '+'
    TOK_MUL, // '*'
    TOK_DIV, // '/'
    TOK_SUB, // '-'
    TOK_OPAREN, // '('
    TOK_CPAREN, // ')'
    TOK_DOUBLE, // '('
    TOK_PRINT, // Print Statement
    TOK_PRINTLN, // PrintLine
    TOK_SEMI_COLON, // Semi colon
    UNKNOWN, // Unknown
    TOK_NULL,// End of string
    VAR,
    VAR_NAME;
}
