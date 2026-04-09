package org.sabaylang.parser;
import org.sabaylang.ast.Expr;
import org.sabaylang.ast.Stmt;
import org.sabaylang.token.Token;
import org.sabaylang.token.TokenType;
import java.util.ArrayList;
import java.util.List;

/**
 * Developed by ChhornSeyha
 * Date: 09/04/2026
 */


public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();

        while (!isAtEnd()) {
            statements.add(statement());
        }

        return statements;
    }

    private Stmt statement() {
        if (match(TokenType.ហៅ)) {
            Expr expr = expression();
            consume(TokenType.SEMICOLON, "Expected ';' or '។' after print statement.");
            return new Stmt.PrintStmt(expr);
        }

        if (match(TokenType.តាង)) {
            if (check(TokenType.ចំនួន)) {
                advance();
            }
            String varName = consume(TokenType.IDENTIFIER, "Expected variable name.").getLexerName();
            consume(TokenType.EQUAL, "Expected '=' after variable name.");
            Expr initializer = expression();
            consume(TokenType.SEMICOLON, "Expected ';' or '។' after variable declaration.");
            return new Stmt.VarDeclStmt(varName, initializer, null);
        }

        if (match(TokenType.បើសិន)) {
            List<Stmt.IfBranch> branches = new ArrayList<>();
            
            Expr condition = expression();
            List<Stmt> thenBranch = block();
            branches.add(new Stmt.IfBranch(condition, thenBranch));
            
            while (check(TokenType.ម្យ៉ាងវិញ)) {
                advance();
                if (check(TokenType.បើសិន)) {
                    advance();
                    Expr elseifCondition = expression();
                    List<Stmt> elseifBody = block();
                    branches.add(new Stmt.IfBranch(elseifCondition, elseifBody));
                } else if (check(TokenType.ផ្សេង)) {
                    // This case is for 'else' without 'if'
                    break;
                } else {
                    throw error("Expected 'if' or 'else' after 'else if'");
                }
            }
            
            List<Stmt> elseBranch = null;
            if (match(TokenType.ផ្សេង)) {
                elseBranch = block();
            }
            
            return new Stmt.IfStmt(branches, elseBranch);
        }

        throw error("Expected statement.");
    }

    private List<Stmt> block() {
        consume(TokenType.LBRACE, "Expected '{' before block.");
        List<Stmt> statements = new ArrayList<>();
        while (!check(TokenType.RBRACE) && !isAtEnd()) {
            statements.add(statement());
        }
        consume(TokenType.RBRACE, "Expected '}' after block.");
        return statements;
    }

    private Expr expression() {
        return addition();
    }

    private Expr addition() {
        Expr expr = multiplication();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Expr right = multiplication();
            expr = new Expr.BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr multiplication() {
        Expr expr = comparison();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr comparison() {
        Expr expr = primary();

        while (match(TokenType.EQUAL_EQUAL, TokenType.BANG_EQUAL,
               TokenType.GREATER, TokenType.GREATER_EQUAL,
               TokenType.LESS, TokenType.LESS_EQUAL)) {
            Token operator = previous();
            Expr right = primary();
            expr = new Expr.BinaryExpr(expr, operator, right);
        }

        return expr;
    }

    private Expr primary() {
        if (match(TokenType.NUMBER)) {
            String numStr = previous().getLexerName();
            if (numStr.contains(".")) {
                double value = Double.parseDouble(numStr);
                return new Expr.NumberExpr(value);
            } else {
                int value = Integer.parseInt(numStr);
                return new Expr.NumberExpr(value);
            }
        }

        if (match(TokenType.STRING)) {
            return new Expr.StringExpr(previous().getLexerName());
        }

        if (match(TokenType.IDENTIFIER)) {
            return new Expr.VariableExpr(previous().getLexerName());
        }

        throw error("Expected number, string, or identifier.");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) {
            return advance();
        }
        throw error(message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return type == TokenType.EOF;
        }
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private RuntimeException error(String message) {
        return new RuntimeException("Parse error near token " + peek() + ": " + message);
    }
}