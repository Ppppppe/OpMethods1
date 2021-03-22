import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class ParabolaMethod implements Method{

    @Override
    public OutputMethodInf apply(ExtendedFunction fn) {

        List<Double> sections = new ArrayList<>();
        fn.getNumOfInvok();

        double x1 = fn.getXmin();
        double x2;
        double x3 = fn.getXmax();

        double y1 = fn.compute(x1);
        double y2;
        double y3 = fn.compute(x3);
        double u = 0.0;
        double yu;
        int iter = 0;

        while ((abs(x3 - x1) > eps) && (iter < 50)) {
            sections.add(abs(x3 - x1));
            ++iter;

            //x2 = (x3 + x1) / 4.0;
            x2 = x1 + (x3 - x1) / 4.0;
            y2 = fn.compute(x2);
            u =     x2 -
                            (pow(x2 - x1, 2) * (y2 - y3) - pow(x2 - x3, 2) * (y2 - y1))
                            //---------------------------------------------//
                            / (2 * ((x2 - x1) * (y2 - y3) - (x2 - x3) * (y2 - y1)));

            yu = fn.compute(u);

            if (u < x2){
                if (yu < y2){
                    x3 = x2;
                }
                else {
                    x1 = u;
                }
            }
            else {
                if (yu > y2){
                    x3 = u;
                }
                else {
                    x1 = x2;
                }
            }
        }
        int inv = fn.getNumOfInvok();
        //System.out.println(inv);
        return new OutputMethodInf(u, sections, inv);
    }
}
