package org.sabaylang.interpreter;
import org.sabaylang.ast.Expr;
import org.sabaylang.ast.Stmt;
import org.sabaylang.token.TokenType;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


/**
 * Developed by ChhornSeyha
 * Date: 09/04/2026
 */


public class Interpreter {
    private final Map<String, Object> variables = new HashMap<>();
    private Consumer<String> outputConsumer;

    public void setOutputConsumer(Consumer<String> consumer) {
        this.outputConsumer = consumer;
    }

    public void interpret(List<Stmt> statements) {
        for (Stmt stmt : statements) {
            execute(stmt);
        }
    }

    private void execute(Stmt stmt) {
        if (stmt instanceof Stmt.VarDeclStmt varDecl) {
            Object value = evaluate(varDecl.getInitializer());
            variables.put(varDecl.getName(), value);
            return;
        }

        if (stmt instanceof Stmt.PrintStmt printStmt) {
            Object value = evaluate(printStmt.getExpression());
            String output = (value == null) ? "null" : String.valueOf(value);
            if (outputConsumer != null) {
                outputConsumer.accept(output);
            } else {
                System.out.println(output);
            }
            return;
        }

        if (stmt instanceof Stmt.IfStmt ifStmt) {
            for (Stmt.IfBranch branch : ifStmt.getBranches()) {
                if (isTruthy(evaluate(branch.getCondition()))) {
                    for (Stmt s : branch.getBody()) {
                        execute(s);
                    }
                    return;
                }
            }
            if (ifStmt.getElseBranch() != null) {
                for (Stmt s : ifStmt.getElseBranch()) {
                    execute(s);
                }
            }
            return;
        }

        throw new RuntimeException("Unknown statement: " + stmt.getClass().getSimpleName());
    }

    private boolean isTruthy(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue() != 0;
        }
        return true;
    }

    private Object evaluate(Expr expr) {
        if (expr instanceof Expr.NumberExpr numberExpr) {
            return numberExpr.getValue();
        }

        if (expr instanceof Expr.StringExpr stringExpr) {
            return stringExpr.getValue();
        }

        if (expr instanceof Expr.VariableExpr varExpr) {
            Object value = variables.get(varExpr.getName());
            if (value == null && !variables.containsKey(varExpr.getName())) {
                throw new RuntimeException("Undefined variable: " + varExpr.getName());
            }
            return value;
        }

        if (expr instanceof Expr.BinaryExpr binaryExpr) {
            Object left = evaluate(binaryExpr.getLeft());
            Object right = evaluate(binaryExpr.getRight());
            TokenType operatorType = binaryExpr.getOperator().getType();

            switch (operatorType) {
                case PLUS -> {
                    if (left instanceof String || right instanceof String) {
                        return String.valueOf(left) + String.valueOf(right);
                    }
                    if (left instanceof Double || right instanceof Double) {
                        return toDouble(left) + toDouble(right);
                    }
                    return (Integer) left + (Integer) right;
                }
                case MINUS -> {
                    return toDouble(left) - toDouble(right);
                }
                case STAR -> {
                    return toDouble(left) * toDouble(right);
                }
                case SLASH -> {
                    double r = toDouble(right);
                    if (r == 0) throw new RuntimeException("Division by zero.");
                    return toDouble(left) / r;
                }
                case EQUAL_EQUAL -> {
                    return equals(left, right);
                }
                case BANG_EQUAL -> {
                    return !equals(left, right);
                }
                case GREATER -> {
                    return toDouble(left) > toDouble(right);
                }
                case GREATER_EQUAL -> {
                    return toDouble(left) >= toDouble(right);
                }
                case LESS -> {
                    return toDouble(left) < toDouble(right);
                }
                case LESS_EQUAL -> {
                    return toDouble(left) <= toDouble(right);
                }
            }
        }

        throw new RuntimeException("Unknown expression: " + expr.getClass().getSimpleName());
    }

    private boolean equals(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;
        if (a instanceof Double || b instanceof Double) {
            return toDouble(a) == toDouble(b);
        }
        return a.equals(b);
    }

    private double toDouble(Object value) {
        if (value instanceof Double) return (Double) value;
        if (value instanceof Integer) return ((Integer) value).doubleValue();
        throw new RuntimeException("Operand must be a number.");
    }

    @FunctionalInterface
    public interface Consumer<T> {
        void accept(T t);
    }
}
