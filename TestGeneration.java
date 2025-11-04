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

            // Get best tree and fitness
            GPTree bestTree = generation.getBestTree();
            double fitness = bestTree.getFitness();

            // Convert fitness to the "0.70E+00" style
            String sciFitness = formatScientific(fitness);

            // Print best tree line
            System.out.print("Best GPTree: ");
            generation.printBestTree(); // prints only tree structure
            System.out.println(" = " + sciFitness);

            // Print Fitness line
            System.out.println("Fitness: " + String.format("%.2f", fitness));

            // Print top ten fitness values
            System.out.println("Top Ten Fitness Values:");
            ArrayList<GPTree> topTen = generation.getTopTen();
            for (int i = 0; i < topTen.size(); i++) {
                System.out.print(String.format("%.2f", topTen.get(i).getFitness()));
                if (i < topTen.size() - 1) System.out.print(", ");
            }
            System.out.println(); // single newline at end

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    // Helper method: formats small numbers like 0.7 as "0.70E+00"
    private static String formatScientific(double value) {
        if (value == 0) return "0.00E+00";
        int exp = (int) Math.floor(Math.log10(Math.abs(value)));
        double mantissa = value / Math.pow(10, exp);

        // For numbers < 1, force leading 0
        if (mantissa < 1) {
            mantissa *= 10;
            exp -= 1;
        }

        return String.format("%.2fE%+03d", mantissa, exp);
    }
}
