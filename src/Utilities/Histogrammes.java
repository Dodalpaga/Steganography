package Utilities;

import java.awt.image.BufferedImage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;



public class Histogrammes extends ApplicationFrame {



    private static BufferedImage image;
    private final ChartPanel histoPanel;
    
    public Histogrammes(String title, Texte fenetre) {
        
        super(title);
        Histogrammes.image = fenetre.getCourante();

        this.histoPanel = createChartPanel();

        histoPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        setContentPane(histoPanel);
    }
    
    
    public ChartPanel getPanel(){
        return histoPanel;
    }
    
    //Crée la base de données du nombre de pixels possédant une certaine valeur
    //pour chaque couleur (3 séries).
    private static IntervalXYDataset createDataset() {

        HistogramDataset dataset = new HistogramDataset();

        double[] r = new double[image.getWidth()*image.getHeight()];
        double[] v = new double[image.getWidth()*image.getHeight()];
        double[] b = new double[image.getWidth()*image.getHeight()];
        
        
        for (int x = 0; x < image.getWidth()-1; x++) {
            for(int y = 0; y < image.getHeight()-1; y++){
                int i = x*image.getHeight() + y;
                int p = image.getRGB(x, y);
                r[i]=(p>>16) & 0xff;
                v[i]=(p>>8) & 0xff;
                b[i]=(p) & 0xff;
                
            }


        }

        dataset.addSeries("Red", r,image.getWidth()*image.getHeight());
        dataset.addSeries("Green", v,image.getWidth()*image.getHeight());
        dataset.addSeries("Blue", b,image.getWidth()*image.getHeight());


        return dataset;

    }
    
    //Création de l'histogramme
    private static JFreeChart createChart(IntervalXYDataset dataset) {
        JFreeChart chart = ChartFactory.createHistogram("Histogram Demo 1",null,null,dataset,PlotOrientation.VERTICAL,true,true,false);
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setForegroundAlpha(0.85f);

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setBarPainter(new StandardXYBarPainter());
        renderer.setShadowVisible(false);

        return chart;

    }


    public static ChartPanel createChartPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        
        return panel;

    }
}