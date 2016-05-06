package KnapsackProblem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import KnapsackProblem.Algorithm.KnapsackDP;
import KnapsackProblem.Algorithm.EvolutionaryAlgorithm;
import KnapsackProblem.Algorithm.Organism;
import KnapsackProblem.Algorithm.Pair;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class MainController {
    public HBox graphBox;
    public VBox mainBox;
    public int generation;
    Series<Number, Number> averageFitness;
    Series<Number, Number> maximumFitness;
    
    public EvolutionaryAlgorithm solver;
    
    public Label fileName;

    public ProgressBar progressBar;
    public Label readyLabel;

    public Label fitnessLabel;
    public Label maxFitnessLabel;
    public Label generationLabel;
    public Label averageLabel;
    
    public Button oneG;
    public Button fiveG;
    public Button twentyG;
    public Button xGButton;
    public Button stopButton;
    public TextField xGenerationsField;
    
    public AreaChart<Number, Number> graphRef;

    public int lastFitness;
    public int stopPressed = 0;

    public void initialize() {
	generation = 0;
	lastFitness = 0;
	prepareChart();
	disableButtons();
    }

    public void prepareChart() {
	final NumberAxis xAxis = new NumberAxis();
	final NumberAxis yAxis = new NumberAxis();
	AreaChart<Number, Number> graph = new AreaChart<Number, Number>(xAxis, yAxis);
	graph.setCreateSymbols(false);
	graph.setTitle("Fitness - Generations relation");
	graph.getXAxis().setLabel("Generations");
	graph.getYAxis().setLabel("Fitness");
	graph.prefWidthProperty().bind(mainBox.prefWidthProperty());
	
	averageFitness = new XYChart.Series<Number, Number>();
	averageFitness.setName("Average fitness");

	maximumFitness = new XYChart.Series<Number, Number>();
	maximumFitness.setName("Maximum fitness");
	
	graph.getData().addAll(averageFitness, maximumFitness);
	
	graphBox.getChildren().add(graph);
	graphRef = graph;
    }

    public void addData(double avgFitness, double maxFitness) {
	averageFitness.getData().add(new XYChart.Data<Number, Number>(generation, avgFitness));
	maximumFitness.getData().add(new XYChart.Data<Number, Number>(generation, maxFitness));
	generation++;

    }

    public void oneGenerationPressed() {
	updateGraph(1);
    }

    public void fiveGenerationsPressed() {
	updateGraph(5);
    }

    public void twentyGenerationsPressed() {
	updateGraph(20);
    }
    
    public void xGenerationsPressed() {
	int number = 0;

	try {
	    number = Integer.parseInt(xGenerationsField.getText());
	} catch (Exception e) {
	    Logger.error("Wrong number");
	}
	updateGraph(number);
    }
    
    public void stopButtonPressed() {
	stopPressed = 1;
    }
    
    
    public void updateGraph(int generations) {
	stopPressed = 0;
	Task<Void> task = new Task<Void>() {
	    @Override
	    public Void call() throws InterruptedException {
		disableButtons();
		progressBar.setProgress(0);
		for (int i = 1; i <= generations; i++) {
		    if (stopPressed == 1) {
			stopPressed = 0;
			break;
		    }
		    solver.nextGenerationSampling();
		    final int avg = solver.getAverage();
		    final int best = solver.getCurrentBest().getFitness();
		    
		    Platform.runLater(new Runnable() {
			@Override
			public void run() {
			    progressBar.setProgress(progressBar.getProgress() + 1.0 / generations);
			    addData(avg, best);
			    updateLabels(avg, best);
			    	
			}

		    });
		}

		return null;
	    }
	};
	task.setOnSucceeded(e -> {
	    enableButtons();
	});

	Thread th = new Thread(task);
	th.setDaemon(true);
	th.start();
    }

    public void updateLabels(int avg, int best) {
	fitnessLabel.setText(Integer.toString(best - lastFitness));
	maxFitnessLabel.setText(Integer.toString(best));
	generationLabel.setText(Integer.toString(generation));
	averageLabel.setText(Integer.toString(avg));
	lastFitness = best;
    }

    public void disableButtons() {
	oneG.setDisable(true);
	fiveG.setDisable(true);
	twentyG.setDisable(true);
	xGButton.setDisable(true);
    }

    public void enableButtons() {
	oneG.setDisable(false);
	fiveG.setDisable(false);
	twentyG.setDisable(false);
	xGButton.setDisable(false);
    }

    public void openPressed() {

	final FileChooser fileChooser = new FileChooser();
	final File selectedDirectory = fileChooser.showOpenDialog(null);
	String path = "";
	if (selectedDirectory.isFile()) {
	    try {
		path = selectedDirectory.getAbsolutePath();
		fileName.setText(selectedDirectory.getName());
		readItems(path);
	    } catch (Exception e) {
		Logger.error("There was a problem finding or oppening the file");
	    }
	} else 
	    Logger.error("This is not a file");

    }

    public void readItems(String filePath) {

	Scanner sc;
	try {
	    sc = new Scanner(new File(filePath));
	    int maxWeight = sc.nextInt(), n = sc.nextInt();
	    Vector<Pair<Integer, Integer>> pairs = new Vector<>();
	    for (int i = 0; i < n; i++) {
		int weight = sc.nextInt(), cost = sc.nextInt();
		pairs.add(new Pair<>(weight, cost));
	    }
	    sc.close();

	    Pair<Vector<Integer>, Integer> result = KnapsackDP.solve(pairs, maxWeight);
	    System.out.printf("DP : %d \n", result.y);
	    Organism.prepare(pairs, maxWeight);
	    solver = new EvolutionaryAlgorithm(500);
	    
	    readyLabel.setText("Ready");
	    readyLabel.getStyleClass().clear();
	    readyLabel.getStyleClass().addAll("label", "lbl", "lbl-success", "lbl-noradius");
	    enableButtons();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
