import net.miginfocom.swing.MigLayout;
import org.jzy3d.chart.Chart;
import org.jzy3d.ui.LookAndFeel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serial;
import java.util.List;

/** A frame to show a list of charts */
public class Window extends JFrame {
    @Serial
    private static final long serialVersionUID = 7519209038396190502L;

    public Window(List<Chart> charts) throws IOException {
        LookAndFeel.apply();
        String lines = "[400px]";
        String columns = "[400px,grow]"; // width
        setLayout(new MigLayout("", columns, lines));
        int k = 0;
        for (Chart c : charts) {
            addChart(c, k++);
        }
        windowExitListener();
        this.pack();
        setVisible(true);
    }

    public void addChart(Chart chart, int id) {
        Component canvas = (java.awt.Component) chart.getCanvas();

        JPanel chartPanel = new JPanel(new BorderLayout());
        Border b = BorderFactory.createLineBorder(java.awt.Color.black);
        chartPanel.setBorder(b);
        chartPanel.add(canvas, BorderLayout.CENTER);
        add(chartPanel, "cell 0 " + id + ", grow");
    }

    public void windowExitListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Window.this.dispose();
                System.exit(0);
            }
        });
    }

    public static void generateSamples(ChartsToWindow log, int numOfIterations)
            throws InterruptedException {
        System.out.println("will generate " + numOfIterations + " samples");
            // Add to time series
            //j = i / 100.0F;
            //log.getSerie2d(0).add(j, sin(j));
            //log.getSerie2d(1).add(j, cos(j));
            //log.getSerie2d(2).add(j, pow(j, 2) + 3*j - 2);

        double step;
        double Xcurr;
            for (int k = 0; k < log.getFunctions().size(); k++) {
                log.getFunction(k);
                Xcurr = log.getFunction(k).getXmin();
                step =
                        (log.getFunction(k).getXmax() - log.getFunction(k).getXmin())
                        //---------------------------------------------------------//
                                            /numOfIterations;

                for (int i = 0; i < numOfIterations; i++) {
                    log.getSerie2d(k).add(Xcurr, log.getFunction(k).compute(Xcurr));
                    Xcurr += step;
                }
            }
    }
}
