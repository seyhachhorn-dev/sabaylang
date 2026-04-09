package org.sabaylang.ast;

import org.sabaylang.token.TokenType;

import java.util.List;

public abstract class Stmt {
    public static class PrintStmt extends Stmt {
        private final Expr expression;

        public PrintStmt(Expr expression) {
            this.expression = expression;
        }

        public Expr getExpression() {
            return expression;
        }
    }

    public static class VarDeclStmt extends Stmt {
        private final String name;
        private final Expr initializer;
        private final TokenType type;

        public VarDeclStmt(String name, Expr initializer, TokenType type) {
            this.name = name;
            this.initializer = initializer;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public Expr getInitializer() {
            return initializer;
        }

        public TokenType getType() {
            return type;
        }
    }

    public static class IfBranch {
        private final Expr condition;
        private final List<Stmt> body;

        public IfBranch(Expr condition, List<Stmt> body) {
            this.condition = condition;
            this.body = body;
        }

        public Expr getCondition() {
            return condition;
        }

        public List<Stmt> getBody() {
            return body;
        }
    }

    public static class IfStmt extends Stmt {
        private final List<IfBranch> branches;
        private final List<Stmt> elseBranch;

        public IfStmt(List<IfBranch> branches, List<Stmt> elseBranch) {
            this.branches = branches;
            this.elseBranch = elseBranch;
        }

        public List<IfBranch> getBranches() {
            return branches;
        }

        public List<Stmt> getElseBranch() {
            return elseBranch;
        }
    }
}