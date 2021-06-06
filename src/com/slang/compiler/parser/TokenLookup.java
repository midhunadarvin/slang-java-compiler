package com.slang.compiler.parser;

import java.util.HashMap;

public class TokenLookup {
    private static HashMap tokenHashMap = new HashMap<String, TOKEN>();
    static {
        tokenHashMap.put("PRINT", TOKEN.TOK_PRINT);
        tokenHashMap.put("PRINTLINE", TOKEN.TOK_PRINTLN);
        tokenHashMap.put("FALSE", TOKEN.TOK_BOOL_FALSE);
        tokenHashMap.put("TRUE", TOKEN.TOK_BOOL_TRUE);
        tokenHashMap.put("STRING", TOKEN.TOK_VAR_STRING);
        tokenHashMap.put("BOOLEAN", TOKEN.TOK_VAR_BOOL);
        tokenHashMap.put("NUMERIC", TOKEN.TOK_VAR_NUMBER);
        // -------------- To support control structures
        tokenHashMap.put("IF", TOKEN.TOK_IF);

        tokenHashMap.put("WHILE", TOKEN.TOK_WHILE);
        tokenHashMap.put("WEND", TOKEN.TOK_WHILE_END);
        tokenHashMap.put("ELSE", TOKEN.TOK_ELSE);
        tokenHashMap.put("ENDIF", TOKEN.TOK_END_IF);
        tokenHashMap.put("THEN", TOKEN.TOK_THEN);
    }
    public static TOKEN getToken(String key) {
        if (tokenHashMap.get(key) != null) {
            return (TOKEN) tokenHashMap.get(key);
        } else {
            return TOKEN.UNKNOWN;
        }
    }
}
