import java.util.function.Function;

public class ExtendedFunction {

    private final Function<Double, Double> function;
    private final Double XminInclusive;
    private final Double XmaxInclusive;
    private final int numOfIterations;
    private int invocations;
    private Function<Double, Double> derivative;

    public ExtendedFunction(Function<Double, Double> function, int noi, Double XminInclusive, Double XmaxInclusive) {
        this.function = function;
        this.XminInclusive = XminInclusive;
        this.XmaxInclusive = XmaxInclusive;
        this.numOfIterations = noi;
        invocations = 0;
    }

    public Double getXmin() { return XminInclusive; }
    public Double getXmax() { return XmaxInclusive; }
    public int getNumOfIterations() { return numOfIterations; }
    public int getNumOfInvok() {
        int a = invocations;
        invocations = 0;
        return a;
    }
    public Double compute(Double x) {
        invocations++;
        return function.apply(x);
    }

    public void setDerivative(Function<Double, Double> derivative) {
        this.derivative = derivative;
    }

    public Double getDerivative(double x) {
        return derivative.apply(x);
    }
}
