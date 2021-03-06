import org.apache.commons.lang3.ObjectUtils;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart2d.Chart2d;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.plot2d.primitives.Serie2d;
import org.jzy3d.plot3d.primitives.ConcurrentLineStrip;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class ChartsToWindow {
    private List<Chart> charts;
    private List<Serie2d> serie2dList;
    private List<ConcurrentLineStrip> lineStrips;
    private List<ExtendedFunction> functions;

    public ChartsToWindow(List<ExtendedFunction> inputFunctions) {
        functions = inputFunctions;
        charts = new ArrayList<>();
        serie2dList = new ArrayList<>();
        lineStrips = new ArrayList<>();

        Chart2d chart;
        Serie2d serie2d;
        ConcurrentLineStrip lineStrip;
        double xmin;
        double xmax;
        double xmid;
        double yOfXmin;
        double yOfXmax;
        double yOfXmid;
        double ymin;
        double ymax;
        double absymax;

        for (int i = 0; i < functions.size(); i++) {
            chart = new Chart2d();
            xmin = functions.get(i).getXmin();
            xmax = functions.get(i).getXmax();
            xmid = (xmax + xmin) / 2.0;
            yOfXmin = functions.get(i).compute(xmin);
            yOfXmax = functions.get(i).compute(xmax);
            yOfXmid = functions.get(i).compute(xmid);
            ymax = max(yOfXmin, yOfXmax);
            ymax = max(ymax, yOfXmid);
            ymin = min(yOfXmin, yOfXmax);
            ymin = min(ymin, yOfXmid);
            absymax = max(abs(ymax), abs(ymin));
            ymax += absymax / 5.0;
            ymin -= absymax / 10.0;
            chart.getView().setBoundManual(new BoundingBox3d((float) xmin, (float) xmax, (float) ymin, (float) ymax, -1.0F, 1.0F));
            //chart.add();
            charts.add(chart);
            serie2d = chart.getSerie(Integer.toString(i), Serie2d.Type.LINE);
            serie2d.setColor(Color.RED);
            serie2dList.add(serie2d);
            lineStrip = (ConcurrentLineStrip) serie2d.getDrawable();
            lineStrips.add(lineStrip);
        }
    }

    public Serie2d getSerie2d(int num) {
        return serie2dList.get(num);
    }

    public List<Serie2d> getSeries2d() {
        return serie2dList;
    }

    public List<Chart> getCharts() {
        return this.charts;
    }

    public List<ExtendedFunction> getFunctions() {
        return functions;
    }

    public ExtendedFunction getFunction(int num) {
        return functions.get(num);
    }
}
