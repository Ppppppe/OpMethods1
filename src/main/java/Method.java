import java.util.function.Function;
@FunctionalInterface
public interface Method {

    double eps = 0.001;

    OutputMethodInf apply(ExtendedFunction fn);
}
