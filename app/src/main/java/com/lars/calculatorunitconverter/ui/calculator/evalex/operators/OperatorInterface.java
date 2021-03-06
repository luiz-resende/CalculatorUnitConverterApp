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

package com.lars.calculatorunitconverter.ui.calculator.evalex.operators;


import com.lars.calculatorunitconverter.ui.calculator.evalex.lazynumber.LazyNumber;

import java.math.BigDecimal;


/**
 * Base interface which is required for all operators. Contains methods which
 * for inquiring about operator's parameters. After modification, abstract class
 * AbstractOperator accepts postfix unary operators with the second operand v2=null.
 */
@SuppressWarnings({"UnnecessaryInterfaceModifier", "unused", "RedundantSuppression"})
public interface OperatorInterface  {


    /**
     * Gets the String that is used to denote the operator in the expression.
     *
     * @return The String that is used to denote the operator in the expression.
     */
    public abstract String getOper();


    /**
     * Gets the precedence value of this operator.
     *
     * @return the precedence value of this operator.
     */
    public abstract int getPrecedence();


    /**
     * Gets whether this operator is left associative (<code>true</code>) or if
     * this operator is right associative (<code>false</code>).
     *
     * @return <code>true</code> if this operator is left associative.
     */
    public abstract boolean isLeftAssoc();


    /**
     * Gets the number of operands expected for this operator.
     *
     * @return the number of operands for this operator. Expect return either 1 or 2.
     */
    public abstract int getNumberOfOperands();


    /**
     * Gets whether this operator evaluates to a boolean expression.
     *
     * @return <code>true</code> if this operator evaluates to a boolean
     *         expression.
     */
    public abstract boolean isBooleanOperator();


    /**
     * Implementation for this operator.
     *
     * @param v1
     * 			Operand 1.
     * @param v2
     * 			Operand 2.
     * @return
     * 			The result of the operation.
     */
    public abstract LazyNumber eval(LazyNumber v1, LazyNumber v2);


    /**
     * Implementation for this operator evaluation.
     *
     * @param v1
     * 			Operand 1.
     * @param v2
     *          Operand 2. May be null for postfix unary operators.
     * @return
     * 			The result of the operation.
     */
    public abstract BigDecimal eval(BigDecimal v1, BigDecimal v2);
}