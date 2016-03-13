import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
/** 
 *  
 * @author Denis
 * Gets as argument the path to the text file
 * That needs to be analyzed and graphed
 */
public class Main extends Application {
	public static String filename;
	public static HashMap<String, Integer> words;
	
	public Scene scene;
	
	@Override public void start(Stage stage) throws IOException {
        stage.setTitle("Word frequency graph");
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        xAxis.setLabel("Words");
        yAxis.setLabel("Frequency");
        
        final LineChart<String,Number> lineChart = 
                new LineChart<String,Number>(xAxis,yAxis);
        
        lineChart.setTitle("Word frequency graph in "+filename);

        XYChart.Series series = new XYChart.Series();

                
        words = Counter.count(filename);
        series.setName(Integer.toString(Counter.getCounter())+ " words");
        int j = 1;
        for (Entry<String, Integer> i : words.entrySet()) {
        	series.getData().add(new XYChart.Data(i.getKey(), i.getValue()));
        	j++;
        	if (j > 30) break;
        }
        lineChart.getData().add(series);
        scene = new Scene(lineChart, 800, 800);
        stage.setScene(scene);
        stage.show();
        
    }
 
    public static void main(String[] args) {
    	if (args.length == 0) throw new IllegalArgumentException();
    	filename = args[0];
    	
        launch(args);
    }
}