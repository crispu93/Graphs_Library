public class Node {
    String label;
    int degree;

    public Node(String label) {
        this.label = label;
        this.degree = 0;
    }

    public String getLabel() {
        return this.label;
    }

    public int getDegree() {
        return this.degree;
    }

    public void upDegree() {
        this.degree++;
    }

    public static void main() {
        
    }
}