package com.slang.compiler.parser;

import java.util.HashMap;

public class TokenLookup {
    private static HashMap tokenHashMap = new HashMap<String, TOKEN>();
    static {
        tokenHashMap.put("print", TOKEN.TOK_PRINT);
        tokenHashMap.put("printline", TOKEN.TOK_PRINTLN);
    }
    public static TOKEN getToken(String key) {
        if (tokenHashMap.get(key) != null) {
            return (TOKEN) tokenHashMap.get(key);
        } else {
            return TOKEN.UNKNOWN;
        }
    }
}
