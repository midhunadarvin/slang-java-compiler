package com.slang.compiler.parser;

public enum TOKEN {
    ILLEGAL_TOKEN("-1"), // Not a Token
    TOK_PLUS("+"), // '+'
    TOK_MUL("*"), // '*'
    TOK_DIV("/"), // '/'
    TOK_SUB("-"), // '-'
    TOK_OPAREN("("), // '('
    TOK_CPAREN(")"), // ')'
    TOK_DOUBLE("D"), // '('
    TOK_NULL(null); // End of string

    private final String token;
    TOKEN(String token) { this.token = token; }
    public String getValue() { return token; }
}
