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
    }
    public static TOKEN getToken(String key) {
        if (tokenHashMap.get(key) != null) {
            return (TOKEN) tokenHashMap.get(key);
        } else {
            return TOKEN.UNKNOWN;
        }
    }
}
