import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class GoldenSectionMethod  implements Method{
    @Override
    public OutputMethodInf apply(ExtendedFunction fn) {

        List<Double> sections = new ArrayList<>();
        fn.getNumOfInvok();

        double a = fn.getXmin();
        double b = fn.getXmax();
        double FI = (1 + sqrt(5)) / 2.0;
        double x1 = b - (b - a) / FI;
        double x2 = a + (b - a) / FI;
        double y1 = fn.compute(x1);
        double y2 = fn.compute(x2);
        int iter = 0;
        double numOfIterations = log((b - a) / eps) / log(FI);
        while ((abs(a - b) > eps) && (iter < numOfIterations)) {
            sections.add(abs(a - b));
            ++iter;

            if (y1 < y2) {
                b = x2;
                x2 = x1;
                y2 = y1;
                x1 = b - (b - a) / FI;
                y1 = fn.compute(x1);
            }
            else {
                a = x1;
                x1 = x2;
                y1 = y2;
                x2 = a + (b - a) / FI;
                y2 = fn.compute(x2);
            }
        }

        int inv = fn.getNumOfInvok();
        //System.out.println(inv);
        return new OutputMethodInf((a + b) / 2.0, sections, inv);
    }
}
