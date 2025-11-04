import java.util.*;

public class TestGeneration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter the CSV data file path: ");
            String fileName = sc.nextLine();

            int populationSize = 500;
            int maxDepth = 3;

            Generation generation = new Generation(populationSize, maxDepth, fileName);

            generation.evalAll();

            System.out.println("Best GPTree: ");
            generation.printBestTree();
            System.out.println("Fitness: " + String.format("%.2f", generation.getBestTree().getFitness()));

            System.out.println("\nTop Ten Fitness Values:");
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
