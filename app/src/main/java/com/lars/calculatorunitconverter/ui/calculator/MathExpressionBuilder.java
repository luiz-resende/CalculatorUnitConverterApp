package com.lars.calculatorunitconverter.ui.calculator;

import net.objecthunter.exp4j.Expression;  // https://www.objecthunter.net/exp4j/index.html
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

@SuppressWarnings({"UnnecessaryLocalVariable", "WrapperTypeMayBePrimitive", "unused", "RedundantSuppression", "RedundantCast"})
public class MathExpressionBuilder {

    /* String variable containing mathematical expression typed */
    private final String math_expression;



    /* Initialized constructor */
    public MathExpressionBuilder(String inputExpression) {
        this.math_expression = inputExpression;
    }



    /*
    ** Method to build mathematical expression using exp4j library and exp4j custom defined functions
    * OUTPUT: Expression type object to be analysed
    */
    public Expression getExpression(){

        // Custom user exp4j function to calculate factorial of a number
        Function fct = new Function("fct", 1) {
            @Override
            public double apply(double... args) {
                int factor = (int) args[0];
                int counter = (int) args[0] - 1;
                if(factor == 0 || factor == 1) {
                    return 1;
                }
                else {
                    while(counter > 1) {
                        factor = factor * counter;
                        counter--;
                    }
                    return (double) factor;
                }
            }
        };

        // Custom user exp4j function to calculate percentage
        Function pct = new Function("pct", 1) {
            @Override
            public double apply(double... args) {
                double percentage = (args[0] / 100.0);
                return percentage;
            }
        };

        // Custom user exp4j function to convert from degrees to radians
        Function toRad = new Function("toRad", 1) {
            @Override
            public double apply(double... args) {
                double dgr2rad = (args[0] * (Math.PI / 180.0));
                return dgr2rad;
            }
        };

        // Custom user exp4j function to calculate arc sine and throw domain exception
        Function asin = new Function("asin"){
            @Override
            public double apply(double... args) {
                final Double arg = args[0];
                if(arg > 1.0 || arg < -1.0) {
                    throw new IllegalArgumentException("ArcSineOperandOutOfBounds");
                }
                return Math.asin(arg);
            }
        };

        // Custom user exp4j function to calculate arc cosine and throw domain exception
        Function acos = new Function("acos"){
            @Override
            public double apply(double... args) {
                final Double arg = args[0];
                if(arg > 1.0 || arg < -1.0) {
                    throw new IllegalArgumentException("ArcCosineOperandOutOfBounds");
                }
                return Math.asin(arg);
            }
        };

        // Custom user exp4j function to calculate natural logarithm and throw domain exception
        Function log = new Function("log"){
            @Override
            public double apply(double... args) {
                final double arg = args[0];
                if(arg <= 0.00000) {
                    throw new IllegalArgumentException("LnOperandOutOfBounds");
                }
                return Math.log(arg);
            }
        };

        // Custom user exp4j function to calculate base 10 logarithm and throw domain exception
        Function log10 = new Function("log10"){
            @Override
            public double apply(double... args) {
                final double arg = args[0];
                if(arg <= 0.00000) {
                    throw new IllegalArgumentException("LogOperandOutOfBounds");
                }
                return Math.log10(arg);
            }
        };

        // Custom user exp4j function to convert from rad to degrees
        Function toDeg = new Function("toDeg", 1) {
            @Override
            public double apply(double... args) {
                final Double arg = args[0];
                if(arg.isNaN()) {
                    throw new IllegalArgumentException("Radian2DegreeConversionNaN");
                }
                double rad2dgr = (args[0] * (180.0 / Math.PI));
                return rad2dgr;
            }
        };

        // Custom user exp4j operator to calculate factorial of a number
        Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                final int arg = (int) args[0];
                if ((double) arg != args[0]) {
                    throw new IllegalArgumentException("FactorialOperandNotInteger");
                }
                if (arg < 0) {
                    throw new IllegalArgumentException("FactorialOperandNotPositive");
                }
                double result = 1;
                for (int i = 1; i <= arg; i++) {
                    result *= i;
                }
                return result;
            }
        };

        // Custom user exp4j operator to calculate percentage of a number
        Operator percentage = new Operator("#%", 1, true, Operator.PRECEDENCE_POWER + 1) {
            @Override
            public double apply(double... args) {
                double percentage = (args[0] / 100.0);
                return percentage;
            }
        };


        Expression temp_expression = new ExpressionBuilder(this.math_expression).operator(factorial, percentage)
                .functions(acos, asin, log, log10, toDeg, toRad).build();


        return temp_expression;
    }



    /*
     ** Method to get mathematical expression using exp4j library and evaluate it.
     * OUTPUT: String type object with mathematical expression analysed
     */
    public Double getExpressionResult() {
        Expression mathExpress = getExpression();

        return mathExpress.evaluate();
    }
}
