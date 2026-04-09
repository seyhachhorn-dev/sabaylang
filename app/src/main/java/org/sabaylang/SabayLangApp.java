
package org.sabaylang;

import org.sabaylang.ast.Stmt;
import org.sabaylang.interpreter.Interpreter;
import org.sabaylang.lexer.Lexer;
import org.sabaylang.parser.Parser;
import org.sabaylang.token.Token;

import java.util.List;

public class SabayLangApp {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        String source = """
                តាង ចំនួន ក = 10;
                តាង ចំនួន ខ = 3.14;
                តាង ចំនួន គ = 5;
                ហៅ ក;
                ហៅ ខ;
                ហៅ គ;
                """;

        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.tokenize();

        System.out.println("=== TOKENS ===");
        for (Token token : tokens) {
            System.out.println(token);
        }

        Parser parser = new Parser(tokens);
        List<Stmt> program = parser.parse();

        System.out.println("=== OUTPUT ===");
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(program);
    }

    }

