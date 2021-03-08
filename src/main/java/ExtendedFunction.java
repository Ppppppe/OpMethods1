import java.util.function.Function;

public class ExtendedFunction {

    private final Function<Double, Double> function;
    private final Double XminInclusive;
    private final Double XmaxInclusive;

    public ExtendedFunction(Function<Double, Double> function, Double XminInclusive, Double XmaxInclusive) {
        this.function = function;
        this.XminInclusive = XminInclusive;
        this.XmaxInclusive = XmaxInclusive;
    }

    public Double getXmin() { return XminInclusive; }
    public Double getXmax() { return XmaxInclusive; }
    public Double compute(Double x) { return function.apply(x); }

}
