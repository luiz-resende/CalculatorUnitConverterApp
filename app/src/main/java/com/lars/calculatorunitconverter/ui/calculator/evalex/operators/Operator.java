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


import com.lars.calculatorunitconverter.ui.calculator.evalex.ExpressionSettings;
import com.lars.calculatorunitconverter.ui.calculator.evalex.lazynumber.LazyNumber;

import java.math.BigDecimal;


/**
 * Abstract implementation of an operator.
 */
public abstract class Operator implements OperatorInterface {


    /**
     * Precedence for addition (+) and subtraction (-) operators.
     */
    public static final int OPERATOR_PRECEDENCE_ADDITIVE = 20;
    /**
     * Precedence for multiplication (*), division (/) and modulo (%) operators.
     */
    public static final int OPERATOR_PRECEDENCE_MULTIPLICATIVE = 30;
    /**
     * Precedence for power (^) operator.
     */
    public static final int OPERATOR_PRECEDENCE_POWER = 40;
    /**
     * Optional higher precedence for power (^) operator. {@link ExpressionSettings}
     */
    public static final int OPERATOR_PRECEDENCE_POWER_HIGHER = 80;
    /**
     * Precedence for prefix unary operators ('+' and '-').
     */
    public static final int OPERATOR_PRECEDENCE_UNARY = 60;
    /**
     * Precedence for equality operators ('=', '==', '!=' and '<>').
     */
    public static final int OPERATOR_PRECEDENCE_EQUALITY = 7;
    /**
     * Precedence for comparative operators ('<', '>', '<=' and '>=').
     */
    public static final int OPERATOR_PRECEDENCE_COMPARISON = 10;
    /**
     * Precedence for OR operator (||).
     */
    public static final int OPERATOR_PRECEDENCE_OR = 2;
    /**
     * Precedence for AND operator (&&).
     */
    public static final int OPERATOR_PRECEDENCE_AND = 4;



    /**
     * Exception message for missing operators.
     */
    public static final String MISSING_OPERATOR = "Missing operator";
    /**
     * Exception message for missing operator's parameters.
     */
    public static final String MISSING_PARAMETERS_FOR_OPERATOR = "Missing parameter(s) for operator ";



    /**
     * This operators name (pattern).
     */
    protected String oper;

    /**
     * Operators precedence.
     */
    protected int precedence;

    /**
     * Operator is left associative.
     */
    protected boolean leftAssoc;

    /**
     * Number of operands expected for this operator.
     */
    protected int numberOperands;

    /**
     * Number of operands expected for this operator.
     */
    protected boolean booleanOperator = false;



    /**
     * Creates a new boolean operator for use in the constructed expression.
     *
     * @param oper
     *            The operator name (pattern).
     * @param precedence
     *            The operators precedence.
     * @param leftAssoc
     *            <code>true</code> if the operator is left associative,
     *            else <code>false</code>.
     * @param numberOperands
     *            The number of operands that are expected for this operator. Added for required conditionals.
     * @param booleanOperator
     *            Whether this operator is boolean.
     */
    protected Operator(String oper, int precedence, boolean leftAssoc, int numberOperands, boolean booleanOperator) {
        this.oper = oper;
        this.precedence = precedence;
        this.leftAssoc = leftAssoc;
        this.numberOperands = numberOperands;
        this.booleanOperator = booleanOperator;
    }



    /**
     * Creates a new non-boolean operator for use in the constructed expression.
     *
     * @param oper
     *            The operator name (pattern).
     * @param precedence
     *            The operators precedence.
     * @param leftAssoc
     *            <code>true</code> if the operator is left associative,
     *            else <code>false</code>.
     * @param numberOperands
     *            The number of operands that are expected for this operator. Added for required conditionals.
     */
    protected Operator(String oper, int precedence, boolean leftAssoc, int numberOperands) {
        this.oper = oper;
        this.precedence = precedence;
        this.leftAssoc = leftAssoc;
        this.numberOperands = numberOperands;
    }



    /**
     * @return The String that is used to denote the operator in the expression.
     */
    public String getOper() {
        return oper;
    }



    /**
     * @return the precedence value of this operator.
     */
    public int getPrecedence() {
        return precedence;
    }



    /**
     * @return <code>true</code> if this operator is left associative.
     */
    public boolean isLeftAssoc() {
        return leftAssoc;
    }



    /**
     * @return the number of operands for this operator. Either 1 or 2.
     */
    public int getNumberOfOperands() {
        return numberOperands;
    }



    /**
     * @return <code>true</code> if this operator evaluates to a boolean
     *         expression.
     */
    public boolean isBooleanOperator() {
        return booleanOperator;
    }





    /**
     * Implementation of this operator supporting either 1 or 2 operands.
     *
     * @param v1
     * 			The first operand expected for the operation.
     * @param v2
     * 			The second operand expected for the operation. For postfix unary operators, v2=null condition was added.
     * @return
     * 			LazyNumber object. The result of the operation.
     */
    public LazyNumber eval(final LazyNumber v1, final LazyNumber v2) {
        if(v2 == null) {  // Condition to accept postfix unary operators (e.g. factorial operator '!')
            return new LazyNumber() {
                public BigDecimal eval() {
                    return Operator.this.eval(v1.eval(), null);
                }

                public String getString() {
                    return String.valueOf(Operator.this.eval(v1.eval(), null));
                }
            };
        }
        else {
            return new LazyNumber() {
                public BigDecimal eval() {
                    return Operator.this.eval(v1.eval(), v2.eval());
                }

                public String getString() {
                    return String.valueOf(Operator.this.eval(v1.eval(), v2.eval()));
                }
            };
        }
    }

}