# Expression evaluator

Expression evaluator using lexical analyses and LR parsing. The project is made without the help of compiler compilers like yacc or lex.

# Basics

The program takes an arithmetic expression as a string, identifies its tokens and emits a parse tree. Also, it calculates the result of the expressions. Numbers must be in the standard floating-point number format, and there are 5 operators in order of increasing precedence:
- '+' : dyadic, left-associative infix operator
- '-' : dyadic, left-associative infix operator
- '*' : dyadic, right-associative infix operator
- 'cos' : monadic prefix operator
- '!' : monadic postfix operator

Also, the expression may contain parentheses that have precedence over all operators.
The evaluate function calculates the result based on the "standard" meaning of these operators (e.g '+' stands for addition). The only exception is '!', which stands for exponentiation with base e. Note that the main goal is to create a parsing tree that has more general uses (compilers, for example) than the concrete goal of this project.

# Lexical Analyser

The lexical analyser works based on a Finite State Machine with slightly modified rules. The states are generated from the names of the operators merged with a FSM specified to recognise floating-point numbers. The lexer is designed in such a way that adding extra operators is easy provided that no two operators have common prefixes. A feature of the lexer is that unlike many lexers in use, it can distinguish between the subtraction operator and the minus sign of a negative number. As it involves some logical analysis of the input, the lexer rejects some lexically valid but logically invalid (unparsable) inputs.

# Parser

Given the list of tokens generated by the lexer, the parser generates parse trees. The parser uses an SLR(1) technique. The code generates the SLR(1) machine from the Rules defined in Rule.java In Rule.java, any context-free grammar can be specified, but the code will work properly for SLR(1) languages only. The current rules are:
- 𝐴 → 𝐵 | 𝐴 + 𝐵 (expression for sum)
- 𝐵 → 𝐶 | 𝐵 − 𝐶 (expression for difference)
- 𝐶 → 𝐷 | 𝐷 ∗ 𝐶 (product)
- 𝐷 → 𝑐𝑜𝑠 D | 𝐸
- 𝐸 → 𝐹 | E!
- 𝐹 → 𝑖𝑑 | (𝐴)

Where the parser tries to match A.

# Use

In Main.java, type an expression following the rules. The code will either sends an error message if the expression is invalid or prints the parse tree and the value of the expression. The parse tree is printed in format: root[child_1][child_2]...[clild_n].
