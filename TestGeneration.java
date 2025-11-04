import java.util.*;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Prompt for CSV file path
            System.out.print("Enter the CSV data file path: ");
            String fileName = sc.nextLine();

            // Parameters for the generation
            int populationSize = 500;
            int maxDepth = 3;

            // Initialize generation
            Generation generation = new Generation(populationSize, maxDepth, fileName);

            // Evaluate all trees
            generation.evalAll();

            // Print best tree on a single line with scientific notation
            System.out.print("Best GPTree: ");
            generation.printBestTree(); // no newline added
            System.out.println(" = " + String.format("%.2E", generation.getBestTree().getFitness()));

            System.out.println("Fitness: " + String.format("%.2f", generation.getBestTree().getFitness()));

            System.out.println("Top Ten Fitness Values:");
            ArrayList<GPTree> topTen = generation.getTopTen();
            for (int i = 0; i < topTen.size(); i++) {
                System.out.print(String.format("%.2f", topTen.get(i).getFitness()));
                if (i < topTen.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
