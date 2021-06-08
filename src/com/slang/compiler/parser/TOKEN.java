package com.slang.compiler.parser;

public enum TOKEN {
    ILLEGAL_TOKEN, // Not a Token
    TOK_PLUS, // '+'
    TOK_MUL, // '*'
    TOK_DIV, // '/'
    TOK_SUB, // '-'
    TOK_OPAREN, // '('
    TOK_CPAREN, // ')'
    TOK_PRINT, // Print Statement
    TOK_PRINTLN, // PrintLine
    TOK_SEMI_COLON, // Semi colon
    UNKNOWN, // Unknown
    TOK_NULL,// End of string
    VAR,
    VAR_NAME,
    TOK_UNQUOTED_STRING,
    TOK_VAR_NUMBER, // NUMBER data type
    TOK_VAR_STRING, // STRING data type
    TOK_VAR_BOOL, // Bool data type
    TOK_NUMERIC, // [0-9]+
    TOK_COMMENT, // Comment Token ( presently not used )
    TOK_BOOL_TRUE, // Boolean TRUE
    TOK_BOOL_FALSE, // Boolean FALSE
    TOK_STRING, // String Literal
    TOK_ASSIGN, // Assignment Symbol =

    // Relational operator support
    TOK_EQUALS, // '=='
    TOK_NOT_EQUALS, // '<>'
    TOK_GREATER_THAN, // '>'
    TOK_GREATER_OR_EQUAL, // '>='
    TOK_LESS_THAN, // '<'
    TOK_LESS_OR_EQUAL, // '<='
    TOK_AND, // '&&'
    TOK_OR, // '||'
    TOK_NOT, // '!'

    // Control structures support
    TOK_IF, // IF
    TOK_THEN, // Then
    TOK_ELSE, // Else Statement
    TOK_END_IF, // Endif Statement
    TOK_WHILE, // WHILE
    TOK_WHILE_END // While end Statement
}
