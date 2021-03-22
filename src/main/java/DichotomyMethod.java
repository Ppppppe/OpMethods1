import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class DichotomyMethod implements Method{

    @Override
    public OutputMethodInf apply(ExtendedFunction fn) {

        List<Double> sections = new ArrayList<>();
        fn.getNumOfInvok();

        double δ = eps / 2.0;
        double x1;
        double x2;
        double m;
        double y1;
        double y2;
        double a = fn.getXmin();
        double b = fn.getXmax();
        int iter = 0;

        double numOfIterations = log((b - a) / eps) / log(2);
        while ((abs(b - a) > eps) && (iter < numOfIterations)) {
            sections.add(abs(b - a));
            ++iter;
            m = (a + b) / 2;
            x1 = m - δ;
            x2 = m + δ;
            y1 = fn.compute(x1);
            y2 = fn.compute(x2);

            if (y1 < y2) {
                b = x2;
            }
            else {
                a = x1;
            }
        }
        int inv = fn.getNumOfInvok();
        //System.out.println(inv);

        OutputMethodInf output = new OutputMethodInf(a, sections, inv);
        return output;
    }
}