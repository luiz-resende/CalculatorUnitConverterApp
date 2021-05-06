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

/**
 * Abstract base class for tokens used to tokenize expressions.
 */
@SuppressWarnings({"NullableProblems", "unused", "RedundantSuppression"})
public class Token {


    /**
     * The characters (other than letters and digits) allowed as the first character in a variable.
     */
    public static String firstVarChars = "_";

    /**
     * The characters (other than letters and digits) allowed as the second or subsequent characters in a variable.
     */
    public static String varChars = "_";
    /**
     * Defined character to use for decimal separators.
     */
    public static final char DECIMAL_SEPARATOR = '.';

    /**
     * Defined character to use for minus sign (negative values).
     */
    public static final char MINUS_SIGN = '-';
    /**
     * Defined character to use for plus sign (positve values).
     */
    public static final char PLUS_SIGN = '+';


    /**
     * Constants representing the different token types classified when tokenizing the expression.
     */
    public enum TokenType {
        TOKEN_TYPE_VARIABLE, TOKEN_TYPE_FUNCTION, TOKEN_TYPE_LITERAL, TOKEN_TYPE_OPERATOR, TOKEN_TYPE_OPERATOR_UNARY_PRE,
        TOKEN_TYPE_PAREN_OPEN, TOKEN_TYPE_COMMA, TOKEN_TYPE_PAREN_CLOSE, TOKEN_TYPE_HEX_LITERAL, TOKEN_TYPE_STRINGPARAM
    }



    /**
     * {@link String} variable to store token characters
     */
    public String surface = "";
    /**
     * Classification attributed to this token.
     */
    public TokenType type;
    /**
     * {@link Integer} defining the position this token holds in the expression.
     */
    public int pos;



    /**
     * Abstract class Token constructor.
     */
    public Token() {

    }



    /**
     * Method to append character to this token.
     *
     * @param c	{@link Character} composing this token.
     */
    public void append(char c) {
        this.surface += c;
    }



    /**
     * Method to append string to this token.
     *
     * @param s	{@link String} composing this token.
     */
    public void append(String s) {
        this.surface += s;
    }



    /**
     * Inquires and returns the {@link Character} at position <code>pos</code>
     * in this token <code>surface</code> {@link String}.
     *
     * @param pos	{@link Integer} corresponding to desired position.
     *
     * @return	{@link Character} at given position <code>pos</code>.
     */
    public char charAt(int pos) {
        return this.surface.charAt(pos);
    }



    /**
     * Inquires and returns the {@link Integer} number corresponding to
     * the length of this token <code>surface</code> {@link String}.
     *
     * @return	{@link Integer} length for this token <code>surface</code> {@link String}.
     */
    public int length() {
        return this.surface.length();
    }



    /**
     * Gets the {@link String} <code>surface</code> for this token.
     *
     * @return	{@link String} this token <code>surface</code>.
     */
    @Override
    public String toString() {
        return this.surface;
    }


}