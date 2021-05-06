/*
 * Copyright 2018 Udo Klimaschewski
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * THE HEREIN CODE WAS EXTRACTED FROM https://github.com/uklimaschewski/EvalEx
 * AND MODIFIED (https://github.com/luiz-resende/EvalEx/tree/postfix-unary-operator)
 * TO FIT THE CURRENT USE. THE MODIFICATIONS AND ADDITIONS WERE SUBMITTED TO THE
 * REPOSITORY'S AUTHOR FOR HIS APPRECIATION AND LATER INCLUSION ON THE RELEASE VERSION.
 *
 */

package com.lars.calculatorunitconverter.ui.calculator.evalex;


import com.lars.calculatorunitconverter.ui.calculator.evalex.functions.Function;
import com.lars.calculatorunitconverter.ui.calculator.evalex.lazynumber.LazyNumber;
import com.lars.calculatorunitconverter.ui.calculator.evalex.operators.Operator;
import com.lars.calculatorunitconverter.ui.calculator.evalex.operators.UnaryOperator;
import com.lars.calculatorunitconverter.ui.calculator.evalex.tokenization.Token;
import com.lars.calculatorunitconverter.ui.calculator.evalex.tokenization.TokenOperator;
import com.lars.calculatorunitconverter.ui.calculator.evalex.tokenization.Tokenizer;
import com.lars.calculatorunitconverter.ui.calculator.evalex.variables.Variable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;


@SuppressWarnings({"Convert2Diamond", "NullableProblems", "InnerClassMayBeStatic", "FieldMayBeFinal", "UnusedReturnValue", "UnnecessaryLocalVariable", "UnpredictableBigDecimalConstructorCall", "RedundantCast", "unused", "RedundantSuppression"})
public class Expression {


    /**
     * The original infix expression.
     */
    private String originalExpression;
    /**
     * The current infix expression, with optional variable substitutions.
     */
    private String expressionString;
    /**
     * Boolean primitive to allow or not implicit multiplication of type '2sin(90)'
     */
    private boolean implicitMultiplication;
    /**
     * The cached RPN (Reverse Polish Notation) of the expression.
     */
    private List<Token> rpn = null;

    /**
     * All defined operators with name and implementation.
     */
    public Map<String, Operator> operators = new TreeMap<String, Operator>(String.CASE_INSENSITIVE_ORDER);
    /**
     * All defined functions with name and implementation.
     */
    public Map<String, Function> functions = new TreeMap<String, Function>(String.CASE_INSENSITIVE_ORDER);
    /**
     * All defined variables with name and value.
     */
    public Map<String, LazyNumber> variables = new TreeMap<String, LazyNumber>(String.CASE_INSENSITIVE_ORDER);

    /**
     * The {@link MathContext} to use for calculations.
     */
    private MathContext mc;

    /**
     * The precedence of the power (^) operator. Default is 40.
     */
    public static int powerOperatorPrecedence = Operator.OPERATOR_PRECEDENCE_POWER;



    /**
     * The BigDecimal representation of the left parentheses, used for parsing varying numbers of function parameters.
     */
    private static final LazyNumber PARAMS_START = new LazyNumber() {
        public BigDecimal eval() {
            return null;
        }

        public String getString() {
            return null;
        }
    };


    /**
     * The expression evaluators exception class.
     */
    public static class ExpressionException extends RuntimeException {

        private static final long serialVersionUID = 1118142866870779047L;

        public ExpressionException(String message) {
            super(message);
        }

        public ExpressionException(String message, int characterPosition) {
            super(message + " at character position " + characterPosition);
        }
    }



    /**
     * Construct a LazyNumber from a BigDecimal
     */
    protected LazyNumber createLazyNumber(final BigDecimal bigDecimal) {
        return new LazyNumber() {
            @Override
            public String getString() {
                return bigDecimal.toPlainString();
            }

            @Override
            public BigDecimal eval() {
                return bigDecimal;
            }
        };
    }



    /**
     * Creates a new expression instance from an expression string with a given default match context
     * of {@link MathContext#DECIMAL32}.
     *
     * @param expression The expression. E.g. <code>"2.4*sin(3)/(2-4)"</code> or
     *                   <code>"sin(y)>0 & max(z, 3)>3"</code>
     */
    public Expression(String expression) {
        this(expression, MathContext.DECIMAL32);
    }


    /**
     * Creates a new expression instance from an expression string with a given default match
     * context.
     *
     * @param expression         The expression. E.g. <code>"2.4*sin(3)/(2-4)"</code> or
     *                           <code>"sin(y)>0 & max(z, 3)>3"</code>
     * @param defaultMathContext The {@link MathContext} to use by default.
     */
    public Expression(String expression, MathContext defaultMathContext) {
        this(expression, ExpressionSettings
                .builder()
                .mathContext(defaultMathContext)
                .build());
    }



    /* CODE MODIFICATION:
     * The number of operands was added to all predefined operators following AbstractOperator class modification
     * and the Operator, Function, Token and Tokenizer classes were divided into separate files. The default operators,
     * default functions and default variables were also moved to a separate <code>.java</code> file.*/
    /**
     * Creates a new expression instance from an expression string with given settings.
     *
     * @param expression         The expression. E.g. <code>"2.4*sin(3)/(2-4)"</code> or
     *                           <code>"sin(y)>0 & max(z, 3)>3"</code>
     * @param expressionSettings The {@link ExpressionSettings} to use by default.
     */
    @SuppressWarnings("static-access")
    public Expression(String expression, ExpressionSettings expressionSettings) { // NOSONAR- cognitive complexity
        this.mc = expressionSettings.getMathContext();
        this.powerOperatorPrecedence = expressionSettings.getPowerOperatorPrecedence();
        this.implicitMultiplication = expressionSettings.getImplicitMulti();
        this.expressionString = expression;
        this.originalExpression = expression;

        /* Retrieving all predefined operators, functions and variables. */
        addOperators(Operations.getBuiltInOperators(mc));
        addFunctions(Operations.getBuiltInFunctions(mc));
        addVariables(Operations.getBuiltInVariables(mc));
    }



    /**
     * Implementation of the <i>Shunting Yard</i> algorithm to transform an infix expression to a RPN
     * expression.
     *
     * @param expression	The input {@link String} expression in infix.
     *
     * @return	A RPN representation of the expression, with each token as a list member.
     */
    private List<Token> shuntingYard(String expression) {

        List<Token> outputQueue = new ArrayList<Token>();
        Stack<Token> stack = new Stack<Token>(); // NOSONAR - Stack is needed here

        Tokenizer tokenizer = new Tokenizer(expression, this.operators, this.functions, this.variables);

        Token lastFunction = null;
        Token previousToken = null;
        while (tokenizer.hasNext()) {

            Token token = tokenizer.next();
            switch (token.type) {
                case TOKEN_TYPE_STRINGPARAM:
                    stack.push(token);
                    break;
                case TOKEN_TYPE_LITERAL:
                case TOKEN_TYPE_HEX_LITERAL:
                    if (previousToken != null && (previousToken.type == Token.TokenType.TOKEN_TYPE_LITERAL
                            || previousToken.type == Token.TokenType.TOKEN_TYPE_HEX_LITERAL)) {
                        throw new ExpressionException(Operator.MISSING_OPERATOR, token.pos);
                    }
                    outputQueue.add(token);
                    break;
                case TOKEN_TYPE_VARIABLE:
                    if (previousToken != null) {
                        /* CODE MODIFICATION:
                         * Added implicit multiplication between number/variables, e.g. '2PI' */
                        if (this.implicitMultiplication
                                && previousToken.type == Token.TokenType.TOKEN_TYPE_LITERAL
                                || previousToken.type == Token.TokenType.TOKEN_TYPE_PAREN_CLOSE
                                || previousToken.type == Token.TokenType.TOKEN_TYPE_VARIABLE
                                || previousToken.type == Token.TokenType.TOKEN_TYPE_HEX_LITERAL) {
                            Token multiplication = (Token)new TokenOperator(Operations.getBuiltInOperators("*", Operator.OPERATOR_PRECEDENCE_POWER));
                            stack.push(multiplication);
                        }
                    }
                    //stack.push(token);
                    outputQueue.add(token);
                    break;
                case TOKEN_TYPE_FUNCTION:
                    if (previousToken != null) {
                        /* CODE MODIFICATION:
                         * Added implicit multiplication between number/variables and functions, e.g. '2sin(90)' */
                        if (this.implicitMultiplication
                                && previousToken.type == Token.TokenType.TOKEN_TYPE_LITERAL
                                || previousToken.type == Token.TokenType.TOKEN_TYPE_PAREN_CLOSE
                                || previousToken.type == Token.TokenType.TOKEN_TYPE_VARIABLE
                                || previousToken.type == Token.TokenType.TOKEN_TYPE_HEX_LITERAL) {
                            Token multiplication = (Token)new TokenOperator(Operations.getBuiltInOperators("*", Operator.OPERATOR_PRECEDENCE_POWER));
                            stack.push(multiplication);
                        }
                    }
                    stack.push(token);
                    lastFunction = token;
                    break;
                case TOKEN_TYPE_COMMA:
                    if (previousToken != null && previousToken.type == Token.TokenType.TOKEN_TYPE_OPERATOR) {
                        throw new ExpressionException(Operator.MISSING_PARAMETERS_FOR_OPERATOR + previousToken,
                                previousToken.pos);
                    }
                    while (!stack.isEmpty() && stack.peek().type != Token.TokenType.TOKEN_TYPE_PAREN_OPEN) {
                        outputQueue.add(stack.pop());
                    }
                    if (stack.isEmpty()) {
                        if (lastFunction == null) {
                            throw new ExpressionException("Unexpected comma", token.pos);
                        } else {
                            throw new ExpressionException(
                                    "Parse error for function " + lastFunction, token.pos);
                        }
                    }
                    break;
                case TOKEN_TYPE_OPERATOR: {
                    /* CODE MODIFICATION:
                     * Added condition to throw ExpressionException only for operator whose number of
                     * operands is greater than 1. This allows postfix unary operators without throwing
                     * "MISSING_PARAMETERS_FOR_OPERATOR" exception.*/
                    if (previousToken != null && ((TokenOperator)token).getOperator() != null
                            && (previousToken.type == Token.TokenType.TOKEN_TYPE_COMMA
                            || previousToken.type == Token.TokenType.TOKEN_TYPE_PAREN_OPEN)) {
                        if(((TokenOperator)token).getOperator().getNumberOfOperands() > 1) {
                            throw new ExpressionException(
                                Operator.MISSING_PARAMETERS_FOR_OPERATOR + token, token.pos);
                        }
                    }
                    else if (previousToken != null && ((TokenOperator)token).getOperator() != null
                    		&& (((TokenOperator)token).surface.equals(Character.toString(Token.MINUS_SIGN))
                    				|| ((TokenOperator)token).surface.equals(Character.toString(Token.PLUS_SIGN)))
                    		&& previousToken.type != Token.TokenType.TOKEN_TYPE_OPERATOR
                            && previousToken.type != Token.TokenType.TOKEN_TYPE_COMMA
                            && previousToken.type != Token.TokenType.TOKEN_TYPE_PAREN_OPEN
                            && previousToken.type != Token.TokenType.TOKEN_TYPE_OPERATOR_UNARY_PRE) {
                        if(((TokenOperator)token).getOperator().getNumberOfOperands() < 2) {
                            throw new ExpressionException(
                                "Invalid position for unary operator " + token, token.pos);
                        }
                    }
                    Operator o1 = ((TokenOperator)token).getOperator();
                    if (o1 == null) {
                        throw new ExpressionException("Unknown operator " + token, token.pos + 1);
                    }

                    shuntOperators(outputQueue, stack, o1);
                    stack.push(token);
                    break;
                }
                /*case TOKEN_TYPE_OPERATOR_UNARY_PRE: {
                    if (previousToken != null && previousToken.type != Token.TokenType.TOKEN_TYPE_OPERATOR
                            && previousToken.type != Token.TokenType.TOKEN_TYPE_COMMA && previousToken.type != Token.TokenType.TOKEN_TYPE_PAREN_OPEN
                            && previousToken.type != Token.TokenType.TOKEN_TYPE_OPERATOR_UNARY_PRE) {
                        throw new ExpressionException(
                                "Invalid position for unary operator " + token, token.pos);
                    }
                    Operator o1 = ((TokenOperator)token).getOperator();
                    if (o1 == null) {
                        throw new ExpressionException(
                                "Unknown unary operator " + token.surface.substring(0, token.surface.length() - 1)
                                , token.pos + 1);
                    }

                    shuntOperators(outputQueue, stack, o1);
                    stack.push(token);
                    break;
                }*/
                case TOKEN_TYPE_PAREN_OPEN:
                    if (previousToken != null) {
                        if (this.implicitMultiplication
                                && previousToken.type == Token.TokenType.TOKEN_TYPE_LITERAL
                                || previousToken.type == Token.TokenType.TOKEN_TYPE_PAREN_CLOSE
                                || previousToken.type == Token.TokenType.TOKEN_TYPE_VARIABLE
                                || previousToken.type == Token.TokenType.TOKEN_TYPE_HEX_LITERAL) {
                            // Implicit multiplication, e.g. 23(a+b) or (a+b)(a-b)
                            //Token multiplication = new Token();
                            //multiplication.append("*");
                            //multiplication.type = Token.TokenType.TOKEN_TYPE_OPERATOR;
                            //stack.push(multiplication);
                            Token multiplication = (Token)new TokenOperator(Operations.getBuiltInOperators("*", Operator.OPERATOR_PRECEDENCE_POWER));
                            stack.push(multiplication);
                        }
                        // if the ( is preceded by a valid function, then it
                        // denotes the start of a parameter list
                        if (previousToken.type == Token.TokenType.TOKEN_TYPE_FUNCTION) {
                            outputQueue.add(token);
                        }
                    }
                    stack.push(token);
                    break;
                case TOKEN_TYPE_PAREN_CLOSE:
                    /* CODE MODIFICATION:
                     * Added condition the handle "MISSING_PARAMETERS_FOR_OPERATOR", which is thrown only for
                     * operators whose number of operands is greater than 1. This allows for postfix unary
                     * operators to be followed by closed parentheses.*/
                    if (previousToken != null && previousToken.type == Token.TokenType.TOKEN_TYPE_OPERATOR
                            && ((TokenOperator)previousToken).getOperator().getNumberOfOperands() > 1) {
                        throw new ExpressionException(Operator.MISSING_PARAMETERS_FOR_OPERATOR + previousToken,
                                previousToken.pos);
                    }
                    while (!stack.isEmpty() && stack.peek().type != Token.TokenType.TOKEN_TYPE_PAREN_OPEN) {
                        outputQueue.add(stack.pop());
                    }
                    if (stack.isEmpty()) {
                        throw new ExpressionException("Mismatched parentheses");
                    }
                    stack.pop();
                    if (!stack.isEmpty() && stack.peek().type == Token.TokenType.TOKEN_TYPE_FUNCTION) {
                        outputQueue.add(stack.pop());
                    }
            }
            previousToken = token;
        }

        while (!stack.isEmpty()) {
            Token element = stack.pop();
            if (element.type == Token.TokenType.TOKEN_TYPE_PAREN_OPEN || element.type == Token.TokenType.TOKEN_TYPE_PAREN_CLOSE) {
                throw new ExpressionException("Mismatched parentheses");
            }
            outputQueue.add(element);
        }
        return outputQueue;
    }



    /**
     * Method to stack operators in <code>outputQueue</code> following precedence.
     *
     * @param outputQueue	The output {@link List} of {@link Token}.
     * @param stack			The input {@link Stack} of {@link Token}.
     * @param o1			The {@link Operator} of this token.
     */
    private void shuntOperators(List<Token> outputQueue, Stack<Token> stack, Operator o1) { // NOSONAR - Stack is needed here
        Token nextToken = stack.isEmpty() ? null : stack.peek();
        while (nextToken != null
                && (nextToken.type == Token.TokenType.TOKEN_TYPE_OPERATOR || nextToken.type == Token.TokenType.TOKEN_TYPE_OPERATOR_UNARY_PRE)) {
            if (o1.getNumberOfOperands() == 1 && ((TokenOperator)nextToken).getOperator().getNumberOfOperands() == 2) {
                break;
            }
            else if((o1.isLeftAssoc() && o1.getPrecedence() <= ((TokenOperator)nextToken).getOperator().getPrecedence())
                    || (o1.getPrecedence() < ((TokenOperator)nextToken).getOperator().getPrecedence())) {
                outputQueue.add(stack.pop());
                nextToken = stack.isEmpty() ? null : stack.peek();
            }
            else {
                break;
            }
        }
    }



    /**
     * Get an iterator for this expression, allows iterating over an expression token by token.
     *
     * @return A new iterator instance for this expression.
     */
    public Iterator<Token> getExpressionTokenizer() {
        final String expression = this.expressionString;

        return new Tokenizer(expression, this.operators, this.functions, this.variables);
    }



    /**
     * Cached access to the RPN notation of this expression, ensures only one calculation of the RPN
     * per expression instance. If no cached instance exists, a new one will be created and put to the
     * cache.
     *
     * @return The cached RPN instance.
     */
    private List<Token> getRPN() {
        if (rpn == null) {
            rpn = shuntingYard(this.expressionString);
            validate(rpn);
        }
        return rpn;
    }



    /**
     * Check that the expression has enough numbers and variables to fit the requirements of the
     * operators and functions, also check for only 1 result stored at the end of the evaluation.
     */
    private void validate(List<Token> rpn) {
        /*-
         * Thanks to Norman Ramsey:
         * http://http://stackoverflow.com/questions/789847/postfix-notation-validation
         */
        // each push on to this stack is a new function scope, with the value of
        // each
        // layer on the stack being the count of the number of parameters in
        // that scope
        Stack<Integer> stack = new Stack<Integer>(); // NOSONAR - we need a stack here for the Stack.set() method.

        // push the 'global' scope
        stack.push(0);
        /* CODE MODIFICATION:
         * Code added to analyze occurrences of token types function and open parentheses to
         * enable validation of functions without parentheses. The boolean value <code>openParenToken</code>
         * helps keep tracking if a new stack level was pushed, so the new code does not push another. */
        boolean openParenToken = false;
        int counterFunctions = 0, counterOpenParen = 0;
        for (final Token token : rpn) {
            switch(token.type) {
                case TOKEN_TYPE_FUNCTION:
                    counterFunctions++;
                    break;
                case TOKEN_TYPE_PAREN_OPEN:
                    counterOpenParen++;
                    break;
                default:
            }
        }
        if(counterOpenParen <= counterFunctions) {
            counterFunctions = counterFunctions - counterOpenParen;
        }

        for (final Token token : rpn) {
            switch (token.type) {
                /*case TOKEN_TYPE_OPERATOR_UNARY_PRE:
                    if (stack.peek() < 1) {
                        throw new ExpressionException(Operator.MISSING_PARAMETERS_FOR_OPERATOR + token);
                    }
                    break;*/
                case TOKEN_TYPE_OPERATOR:
                    Operator op = ((TokenOperator)token).getOperator();  // = ;
                    /* CODE MODIFICATION:
                     * IF condition changed to throw "MISSING_PARAMETERS_FOR_OPERATOR" exception
                     * only if the stack.peek() is less than the expected number of operands for
                     * this operator, allowing for single-operand operators without exception.
                     * PREVIOUS: hard-coded 'stack.peek() < 2'*/
                    if (stack.peek() < op.getNumberOfOperands()) {
                        throw new ExpressionException(Operator.MISSING_PARAMETERS_FOR_OPERATOR + token);
                    }
                    /* CODE MODIFICATION:
                     * Added IF condition to modify stack popping 2 parameters and adding to result
                     * only for operators whose numberOperands > 1.
                     * PREVIOUS: hard-coded 'stack.set(stack.size() - 1, stack.peek() - 2 + 1)'*/
                    if(op.getNumberOfOperands() > 1) {
                        stack.set(stack.size() - 1, stack.peek() - op.getNumberOfOperands() + 1);
                    }
                    break;
                case TOKEN_TYPE_FUNCTION:
                    Function func = functions.get(token.surface.toUpperCase(Locale.ROOT));
                    if (func == null) {
                        throw new ExpressionException("Unknown function " + token, token.pos + 1);
                    }

                    /* CODE MODIFICATION:
                     * Added IF condition to modify stack push and popping, allowing the validation of
                     * functions without parentheses, e.g. 'sin90'. If parenthesis are used for each function,
                     * this code block is ignored. */
                    if(counterFunctions > 0 && !openParenToken) {
                        counterFunctions--;
                        int temp = stack.peek();
                        stack.set(stack.size() - 1, stack.peek() - 1);
                        stack.push(0);
                        stack.set(stack.size() - 1, temp);
                    }
                    int numParams = stack.pop();
                    openParenToken = false;
                    if (!func.numParamsVaries() && numParams < func.getNumParams()) {  // Changed from '!= f.getNumParams()' to handle error
                        throw new ExpressionException(
                                "Function " + token + " expected " + func.getNumParams() + " parameters, got " + numParams);
                    }
                    if (stack.isEmpty()) {
                        throw new ExpressionException("Too many function calls, maximum scope exceeded");
                    }
                    // push the result of the function
                    stack.set(stack.size() - 1, stack.peek() + 1);
                    break;
                case TOKEN_TYPE_PAREN_OPEN:

                    stack.push(0);
                    openParenToken = true;
                    break;
                default:
                    stack.set(stack.size() - 1, stack.peek() + 1);
            }
        }

        if (stack.size() > 1) {
            throw new ExpressionException("Too many unhandled function parameter lists");
        } else if (stack.peek() > 1) {
            throw new ExpressionException("Too many numbers or variables");
        } else if (stack.peek() < 1) {
            throw new ExpressionException("Empty expression");
        }
    }



    /**
     * Get a string representation of the RPN (Reverse Polish Notation) for this expression.
     *
     * @return A string with the RPN representation for this expression.
     */
    public String toRPN() {
        StringBuilder result = new StringBuilder();
        for (Token t : getRPN()) {
            if (result.length() != 0) {
                result.append(" ");
            }
            if (t.type == Token.TokenType.TOKEN_TYPE_VARIABLE && variables.containsKey(t.surface)) {
                LazyNumber innerVariable = variables.get(t.surface);
                String innerExp = innerVariable.getString();
                if (isNumber(innerExp)) { // if it is a number, then we don't
                    // expan in the RPN
                    result.append(t.toString());
                } else { // expand the nested variable to its RPN representation
                    Expression exp = createEmbeddedExpression(innerExp);
                    String nestedExpRpn = exp.toRPN();
                    result.append(nestedExpRpn);
                }
            } else {
                result.append(t.toString());
            }
        }
        return result.toString();
    }



    /**
     * Evaluates the expression.
     *
     * @return The result of the expression. Trailing zeros are stripped.
     */
    public BigDecimal eval() {
        return eval(true);
    }


    /**
     * Evaluates the expression.
     *
     * @param stripTrailingZeros	If set to <code>true</code> trailing zeros in the result are stripped.
     *
     * @return The result of the expression.
     */
    public BigDecimal eval(boolean stripTrailingZeros) {

        Deque<LazyNumber> stack = new ArrayDeque<LazyNumber>();

        for(final Token token : getRPN()) {
            switch(token.type) {
                /*case TOKEN_TYPE_OPERATOR_UNARY_PRE: {
                    final LazyNumber value = stack.pop();
                    LazyNumber result = new LazyNumber() {
                        public BigDecimal eval() {
                            return ((TokenOperator)token).getOperator().eval(value, null).eval();
                        }
                        @Override
                        public String getString() {
                            return String.valueOf(((TokenOperator)token).getOperator().eval(value, null).eval());
                        }
                    };
                    stack.push(result);
                    break;
                }*/
                case TOKEN_TYPE_OPERATOR:
                    /* CODE MODIFICATION:
                     * IF-ELSE block added to handle operators with 1 or 2 operands required,
                     * allowing for the second operand v2=null in operators which numberOperands=1.*/
                    if(((TokenOperator)token).getOperator().getNumberOfOperands() < 2) {
                        final LazyNumber value = stack.pop();
                        LazyNumber result = new LazyNumber() {
                            public BigDecimal eval() {
                                return ((TokenOperator)token).getOperator().eval(value, null).eval();
                            }

                            @Override
                            public String getString() {
                                return String.valueOf(((TokenOperator)token).getOperator().eval(value, null).eval());
                            }
                        };
                        stack.push(result);
                        break;
                    }
                    else {
                        final LazyNumber v1 = stack.pop();
                        final LazyNumber v2 = stack.pop();
                        LazyNumber result = new LazyNumber() {
                            public BigDecimal eval() {
                                return ((TokenOperator)token).getOperator().eval(v2, v1).eval();
                            }

                            public String getString() {
                                return String.valueOf(((TokenOperator)token).getOperator().eval(v2, v1).eval());
                            }
                        };
                        stack.push(result);
                        break;
                    }
                case TOKEN_TYPE_VARIABLE:
                    if (!variables.containsKey(token.surface)) {
                        throw new ExpressionException("Unknown operator or function: " + token);
                    }

                    stack.push(new LazyNumber() {
                        public BigDecimal eval() {
                            LazyNumber lazyVariable = variables.get(token.surface);
                            BigDecimal value = lazyVariable == null ? null : lazyVariable.eval();
                            return value == null ? null : value.round(mc);
                        }

                        public String getString() {
                            return token.surface;
                        }
                    });
                    break;
                case TOKEN_TYPE_FUNCTION:
                    Function func = functions.get(token.surface.toUpperCase(Locale.ROOT));
                    ArrayList<LazyNumber> p = new ArrayList<LazyNumber>(
                            !func.numParamsVaries() ? func.getNumParams() : 0);
                    // pop parameters off the stack until we hit the start of
                    // this function's parameter list

//                    while (!stack.isEmpty() && stack.peek() != PARAMS_START) {
//                        p.add(0, stack.pop());
//                    }
                    /* CODE MODIFICATION:
                     * Added <code>int</code> variable to retrieve number of parameters in the function and
                     * serve as counter to the number of parameters to pop from stack in the modified 'while()'
                     * block, which enables evaluation of functions without parentheses. */
                    int numberParams = func.getNumParams();
                    while(numberParams > 0) {
                        p.add(0, stack.pop());
                        numberParams--;
                    }

                    //if(stack.peek() != null) {}
                    if (stack.peek() == PARAMS_START) {
                        stack.pop();
                    }

                    LazyNumber fResult = func.lazyEval(p);
                    stack.push(fResult);
                    break;
                case TOKEN_TYPE_PAREN_OPEN:
                    stack.push(PARAMS_START);
                    break;
                case TOKEN_TYPE_LITERAL:
                    stack.push(new LazyNumber() {
                        public BigDecimal eval() {
                            if (token.surface.equalsIgnoreCase("NULL")) {
                                return null;
                            }

                            return new BigDecimal(token.surface, mc);
                        }

                        public String getString() {
                            return String.valueOf(new BigDecimal(token.surface, mc));
                        }
                    });
                    break;
                case TOKEN_TYPE_STRINGPARAM:
                    stack.push(new LazyNumber() {
                        public BigDecimal eval() {
                            return null;
                        }

                        public String getString() {
                            return token.surface;
                        }
                    });
                    break;
                case TOKEN_TYPE_HEX_LITERAL:
                    stack.push(new LazyNumber() {
                        public BigDecimal eval() {
                            return new BigDecimal(new BigInteger(token.surface.substring(2), 16), mc);
                        }

                        public String getString() {
                            return new BigInteger(token.surface.substring(2), 16).toString();
                        }
                    });
                    break;
                default:
                    throw new ExpressionException(
                            "Unexpected token " + token.surface, token.pos);
            }
        }
        BigDecimal result = stack.pop().eval();
        if (result == null) {
            return null;
        }
        if (stripTrailingZeros) {
            result = result.stripTrailingZeros();
        }
        return result;
    }


    /**
     * Sets the precision for expression evaluation.
     *
     * @param precision The new precision.
     * @return The expression, allows to chain methods.
     */
    public Expression setPrecision(int precision) {
        this.mc = new MathContext(precision);
        return this;
    }



    /**
     * Sets the rounding mode for expression evaluation.
     *
     * @param roundingMode The new rounding mode.
     * @return The expression, allows to chain methods.
     */
    public Expression setRoundingMode(RoundingMode roundingMode) {
        this.mc = new MathContext(mc.getPrecision(), roundingMode);
        return this;
    }



    /**
     * Sets the characters other than letters and digits that are valid as the first character of a
     * variable.
     *
     * @param chars The new set of variable characters.
     * @return The expression, allows to chain methods.
     */
    public Expression setFirstVariableCharacters(String chars) {
        Token.firstVarChars = chars;
        return this;
    }



    /**
     * Sets the characters other than letters and digits that are valid as the second and subsequent
     * characters of a variable.
     *
     * @param chars The new set of variable characters.
     * @return The expression, allows to chain methods.
     */
    public Expression setVariableCharacters(String chars) {
        Token.varChars = chars;
        return this;
    }



    /**
     * Adds an operator to the list of supported operators.
     *
     * @param operator The operator to add.
     * @return The previous operator with that name, or <code>null</code> if there was none.
     */
    public Operator addOperator(Operator operator) {  // <OPERATOR extends AbstractOperator> OPERATOR
        String key = operator.getOper();
        if (operator instanceof UnaryOperator) {
            key += "u";
        }
        return operators.put(key, operator);  // (OPERATOR)
    }



    /**
     * Adds a list varargs of operators to the map of supported operators by
     * calling the addOperator() method for each operator in the list.
     *
     * @param operators The varargs of operators to add.
     */
    public void addOperators(Operator... operators) {  // <OPERATOR extends AbstractOperator>
        if(operators.length == 0) {  // Exception placed to avoid construction of expression with new operators without them being added
            throw new ExpressionException("The number of added operators by this method must be at least 1");
        }
        for(Operator operator : operators) {
            addOperator(operator);
        }
    }



    /**
     * Adds a {@link List} of operators to the map of supported operators by
     * calling the addOperator() method for each operator in the List.
     *
     * @param operators The {@link List} of {@link Operator} to add.
     */
    public void addOperators(List<Operator> operators) {
        if(operators.size() == 0) {  // Exception placed to avoid construction of expression with new operators without them being added
            throw new ExpressionException("The number of added operators by this method must be at least 1");
        }
        for(Operator operator : operators) {
            addOperator(operator);
        }
    }



    /**
     * Adds a function to the list of supported functions
     *
     * @param function The function to add.
     * @return The previous operator with that name, or <code>null</code> if there was none.
     */
    public Function addFunction(Function function) {
        return (Function)functions.put(function.getName(), function);
    }



    /**
     * Adds a list of varargs functions to the map of supported functions
     *
     * @param functions The varargs functions to add.
     */
    public void addFunctions(Function... functions) {
        if(functions.length == 0) {  // Exception placed to avoid construction of expression with new functions without them being added
            throw new ExpressionException("The number of added functions by this method must be at least 1");
        }
        for(Function function : functions) {
            addFunction(function);
        }
    }



    /**
     * Adds a {@link List} of functions to the map of supported functions
     *
     * @param functions The {@link List} of {@link Function} to add.
     */
    public void addFunctions(List<Function> functions) {
        if(functions.size() == 0) {  // Exception placed to avoid construction of expression with new functions without them being added
            throw new ExpressionException("The number of added functions by this method must be at least 1");
        }
        for(Function function : functions) {
            addFunction(function);
        }
    }



    /**
     * Adds a list of varargs of variables to the map of variables
     *
     * @param variables The varargs of {@link Variable} to add.
     */
    private void addVariables(Variable... variables) {
        if(variables.length > 0) {
            for(Variable variable : variables) {
                setVariable(variable.getVar(), variable.getLazyValue());
            }
        }
    }



    /**
     * Adds a {@link List} of variables to the map of variables
     *
     * @param variables The {@link List} of {@link Variable} to add.
     */
    private void addVariables(List<Variable> variables) {
        if(variables.size() > 0) {
            for(Variable variable : variables) {
                setVariable(variable.getVar(), variable.getLazyValue());
            }
        }
    }



    /**
     * Adds a lazy function function to the list of supported functions
     *
     * @param function The function to add.
     * @return The previous operator with that name, or <code>null</code> if there was none.
     */
    public Function addLazyFunction(Function function) {
        return functions.put(function.getName(), function);
    }



    /**
     * Sets a variable value.
     *
     * @param variable The variable name.
     * @param value    The variable value.
     * @return The expression, allows to chain methods.
     */
    public Expression setVariable(String variable, BigDecimal value) {
        return setVariable(variable, createLazyNumber(value));
    }



    /**
     * Sets a variable value.
     *
     * @param variable The variable name.
     * @param value    The variable value.
     * @return The expression, allows to chain methods.
     */
    public Expression setVariable(String variable, LazyNumber value) {
        variables.put(variable, value);
        return this;
    }



    /**
     * Sets a variable value.
     *
     * @param variable The variable to set.
     * @param value    The variable value.
     * @return The expression, allows to chain methods.
     */
    public Expression setVariable(String variable, String value) {
        if (isNumber(value)) {
            variables.put(variable, createLazyNumber(new BigDecimal(value, mc)));
        } else if (value.equalsIgnoreCase("null")) {
            variables.put(variable, null);
        } else {
            final String expStr = value;
            variables.put(variable, new LazyNumber() {
                private final Map<String, LazyNumber> outerVariables = variables;
                private final Map<String, Function> outerFunctions = functions;
                private final Map<String, Operator> outerOperators = operators;
                private final String innerExpressionString = expStr;
                private final MathContext inneMc = mc;

                @Override
                public String getString() {
                    return innerExpressionString;
                }

                @Override
                public BigDecimal eval() {
                    Expression innerE = new Expression(innerExpressionString, inneMc);
                    innerE.variables = outerVariables;
                    innerE.functions = outerFunctions;
                    innerE.operators = outerOperators;
                    BigDecimal val = innerE.eval();
                    return val;
                }
            });
            rpn = null;
        }
        return this;
    }




    /**
     * Is the string a number?
     *
     * @param st The string.
     * @return <code>true</code>, if the input string is a number.
     */
    protected boolean isNumber(String st) {
        if (st.charAt(0) == Token.MINUS_SIGN && st.length() == 1) {
            return false;
        }
        if (st.charAt(0) == '+' && st.length() == 1) {
            return false;
        }
        if (st.charAt(0) == Token.DECIMAL_SEPARATOR && (st.length() == 1 || !Character
                .isDigit(st.charAt(1)))) {
            return false;
        }
        if (st.charAt(0) == 'e' || st.charAt(0) == 'E') {
            return false;
        }
        for (char ch : st.toCharArray()) {
            if (!Character.isDigit(ch) && ch != Token.MINUS_SIGN && ch != Token.DECIMAL_SEPARATOR && ch != 'e'
                    && ch != 'E'
                    && ch != Token.PLUS_SIGN) {
                return false;
            }
        }
        return true;
    }



    /**
     * Creates a new inner expression for nested expression.
     *
     * @param expression The string expression.
     *
     * @return The inner Expression instance.
     */
    private Expression createEmbeddedExpression(final String expression) {
        final Map<String, LazyNumber> outerVariables = variables;
        final Map<String, Function> outerFunctions = functions;
        final Map<String, Operator> outerOperators = operators;
        final MathContext inneMc = mc;
        Expression exp = new Expression(expression, inneMc);
        exp.variables = outerVariables;
        exp.functions = outerFunctions;
        exp.operators = outerOperators;
        return exp;
    }



    /**
     * Sets a variable value.
     *
     * @param variable The variable to set.
     * @param value    The variable value.
     *
     * @return The expression, allows to chain methods.
     */
    public Expression with(String variable, BigDecimal value) {
        return setVariable(variable, value);
    }



    /**
     * Sets a variable value.
     *
     * @param variable The variable to set.
     * @param value    The variable value.
     *
     * @return The expression, allows to chain methods.
     */
    public Expression with(String variable, LazyNumber value) {
        return setVariable(variable, value);
    }



    /**
     * Sets a variable value.
     *
     * @param variable The variable to set.
     * @param value    The variable value.
     *
     * @return The expression, allows to chain methods.
     */
    public Expression and(String variable, String value) {
        return setVariable(variable, value);
    }



    /**
     * Sets a variable value.
     *
     * @param variable The variable to set.
     * @param value    The variable value.
     *
     * @return The expression, allows to chain methods.
     */
    public Expression and(String variable, BigDecimal value) {
        return setVariable(variable, value);
    }



    /**
     * Sets a variable value.
     *
     * @param variable The variable to set.
     * @param value    The variable value.
     *
     * @return The expression, allows to chain methods.
     */
    public Expression and(String variable, LazyNumber value) {
        return setVariable(variable, value);
    }



    /**
     * Sets a variable value.
     *
     * @param variable The variable to set.
     * @param value    The variable value.
     *
     * @return The expression, allows to chain methods.
     */
    public Expression with(String variable, String value) {
        return setVariable(variable, value);
    }



    /**
     * Exposing declared variables in the expression.
     *
     * @return All declared variables.
     */
    public Set<String> getDeclaredVariables() {
        return Collections.unmodifiableSet(variables.keySet());
    }



    /**
     * Exposing declared operators in the expression.
     *
     * @return All declared operators.
     */
    public Set<String> getDeclaredOperators() {
        return Collections.unmodifiableSet(operators.keySet());
    }



    /**
     * Exposing declared functions.
     *
     * @return All declared functions.
     */
    public Set<String> getDeclaredFunctions() {
        return Collections.unmodifiableSet(functions.keySet());
    }



    /**
     * @return The original expression string
     */
    public String getExpression() {
        return expressionString;
    }



    /**
     * Returns a list of the variables in the expression.
     *
     * @return A list of the variable names in this expression.
     */
    public List<String> getUsedVariables() {
        List<String> result = new ArrayList<String>();
        Tokenizer tokenizer = new Tokenizer(expressionString, this.operators, this.functions, this.variables);
        while (tokenizer.hasNext()) {
            Token nextToken = tokenizer.next();
            String token = nextToken.toString();
            if (nextToken.type != Token.TokenType.TOKEN_TYPE_VARIABLE || token.equals("PI") || token.equals("e")
                    || token.equals("TRUE") || token.equals("FALSE")) {
                continue;
            }
            result.add(token);
        }
        return result;
    }



    /**
     * The original expression used to construct this expression, without variables substituted.
     */
    public String getOriginalExpression() {
        return this.originalExpression;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Expression that = (Expression) o;
        if (this.expressionString == null) {
            return that.expressionString == null;
        } else {
            return this.expressionString.equals(that.expressionString);
        }
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return this.expressionString == null ? 0 : this.expressionString.hashCode();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.expressionString;
    }



    /**
     * Checks whether the expression is a boolean expression. An expression is considered a boolean
     * expression, if the last operator or function is boolean. The IF function is handled special. If
     * the third parameter is boolean, then the IF is also considered boolean, else non-boolean.
     *
     * @return <code>true</code> if the last operator/function was a boolean.
     */
    public boolean isBoolean() {
        List<Token> rpnList = getRPN();
        if (!rpnList.isEmpty()) {
            for (int i = rpnList.size() - 1; i >= 0; i--) {
                Token t = rpnList.get(i);
                /* The IF function is handled special. If the third parameter is
                 * boolean, then the IF is also considered a boolean. Just skip
                 * the IF function to check the second parameter.*/
                if (t.surface.equals("IF")) {
                    continue;
                }
                if (t.type == Token.TokenType.TOKEN_TYPE_FUNCTION) {
                    return functions.get(t.surface).isBooleanFunction();
                } else if (t.type == Token.TokenType.TOKEN_TYPE_OPERATOR) {
                    return operators.get(t.surface).isBooleanOperator();
                }
            }
        }
        return false;
    }



    /**
     * Assert the single expected operand is not null.
     */
    protected static void assertNotNull(BigDecimal v1) {
        if (v1 == null) {
            throw new ArithmeticException("Operand may not be null");
        }
    }


    /**
     * Assert the two expected operands are not null.
     */
    protected static void assertNotNull(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            throw new ArithmeticException("First operand may not be null");
        }
        if (v2 == null) {
            throw new ArithmeticException("Second operand may not be null");
        }
    }



    public List<String> infixNotation() {
        final List<String> infix = new ArrayList<String>();
        Tokenizer tokenizer = new Tokenizer(expressionString, this.operators, this.functions, this.variables);
        while (tokenizer.hasNext()) {
            Token token = tokenizer.next();
            String infixNotation = "{" + token.type + ":" + token.surface + "}";
            infix.add(infixNotation);
        }
        return infix;
    }

}
