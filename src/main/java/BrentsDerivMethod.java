import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class BrentsDerivMethod implements Method{
    @Override
    public OutputMethodInf apply(ExtendedFunction fn) {
        List<Double> sections = new ArrayList<>();
        fn.getNumOfInvok();

        double FI = (1 + sqrt(5)) / 2.0;

        double a = fn.getXmin();
        double c = fn.getXmax();
        double x = (a + c) / 2.0; // наименьшее значение функции
        double w = x; // второе снизу значение функции
        double v = x; // предыдущее значение w
        double yx = fn.compute(x);
        double yw = yx;
        double yv = yx;

        double ydx = fn.getDerivative(x);
        double ydw = ydx;
        double ydv = ydx;

        double d = c - a;
        double e = d;
        double g;
        double tol;
        double u; // минимум аппроксимирующей параболы
        double u1 = 0;
        double u2 = 0;
        double yu;
        double ydu;
        boolean u1lock;
        boolean u2lock;

        int iter = 200;
        while ((abs(a - c) > eps) && (--iter > 0)) {
            sections.add(abs(a - c));

            g = e;
            e = d;
            tol = eps * abs(x) + eps / 10.0;
            u1lock = false;
            u2lock = false;

            if ((abs(x - (a + c) / 2.0) + (c - a) / 2.0) <= 2*tol) {
                break;
            }

            if (x != w & ydx != ydw) {
                u1 = w -
                        (pow(w - x, 2) * (yw - yv) - pow(w - v, 2) * (yw - yx))
                         //---------------------------------------------------//
                        / (2 * ((w - x) * (yw - yv) - (w - v) * (yw - yx)));

                if ((u1 >= a & u1 <= c) && ((u1 - x)*ydx <=0) & (abs(u1 - x) < g / 2.0)) {
                        u1lock = true;
                }
            }

            if (x != v & ydx != ydv) {
                u2 = w -
                        (pow(w - x, 2) * (yw - yv) - pow(w - v, 2) * (yw - yx))
                                //---------------------------------------------------//
                                / (2 * ((w - x) * (yw - yv) - (w - v) * (yw - yx)));

                if ((u2 >= a & u2 <= c) && ((u2 - x)*ydx <=0) & (abs(u2 - x) < g / 2.0)) {
                        u2lock = true;
                }
            }

            if (u1lock | u2lock) {
                u = u1lock ? u1 : u2;
                if (u1lock | u2lock) {
                    u = min(u1, u2);
                }
            }
            else {
                if (ydx > 0) {
                    u = (a + x) / 2.0;
                    e = x - a; // деление отрезка пополам
                }
                else {
                    u = (x + c) / 2.0;
                    e = c - x;
                }
            }

            if (abs(u - x) < tol) {
                u = x + signum(u - x)*tol;
            }
            d = abs(u - x);

            yu = fn.compute(u);
            ydu = fn.getDerivative(u);

            if (ydu <= ydx) {
                if (u >= x) {
                    a = x;
                }
                else {
                    c = x;
                }
                v = w; w = x; x = u;
                yv = yw; yw = yx; yx = yu;
                ydv = ydw; ydw = ydx; ydx = ydu;
            }
            else {
                if (u >= x) {
                    c = u;
                }
                else {
                    a = u;
                }
                if (yu <= yw | w == x) {
                    v = w; w = u;
                    yv = yw; yw = yu;
                    ydv = ydw; ydw = ydu;
                }
                else {
                    if (yu <= yv | v == x | v == w) {
                        v = u;
                        yv = yu;
                        ydv = ydu;
                    }
                }
            }
        }

        int inv = fn.getNumOfInvok();
        System.out.println(inv);
        return new OutputMethodInf(x, sections, inv);
    }
}
