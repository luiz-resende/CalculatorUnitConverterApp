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
@SuppressWarnings("UnnecessaryInterfaceModifier")
public interface VariableInterface {



    /**
     * @return	The String that is used to denote the variable in the expression.
     */
    public abstract String getVar();



    /**
     * @return	This variable's {@link BigDecimal} value in the expression.
     */
    public abstract BigDecimal getValue();



    /**
     * @return	This variable's {@link LazyNumber} value in the expression.
     */
    public abstract LazyNumber getLazyValue();



    /**
     * Implementation for this variable for evaluation.
     *
     * @param value
     * 			{@link BigDecimal} value for this variable.
     * @return
     * 			Sets the value for the variable.
     */
    //public abstract BigDecimal eval(BigDecimal value);

}