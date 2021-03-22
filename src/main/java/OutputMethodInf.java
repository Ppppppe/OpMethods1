import java.util.List;

public class OutputMethodInf {

    private double min;
    private List<Double> sections;

    private int invokes;

    public OutputMethodInf (double min, List<Double> sections, int invokes) {
        this.min = min;
        this.sections = sections;
        this.invokes = invokes;
    }


    public double getMin() {
        return min;
    }

    public List<Double> getSections() {
        return sections;
    }

    public int getInvokes() {
        return invokes;
    }
}
