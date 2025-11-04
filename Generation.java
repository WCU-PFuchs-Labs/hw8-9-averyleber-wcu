/*
 * Code Author: Avery Leber
 * 11/03/2025 CSC240
 * Purpose: Represents a generation of GPTrees.
 * Handles evaluation, sorting, and evolution operations.
 */

import java.util.*;

public class Generation {
    private GPTree[] trees;     // An array of GPTrees
    private final DataSet data;       // The DataSet for evaluating fitness
    private final Random rand;        // Random generator
    private final NodeFactory factory; // Used to generate random Nodes for trees

    /*
     * Constructor
     * Purpose: Builds a generation of GPTrees
     * 
     * @param size      The number of GPTrees in this generation
     * @param maxDepth  Maximum tree depth
     * @param fileName  CSV file to load as DataSet 
     */
    public Generation(int size, int maxDepth, String fileName) throws Exception {
        this.data = new DataSet(fileName);
        this.rand = new Random();

        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        int numVars = data.getRows().get(0).getX().length;
        this.factory = new NodeFactory(ops, numVars);

        trees = new GPTree[size];
        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand);
        }
    }

    /*
     * Purpose: Evaluates all GPTrees in this generation against the DataSet
     * and sorts them by fitness in ascending order.
     */
    public void evalAll() {
        for (GPTree tree : trees) {
            tree.evalFitness(data);
        }

        Arrays.sort(trees); 
    }

    /*
     * Purpose: Returns an ArrayList of the top 10 GPTrees (Lowest fitness first)
     */
    public ArrayList<GPTree> getTopTen() {
        ArrayList<GPTree> top = new ArrayList<>();
        int limit = Math.min(10, trees.length);
        for(int i = 0; i < limit; i++) {
            top.add(trees[i]);
        }

        return top;
    }

    /*
     * Purpose: Prints the best fitness values in this generation:
     */
    public void printBestFitness() {
        if (trees == null || trees.length == 0) return;
        System.out.println("Best Fitness: " + trees[0].getFitness());
    }

    /*
     * Purpose: Evolves this generation into the next one using crossover and cloning.
     */
    public void evolve() throws CloneNotSupportedException {
        GPTree[] nextGen = new GPTree[trees.length];

        //Ensure that the current generation is evaluated and sorted first:
        evalAll();

        for(int i = 0; i < trees.length; i += 2) {
            // Select two parents from the more fit half:
            GPTree parent1 = selectParent();
            GPTree parent2 = selectParent();

            GPTree child1 = (GPTree) parent1.clone();
            GPTree child2 = (GPTree) parent2.clone();

            // Perform crossover:
            child1.getRoot().crossover(child2.getRoot(), rand);
            
            nextGen[i] = child1;
            if (i + 1 < trees.length) {
                nextGen[i + 1] = child2;
            }
        }

        // Replace old generation with new generation:
        this.trees = nextGen;
    }

    /*
     * Helper Method
     * Purpose: Randomly selects a parent from the top half of the population/
     */
    private GPTree selectParent() {
        int cutoff = trees.length / 2;
        return trees[rand.nextInt(cutoff)];
    }

    /*
     * Purpose: Accessor for the best tree
     */
    public GPTree getBestTree() {
        return trees[0];
    }

    /*
     * Purpose: Prints the best tree
     */
    public void printBestTree() {
        if (trees == null || trees.length == 0) return;
        System.out.println(getBestTree());
    }
}
