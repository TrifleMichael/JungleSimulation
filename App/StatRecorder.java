package App;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Writes simulation info into txt file.
public class StatRecorder {
    String fileName = "SimulationStatistics";
    int simulationNum = 1;

    public StatRecorder() {
        try {
            File newFile = new File(fileName+simulationNum+".txt");
            while (newFile.exists()) {
                simulationNum++;
                newFile = new File(fileName+simulationNum+".txt");
            }
            fileName = fileName+simulationNum+".txt";
            newFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred when creating "+fileName+" file.");
            e.printStackTrace();
        }

        try {
            // Clearing file from last simulation
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred when writing in "+fileName);
            e.printStackTrace();
        }
    }

    public void addLine(String text) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred when writing in "+fileName);
            e.printStackTrace();
        }
    }
}
