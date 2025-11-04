/*
 * Code Author: Avery Leber
 * 11/03/2025 CSC240
 * Purpose: Holds a row of numeric data consisting of a dependent variable (y) 
 * and one or more independent variables (x0, x1, ...).
 */

public class DataRow {
    private double y;
    private double[] x;

    public DataRow(double y, double[] x) {
        this.y = y;
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public double[] getX() {
        return x;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("y=").append(y).append(", x=[");
        for (int i = 0; i < x.length; i++) {
            sb.append(x[i]);
            if (i < x.length - 1) {
                sb.append(", ");
        
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
