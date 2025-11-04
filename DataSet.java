/*
 * Code Author: Avery Leber
 * CSC240 11/03/2025
 * Purpose: Stores multiple DataRow objects
 */
import java.io.*;
import java.util.*;

public class DataSet {
    private List<DataRow> rows;

    public DataSet(String filename) throws IOException {
        rows = new ArrayList<>();
        loadFromFile(filename);
    }

    private void loadFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while ((line = br.readLine() ) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length < 2) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                
                double y = Double.parseDouble(parts[0]);
                double[] x = new double[parts.length - 1];

                for (int i = 1; i < parts.length; i++) {
                    x[i - 1] = Double.parseDouble(parts[i]);
                }

                rows.add(new DataRow(y, x));
            }
        }
    }

    public List<DataRow> getRows() {
        return rows;
    }

    public int size() {
        return rows.size();
    }

    @Override
    public String toString() {
        return "DataSet{" + "rows=" + rows + "}";
    }
}
