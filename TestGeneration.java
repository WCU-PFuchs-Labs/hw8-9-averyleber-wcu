import java.util.*;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Prompt for CSV file path
            System.out.print("Enter the CSV data file path: ");
            String fileName = sc.nextLine();

            int populationSize = 500;
            int maxDepth = 3;

            Generation generation = new Generation(populationSize, maxDepth, fileName);

            // Evaluate all trees
            generation.evalAll();

            // Best tree with scientific notation
            System.out.print("Best GPTree: ");
            generation.printBestTree(); // prints only the tree structure, no newline
            System.out.println(" = " + String.format("%.2E", generation.getBestTree().getFitness()));

            // Fitness line
            System.out.println("Fitness: " + String.format("%.2f", generation.getBestTree().getFitness()));

            // Top ten fitness values
            System.out.println("Top Ten Fitness Values:");
            ArrayList<GPTree> topTen = generation.getTopTen();
            for (int i = 0; i < topTen.size(); i++) {
                System.out.print(String.format("%.2f", topTen.get(i).getFitness()));
                if (i < topTen.size() - 1) System.out.print(", ");
            }
            System.out.println(); // only one newline at the end

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
