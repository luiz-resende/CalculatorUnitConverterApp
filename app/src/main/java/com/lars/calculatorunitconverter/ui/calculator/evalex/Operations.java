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

import com.lars.calculatorunitconverter.ui.calculator.evalex.Expression.ExpressionException;
import com.lars.calculatorunitconverter.ui.calculator.evalex.functions.Function;
import com.lars.calculatorunitconverter.ui.calculator.evalex.lazynumber.LazyIfNumber;
import com.lars.calculatorunitconverter.ui.calculator.evalex.lazynumber.LazyNumber;
import com.lars.calculatorunitconverter.ui.calculator.evalex.operators.Operator;
import com.lars.calculatorunitconverter.ui.calculator.evalex.variables.Variable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;


/**
 * Operations class contains all the predefined built-in operators, functions and variables that can
 * be directly used when constructing an expression. These are later imported to the Expression.java.
 */
@SuppressWarnings({"unused", "RedundantCast", "RedundantSuppression", "UnpredictableBigDecimalConstructorCall"})
public class Operations {


    /**
     * Indices for the built-in operators.
     */
    private static final int ADDITION = 0;
    private static final int SUBTRACTION = 1;
    private static final int MULTIPLICATION = 2;
    private static final int DIVISION = 3;
    private static final int POWER = 4;
    private static final int MODULO = 5;
    private static final int UNARY_MINUS = 6;
    private static final int UNARY_PLUS = 7;
    private static final int LOGICAL_AND = 8;
    private static final int LOGICAL_OR = 9;
    private static final int LOGICAL_GREATER = 10;
    private static final int LOGICAL_GT_EQUAL = 11;
    private static final int LOGICAL_LESS = 12;
    private static final int LOGICAL_LT_EQUAL = 13;
    private static final int EQUAL = 14;
    private static final int LOGICAL_EQUAL = 15;
    private static final int LOGICAL_NOT_EQUAL = 16;
    private static final int LOGICAL_NOT_EQUAL_2 = 17;


    /**
     * Indices for the built-in functions.
     */
    private static final int FACTORIAL = 0;
    private static final int LOGICAL_NOT = 1;
    private static final int LOGICAL_IF = 2;
    private static final int RANDOM = 3;
    private static final int SINE_RAD = 4;
    private static final int COSINE_RAD = 5;
    private static final int TANGENT_RAD = 6;
    private static final int COTANGENT_RAD = 7;
    private static final int SECANT_RAD = 8;
    private static final int COSSECANT_RAD = 9;
    private static final int SINE_DEG = 10;
    private static final int COSINE_DEG = 11;
    private static final int TANGENT_DEG = 12;
    private static final int COTANGENT_DEG = 13;
    private static final int SECANT_DEG = 14;
    private static final int COSSECANT_DEG = 15;
    private static final int ARCSINE_RAD = 16;
    private static final int ARCCOSINE_RAD = 17;
    private static final int ARCTANGENT_RAD = 18;
    private static final int ARCCOTANGENT_RAD = 19;
    private static final int ARCTANGENT2_RAD = 20;
    private static final int ARCSINE_DEG = 21;
    private static final int ARCCOSINE_DEG = 22;
    private static final int ARCTANGENT_DEG = 23;
    private static final int ARCCOTANGENT_DEG = 24;
    private static final int ARCTANGENT2_DEG = 25;
    private static final int SINE_HYPERBOLIC = 26;
    private static final int COSINE_HYPERBOLIC = 27;
    private static final int TANGENT_HYPERBOLIC = 28;
    private static final int SECANT_HYPERBOLIC = 29;
    private static final int COSSECANT_HYPERBOLIC = 30;
    private static final int COTANGENT_HYPERBOLIC = 31;
    private static final int ARCSINE_HYPERBOLIC = 32;
    private static final int ARCCOSINE_HYPERBOLIC = 33;
    private static final int ARCTANGENT_HYPERBOLIC = 34;
    private static final int DEGREES_TO_RADIANS = 35;
    private static final int RADIANS_TO_DEGREES = 36;
    private static final int MAXIMUM = 37;
    private static final int MINIMUM = 38;
    private static final int ABSOLUTE = 39;
    private static final int NATURAL_LOGARITHM = 40;
    private static final int LOGARITHM_BASE10 = 41;
    private static final int ROUND_VALUE = 42;
    private static final int FLOOR_VALUE = 43;
    private static final int CEILING_VALUE = 44;
    private static final int SQUARE_ROOT = 45;


    /**
     * Indices for the built-in variables.
     */
    private static final int VAR_PI = 0;
    private static final int VAR_EULER = 1;
    private static final int VAR_NULL = 2;
    private static final int VAR_TRUE = 3;
    private static final int VAR_FALSE = 4;
    private static final int VAR_PI2 = 5;
    private static final int VAR_PHI = 6;
    private static final int VAR_PHI2 = 7;


    /**
     * Array of {@link Operator} holding the built-in operators.
     */
    private static final Operator[] INITIAL_OPERATORS = new Operator[18];
    /**
     * Array of {@link Function} holding the built-in functions.
     */
    private static final Function[] INITIAL_FUNCTIONS = new Function[46];
    /**
     * Array of {@link Variable} holding the built-in variables.
     */
    private static final Variable[] INITIAL_VARIABLES = new Variable[8];


    /**
     * Definition of PI as a constant, can be used in expressions as variable.
     */
    public static final BigDecimal PI = new BigDecimal(
            "3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679");
    /**
     * Definition of e: "Euler's number" as a constant, can be used in expressions as variable.
     */
    public static final BigDecimal e = new BigDecimal(
            "2.718281828459045235360287471352662497757247093699959574966967627724076630353547594571382178525166427");
    /**
     * Definition of Golden Ratio PHI as a constant, can be used in expressions as variable.
     */
    public static final BigDecimal PHI = new BigDecimal(
            "1.6180339887498948482045868343656381177203091798057628621354486227052604628189024497072072041893911374");
    /**
     * Definition of <code>true</code> as a constant, can be used in expressions as variable.
     */
    public static final BigDecimal TRUE = BigDecimal.ONE;
    /**
     * Definition of <code>false</code> as a constant, can be used in expressions as variable.
     */
    public static final BigDecimal FALSE = BigDecimal.ZERO;



    /**
     * Default constructor.
     */
    public Operations() {}


    /**
     * Method to create the built-in operators and populate the array of {@link Operator}.
     *
     * @param mc	{@link MathContext} to be use for {@link BigDecimal} calculations.
     */
    private static void createOperators(MathContext mc) {
        INITIAL_OPERATORS[ADDITION] = new Operator("+", Operator.OPERATOR_PRECEDENCE_ADDITIVE, true, 2) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return v1.add(v2, mc);
            }
        };
        INITIAL_OPERATORS[SUBTRACTION] = new Operator("-", Operator.OPERATOR_PRECEDENCE_ADDITIVE, true, 2) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return v1.subtract(v2, mc);
            }
        };
        INITIAL_OPERATORS[MULTIPLICATION] = new Operator("*", Operator.OPERATOR_PRECEDENCE_MULTIPLICATIVE, true, 2) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return v1.multiply(v2, mc);
            }
        };
        INITIAL_OPERATORS[DIVISION] = new Operator("/", Operator.OPERATOR_PRECEDENCE_MULTIPLICATIVE, true, 2) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return v1.divide(v2, mc);
            }
        };
        INITIAL_OPERATORS[POWER] = new Operator("^", Expression.powerOperatorPrecedence, false, 2) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                // Thanks to Gene Marin:
                // http://stackoverflow.com/questions/3579779/how-to-do-a-fractional-power-on-bigdecimal-in-java
                int signOf2 = v2.signum();
                double dn1 = v1.doubleValue();
                v2 = v2.multiply(new BigDecimal(signOf2)); // n2 is now positive
                BigDecimal remainderOf2 = v2.remainder(BigDecimal.ONE);
                BigDecimal n2IntPart = v2.subtract(remainderOf2);
                BigDecimal intPow = v1.pow(n2IntPart.intValueExact(), mc);
                BigDecimal doublePow = BigDecimal.valueOf(Math.pow(dn1, remainderOf2.doubleValue()));

                BigDecimal result = intPow.multiply(doublePow, mc);
                if (signOf2 == -1) {
                    result = BigDecimal.ONE.divide(result, mc.getPrecision(), RoundingMode.HALF_UP);
                }
                return result;
            }
        };
        INITIAL_OPERATORS[MODULO] = new Operator("%", Operator.OPERATOR_PRECEDENCE_MULTIPLICATIVE, true, 2) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return v1.remainder(v2, mc);
            }
        };
        INITIAL_OPERATORS[LOGICAL_AND] = new Operator("&&", Operator.OPERATOR_PRECEDENCE_AND, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);

                boolean b1 = v1.compareTo(BigDecimal.ZERO) != 0;

                if (!b1) {
                    return BigDecimal.ZERO;
                }

                boolean b2 = v2.compareTo(BigDecimal.ZERO) != 0;
                return b2 ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        };
        INITIAL_OPERATORS[LOGICAL_OR] = new Operator("||", Operator.OPERATOR_PRECEDENCE_OR, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);

                boolean b1 = v1.compareTo(BigDecimal.ZERO) != 0;

                if (b1) {
                    return BigDecimal.ONE;
                }

                boolean b2 = v2.compareTo(BigDecimal.ZERO) != 0;
                return b2 ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        };
        INITIAL_OPERATORS[LOGICAL_GREATER] = new Operator(">", Operator.OPERATOR_PRECEDENCE_COMPARISON, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return v1.compareTo(v2) > 0 ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        };
        INITIAL_OPERATORS[LOGICAL_GT_EQUAL] = new Operator(">=", Operator.OPERATOR_PRECEDENCE_COMPARISON, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return v1.compareTo(v2) >= 0 ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        };
        INITIAL_OPERATORS[LOGICAL_LESS] = new Operator("<", Operator.OPERATOR_PRECEDENCE_COMPARISON, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return v1.compareTo(v2) < 0 ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        };
        INITIAL_OPERATORS[LOGICAL_LT_EQUAL] = new Operator("<=", Operator.OPERATOR_PRECEDENCE_COMPARISON, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return v1.compareTo(v2) <= 0 ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        };
        INITIAL_OPERATORS[EQUAL] = new Operator("=", Operator.OPERATOR_PRECEDENCE_EQUALITY, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                if (v1.equals(v2)) {
                    return BigDecimal.ONE;
                }
                if (v1 == null || v2 == null) {
                    return BigDecimal.ZERO;
                }
                return v1.compareTo(v2) == 0 ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        };
        INITIAL_OPERATORS[LOGICAL_EQUAL] = new Operator("==", Operator.OPERATOR_PRECEDENCE_EQUALITY, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                return ((Operator) INITIAL_OPERATORS[EQUAL]).eval(v1, v2);  // operators.get("=")
            }
        };
        INITIAL_OPERATORS[LOGICAL_NOT_EQUAL] = new Operator("!=", Operator.OPERATOR_PRECEDENCE_EQUALITY, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                if (v1.equals(v2)) {
                    return BigDecimal.ZERO;
                }
                if (v1 == null || v2 == null) {
                    return BigDecimal.ONE;
                }
                return v1.compareTo(v2) != 0 ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        };
        INITIAL_OPERATORS[LOGICAL_NOT_EQUAL_2] = new Operator("<>", Operator.OPERATOR_PRECEDENCE_EQUALITY, false, 2, true) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                assertNotNull(v1, v2);
                return ((Operator) INITIAL_OPERATORS[LOGICAL_NOT_EQUAL]).eval(v1, v2);  // operators.get("!=")
            }
        };
        INITIAL_OPERATORS[UNARY_MINUS] = new Operator("u-", Operator.OPERATOR_PRECEDENCE_UNARY, false, 1) {  //UnaryOperator
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {  //evalUnary(BigDecimal v1)
                return v1.multiply(new BigDecimal(-1));
            }
        };
        INITIAL_OPERATORS[UNARY_PLUS] = new Operator("u+", Operator.OPERATOR_PRECEDENCE_UNARY, false, 1) {
            @Override
            public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
                return v1.multiply(BigDecimal.ONE);
            }
        };
    }



    /**
     * Method to create the built-in functions and populate the array of {@link Function}.
     *
     * @param mc	{@link MathContext} to be use for {@link BigDecimal} calculations.
     */
    private static void createFunctions(MathContext mc) {
        INITIAL_FUNCTIONS[FACTORIAL] = new Function("FACT", 1, false) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));

                int number = parameters.get(0).intValue();
                BigDecimal factorial = BigDecimal.ONE;
                for (int i = 1; i <= number; i++) {
                    factorial = factorial.multiply(new BigDecimal(i));
                }
                return factorial;
            }
        };
        INITIAL_FUNCTIONS[LOGICAL_NOT] = new Function("NOT", 1, true) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                boolean zero = parameters.get(0).compareTo(BigDecimal.ZERO) == 0;
                return zero ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        };
        INITIAL_FUNCTIONS[LOGICAL_IF] = new Function("IF", 3) {
            @Override
            public LazyNumber lazyEval(List<LazyNumber> lazyParams) {
                return new LazyIfNumber(lazyParams);
            }

            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {return null;}
        };
        INITIAL_FUNCTIONS[RANDOM] = new Function("RANDOM", 0) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                double d = Math.random();
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[SINE_RAD] = new Function("SINR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.sin(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[COSINE_RAD] = new Function("COSR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.cos(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[TANGENT_RAD] = new Function("TANR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.tan(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[COTANGENT_RAD] = new Function("COTR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: cot(x) = cos(x) / sin(x) = 1 / tan(x)
                double one = 1.0;
                double d = Math.tan(parameters.get(0).doubleValue());
                return new BigDecimal((one / d), mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[SECANT_RAD] = new Function("SECR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: sec(x) = 1 / cos(x)
                double one = 1.0;
                double d = Math.cos(parameters.get(0).doubleValue());
                return new BigDecimal((one / d), mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[COSSECANT_RAD] = new Function("CSCR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: csc(x) = 1 / sin(x)
                double one = 1.0;
                double d = Math.sin(parameters.get(0).doubleValue());
                return new BigDecimal((one / d), mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[SINE_DEG] = new Function("SIN", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.sin(Math.toRadians(parameters.get(0).doubleValue()));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[COSINE_DEG] = new Function("COS", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.cos(Math.toRadians(parameters.get(0).doubleValue()));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[TANGENT_DEG] = new Function("TAN", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.tan(Math.toRadians(parameters.get(0).doubleValue()));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[COTANGENT_DEG] = new Function("COT", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: cot(x) = cos(x) / sin(x) = 1 / tan(x)
                double one = 1;
                double d = Math.tan(Math.toRadians(parameters.get(0).doubleValue()));
                return new BigDecimal((one / d), mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[SECANT_DEG] = new Function("SEC", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: sec(x) = 1 / cos(x)
                double one = 1.0;
                double d = Math.cos(Math.toRadians(parameters.get(0).doubleValue()));
                return new BigDecimal((one / d), mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[COSSECANT_DEG] = new Function("CSC", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: csc(x) = 1 / sin(x)
                double one = 1;
                double d = Math.sin(Math.toRadians(parameters.get(0).doubleValue()));
                return new BigDecimal((one / d), mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCSINE_RAD] = new Function("ASINR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.asin(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCCOSINE_RAD] = new Function("ACOSR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.acos(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCTANGENT_RAD] = new Function("ATANR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.atan(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCCOTANGENT_RAD] = new Function("ACOTR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: acot(x) = atan(1/x)
                if (parameters.get(0).doubleValue() == 0) {
                    throw new ExpressionException("Number must not be 0");
                }
                double d = Math.atan(1 / parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCTANGENT2_RAD] = new Function("ATAN2R", 2) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0), parameters.get(1));
                double d = Math.atan2(parameters.get(0).doubleValue(), parameters.get(1).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCSINE_DEG] = new Function("ASIN", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.toDegrees(Math.asin(parameters.get(0).doubleValue()));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCCOSINE_DEG] = new Function("ACOS", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.toDegrees(Math.acos(parameters.get(0).doubleValue()));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCTANGENT_DEG] = new Function("ATAN", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.toDegrees(Math.atan(parameters.get(0).doubleValue()));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCCOTANGENT_DEG] = new Function("ACOT", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: acot(x) = atan(1/x)
                if (parameters.get(0).doubleValue() == 0) {
                    throw new ExpressionException("Number must not be 0");
                }
                double d = Math.toDegrees(Math.atan(1 / parameters.get(0).doubleValue()));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCTANGENT2_DEG] = new Function("ATAN2", 2) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0), parameters.get(1));
                double d = Math.toDegrees(Math.atan2(parameters.get(0).doubleValue(), parameters.get(1).doubleValue()));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[SINE_HYPERBOLIC] = new Function("SINH", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.sinh(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[COSINE_HYPERBOLIC] = new Function("COSH", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.cosh(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[TANGENT_HYPERBOLIC] = new Function("TANH", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.tanh(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[SECANT_HYPERBOLIC] = new Function("SECH", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: sech(x) = 1 / cosh(x)
                double one = 1;
                double d = Math.cosh(parameters.get(0).doubleValue());
                return new BigDecimal((one / d), mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[COSSECANT_HYPERBOLIC] = new Function("CSCH", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: csch(x) = 1 / sinh(x)
                double one = 1;
                double d = Math.sinh(parameters.get(0).doubleValue());
                return new BigDecimal((one / d), mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[COTANGENT_HYPERBOLIC] = new Function("COTH", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: coth(x) = 1 / tanh(x)
                double one = 1;
                double d = Math.tanh(parameters.get(0).doubleValue());
                return new BigDecimal((one / d), mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCSINE_HYPERBOLIC] = new Function("ASINH", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: asinh(x) = ln(x + sqrt(x^2 + 1))
                double d = Math.log(parameters.get(0).doubleValue()
                        + (Math.sqrt(Math.pow(parameters.get(0).doubleValue(), 2) + 1)));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCCOSINE_HYPERBOLIC] = new Function("ACOSH", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: acosh(x) = ln(x + sqrt(x^2 - 1))
                if (Double.compare(parameters.get(0).doubleValue(), 1) < 0) {
                    throw new ExpressionException("Number must be x >= 1");
                }
                double d = Math.log(parameters.get(0).doubleValue()
                        + (Math.sqrt(Math.pow(parameters.get(0).doubleValue(), 2) - 1)));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ARCTANGENT_HYPERBOLIC] = new Function("ATANH", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                // Formula: atanh(x) = 0.5*ln((1 + x)/(1 - x))
                if (Math.abs(parameters.get(0).doubleValue()) > 1
                        || Math.abs(parameters.get(0).doubleValue()) == 1) {
                    throw new ExpressionException("Number must be |x| < 1");
                }
                double d = 0.5
                        * Math
                        .log((1 + parameters.get(0).doubleValue()) / (1 - parameters.get(0).doubleValue()));
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[DEGREES_TO_RADIANS] = new Function("RAD", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.toRadians(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[RADIANS_TO_DEGREES] = new Function("DEG", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.toDegrees(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[MAXIMUM] = new Function("MAX", -1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                if (parameters.isEmpty()) {
                    throw new ExpressionException("MAX requires at least one parameter");
                }
                BigDecimal max = null;
                for (BigDecimal parameter : parameters) {
                    assertNotNull(parameter);
                    if (max == null || parameter.compareTo(max) > 0) {
                        max = parameter;
                    }
                }
                return max;
            }
        };
        INITIAL_FUNCTIONS[MINIMUM] = new Function("MIN", -1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                if (parameters.isEmpty()) {
                    throw new ExpressionException("MIN requires at least one parameter");
                }
                BigDecimal min = null;
                for (BigDecimal parameter : parameters) {
                    assertNotNull(parameter);
                    if (min == null || parameter.compareTo(min) < 0) {
                        min = parameter;
                    }
                }
                return min;
            }
        };
        INITIAL_FUNCTIONS[ABSOLUTE] = new Function("ABS", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                return parameters.get(0).abs(mc);
            }
        };
        INITIAL_FUNCTIONS[NATURAL_LOGARITHM] = new Function("LOG", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.log(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[LOGARITHM_BASE10] = new Function("LOG10", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                double d = Math.log10(parameters.get(0).doubleValue());
                return new BigDecimal(d, mc); // NOSONAR - false positive, mc is passed
            }
        };
        INITIAL_FUNCTIONS[ROUND_VALUE] = new Function("ROUND", 2) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0), parameters.get(1));
                BigDecimal toRound = parameters.get(0);
                int precision = parameters.get(1).intValue();
                return toRound.setScale(precision, mc.getRoundingMode());
            }
        };
        INITIAL_FUNCTIONS[FLOOR_VALUE] = new Function("FLOOR", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                BigDecimal toRound = parameters.get(0);
                return toRound.setScale(0, RoundingMode.FLOOR);
            }
        };
        INITIAL_FUNCTIONS[CEILING_VALUE] = new Function("CEILING", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));
                BigDecimal toRound = parameters.get(0);
                return toRound.setScale(0, RoundingMode.CEILING);
            }
        };
        INITIAL_FUNCTIONS[SQUARE_ROOT] = new Function("SQRT", 1) {
            @Override
            public BigDecimal eval(List<BigDecimal> parameters) {
                assertNotNull(parameters.get(0));

                // From The Java Programmers Guide To numerical Computing
                // (Ronald Mak, 2003)

                BigDecimal x = parameters.get(0);
                if (x.compareTo(BigDecimal.ZERO) == 0) {
                    return new BigDecimal(0);
                }
                if (x.signum() < 0) {
                    throw new ExpressionException("Argument to SQRT() function must not be negative");
                }
                BigInteger n = x.movePointRight(mc.getPrecision() << 1).toBigInteger();

                int bits = (n.bitLength() + 1) >> 1;
                BigInteger ix = n.shiftRight(bits);
                BigInteger ixPrev;
                BigInteger test;
                do {
                    ixPrev = ix;
                    ix = ix.add(n.divide(ix)).shiftRight(1);
                    // Give other threads a chance to work
                    Thread.yield();
                    test = ix.subtract(ixPrev).abs();
                } while (test.compareTo(BigInteger.ZERO) != 0 && test.compareTo(BigInteger.ONE) != 0);

                return new BigDecimal(ix, mc.getPrecision());
            }
        };
    }



    /**
     * Method to create the built-in variables and populate the array of {@link Variable}.
     *
     * @param mc	{@link MathContext} to be use for {@link BigDecimal} calculations.
     */
    private static void createVariables(MathContext mc) {
        INITIAL_VARIABLES[VAR_PI] = new Variable("PI", PI){};
        INITIAL_VARIABLES[VAR_PI2] = new Variable("π", PI){};
        INITIAL_VARIABLES[VAR_PHI] = new Variable("PHI", PHI){};
        INITIAL_VARIABLES[VAR_PHI2] = new Variable("φ", PHI){};
        INITIAL_VARIABLES[VAR_EULER] = new Variable("e", e){};
        INITIAL_VARIABLES[VAR_NULL] = new Variable("NULL", null){};
        INITIAL_VARIABLES[VAR_TRUE] = new Variable("TRUE", new BigDecimal(1, mc)){};
        INITIAL_VARIABLES[VAR_FALSE] = new Variable("FALSE", new BigDecimal(0, mc)){};
    }



    /**
     * Gets the array containing the built-in operators.
     *
     * @param mc	{@link MathContext} to be use for {@link BigDecimal} calculations.
     *
     * @return	Array of {@link Operator}.
     */
    public static Operator[] getBuiltInOperators(MathContext mc) {
        createOperators(mc);
        return INITIAL_OPERATORS;
    }



    /**
     * Gets the built-in operators.
     *
     * @param oper	{@link String} representing the operator characters.
     * @param numberOperands	{@link Integer} for the number of expected operands.
     *
     * @return	Built-in {@link Operator}.
     */
    public static Operator getBuiltInOperators(String oper, int numberOperands) {
        if(numberOperands < 2) {
            if(oper.equals("+")) {
                return INITIAL_OPERATORS[UNARY_PLUS];
            }
            else if(oper.equals("-")) {
                return INITIAL_OPERATORS[UNARY_MINUS];
            }
        }
        else {
            switch(oper) {
                case "+":
                    return INITIAL_OPERATORS[ADDITION];
                case "-":
                    return INITIAL_OPERATORS[SUBTRACTION];
                case "*":
                    return INITIAL_OPERATORS[MULTIPLICATION];
                case "/":
                    return INITIAL_OPERATORS[DIVISION];
                case "^":
                    return INITIAL_OPERATORS[POWER];
                case "%":
                    return INITIAL_OPERATORS[MODULO];
                case "&&":
                    return INITIAL_OPERATORS[LOGICAL_AND];
                case "||":
                    return INITIAL_OPERATORS[LOGICAL_OR];
                case ">":
                    return INITIAL_OPERATORS[LOGICAL_GREATER];
                case ">=":
                    return INITIAL_OPERATORS[LOGICAL_GT_EQUAL];
                case "<":
                    return INITIAL_OPERATORS[LOGICAL_LESS];
                case "<=":
                    return INITIAL_OPERATORS[LOGICAL_LT_EQUAL];
                case "=":
                    return INITIAL_OPERATORS[EQUAL];
                case "==":
                    return INITIAL_OPERATORS[LOGICAL_EQUAL];
                case "!=":
                    return INITIAL_OPERATORS[LOGICAL_NOT_EQUAL];
                case "<>":
                    return INITIAL_OPERATORS[LOGICAL_NOT_EQUAL_2];
                default:
            }
        }
        return null;
    }



    /**
     * Gets the array containing the built-in functions.
     *
     * @param mc	{@link MathContext} to be use for {@link BigDecimal} calculations.
     *
     * @return	Array of {@link Function}.
     */
    public static Function[] getBuiltInFunctions(MathContext mc) {
        createFunctions(mc);
        return INITIAL_FUNCTIONS;
    }



    /**
     * Gets the array containing the built-in variables.
     *
     * @param mc	{@link MathContext} to be use for {@link BigDecimal} calculations.
     *
     * @return	Array of {@link Variable}.
     */
    public static Variable[] getBuiltInVariables(MathContext mc) {
        createVariables(mc);
        return INITIAL_VARIABLES;
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

}