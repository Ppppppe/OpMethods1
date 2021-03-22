import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.*;

public class FibonacciMethod implements Method{

    @Override
    public OutputMethodInf apply(ExtendedFunction fn) {

        List<Double> sections = new ArrayList<>();
        fn.getNumOfInvok();

        double FI = (1 + sqrt(5)) / 2.0;
        Function<Integer, Double> binet = n ->
                (pow(FI, n) - pow(-FI, -n))
                        //-----------------------//
                        / (2 * FI - 1);

        double a = fn.getXmin();
        double b = fn.getXmax();
        int n;
        for (n = 0; binet.apply(n) < (b - a) / eps; n++);
        double x1 = a + (binet.apply(n - 2) / binet.apply(n)) * (b - a);
        double y1 = fn.compute(x1);
        double x2 = a + (binet.apply(n - 1) / binet.apply(n)) * (b - a);
        double y2 = fn.compute(x2);

        for (int k = 0; k < n - 2; k++) {
            sections.add(abs(a - b));
            if (y1 > y2) {
                a = x1;
                x1 = x2;
                y1 = y2;
                x2 = a + (binet.apply(n - k - 2) / binet.apply(n - k - 1)) * (b - a);
                y2 = fn.compute(x2);
            }
            else {
                b = x2;
                x2 = x1;
                y2 = y1;
                x1 = a + (binet.apply(n - k - 3) / binet.apply(n - k - 1)) * (b - a);
                y1 = fn.compute(x1);
            }
        }
        x2 = x1 + eps;
        y2 = fn.compute(x2);
        if (y1 <= y2)
            b = x1;
        else
            a = x1;

        int inv = fn.getNumOfInvok();
        //System.out.println(inv);
        return new OutputMethodInf((a + b) / 2.0, sections, inv);
    }
}
