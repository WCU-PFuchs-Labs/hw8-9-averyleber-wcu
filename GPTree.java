/*
 * Code Author: Avery Leber
 * 10/28/2025 CSC240
 * Purpose: Encapsulates the Algebra Tree (Node) and implements a crossover() method.
 */
import java.util.ArrayList;
import java.util.Random;

public class GPTree implements Collector, Comparable<GPTree>, Cloneable {
    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness;

    /*
     * Purpose: Constructor
     */
    public GPTree(Node root) {
        this.root = root;
        this.fitness = Double.MAX_VALUE; 
    }

    /*
     * Purpose: Stores nodes that have children.
     */
    @Override
    public void collect(Node node) {
        if (!node.isLeaf()) {
            crossNodes.add(node);
        }

    }

    /*
     * Purpose: Evaluate the GPTree against all rows in the DataSet
     * and compute the total squared error.
     */
    public void evalFitness(DataSet data) {
        double sumSquaredError = 0.0;
        for(DataRow row: data.getRows()) {
            double predicted = eval(row.getX());
            double diff = predicted - row.getY();
            sumSquaredError += diff * diff;
        }

        this.fitness = sumSquaredError;
    }

    /*
     * Purpose: Returns the fitness computed after evalFitness()
     */
    public double getFitness() {
        return fitness;
    }

    /*
     * Purpose: Compare GPTrees by fitness value
     * Returns -1 if this tree is less than other tree
     * Returns 1 if this tree is greater than other tree
     * Returns 0 if trees are equal 
     */
    @Override
    public int compareTo(GPTree t) {
        if (this.fitness < t.fitness) return -1;
        if (this.fitness > t.fitness) return 1;
        return 0;
    }

    /*
     * Purpose: Two GPTress are equal if their fitness values are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if(!(o instanceof GPTree)) return false;
        GPTree other = (GPTree) o;
        return this.compareTo(other) == 0;
    }

    /*
     * Purpose: Create a deep copy of this GPTree.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            GPTree copy = (GPTree) super.clone();

            // Deep clone of root:
            copy.root = (Node) root.clone();
            copy.fitness = this.fitness;
            return copy;
        } catch (Exception e) {
            throw new AssertionError("Clone not supported in GPTree: " + e);
        }
    }

    /*
     * Purpose: Return root Node.
     */
    public Node getRoot() {
        return root;
    }

       // DO NOT EDIT code below for Homework 8. 
    // If you are doing the challenge mentioned in 
    // the comments above the crossover method
    // then you should create a second crossover
    // method above this comment with a slightly 
    // different name that handles all types
    // of crossover.
    
    
    /**
     * This initializes the crossNodes field and
     * calls the root Node's traverse method on this
     * so that this can collect the Binop Nodes.
     */
    public void traverse() {
        crossNodes = new ArrayList<>();
        root.traverse(this);
    }
    
    /**
     * This returns a String with all of the binop Strings
     * separated by semicolons
     */
    public String getCrossNodes() {
        StringBuilder string = new StringBuilder();
        int lastIndex = crossNodes.size() - 1;
        for(int i = 0; i < lastIndex; ++i) {
            Node node = crossNodes.get(i);
            string.append(node.toString());
            string.append(";");
        }
        string.append(crossNodes.get(lastIndex));
        return string.toString();
    }
   
    
    /**
     * this implements left child to left child
     * and right child to right child crossover.
     * Challenge: additionally implement left to 
     * right child and right to left child crossover.
     */
    public void crossover(GPTree tree, Random rand) {
        // find the points for crossover
        this.traverse();
        tree.traverse();
        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();
        // get the connection points
        Node thisTrunk = crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);

        
        if(left) {
            thisTrunk.swapLeft(treeTrunk);
            
        } else {
            thisTrunk.swapRight(treeTrunk);
        }
        
    }

    GPTree() { 
        root = null; 
    }    
    
    public GPTree(NodeFactory n, int maxDepth, Random rand)
    {
        try {
            root = n.getOperator(rand);
            root.depth = 0;

            growNode(root, n, maxDepth, rand);
            
            fitness = Double.MAX_VALUE;
        } catch (CloneNotSupportedException e) {
            System.err.println("Error constructing GPTree: " + e);
            root = null;
        }
    }

    private void growNode(Node node, NodeFactory nf, int maxDepth, Random rand) throws CloneNotSupportedException {
        // Base case: stop growing if at max depth or node is not a Binop
        if (node.depth >= maxDepth || !(node.operation instanceof Binop)) {
            return;
        }

        // Ensure left child exists
        if (node.left == null) {
            if (rand.nextDouble() < 0.5 && node.depth + 1 < maxDepth) {
                node.left = nf.getOperator(rand);
            } else {
                node.left = nf.getTerminal(rand);
            }
            node.left.depth = node.depth + 1;
        }

        // Ensure right child exists
        if (node.right == null) {
            if (rand.nextDouble() < 0.5 && node.depth + 1 < maxDepth) {
                node.right = nf.getOperator(rand);
            } else {
                node.right = nf.getTerminal(rand);
            }
            node.right.depth = node.depth + 1;
        }

        // Recursively grow the children
        growNode(node.left, nf, maxDepth, rand);
        growNode(node.right, nf, maxDepth, rand);
    }


    
    @Override
    public String toString() { 
        return root.toString(); 
    }
    
    public double eval(double[] data) { 
        if (root == null) {
            System.err.println("Warning: GPTree root is null!");
            return 0.0;
        }
        return root.eval(data);
    }

}

