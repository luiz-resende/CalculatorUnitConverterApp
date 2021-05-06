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

package com.lars.calculatorunitconverter.ui.calculator.evalex.functions;


import com.lars.calculatorunitconverter.ui.calculator.evalex.lazynumber.LazyNumber;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Abstract implementation of a direct function.<br>
 * <br>
 * This abstract implementation does implement lazyEval so that it returns
 * the result of eval.
 */
@SuppressWarnings("Convert2Diamond")
public abstract class Function implements FunctionInterface {


    /**
     * Name of this function.
     */
    protected String name;


    /**
     * Number of parameters expected for this function. <code>-1</code>
     * denotes a variable number of parameters.
     */
    protected int numParams;


    /**
     * Whether this function is a boolean function.
     */
    protected boolean booleanFunction;



    /**
     * Creates a new function with given name and parameter count.
     *
     * @param name
     *            The name of the function.
     * @param numParams
     *            The number of parameters for this function.
     *            <code>-1</code> denotes a variable number of parameters.
     * @param booleanFunction
     *            Whether this function is a boolean function.
     */
    protected Function(String name, int numParams, boolean booleanFunction) {
        this.name = name.toUpperCase(Locale.ROOT);
        this.numParams = numParams;
        this.booleanFunction = booleanFunction;
    }



    /**
     * Creates a new function with given name and parameter count.
     * The <code>booleanFunction</code> parameter is set to <code>false</code>.
     *
     * @param name
     *            The name of the function.
     * @param numParams
     *            The number of parameters for this function.
     *            <code>-1</code> denotes a variable number of parameters.
     */
    protected Function(String name, int numParams) {
        this(name, numParams, false);
    }



    /**
     * Gets the name of the given function.
     *
     * @return	The {@link String} representing the function name.
     */
    public String getName() {
        return name;
    }



    /**
     * Gets the number of parameters for the given function.
     *
     * @return	The {@link int} representing the number of parameters
     * 			expected for the function.
     */
    public int getNumParams() {
        return numParams;
    }



    /**
     * Gets the {@link boolean} flag indicating if this function number of
     * parameters varies.
     *
     * @return	The <code>true</code> if this function number of parameters
     * 			is less than 0, <code>false</code> otherwise.
     */
    public boolean numParamsVaries() {
        return numParams < 0;
    }



    /**
     * Gets the {@link boolean} flag indicating if this function is boolean.
     *
     * @return	The <code>true</code> if this function is boolean,
     * 			<code>false</code> otherwise.
     */
    public boolean isBooleanFunction() {
        return booleanFunction;
    }



    /**
     *
     */
    public LazyNumber lazyEval(final List<LazyNumber> lazyParams) {
        return new LazyNumber() {

            private List<BigDecimal> params;

            public BigDecimal eval() {
                return Function.this.eval(getParams());
            }

            public String getString() {
                return String.valueOf(Function.this.eval(getParams()));
            }

            private List<BigDecimal> getParams() {
                if (params == null) {
                    params = new ArrayList<BigDecimal>();
                    for (LazyNumber lazyParam : lazyParams) {
                        params.add(lazyParam.eval());
                    }
                }
                return params;
            }
        };
    }
}