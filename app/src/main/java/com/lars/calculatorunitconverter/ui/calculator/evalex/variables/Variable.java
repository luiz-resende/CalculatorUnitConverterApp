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

package com.lars.calculatorunitconverter.ui.calculator.evalex.variables;

import com.lars.calculatorunitconverter.ui.calculator.evalex.lazynumber.LazyNumber;

import java.math.BigDecimal;


/**
 * Abstract implementation of a variable.
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public abstract class Variable implements VariableInterface {



    /**
     * This variable {@link String} of characters.
     */
    protected String var;

    /**
     * This variable {@link BigDecimal} value.
     */
    protected BigDecimal value;

    /**
     * This variable {@link LazyNumber} value.
     */
    protected LazyNumber lazyValue;



    /**
     * Creates a new variable for use in the constructed expression.
     *
     * @param var
     *            The {@link String} of characters defining the variable.
     * @param value
     *            The {@link BigDecimal} value for this variable.
     */
    protected Variable(String var, BigDecimal value) {
        this.var = var;
        this.value = value;
        this.lazyValue = createLazyNumber(value);
    }



    /**
     * Creates a new variable for use in the constructed expression.
     *
     * @param var
     *            The {@link String} of characters defining the variable.
     */
    protected Variable(String var) {
        this.var = var;
        this.value = null;
        this.lazyValue = null;
    }



    /**
     * Construct a LazyNumber from a BigDecimal
     *
     * @return	New {@link LazyNumber} overridden methods.
     */
    public static LazyNumber createLazyNumber(final BigDecimal bigDecimal) {
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
     * @return	The String that is used to denote the variable in the expression.
     */
    public String getVar() {
        return var;
    }



    /**
     * @return	This variable's {@link BigDecimal} value in the expression.
     */
    public BigDecimal getValue() {
        return value;
    }



    /**
     * @return	This variable's {@link LazyNumber} value in the expression.
     */
    public LazyNumber getLazyValue() {
        return lazyValue;
    }

}