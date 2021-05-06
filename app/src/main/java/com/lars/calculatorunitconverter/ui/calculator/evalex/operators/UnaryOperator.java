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


import com.lars.calculatorunitconverter.ui.calculator.evalex.Expression.ExpressionException;
import com.lars.calculatorunitconverter.ui.calculator.evalex.lazynumber.LazyNumber;

import java.math.BigDecimal;


/**
 * Abstract definition of a prefix unary operator with right-side operand.
 * It is defined by its name (pattern), precedence, if it is left- or right-associative
 * and the number of operands expected for the operation. For this class,
 * the operator is expected to have the single operand to its right.
 *
 * This abstract implementation implements eval() so that it forwards its first
 * parameter to evalUnary.
 */
public abstract class UnaryOperator extends Operator {



    /**
     * Creates a new operator.
     *
     * @param oper
     *            The operator name (pattern).
     * @param precedence
     *            The operators precedence.
     * @param leftAssoc
     *            <code>true</code> if the operator is left associative,
     *            else <code>false</code>.
     */
    protected UnaryOperator(String oper, int precedence, boolean leftAssoc, int numberOperands) {
        super(oper, precedence, leftAssoc, numberOperands);
    }



    /**
     * Implementation of this operator LazyNumber eval() calling unary operator evaluation method.
     *
     * @param v1
     *            The first <code>LazyNumber</code> parameter.
     * @param v2
     *            The second <code>LazyNumber</code> parameter. Expected to be null
     * @return The result of the operation.
     */
    @Override
    public LazyNumber eval(final LazyNumber v1, final LazyNumber v2) {
        if (v2 != null) {
            throw new ExpressionException("Did not expect a second parameter for unary operator");
        }
        return new LazyNumber() {
            @Override
            public String getString() {
                return String.valueOf(UnaryOperator.this.evalUnary(v1.eval()));
            }

            @Override
            public BigDecimal eval() {
                return UnaryOperator.this.evalUnary(v1.eval());
            }
        };
    }



    /**
     * Implementation of this operator eval() calling unary operator evaluation method.
     *
     * @param v1
     *            The first <code>BigDecimal</code> parameter.
     * @param v2
     *            The second <code>BigDecimal</code> parameter. Expected to be null
     * @return The result of the operation.
     */
    public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
        if (v2 != null) {
            throw new ExpressionException("Did not expect a second parameter for unary operator");
        }
        return evalUnary(v1);
    }



    /**
     * Implementation of this prefix unary operator specific eval() method.
     *
     * @param v1
     *            The parameter.
     * @return The result of the operation.
     */
    public abstract BigDecimal evalUnary(BigDecimal v1);
}