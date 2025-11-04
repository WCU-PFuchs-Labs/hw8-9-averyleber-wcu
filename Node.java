/**
 * Code Author: Avery Leber
 * CSC240 10/18/2025
 * Purpose: Randomly generated expression trees of various sizes and shapes.
 */
import java.util.Random;

public class Node implements Cloneable {
    public Node left;
    public Node right;
    public Op operation;
    protected int depth;

    public Node(Binop operation) {
        this.operation = operation;
        this.depth = 0;
    }

    public Node(Unop operation) {
        this.operation = operation;
        this.depth = 0;
    }

    public double eval(double[] values) {
        if (operation instanceof Unop unop) {
            return unop.eval(values);
        } else if (operation instanceof Binop binop) {
            if (left == null || right == null) {
                System.err.println("Warning: Binop node has null child!");
                return 0.0;
            }
            return binop.eval(left.eval(values), right.eval(values));
        } else {
            System.err.println("Error operation is not a Unop or Binop!");
            return 0.0;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Node copy = (Node) super.clone();
        if (left != null) copy.left = (Node) left.clone();
        if (right != null) copy.right = (Node) right.clone();
        if (operation != null) copy.operation = (Op) operation.clone();
        return copy;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public void swapLeft(Node trunk) {
        Node temp = this.left;
        this.left = trunk.left;
        trunk.left = temp;
    }

    public void swapRight(Node trunk) {
        Node temp = this.right;
        this.right = trunk.right;
        trunk.right = temp;
    }

    public Node getRandomNode(Random rand) {
        if (rand.nextDouble() < 0.1 || isLeaf()) return this;
        if (left != null && right != null) {
            return rand.nextBoolean() ? left.getRandomNode(rand) : right.getRandomNode(rand);
        } else if (left != null) return left.getRandomNode(rand);
        else return right.getRandomNode(rand);
    }

    public void replaceWith(Node newNode) {
        this.operation = newNode.operation;
        this.left = newNode.left;
        this.right = newNode.right;
        this.depth = newNode.depth;
    }

    public void traverse(Collector c) {
        // Collect this node if it's a Binop (binary operator)
        if (operation instanceof Binop) {
            c.collect(this);
        }

        // Traverse children if they exist
        if (left != null) {
            left.traverse(c);
        }
        if (right != null) {
            right.traverse(c);
        }
    }

    public void crossover(Node other, Random rand) throws CloneNotSupportedException {
        // Pick random crossover points in both trees
        Node crossoverPoint1 = this.getRandomNode(rand);
        Node crossoverPoint2 = other.getRandomNode(rand);

        if (crossoverPoint1 != null && crossoverPoint2 != null) {
            // Swap their subtrees
            Node temp = (Node) crossoverPoint1.clone();
            crossoverPoint1.replaceWith(crossoverPoint2);
            crossoverPoint2.replaceWith(temp);
        }
    }

    @Override
    public String toString() {
        String leftStr = (left != null) ? left.toString() : "null";
        String rightStr = (right != null) ? right.toString() : "null";
        if (operation instanceof Binop) return "(" + leftStr + " " + operation.toString() + " " + rightStr + ")";
        else return operation.toString();
    }
}

