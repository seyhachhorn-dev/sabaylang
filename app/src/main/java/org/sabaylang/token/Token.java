package org.sabaylang.token;

/**
 * Developed by ChhornSeyha
 * Date: 06/04/2026
 */

public class Token {
    private final TokenType type;
    private final String lexerName;


    public Token(TokenType type, String lexerName) {
        this.type = type;
        this.lexerName = lexerName;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexerName() {
        return lexerName;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexerName='" + lexerName + '\'' +
                '}';
    }
}
