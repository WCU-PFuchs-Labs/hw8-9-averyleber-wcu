import java.util.*;

public class TestGP {
    /*
     * Purpose: Tests the GPTree class as well as new versions using crossover.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Prompt for CSV file path
            System.out.print("Enter the CSV data file path: ");
            String fileName = sc.nextLine();

            // Generation parameters
            int populationSize = 500;
            int maxDepth = 2;

            // Initialize generation
            Generation generation = new Generation(populationSize, maxDepth, fileName);

            // Evaluate all trees
            generation.evalAll();

            GPTree bestTree = generation.getBestTree();
            double fitness = bestTree.getFitness();

            for (int i = 0; i < 50; i++) {
                System.out.println("Generation " + (i + 1) + ": ");
                generation.evolve();

                // Print Best GPTree line with single equals sign
                System.out.print("Best GPTree: ");
                generation.printBestTree(); // prints only tree structure
                System.out.print(" = " + String.format("%.2f", fitness) + "\n");

                // Print Fitness line
                System.out.print("Fitness: " + String.format("%.2f", fitness) + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }

        
    }
}
