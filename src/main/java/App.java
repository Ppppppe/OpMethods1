import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.*;

public class App {

    public static void main(String[] args) throws Exception {

        List<ExtendedFunction> listOfFunctions = new ArrayList<>();
        List<Method> methods = new ArrayList<>();
        methods.add(new DichotomyMethod());
        methods.add(new GoldenSectionMethod());
        methods.add(new FibonacciMethod());
        methods.add(new ParabolaMethod());
        methods.add(new BrentsMethod());
        //methods.add(new BrentsDerivMethod());

        // [-0.5 ; 0.5]
        //d: -25*pow(x, 4) + 16*pow(x, 3) - 36*(x, 2) + 22*x - 2
        Function<Double, Double> f1 = x -> -5*pow(x, 5) + 4*pow(x, 4) - 12*pow(x, 3) + 11*pow(x, 2) -2*x + 1;
        listOfFunctions.add(new ExtendedFunction(f1, 100,  -0.5, 0.5));
        listOfFunctions.get(listOfFunctions.size() - 1).setDerivative(
                (x -> -25*pow(x, 4) + 16*pow(x, 3) - 36*pow(x, 2) + 22*x - 2)
        );
        // [6 ; 9.9]
        // d: -(0.2/pow(x, 0.8)) + ((2*log(x-2))/((x-2)*pow(log(10)), 2)) - ((2*log(10-x))/((10-x)*pow(log(10)), 2))
        Function<Double, Double> f2 = x -> pow(log10(x - 2), 2) + pow(log10(10 - x), 2) - pow(x, 0.2D);
        listOfFunctions.add(new ExtendedFunction(f2, 100,  6.0, 9.9));
        listOfFunctions.get(listOfFunctions.size() - 1).setDerivative(
                (x -> -(0.2/pow(x, 0.8)) + ((2*log(x-2))/((x-2)*pow(log(10), 2))) - ((2*log(10-x))/((10-x)*pow(log(10), 2))))
        );
        // [0 ; 2*PI]
        // d: -3*sin(0.75*x)-2.25*x*cos(0.75*x)-2
        Function<Double, Double> f3 = x -> -3*x*sin(0.75 * x) + E - 2*x;
        listOfFunctions.add(new ExtendedFunction(f3, 100,  0.0, 2*PI));
        listOfFunctions.get(listOfFunctions.size() - 1).setDerivative(
                (x -> -3*sin(0.75*x)-2.25*x*cos(0.75*x)-2)
        );
        // [0 ; 1]
        // d: exp(x)+3*exp(3*x)-2
        Function<Double, Double> f4 = x -> exp(3.0 * x) + exp(x) - 2*x;
        listOfFunctions.add(new ExtendedFunction(f4, 100,  0.0, 1.0));
        listOfFunctions.get(listOfFunctions.size() - 1).setDerivative(
                (x -> exp(x)+3*exp(3*x)-2)
        );
        // [0.5 ; 2.5]
        // d: -4.6 + 2*x + 0.08686/x
        Function<Double, Double> f5 = x -> 0.2*log10(x) + pow(x - 2.3, 2);
        listOfFunctions.add(new ExtendedFunction(f5, 100,  0.5, 2.5));
        listOfFunctions.get(listOfFunctions.size() - 1).setDerivative(
                (x -> -4.6 + 2*x + 0.08686/x)
        );

        Function<Double, Double> f6 = x -> sin(x);
        listOfFunctions.add(new ExtendedFunction(f6, 100,  -7.0, 7.0));

        List<ExtendedFunction> listOfSections;
        List<Integer> listOfInvokes;
        List<ExtendedFunction> listOfInvokeSeries = new ArrayList<>();;

        for (var method : methods) {
            System.out.println(method.toString().replaceAll("@.*", ""));
            int num = 1;
            listOfSections = new ArrayList<>();
            listOfInvokes = new ArrayList<>();


            for (var fn : listOfFunctions) {
                OutputMethodInf out1 = method.apply(fn);
                listOfInvokes.add(out1.getInvokes());
                // System.out.println("min " + num++ + ":  " + out1.getMin());
                System.out.println("   " + out1.getMin());
                listOfSections.add(new ExtendedFunction(
                        new Function<Double, Double>() {
                            private int i;
                            private final List<Double> sections = out1.getSections();

                            {
                                i = -4;
                            }

                            @Override
                            public Double apply(Double aDouble) {
                                i++;
                                if (i == -3)
                                    return 0.0;
                                if (i == -2)
                                    return sections.get(0);
                                if (i == -1)
                                    return 0.0;
                                return sections.get(i);
                            }
                        }, out1.getSections().size(), 0.0, (double) (out1.getSections().size() - 1)
                ));
            }


            ChartsToWindow l = new ChartsToWindow(listOfSections);
            new Window(l.getCharts());
            Window.generateSamplesForFunctions(l, 100);
        }

        ChartsToWindow log = new ChartsToWindow(listOfFunctions);
        new Window(log.getCharts());
        Window.generateSamplesForFunctions(log, 100);
    }

}
