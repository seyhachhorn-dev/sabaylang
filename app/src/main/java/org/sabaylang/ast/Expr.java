package org.sabaylang.ast;

import org.sabaylang.token.Token;

/**
 * Developed by ChhornSeyha
 * Date: 09/04/2026
 */

public abstract class Expr {

    public static class NumberExpr extends Expr {


        private final Number value;

        public NumberExpr(Number value) {
            this.value = value;
        }

        public Number getValue() {
            return value;
        }
    }
    public static class BinaryExpr extends Expr {
        private final Expr left;
        private final Token operator;
        private final Expr right;

        public BinaryExpr(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        public Expr getLeft() {
            return left;
        }

        public Token getOperator() {
            return operator;
        }

        public Expr getRight() {
            return right;
        }
    }

    public static class VariableExpr extends Expr {
        private final String name;

        public VariableExpr(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class StringExpr extends Expr {
        private final String value;

        public StringExpr(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
