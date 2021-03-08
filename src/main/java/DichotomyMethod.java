import java.math.BigDecimal;

import static java.lang.Math.*;

public class DichotomyMethod implements Method{

    private static final double eps = 0.000001;

    public double apply(ExtendedFunction fn) {

        double δ = eps / 2.0;
        double x1;
        double x2;
        double m;
        double fx1;
        double fx2;
        double a = fn.getXmin();
        double b = fn.getXmax();
        int iter = 0;
        double numOfIterations = log((b - a) / eps) / log(2);
        //System.out.println("num of iterations: " + numOfIterations);
        while ((abs(b - a) > eps) && (iter < numOfIterations)) {
            //System.out.println("длина фактическая:  " + abs(b - a));
            //System.out.println("длина теоретическая:  " + (fn.getXmax() - fn.getXmin()) / pow(2, iter));
            System.out.println();
            ++iter;
            m = (a + b) / 2;
            x1 = m - δ;
            x2 = m + δ;
            fx1 = fn.compute(x1);
            fx2 = fn.compute(x2);

            if (fx1 < fx2) {
                b = x2;
            }
            else {
                if (fx1 > fx2) {
                    a = x1;
                }
            }


            /*
            if (abs(fx1 - fx2) < eps) {
                a = x1;
                b = x2;
            }
            */
        }
        System.out.println("iter: " + iter);
        return a;
    }
}