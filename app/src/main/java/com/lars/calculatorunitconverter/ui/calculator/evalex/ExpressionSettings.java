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


import com.lars.calculatorunitconverter.ui.calculator.evalex.operators.Operator;

import java.math.MathContext;


/**
 * Expression settings can be used to set certain defaults, when creating a new expression. Settings
 * are read only and can be created using a {@link ExpressionSettings#builder()}.
 *
 * @see Expression#Expression(String, ExpressionSettings)
 *
 * MODIFICATION 2021-APR-27 -> Added implicit multiplication enabling boolean flag.
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public class ExpressionSettings {


    /**
     * The math context to use. Default is {@link MathContext#DECIMAL32}.
     */
    private MathContext mathContext;


    /**
     * The precedence of the power (^) operator. Default is 40.
     */
    private int powerOperatorPrecedence;


    /**
     * Boolean primitive to allow or not implicit multiplication of type '2sin(90)'
     */
    private boolean implicitMultiplication;


    /**
     * Default constructor
     */
    private ExpressionSettings() {}


    /**
     * Create a new settings object.
     *
     * @param mathContext             The default math context to use.
     * @param powerOperatorPrecedence The default power of operator precedence.
     */
    public ExpressionSettings(MathContext mathContext, int powerOperatorPrecedence) {
        this.mathContext = mathContext;
        this.powerOperatorPrecedence = powerOperatorPrecedence;
        this.implicitMultiplication = true;
    }


    /**
     * Create a new settings object.
     *
     * @param mathContext             The default math context to use.
     * @param powerOperatorPrecedence The default power of operator precedence.
     */
    public ExpressionSettings(MathContext mathContext, int powerOperatorPrecedence, boolean enableImplicitMulti) {
        this.mathContext = mathContext;
        this.powerOperatorPrecedence = powerOperatorPrecedence;
        this.implicitMultiplication = enableImplicitMulti;
    }


    /**
     * Get the current math context.
     *
     * @return The current math context.
     */
    public MathContext getMathContext() {
        return mathContext;
    }


    /**
     * Get the current power precedence.
     *
     * @return The current power precedence.
     */
    public int getPowerOperatorPrecedence() {
        return powerOperatorPrecedence;
    }


    /**
     * Get the current flag for the implicit multiplication.
     *
     * @return The current boolean value for the implicit multiplication.
     */
    public boolean getImplicitMulti() {
        return implicitMultiplication;
    }


    public static Builder builder() {
        return new Builder();
    }


    /**
     * A builder to create a read-only {@link ExpressionSettings} instance.
     */
    public static class Builder {

        private MathContext mathContext = MathContext.DECIMAL32;

        private int powerOperatorPrecedence = Operator.OPERATOR_PRECEDENCE_POWER;

        private boolean implicitMultiplication = true;

        public Builder mathContext(MathContext mathContext) {
            this.mathContext = mathContext;
            return this;
        }

        public Builder powerOperatorPrecedenceHigher() {
            this.powerOperatorPrecedence = Operator.OPERATOR_PRECEDENCE_POWER_HIGHER;
            return this;
        }

        public Builder powerOperatorPrecedence(int powerOperatorPrecedence) {
            this.powerOperatorPrecedence = powerOperatorPrecedence;
            return this;
        }

        public Builder implicitMulti(boolean enableImplicitMulti) {
            this.implicitMultiplication = enableImplicitMulti;
            return this;
        }

        public ExpressionSettings build() {
            return new ExpressionSettings(mathContext, powerOperatorPrecedence, implicitMultiplication);
        }
    }
}