import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.lang.Math.*;

public class App {

    public static void main(String[] args) throws Exception {

        double min;
        List<ExtendedFunction> listOfFunctions = new ArrayList<>();

        // [-0.5 ; 0.5]
        Function<Double, Double> f1 = x -> -5*pow(x, 5) + 4*pow(x, 4) - 12*pow(x, 3) + 11*pow(x, 2) -2*x + 1;
        listOfFunctions.add(new ExtendedFunction(f1, -0.5, 0.5));
        // [6 ; 9.9]
        Function<Double, Double> f2 = x -> pow(log10(x - 2), 2) + pow(log10(10 - x), 2) - pow(x, 0.2D);
        listOfFunctions.add(new ExtendedFunction(f1, 6.0, 9.9));
        // [0 ; 2*PI]
        Function<Double, Double> f3 = x -> -3*x*sin(0.75 * x) + E - 2*x;
        listOfFunctions.add(new ExtendedFunction(f1, 0.0, 2*PI));
        // [0 ; 1]
        Function<Double, Double> f4 = x -> exp(3.0 * x) + 5*E - 2*x;
        listOfFunctions.add(new ExtendedFunction(f1, 0.0, 1.0));
        // [0.5 ; 2.5]
        Function<Double, Double> f5 = x -> 0.2*log10(x) + pow(x - 2.3, 2);
        listOfFunctions.add(new ExtendedFunction(f1, 0.5, 2.5));

        min = new DichotomyMethod().apply(listOfFunctions.get(0));
        System.out.println("min 0:" + min);
        min = new DichotomyMethod().apply(listOfFunctions.get(1));
        System.out.println("min 1:" + min);
        min = new DichotomyMethod().apply(listOfFunctions.get(2));
        System.out.println("min 2:" + min);
        min = new DichotomyMethod().apply(listOfFunctions.get(3));
        System.out.println("min 3:" + min);
        min = new DichotomyMethod().apply(listOfFunctions.get(4));
        System.out.println("min 4:" + min);

        ChartsToWindow log = new ChartsToWindow(listOfFunctions);
        new Window(log.getCharts());
        Window.generateSamples(log, 5000);


    }

}
