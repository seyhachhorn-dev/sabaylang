package org.sabaylang.lexer;

import org.sabaylang.token.Token;
import org.sabaylang.token.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Developed by ChhornSeyha
 * Date: 09/04/2026
 */



public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int current = 0;

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> tokenize() {
        while (!isAtEnd()) {
            char c = advance();

            switch (c) {
                case ' ', '\r', '\t', '\n' -> {
                    // ignore whitespace
                }
                case '"' -> string();
                case '+' -> tokens.add(new Token(TokenType.PLUS, "+"));
                case '=' -> {
                    if (peek() == '=') {
                        advance();
                        tokens.add(new Token(TokenType.EQUAL_EQUAL, "=="));
                    } else {
                        tokens.add(new Token(TokenType.EQUAL, "="));
                    }
                }
                case '!' -> {
                    if (peek() == '=') {
                        advance();
                        tokens.add(new Token(TokenType.BANG_EQUAL, "!="));
                    }
                }
                case '>' -> {
                    if (peek() == '=') {
                        advance();
                        tokens.add(new Token(TokenType.GREATER_EQUAL, ">="));
                    } else {
                        tokens.add(new Token(TokenType.GREATER, ">"));
                    }
                }
                case '<' -> {
                    if (peek() == '=') {
                        advance();
                        tokens.add(new Token(TokenType.LESS_EQUAL, "<="));
                    } else {
                        tokens.add(new Token(TokenType.LESS, "<"));
                    }
                }
                case '{' -> tokens.add(new Token(TokenType.LBRACE, "{"));
                case '}' -> tokens.add(new Token(TokenType.RBRACE, "}"));
                case ';' -> tokens.add(new Token(TokenType.SEMICOLON, ";"));
                default -> {
                    if (isDigit(c)) {
                        number(c);
                    } else if (isAlpha(c)) {
                        identifier(c);
                    } else {
                        throw new RuntimeException("Unexpected character: " + c);
                    }
                }

            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private void string() {
        StringBuilder sb = new StringBuilder();
        while (!isAtEnd() && peek() != '"') {
            sb.append(advance());
        }
        if (isAtEnd()) {
            throw new RuntimeException("Unterminated string");
        }
        advance();
        tokens.add(new Token(TokenType.STRING, sb.toString()));
    }







    private void identifier(char first) {
        StringBuilder sb = new StringBuilder();
        sb.append(first);

        while (!isAtEnd() && isAlphaNumeric(peek())) {
            sb.append(advance());
        }

        String text = sb.toString();

        if (text.equals("ហៅ")) {
            tokens.add(new Token(TokenType.ហៅ, text));
        } else if (text.equals("តាង")) {
            tokens.add(new Token(TokenType.តាង, text));
        } else if (text.equals("ចំនួន")) {
            tokens.add(new Token(TokenType.ចំនួន, text));
        } else if (text.equals("បើសិន")) {
            tokens.add(new Token(TokenType.បើសិន, text));
        } else if (text.equals("ម្យ៉ាងវិញ")) {
            tokens.add(new Token(TokenType.ម្យ៉ាងវិញ, text));
        } else if (text.equals("ផ្សេង")) {
            tokens.add(new Token(TokenType.ផ្សេង, text));
        } else {
            tokens.add(new Token(TokenType.IDENTIFIER, text));
        }
    }

    private void number(char first){
        StringBuilder sb = new StringBuilder();
        sb.append(first);

        boolean hasDecimal = false;
        while (!isAtEnd() && (isDigit(peek()) || peek() == '.')){
            if (peek() == '.') {
                if (hasDecimal) break;
                hasDecimal = true;
            }
            sb.append(advance());
        }
        tokens.add(new Token(TokenType.NUMBER, sb.toString()));
    }




    private boolean isAtEnd(){
        return  current >= source.length();
    }
    private char advance(){
        return source.charAt(current++);
    }
    private boolean isDigit(char c){
        return c >='0' && c<= '9';
    }
    private char peek(){
        return source.charAt(current);
    }

    private boolean isAlpha(char c){
        return (c >= 'a' && c <='z' || c>='A' && c<='Z' || c == '_' || (c >= '\u1780' && c <= '\u17FF'));
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }


}


//store num


