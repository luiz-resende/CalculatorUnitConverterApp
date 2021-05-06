package com.lars.calculatorunitconverter.ui.calculator;


import com.lars.calculatorunitconverter.ui.calculator.evalex.Expression;
import com.lars.calculatorunitconverter.ui.calculator.evalex.functions.Function;
import com.lars.calculatorunitconverter.ui.calculator.evalex.operators.Operator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;


@SuppressWarnings({"UnnecessaryLocalVariable", "BigDecimalMethodWithoutRoundingCalled", "FieldCanBeLocal", "FieldMayBeFinal", "ForLoopReplaceableByForEach", "unused", "RedundantSuppression", "UnpredictableBigDecimalConstructorCall", "RedundantCast"})
public class MathExpressionBuilderEvalEx {

    /* String variable containing mathematical expression typed */
    private final String math_expression;

    private final int PRECISION = 100;
    private final RoundingMode ROUND_MODE = RoundingMode.HALF_UP;
    private MathContext MC = new MathContext(this.PRECISION, this.ROUND_MODE);

    private final BigDecimal BD_180 = new BigDecimal("180.0", MC);
    private final BigDecimal BD_PI = new BigDecimal(
            "3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679", MC);
    private final BigDecimal BD_e = new BigDecimal(
            "2.71828182845904523536028747135266249775724709369995957496696762772407663", MC);



    /* Initialized constructor */
    public MathExpressionBuilderEvalEx(String inputExpression) {
        this.math_expression = inputExpression;
    }



    /*
    ** Method to build mathematical expression using exp4j library and exp4j custom defined functions
    * OUTPUT: Expression type object to be analysed
    */
    public Expression getExpression(){

        Expression mathExpressionEval = new Expression(this.math_expression, MC);
        mathExpressionEval.setPrecision(MC.getPrecision());
        mathExpressionEval.addOperators(factorial, percentage);
        //temp_expression.addOperator(percentage);
        mathExpressionEval.addFunctions(cos, sin, tan, acos, asin, atan, log, log10, sqrt, toDeg, toRad);  //
        /*temp_expression.addFunction(sin);
        temp_expression.addFunction(tan);
        temp_expression.addFunction(acos);
        temp_expression.addFunction(asin);
        temp_expression.addFunction(atan);
        temp_expression.addFunction(toDeg);
        temp_expression.addFunction(toRad);
        temp_expression.addFunction(log);
        temp_expression.addFunction(log10);
        temp_expression.addFunction(sqrt);*/

        return mathExpressionEval;
    }



    /**
     * Method to get mathematical expression using exp4j library and evaluate it.
     *
     * @return  {@link String} object with mathematical expression analysed
     */
    public BigDecimal getExpressionResult() {
        Expression mathExpressionEval = new Expression(this.math_expression, MC);
        mathExpressionEval.setPrecision(MC.getPrecision());
        mathExpressionEval.addOperators(factorial, percentage);
        mathExpressionEval.addFunctions(cos, sin, tan, acos, asin, atan, log, log10, sqrt, toDeg, toRad);

        return mathExpressionEval.eval();
    }



    // Custom user EvalEx function to calculate factorial of a number
    public Function fct = new Function("fct", 1) {
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            assertNotNull(parameters.get(0));
            if(!parameters.get(0).remainder(BigDecimal.ONE, MC).equals(BigDecimal.ZERO)) {
                throw new ArithmeticException("OperandMustBeInteger");
            }
            BigDecimal factorial = parameters.get(0);
            BigDecimal temp = parameters.get(0).subtract(BigDecimal.ONE, MC);
            if (factorial.compareTo(BigDecimal.ZERO) == 0 || factorial.compareTo(BigDecimal.ONE) == 0) {
                return BigDecimal.ONE;
            } else {
                while (temp.compareTo(BigDecimal.ONE) > 0) {
                    factorial = factorial.multiply(temp, MC);
                    temp = temp.subtract(BigDecimal.ONE, MC);
                }
                return factorial;
            }
        }
    };

    // Custom user EvalEx function to calculate percentage
    public Function pct = new Function("pct", 1) {
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            assertNotNull(parameters.get(0));
            BigDecimal cent = new BigDecimal("100.0", MC);
            return parameters.get(0).divide(cent);
        }
    };

    // Custom user EvalEx function to convert from degrees to radians
    public Function toRad = new Function("toRad", 1) {
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            assertNotNull(parameters.get(0));
            BigDecimal dgr2rad = parameters.get(0).multiply(BD_PI.divide(BD_180, MC), MC);
            return dgr2rad;
        }
    };

    // Custom user EvalEx function to convert from rad to degrees
    public Function toDeg = new Function("toDeg", 1) {
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            assertNotNull(parameters.get(0));
            BigDecimal rad2dgr = parameters.get(0).multiply(BD_180.divide(BD_PI, MC), MC);
            return rad2dgr;
        }
    };

    // Custom user EvalEx function to calculate sine function
    public Function sin = new Function("sin", 1) {
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            return sine(parameters.get(0), MC);
        }
    };

    // Custom user EvalEx function to calculate sine function
    public Function cos = new Function("COS", 1) {
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            return cosine(parameters.get(0), MC);
        }
    };

    // Custom user EvalEx function to calculate sine function
    public Function tan = new Function("TAN", 1) {
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            return tangent(parameters.get(0), MC);
        }
    };

    // Custom user EvalEx function to calculate arc sine and throw domain exception
    public Function asin = new Function("asin", 1){
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            if(parameters.get(0).compareTo(BigDecimal.ONE) > 0
                    || parameters.get(0).compareTo(new BigDecimal("-1.0")) < 0) {
                throw new IllegalArgumentException("ArcSineOperandOutOfBounds");
            }
            double result = Math.asin(parameters.get(0).doubleValue());
            return new BigDecimal(result, MC);
        }
    };

    // Custom user EvalEx function to calculate arc cosine and throw domain exception
    public Function acos = new Function("ACOS", 1){
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            if(parameters.get(0).compareTo(BigDecimal.ONE) > 0
                    || parameters.get(0).compareTo(new BigDecimal("-1.0")) < 0) {
                throw new IllegalArgumentException("ArcCosineOperandOutOfBounds");
            }
            double result = Math.acos(parameters.get(0).doubleValue());
            return new BigDecimal(result, MC);
        }
    };

    // Custom user EvalEx function to calculate arc tangent
    public Function atan = new Function("ATAN", 1){
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            assertNotNull(parameters.get(0));
            double result = Math.atan(parameters.get(0).doubleValue());
            return new BigDecimal(result, MC);
        }
    };

    // Custom user EvalEx function to calculate natural logarithm and throw domain exception
    public Function log = new Function("LOG", 1){
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            assertNotNull(parameters.get(0));

            BigDecimal number = (BigDecimal)parameters.get(0);
            if (number.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("LnOperandOutOfBounds");
            }
            BigDecimal result = ln(new BigDecimal(10, MC), MC.getPrecision());
            return result;
        }
    };

    // Custom user EvalEx function to calculate base 10 logarithm and throw domain exception
    public Function log10 = new Function("LOG10", 1){
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            assertNotNull(parameters.get(0));

            BigDecimal number = (BigDecimal)parameters.get(0);
            if (number.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("LogOperandOutOfBounds");
            }
            BigDecimal LN_TEN = ln(new BigDecimal(10, MC), MC.getPrecision());
            BigDecimal LN_NUM = ln(number, MC.getPrecision());
            BigDecimal result = LN_NUM.divide(LN_TEN, MC.getPrecision(), MC.getRoundingMode());
            return result;
        }
    };

    // Custom user EvalEx function to calculate square root of a number
    public Function sqrt = new Function("SQRT", 1) {
        @Override
        public BigDecimal eval(List<BigDecimal> parameters) {
            assertNotNull((BigDecimal)parameters.get(0));

            BigDecimal number = (BigDecimal)parameters.get(0);
            if(number.signum() < 0) {
                throw new IllegalArgumentException("SqrtOperandOutOfBounds");
            }
            if (number.compareTo(BigDecimal.ZERO) == 0) {
                return new BigDecimal(0, MC);
            } else if (number.compareTo(BigDecimal.ONE) == 0) {
                return new BigDecimal(1, MC);
            } else {
                BigDecimal two = new BigDecimal("2.0", MC);
                BigDecimal temp;
                BigDecimal squareRoot = number.divide(two, MC);
                do {
                    temp = squareRoot;
                    squareRoot = temp.add(number.divide(temp, MC), MC).divide(two, MC);
                } while(temp.subtract(squareRoot, MC).compareTo(BigDecimal.ZERO) != 0);
                return squareRoot;
            }
        }
    };

    // Custom user EvalEx operator to calculate factorial of a number
    public Operator factorial = new Operator("!", (Operator.OPERATOR_PRECEDENCE_POWER_HIGHER + 1), true, 1) {
        @Override
        public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
            assertNotNull(v1);
            if(!v1.remainder(BigDecimal.ONE, MC).equals(BigDecimal.ZERO)) {
                throw new IllegalArgumentException("OperandMustBeInteger");
            }
            BigDecimal factorial = v1;
            v1 = v1.subtract(BigDecimal.ONE, MC);
            if (factorial.compareTo(BigDecimal.ZERO) == 0 || factorial.compareTo(BigDecimal.ONE) == 0) {
                return BigDecimal.ONE;
            } else {
                while (v1.compareTo(BigDecimal.ONE) > 0) {
                    factorial = factorial.multiply(v1, MC);
                    v1 = v1.subtract(BigDecimal.ONE, MC);
                }
                return factorial;
            }
        }
    };

    // Custom user EvalEx operator to calculate percentage of a number
    public Operator percentage = new Operator("#%", (Operator.OPERATOR_PRECEDENCE_POWER_HIGHER + 1), true,1) {
        @Override
        public BigDecimal eval(BigDecimal v1, BigDecimal v2) {
            assertNotNull(v1);
            BigDecimal cent = new BigDecimal("100.0", MC);

            return v1.divide(cent);
        }
    };


    /**
     * Method to assert if {@link BigDecimal} is null. If <code>true</code>,
     * method throws exception.
     *
     * @param v1    Input {@link BigDecimal} number to be asserted
     */
    public void assertNotNull(BigDecimal v1) {
        if (v1 == null) {
            throw new ArithmeticException("OperandMayNotBeNull");
        }
    }



    /**
     * Method to assert if {@link BigDecimal} is <code>null</code>. If <code>true</code>,
     * method throws exception.
     *
     * @param v1    First input {@link BigDecimal} number to be asserted
     * @param v2    Second input {@link BigDecimal} number to be asserted
     */
    public void assertNotNull(BigDecimal v1, BigDecimal v2) {
        if (v1 == null) {
            throw new ArithmeticException("FirstOperandMayNotBeNull");
        } else if (v2 == null) {
            throw new ArithmeticException("SecondOperandMayNotBeNull");
        }
    }



    /**
     * Method to check if string if number.
     *
     * @param st    Input {@link String} to be tested.
     *
     * @return <code>true</code> if String is number, <code>false</code> otherwise.
     */
    protected boolean isNumber(String st) {
        if (st.charAt(0) == '-' && st.length() == 1) {
            return false;
        } else if (st.charAt(0) == '+' && st.length() == 1) {
            return false;
        } else if (st.charAt(0) == '.' && (st.length() == 1 || !Character.isDigit(st.charAt(1)))) {
            return false;
        } else if (st.charAt(0) != 'e' && st.charAt(0) != 'E') {
            char[] var2 = st.toCharArray();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                char ch = var2[var4];
                if (!Character.isDigit(ch) && ch != '-' && ch != '.' && ch != 'e' && ch != 'E' && ch != '+') {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }



    /**
     * Compute the natural logarithm of x to a given scale, x > 0.
     *
     * @param x	{@link BigDecimal} number to take natural logarithm from
     * @param scale	{@link Integer} scale of {@link BigDecimal} number
     *
     * @return {@link BigDecimal} number resulting from natural logarithm
     * 			operation with set scale.
     */
    public static BigDecimal ln(BigDecimal x, int scale) {

        if (x.signum() <= 0) { // Check if the input number is greater than zero
            throw new IllegalArgumentException("Logarithm operand must be greater than zero");
        }

        // The number of digits to the left of the decimal point.
        int magnitude = x.toString().length() - x.scale() - 1;

        if (magnitude < 4) {
            BigDecimal result = lnNewton(x, scale);

            return result;

        } else { // Compute (magnitude*ln(x^(1/magnitude))) for large numbers.

            BigDecimal root = intRoot(x, magnitude, scale); // x^(1/magnitude)
            BigDecimal lnRoot = lnNewton(root, scale); // ln(x^(1/magnitude))

            BigDecimal result = BigDecimal.valueOf(magnitude);
            result = result.multiply(lnRoot); // magnitude*ln(x^(1/magnitude))

            return result.setScale(scale, RoundingMode.HALF_EVEN);
        }
    }



    /**
     * Compute the natural logarithm of x to a given scale, with x > 0,
     * using Newton's algorithm.
     *
     * @param x	{@link BigDecimal} number to take natural logarithm from
     * @param scale	{@link Integer} scale of {@link BigDecimal} number
     *
     * @return {@link BigDecimal} number resulting from Newton's natural
     * 			logarithm operation with set scale.
     */
    private static BigDecimal lnNewton(BigDecimal x, int scale) {

        int scalePlusOne = scale + 1;
        BigDecimal number = x;
        BigDecimal term;

        // Convergence tolerance = 5*(10^-(scale+1))
        BigDecimal tolerance = BigDecimal.valueOf(5).movePointLeft(scalePlusOne);

        // Loop until the approximations converge rule,
        // i.e. two successive approximations are within the tolerance.
        do {
            BigDecimal eToX = exp(x, scalePlusOne); // e^x
            term = eToX.subtract(number).divide(eToX, scalePlusOne, RoundingMode.DOWN); // (e^x - n)/e^x
            x = x.subtract(term); // x - (e^x - n)/e^x

            Thread.yield();

        } while (term.compareTo(tolerance) > 0);

        return x.setScale(scale, RoundingMode.HALF_EVEN);
    }



    /**
     * Compute the integral root of x to a given scale, with x >= 0,
     * using Newton's algorithm.
     *
     * @param x the {@link BigDecimal} value of x
     * @param index the {@link Integer} integral root value
     * @param scale the desired {@link Integer} scale of the result
     *
     * @return The {@link BigDecimal} result integral root value.
     */
    public static BigDecimal intRoot(BigDecimal x, long index, int scale) {

        if (x.signum() < 0) { // Check that x >= 0.
            throw new IllegalArgumentException("Integral root operand must be greater than or equal to zero");
        }

        int scalePlusOne = scale + 1;
        BigDecimal number = x;
        BigDecimal idx = BigDecimal.valueOf(index);
        BigDecimal idxMinusOne = BigDecimal.valueOf(index - 1);
        BigDecimal tolerance = BigDecimal.valueOf(5).movePointLeft(scalePlusOne);
        BigDecimal numberPrev;

        x = x.divide(idx, scale, RoundingMode.HALF_EVEN); // The initial approximation is x/index.

        // Loop until the approximations converge to set rule,
        // i.e. two successive approximations are equal after rounding.
        do {

            BigDecimal xPowerIdxMinusOne = intPower(x, index - 1, scalePlusOne); // x^(index-1)
            BigDecimal xPowerIdx = x.multiply(xPowerIdxMinusOne).setScale(scalePlusOne, RoundingMode.HALF_EVEN); // x^index

            BigDecimal numerator = number.add(idxMinusOne.multiply(xPowerIdx)).setScale(scalePlusOne,
                    RoundingMode.HALF_EVEN); // n + (index-1)*(x^index)
            BigDecimal denominator = idx.multiply(xPowerIdxMinusOne).setScale(scalePlusOne, RoundingMode.HALF_EVEN); // (index*(x^(index-1))

            numberPrev = x;

            x = numerator.divide(denominator, scalePlusOne, RoundingMode.DOWN); // x = (n + (index-1)*(x^index)) /
            // (index*(x^(index-1)))

            Thread.yield();

        } while (x.subtract(numberPrev).abs().compareTo(tolerance) > 0);

        return x;
    }



    /**
     * Compute e to the power of x (e^x) to a given scale.
     * Break x into its whole and fraction parts and
     * compute (e^(1 + fraction/whole))^whole using Taylor's formula.
     *
     * @param x	The {@link BigDecimal} value of x
     * @param scale	The desired {@link Integer} scale of the result
     *
     * @return	The result value.
     */
    public static BigDecimal exp(BigDecimal x, int scale) {

        if (x.signum() == 0) { // e^0 = 1

            return BigDecimal.ONE;
        } else if (x.signum() == -1) { // If x is negative, return 1/(e^-x).
            BigDecimal result = BigDecimal.ONE;
            result = result.divide(exp(x.negate(), scale), scale, RoundingMode.HALF_EVEN);

            return result;
        }

        BigDecimal intPart = x.setScale(0, RoundingMode.DOWN); // Compute the whole part of x.

        if (intPart.signum() == 0) { // If there isn't a whole part, compute and return e^x.

            return expTaylor(x, scale);
        }

        BigDecimal xFraction = x.subtract(intPart); // Compute the fraction part of x.
        BigDecimal z = BigDecimal.ONE.add(xFraction.divide(intPart, scale, RoundingMode.HALF_EVEN)); // z = 1 +
        // fraction/whole
        BigDecimal taylor = expTaylor(z, scale); // t = e^z

        BigDecimal maxLong = BigDecimal.valueOf(Long.MAX_VALUE);
        BigDecimal result = BigDecimal.ONE;

        // Compute and return taylor^whole using intPower().
        // If whole > Long.MAX_VALUE, then first compute products of e^Long.MAX_VALUE.
        while (intPart.compareTo(maxLong) >= 0) {

            result = result.multiply(intPower(taylor, Long.MAX_VALUE, scale)).setScale(scale, RoundingMode.HALF_EVEN);
            intPart = intPart.subtract(maxLong);

            Thread.yield();
        }
        result = result.multiply(intPower(taylor, intPart.longValue(), scale));

        return result.setScale(scale, RoundingMode.HALF_EVEN);
    }



    /**
     * Compute x^exponent to a given scale.  Uses the same
     * algorithm as class numbercruncher.mathutils.IntPower.
     *
     * @param x	The {@link BigDecimal} value x
     * @param exponent	The exponent {@link Long} value
     * @param scale	The desired {@link Integer} scale of the result
     *
     * @return	The result value
     */
    public static BigDecimal intPower(BigDecimal x, long exponent, int scale) {

        if (exponent < 0) { // If the exponent is negative, compute 1/(x^-exponent).
            BigDecimal result = BigDecimal.ONE.divide(intPower(x, -exponent, scale), scale, RoundingMode.HALF_EVEN);

            return result;
        }

        BigDecimal power = BigDecimal.ONE;

        while (exponent > 0) {
            if ((exponent & 1) == 1) { // Is the rightmost bit a 1?
                power = power.multiply(x).setScale(scale, RoundingMode.HALF_EVEN);
            }

            x = x.multiply(x).setScale(scale, RoundingMode.HALF_EVEN); // Square x and shift exponent 1 bit to the
            // right.
            exponent >>= 1;

            Thread.yield();
        }

        return power;
    }



    /**
     * Compute e^x to a given scale by the Taylor expansion series.
     *
     * @param x	The {@link BigDecimal} value of x
     * @param scale	The desired {@link Integer} scale of the result
     *
     * @return	The result value
     */
    private static BigDecimal expTaylor(BigDecimal x, int scale) {

        BigDecimal factorial = BigDecimal.ONE;
        BigDecimal xPower = x;
        BigDecimal sumPrev;
        BigDecimal sum = x.add(BigDecimal.ONE); // 1 + x

        int n = 2;

        // Loop until the sums converge to rule,
        // i.e. two successive sums are equal after rounding.
        do {

            xPower = xPower.multiply(x).setScale(scale, RoundingMode.HALF_EVEN); // x^n
            factorial = factorial.multiply(BigDecimal.valueOf(n)); // n!
            BigDecimal term = xPower.divide(factorial, scale, RoundingMode.HALF_EVEN); // x^n/n!

            sumPrev = sum;
            sum = sum.add(term); // sum = sum + x^n/n!

            ++n;

            Thread.yield();

        } while (sum.compareTo(sumPrev) != 0);

        return sum;
    }



    /**
     * Compute cosine of x using Taylor expansion
     *
     * @param x	The {@link BigDecimal} radians angle to compute cosine.
     * @param mc	{@link MathContext} from which to get precision and rounding mode.
     *
     * @return	Cosine of a given angle in radians.
     */
    public static BigDecimal cosine(BigDecimal x, MathContext mc) {

        BigDecimal currValue = BigDecimal.ONE;
        BigDecimal lastValue = currValue.add(BigDecimal.ONE);
        BigDecimal xSquared = x.multiply(x);
        BigDecimal numerator = BigDecimal.ONE;
        BigDecimal denominator = BigDecimal.ONE;
        int iter = 0;

        while (lastValue.compareTo(currValue) != 0) {

            lastValue = currValue;

            int z = 2 * iter + 2;

            denominator = denominator.multiply(BigDecimal.valueOf(z));
            denominator = denominator.multiply(BigDecimal.valueOf(z - 1));
            numerator = numerator.multiply(xSquared);

            BigDecimal term = numerator.divide(denominator, mc.getPrecision() + 5, mc.getRoundingMode());

            if (iter % 2 == 0) {
                currValue = currValue.subtract(term);
            } else {
                currValue = currValue.add(term);
            }
            iter++;
        }
        if(currValue.compareTo(new BigDecimal("0.999999999999999999999999999999")) >= 0) {
            currValue = new BigDecimal(1.0, mc);
        }
        else if(currValue.compareTo(new BigDecimal("-0.999999999999999999999999999999")) <= 0) {
            currValue = new BigDecimal(-1.0, mc);
        }
        else if(currValue.compareTo(new BigDecimal("-0.000000000000001")) >= 0 && currValue.compareTo(new BigDecimal("0.000000000000001")) <= 0) {
            currValue = new BigDecimal(0.0, mc);
        }
        else if(currValue.compareTo(new BigDecimal("0.49999999999999")) >= 0 && currValue.compareTo(new BigDecimal("0.500000000000001")) <= 0) {
            currValue = new BigDecimal(0.5, mc);
        }
        else if(currValue.compareTo(new BigDecimal("-0.49999999999999")) <= 0 && currValue.compareTo(new BigDecimal("-0.500000000000001")) >= 0) {
            currValue = new BigDecimal(-0.5, mc);
        }

        return currValue;
    }


    /**
     * Compute sine of x using Taylor expansion
     *
     * @param x	The {@link BigDecimal} radians angle to compute sine.
     * @param mc	{@link MathContext} from which to get precision and rounding mode.
     *
     * @return	Sine of a given angle in radians.
     */
    public static BigDecimal sine(BigDecimal x, MathContext mc) {
        BigDecimal lastValue = x.add(BigDecimal.ONE);
        BigDecimal currValue = x;
        BigDecimal xSquared = x.multiply(x);
        BigDecimal numerator = x;
        BigDecimal denominator = BigDecimal.ONE;
        int iter = 0;

        while (lastValue.compareTo(currValue) != 0) {

            lastValue = currValue;

            int z = 2 * iter + 3;

            denominator = denominator.multiply(BigDecimal.valueOf(z));
            denominator = denominator.multiply(BigDecimal.valueOf(z - 1));
            numerator = numerator.multiply(xSquared);

            BigDecimal term = numerator.divide(denominator, mc.getPrecision() + 5, mc.getRoundingMode());

            if (iter % 2 == 0) {
                currValue = currValue.subtract(term);
            } else {
                currValue = currValue.add(term);
            }

            iter++;
        }
        if(currValue.compareTo(new BigDecimal("0.999999999999999999999999999999")) >= 0) {
            currValue = new BigDecimal(1.0, mc);
        }
        else if(currValue.compareTo(new BigDecimal("-0.999999999999999999999999999999")) <= 0) {
            currValue = new BigDecimal(-1.0, mc);
        }
        else if(currValue.compareTo(new BigDecimal("-0.000000000000001")) >= 0 && currValue.compareTo(new BigDecimal("0.000000000000001")) <= 0) {
            currValue = new BigDecimal(0.0, mc);
        }
        else if(currValue.compareTo(new BigDecimal("0.49999999999999")) >= 0 && currValue.compareTo(new BigDecimal("0.500000000000001")) <= 0) {
            currValue = new BigDecimal(0.5, mc);
        }
        else if(currValue.compareTo(new BigDecimal("-0.49999999999999")) <= 0 && currValue.compareTo(new BigDecimal("-0.500000000000001")) >= 0) {
            currValue = new BigDecimal(-0.5, mc);
        }

        return currValue;
    }


    /**
     * Compute tangent of x using sine and cosine method calculated
     * through Taylor expansion
     *
     * @param x	The {@link BigDecimal} radians angle to compute tangent.
     * @param mc	{@link MathContext} from which to get precision and rounding mode.
     *
     * @return	Tangent of a given angle in radians.
     */
    public static BigDecimal tangent(BigDecimal x, MathContext mc) {

        BigDecimal sin = sine(x, mc);
        BigDecimal cos = cosine(x, mc);

        return sin.divide(cos, mc.getPrecision() + 5, mc.getRoundingMode());
    }


}
