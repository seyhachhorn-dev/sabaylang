package org.sabaylang;

import org.sabaylang.ast.Stmt;
import org.sabaylang.interpreter.Interpreter;
import org.sabaylang.lexer.Lexer;
import org.sabaylang.parser.Parser;
import org.sabaylang.token.Token;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    @PostMapping("/run")
    public RunResult run(@RequestBody RunRequest request) {
        try {
            String source = request.getCode();
            
            Lexer lexer = new Lexer(source);
            List<Token> tokens = lexer.tokenize();
            
            Parser parser = new Parser(tokens);
            List<Stmt> program = parser.parse();
            
            Interpreter interpreter = new Interpreter();
            
            List<String> outputs = new ArrayList<>();
            List<String> errors = new ArrayList<>();
            
            interpreter.setOutputConsumer(outputs::add);
            
            interpreter.interpret(program);
            
            return new RunResult(true, outputs, errors);
            
        } catch (Exception e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            return new RunResult(false, new ArrayList<>(), errors);
        }
    }
    
    public static class RunRequest {
        private String code;
        
        public String getCode() {
            return code;
        }
        
        public void setCode(String code) {
            this.code = code;
        }
    }
    
    public static class RunResult {
        private boolean success;
        private List<String> output;
        private List<String> errors;
        
        public RunResult(boolean success, List<String> output, List<String> errors) {
            this.success = success;
            this.output = output;
            this.errors = errors;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public List<String> getOutput() {
            return output;
        }
        
        public List<String> getErrors() {
            return errors;
        }
    }
}