import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.pow;

public class BrentsMethod implements Method{
    @Override
    public OutputMethodInf apply(ExtendedFunction fn) {

        List<Double> sections = new ArrayList<>();
        fn.getNumOfInvok();

        double FI = (1 + sqrt(5)) / 2.0;

        double a = fn.getXmin();
        double c = fn.getXmax();
        double x = a + (c - a) / FI; // наименьшее значение функции
        double w = x; // второе снизу значение функции
        double v = x; // предыдущее значение w
        double yx = fn.compute(x);
        double yw = yx;
        double yv = yx;
        double d = c - a;
        double e = d;
        double g;
        double tol;
        double u; // минимум аппроксимирующей параболы
        double yu;

        int iter = 200;
        while ((abs(a - c) > eps) && (--iter > 0)) {
            sections.add(abs(a - c));

            g = e;
            e = d;
            tol = eps * abs(x) + eps / 10.0;

            if ((abs(x - (a + c) / 2.0) + (c - a) / 2.0) <= 2*tol) {
                break;
            }

            if ((x != w & w != v & v != x) && (yx != yw & yw != yv & yv != yx)) {
                u =     w -
                        (pow(w - x, 2) * (yw - yv) - pow(w - v, 2) * (yw - yx))
                        //---------------------------------------------------//
                        / (2 * ((w - x) * (yw - yv) - (w - v) * (yw - yx)));
                // u принимается в кач-ве след. точки оптимизационного процесса, если:
                if ((u >= a & u <= c) && // u попал внутрь интервала [a; c] и отстоит от границы интервала не менее чем на eps
                        (abs(u - x) < g / 2.0)) // u отстоит от x не более чем на половину длины предыдущего шага
                {
                    if ((u - a < 2*tol) && (c - u < 2*tol)) {
                        u = x - signum(x - (a + c) / 2.0)*tol;
                    }
                }
            }
            // отвергаем u, находим след. точку с помощью золотого сечения большего из интервалов
            // max([a; x], [x; c]) и выбираем точку золотым сечением большего интервала
            else {
                if (x < (a + c) / 2.0) {
                    u = x + (c - x) / FI;
                    e = c - x;
                }
                else {
                    u = x - (x - a) / FI;
                    e = x - a;
                }
            }

            if (abs(u - x) < tol) {
                u = x + signum(u - x)*tol;
            }
            d = abs(u - x);
            yu = fn.compute(u);
            if (yu <= yx) {
                if (u >= x) {
                    a = x;
                }
                else {
                    c = x;
                }
                v = w;
                w = x;
                x = u;
                yv = yw;
                yw = yx;
                yx = yu;
            }
            else {
                if (u >= x) {
                    c = u;
                }
                else {
                    a = u;
                }
                if (yu <= yw | w == x) {
                    v = w;
                    w = u;
                    yv = yw;
                    yw = yu;
                }
                else
                    if (yu <= yv | v == x | v == w) {
                        v = u;
                        yv = yu;
                    }
            }
        }
        //new OutputMethodInf(x, null);
        int inv = fn.getNumOfInvok();
        //System.out.println(inv);
        return new OutputMethodInf(x, sections, inv);
    }
}
