import java.util.*;

public class TestGeneration {

    // Helper method to format fitness values
    private static String formatFitness(double value) {
        if (value < 100) {
            // Pad small numbers with leading zeros to width 5 (e.g., 09.31, 017.22)
            return String.format("%05.2f", value);
        } else {
            // Numbers >= 100 print normally with 2 decimals
            return String.format("%.2f", value);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Prompt for CSV file path
            System.out.print("Enter the CSV data file path: ");
            String fileName = sc.nextLine();

            // Generation parameters
            int populationSize = 500;
            int maxDepth = 3;

            // Initialize generation
            Generation generation = new Generation(populationSize, maxDepth, fileName);

            // Evaluate all trees
            generation.evalAll();

            GPTree bestTree = generation.getBestTree();
            double fitness = bestTree.getFitness();

            // Print Best GPTree line
            System.out.print("Best GPTree: ");
            generation.printBestTree(); // prints only tree structure
            System.out.println(" = " + formatFitness(fitness));

            // Print Fitness line
            System.out.println("Fitness: " + formatFitness(fitness));

            // Print Top Ten Fitness Values
            System.out.println("Top Ten Fitness Values:");
            ArrayList<GPTree> topTen = generation.getTopTen();
            for (int i = 0; i < topTen.size(); i++) {
                System.out.print(formatFitness(topTen.get(i).getFitness()));
                if (i < topTen.size() - 1) System.out.print(", ");
            }
            System.out.println(); // single newline at the end

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
