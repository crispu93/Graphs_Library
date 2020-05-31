public class Node implements Comparable<Node> {
    String label;
    int degree;
    double dist; //For Dijkstra algorithm

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
    
    public void setDist(double d) {
    	this.dist = d;
    }
    
    public double getDist() {
    	return this.dist;
    }
    
    @Override
    public int compareTo(Node other) {
        //return this.getDist().compareTo(other.getDist());
         if ( this.getDist() < other.getDist())
    		return -1;
 	else if(this.getDist() > other.getDist())
    		return 1;
 	else
    		return 0;
    }

    public static void main() {
        
    }
}
