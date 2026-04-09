# 🚀 SabayLang - Khmer Programming Language

**⚠️ Disclaimer:** This is a fun project for learning and entertainment purposes only! Not for production use! 

---

## 🌟 About SabayLang

SabayLang is a programming language that allows you to write code in Khmer! It was created as a fun project to learn about programming language implementation.

### ✨ Features
- 🔤 Variable Declaration
- 🔢 Math & Comparison Operators
- 🔀 If/Else If/Else Conditions
- 🎯 Print/Output
- 📝 Web IDE 

---

## 🛠️ How to Run

```bash
# Clone the project
git clone https://github.com/your-repo/sabay-lang.git
cd sabay-lang

# Run the IDE
./gradlew bootRun
```

**Open in browser:** http://localhost:8080

---

## 📚 Keywords & Syntax

| Khmer | English | Meaning |
|-------|---------|---------|
| តាង | var/let | Declare variable |
| ចំនួន | number | Number type (int/double) |
| ហៅ | print | Output/Print |
| បើសិន | if | If condition |
| ម្យ៉ាងវិញ | else if | Else if |
| ផ្សេង | else | Else |

---

## 💻 Code Examples

### 1. Variables & Print
```khmer
តាង ចំនួន ក = 10;
តាង ចំនួន ខ = 20;
តាង ចំនួន គ = ក + ខ;
ហៅ គ;
```
**Output:** `30`

### 2. Comparison
```khmer
តាង ចំនួន ក = 15;
បើសិន ក >= 18 {
    ហៅ "Age OK";
}
ផ្សេង {
    ហៅ "Age Not OK";
}
```
**Output:** `Age Not OK`

### 3. If - Else If - Else
```khmer
តាង ចំនួន ក = 75;
បើសិន ក >= 90 {
    ហៅ "A";
}
ម្យ៉ាងវិញ បើសិន ក >= 80 {
    ហៅ "B";
}
ម្យ៉ាងវិញ បើសិន ក >= 70 {
    ហៅ "C";
}
ផ្សេង {
    ហៅ "F";
}
```
**Output:** `C`

### 4. Double (Decimal)
```khmer
តាង ចំនួន ក = 3.14;
តាង ចំនួន ខ = 2.5;
តាង ចំនួន គ = ក * ខ;
ហៅ គ;
```
**Output:** `7.85`

### 5. String
```khmer
ហៅ "សួស្តីប្រទេសកម្ពុជា!";
```
**Output:** `សួស្តីប្រទេសកម្ពុជា!`

---

## 🎮 Project Structure

```
app/
├── src/main/java/org/sabaylang/
│   ├── lexer/          → Lexer (Tokenization)
│   ├── parser/         → Parser (Syntax Analysis)
│   ├── interpreter/    → Interpreter (Execution)
│   ├── ast/            → AST (Abstract Syntax Tree)
│   ├── token/          → Token Types
│   ├── ApiController.java  → REST API
│   └── SabayLangApplication.java
└── src/main/resources/
    └── static/index.html  → Web IDE
```

---

## 📝 Notes

- Every statement must end with `។`
- Use `{}` for code blocks
- Number type is auto-detected (Integer for whole numbers, Double for decimals)
- Variable names can use Khmer characters (like ក, ខ, គ, etc.)

---

## 📝 License

MIT License - Use it for fun! 

---

