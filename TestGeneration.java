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

            GPTree bestTree = generation.getBestTree();
            double fitness = bestTree.getFitness();

            // Print Best GPTree line with fixed decimal (no scientific notation)
            System.out.print("Best GPTree: ");
            generation.printBestTree(); // prints only tree structure
            System.out.println(" = " + String.format("%.2f", fitness));

            // Print Fitness line
            System.out.println("Fitness: " + String.format("%.2f", fitness));

            // Print Top Ten Fitness Values
            System.out.println("Top Ten Fitness Values:");
            ArrayList<GPTree> topTen = generation.getTopTen();
            for (int i = 0; i < topTen.size(); i++) {
                System.out.print(String.format("%.2f", topTen.get(i).getFitness()));
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
