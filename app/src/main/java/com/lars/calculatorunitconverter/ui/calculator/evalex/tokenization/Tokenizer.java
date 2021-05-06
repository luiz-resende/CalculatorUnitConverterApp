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

package com.lars.calculatorunitconverter.ui.calculator.evalex.tokenization;

import com.lars.calculatorunitconverter.ui.calculator.evalex.Expression.ExpressionException;
import com.lars.calculatorunitconverter.ui.calculator.evalex.functions.Function;
import com.lars.calculatorunitconverter.ui.calculator.evalex.lazynumber.LazyNumber;
import com.lars.calculatorunitconverter.ui.calculator.evalex.Operations;
import com.lars.calculatorunitconverter.ui.calculator.evalex.operators.Operator;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Tokenizer class that allows to iterate over a {@link String} expression
 * token by token. Blank characters are skipped.
 */
@SuppressWarnings({"FieldMayBeFinal", "UnusedAssignment", "Convert2Diamond", "RedundantCast"})
public class Tokenizer implements Iterator<Token> {


    /**
     * The original input expression.
     */
    private final String inputExpression;
    /**
     * The original input expression.
     */
    private int inputExpressionLength;
    /**
     * Map for all defined operators with name and implementation.
     */
    private Map<String, Operator> operators = new TreeMap<String, Operator>(String.CASE_INSENSITIVE_ORDER);
    /**
     * Map for all defined functions with name and implementation.
     */
    private Map<String, Function> functions = new TreeMap<String, Function>(String.CASE_INSENSITIVE_ORDER);
    /**
     * Map for all defined variables with name and lazy value.
     */
    private Map<String, LazyNumber> variables = new TreeMap<String, LazyNumber>(String.CASE_INSENSITIVE_ORDER);
    /**
     * Actual position in expression string.
     */
    private int pos = 0;
    /**
     * The previous token or <code>null</code> if none.
     */
    public Token previousToken;



    /**
     * Creates a new tokenizer for an expression.
     *
     * @param input The expression string.
     */
    public Tokenizer(String input, Map<String, Operator> operators, Map<String, Function> functions, Map<String, LazyNumber> variables) {
        this.inputExpression = input.trim();
        this.inputExpressionLength = this.inputExpression.length();
        this.operators = operators;
        this.functions = functions;
        this.variables = variables;
    }


    /**
     * @return	<code>true</code> if current position <code>pos</code> from the
     * input expression analyzed is not the last (<code>pos < expression.length</code>)
     */
    @Override
    public boolean hasNext() {
        return (pos < this.inputExpressionLength);
    }


    /**
     * Peek at the next character, without advancing the iterator.
     *
     * @return The next character or character 0, if at end of string.
     */
    private char peekNextChar() {
        if (pos < (this.inputExpressionLength - 1)) {
            return this.inputExpression.charAt(pos + 1);
        } else {
            return 0;
        }
    }


    /**
     * Checks if the character analyzed is hexadecimal representation.
     *
     * @param ch	{@link Character} currently being viewed.
     *
     * @return	<code>true</code> if the character <code>ch</code> is a hexadecimal
     * 			number representation, <code>false</code> otherwise.
     */
    private boolean isHexDigit(char ch) {
        return ch == 'x' || ch == 'X' || (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F');
    }


    /**
     * Checks if the character is numeric or decimal separator '.'. By setting
     * <code>checkForLetterE</code> to <code>true</code>, letter 'e' or 'E' are
     * also considered in the comparison.
     *
     * @param cha	{@link Character} of current position;
     * @param checkForLetterE {@link Boolean} flag to consider 'e' and 'E' (<code>true</code>),
     * 							or not (<code>false</code>).
     *
     * @return	<code>true</code> {@link Character} is numeric, decimal separator '.',
     * 			the letter 'e' or 'E', and <code>false</code> otherwise.
     */
    private static boolean isNumericChar(char cha, boolean checkForLetterE) {
        if(checkForLetterE) {
            return (Character.isDigit(cha) || cha == Token.DECIMAL_SEPARATOR || cha == 'e' || cha == 'E');
        }
        return (Character.isDigit(cha) || cha == Token.DECIMAL_SEPARATOR);
    }


    /**
     * Checks if character is an alphabetic character.
     *
     * @param cha	{@link Character} of current position;
     *
     * @return	<code>true</code> {@link Character} is alphabetical,
     * 			<code>false</code> otherwise.
     */
    public static boolean isAlphabeticChar(char cha) {
        return Character.isLetter(cha);
    }


    /**
     * Checks if the character matches '_'.
     *
     * @param cha	{@link Character} of current position;
     * @param analyzePattern	{@link String} defining if:
     * 			- <code>analyzePattern="first"</code>, the method should compare to <code>Token.firstVarChars</code>
     * 			- <code>analyzePattern="second"</code>, the method should compare to <code>Token.firstVarChars</code>
     * 			- <code>analyzePattern="both"</code>, the method should compare to both the above.
     *
     * @return	<code>true</code> {@link Character} is '_',
     * 			<code>false</code> otherwise.
     */
    public static boolean isVarAllowedChar(char cha, String analyzePattern) {
        if(analyzePattern.equals("first")) {
            return Token.firstVarChars.indexOf(cha) >= 0;
        } else if(analyzePattern.equals("second")) {
            return Token.varChars.indexOf(cha) >= 0;
        } else {
            return Token.firstVarChars.indexOf(cha) >= 0 || Token.varChars.indexOf(cha) >= 0;
        }
    }


    /**
     * Checks if the character is alphabetic, '_' or numeric as per the method
     * <code>isNumericChar</code> without being equal to either 'e' or 'E'. Its
     * result will help defining tokens that should be classified as either
     * Token.TokenType.TOKEN_TYPE_VARIABLE or Token.TokenType.TOKEN_TYPE_FUNCTION.
     *
     * @param ch	{@link Character} of current position;
     * @param analyzeVar	{@link String} defining if:
     * 			- <code>analyzePattern="first"</code>, the method should compare to <code>Token.firstVarChars</code>
     * 			- <code>analyzePattern="second"</code>, the method should compare to <code>Token.firstVarChars</code>
     * 			- <code>analyzePattern="both"</code>, the method should compare to both the above.
     *
     * @return	<code>true</code> {@link Character} is alphabetical, numeric, decimal separator '.',
     * 			<code>false</code> if it is the letter 'e' or 'E' or otherwise.
     */
    public static boolean isAlphaNumericChar(char ch, String analyzeVar) {
        return isAlphabeticChar(ch) || isVarAllowedChar(ch, analyzeVar) || isNumericChar(ch, false);
    }


    /**
     * Checks if last char of this <code>Token.surface</code> is equal to 'e' or 'E'.
     *
     * @param tok	{@link Token} analyzed.
     *
     * @return	<code>true</code> if last char of this <code>Token.surface</code> is
     * 			equal to 'e' or 'E'. <code>false</code> otherwise.
     */
    private static boolean isLastCharE(Token tok) {
        return ('e' == tok.charAt(tok.length() - 1) || 'E' == tok.charAt(tok.length() - 1));
    }


    /**
     * Checks if the end of the input expression has been reached.
     *
     * @param position	{@link Integer} of current position;
     *
     * @return	<code>true</code> if end of input expression is reached,
     * 			<code>false</code> otherwise.
     */
    private boolean isEndOfInputExpression(int position) {
        return position >= this.inputExpressionLength;
    }


    /**
     * Method advances tokenization.
     *
     * @return	{@link Token} extracted from input expression.
     */
    @Override
    public Token next() {
        Token token = new Token();

        if (pos >= this.inputExpressionLength) {  // Exit when expression end is reached
            previousToken = null;
            return null;
        }

        char ch = this.inputExpression.charAt(pos);
        while (Character.isWhitespace(ch) && !isEndOfInputExpression(pos)) {
            ch = this.inputExpression.charAt(++pos);
        }

        token.pos = pos;

        boolean isHex = false;

        if (Character.isDigit(ch) || (ch == Token.DECIMAL_SEPARATOR && Character.isDigit(peekNextChar()))) {
            if (ch == '0' && (peekNextChar() == 'x' || peekNextChar() == 'X')) {  // Checking if is hexadecimal number
                isHex = true;
            }

            while ((isHex && isHexDigit(ch)) || (isNumericChar(ch, true) || ((ch == Token.MINUS_SIGN || ch == Token.PLUS_SIGN) && token.length() > 0 && isLastCharE(token)))
                    && !isEndOfInputExpression(pos)) {
                token.append(this.inputExpression.charAt(pos++));
                ch = pos == this.inputExpressionLength ? 0 : this.inputExpression.charAt(pos);
            }

            token.type = isHex ? Token.TokenType.TOKEN_TYPE_HEX_LITERAL : Token.TokenType.TOKEN_TYPE_LITERAL;

        } else if (ch == '"') {
            pos++;

            if (previousToken.type != Token.TokenType.TOKEN_TYPE_STRINGPARAM) {
                ch = this.inputExpression.charAt(pos);
                while (ch != '"') {
                    token.append(this.inputExpression.charAt(pos++));
                    ch = pos == this.inputExpressionLength ? 0 : this.inputExpression.charAt(pos);
                }

                token.type = Token.TokenType.TOKEN_TYPE_STRINGPARAM;

            } else {
                return next();
            }
            /* CODE MODIFICATION:
             * Blocks were rearranged, with new methods being implemented to easy, speed and clarify conditionals.
             * This token classification for operators, functions and variables was changed such as to avoid classification
             * errors. That enabled all prefix and postfix unary operators to be classified as TOKEN_TYPE_OPERATORS without need
             * of a separate class. Functions don't require the parenthesis to be parsed (i.e. 'sin90' and 'sin(90)' will be seen
             * as equals), while avoiding errors when classifying functions with shared radicals (e.g. 'sin90' and 'sinrPI').
             * Differentiation between operators and functions/variables is still based on the former not being alpha numerical */
        } else if (ch == '(' || ch == ')' || ch == ',') {
            if (ch == '(') {
                token.type = Token.TokenType.TOKEN_TYPE_PAREN_OPEN;
            } else if (ch == ')') {
                token.type = Token.TokenType.TOKEN_TYPE_PAREN_CLOSE;
            } else {
                token.type = Token.TokenType.TOKEN_TYPE_COMMA;
            }
            token.append(ch);
            pos++;
        } else if(!isAlphaNumericChar(ch, "both") && (ch != '(' && ch != ')' && ch != ',')) {
            token = (Token)checkOperatorToken(ch, pos);
        } else if(isAlphaNumericChar(ch, "both")) {
            token = (Token)checkFunctionOrVariableToken(ch, pos);
        }

        previousToken = token;
        return token;
    }



    /**
     * Method to parse this {@link Token} <code>surface</code> and check to which operator the
     * string/symbol(s) relate to.
     *
     * @param firstChar	First {@link Character} of this token being analyzed.
     *
     * @return	This {@link Token} classified as TOKEN_TYPE_OPERATOR if so.
     */
    private Token checkOperatorToken(char firstChar, int positionToken) {
        final int initPos = this.pos;
        int len = 1;
        final StringBuilder operSymbol = new StringBuilder();
        Operator lastValidOper = null;
        operSymbol.append(firstChar);

        while (!isEndOfInputExpression(initPos + len)) {
            operSymbol.append(inputExpression.charAt(initPos + len++));
        }

        while (operSymbol.length() > 0) {
            Operator op = this.getOperator(operSymbol.toString());
            if (op == null) {
                operSymbol.setLength(operSymbol.length() - 1);
            } else {
                lastValidOper = op;
                break;
            }
        }

        pos += operSymbol.length();
        TokenOperator currentToken = new TokenOperator(lastValidOper);
        currentToken.pos = positionToken;
        return currentToken;
    }



    /**
     * Method to retrieve this token {@link Operator} from Operators map.
     *
     * @param operSymbol	This {@link Token} operator char sequence.
     *
     * @return	{@link Operator}.
     */
    private Operator getOperator(String operSymbol) {
        Operator oper = null;
        if (this.operators != null && !operSymbol.equals("+") && !operSymbol.equals("-")) {
            oper = this.operators.get(operSymbol);
        }
        if (oper == null && operSymbol.length() == 1) {
            int numberOperands = 2;
            if (previousToken == null) {
                numberOperands = 1;
            } else {
                if (previousToken.type == Token.TokenType.TOKEN_TYPE_PAREN_OPEN || previousToken.type == Token.TokenType.TOKEN_TYPE_COMMA) {
                    numberOperands = 1;
                } else if (previousToken.type == Token.TokenType.TOKEN_TYPE_OPERATOR) {
                    Operator lastOper = ((TokenOperator)previousToken).getOperator();
                    if (lastOper.getNumberOfOperands() == 2 || (lastOper.getNumberOfOperands() == 1 && !lastOper.isLeftAssoc())) {
                        numberOperands = 1;
                    }
                }

            }
            oper = Operations.getBuiltInOperators(operSymbol, numberOperands);
        }
        return oper;
    }



    /**
     * Method to parse this {@link Token} <code>surface</code> and check to which function or
     * variable the string relates to.
     *
     * @param firstChar	First {@link Character} of this token being analyzed.
     *
     * @return	This {@link Token} classified as TOKEN_TYPE_FUNCTION or TOKEN_TYPE_VARIABLE if so.
     */
    private Token checkFunctionOrVariableToken(char firstChar, int positionToken) {
        final int initPos = this.pos;
        int testingPos;
        int lastValidLength = 1;
        Token lastValidToken = null;
        int leng = 1;
        if (isEndOfInputExpression(initPos)) {
            this.pos++;
        }
        testingPos = initPos + leng - 1;
        while (!isEndOfInputExpression(testingPos) && isAlphaNumericChar(inputExpression.charAt(testingPos), "both")) {
            String name = new String(inputExpression.toCharArray(), initPos, leng);
            if (variables != null && variables.containsKey(name)) {
                lastValidLength = leng;
                lastValidToken = (Token)new TokenVariable(name);
            } else {
                //final Function func = getFunction(name);
                final Function func = functions.get(name);
                if (func != null) {
                    lastValidLength = leng;
                    lastValidToken = (Token)new TokenFunction(func);
                }
            }
            leng++;
            testingPos = initPos + leng - 1;
        }
        if (lastValidToken == null) {
            int end = (pos + leng - 1);
            if(inputExpressionLength < (pos + leng - 1)) {
                end = inputExpressionLength;
            }
            String name = new String(inputExpression.toCharArray(), pos, end);
            if(inputExpression.substring(pos, end).equals("π") || inputExpression.substring(pos, end).equals("φ")){
                if (variables != null && variables.containsKey(name)) {
                    lastValidLength = leng;
                    lastValidToken = (Token)new TokenVariable(name);
                }
            }
            else {
                throw new ExpressionException(inputExpression.substring(pos, end), pos);
            }
        }
        pos += lastValidLength;
        Token currentToken = lastValidToken;
        currentToken.pos = positionToken;
        return currentToken;
    }



    /**
     * Method to retrieve this token {@link Function} from Function map.
     *
     * @param funcName	This {@link Token} function {@link String} name.
     *
     * @return	{@link Function} for this token.
     */
    private Function getFunction(String funcName) {
        Function func = null;
        if (this.functions != null) {
            func = this.functions.get(funcName);
        }
        return func;
    }



    @Override
    public void remove() {
        throw new ExpressionException("remove() not supported");
    }

}