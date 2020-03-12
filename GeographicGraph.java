import java.io.IOException;

public class GeographicGraph {
    Graph G;    // Inner graph
    int n;  // Number of nodes
    double d;    // Max distance in every arist 0<=d<=1
    int side;   // Division in side x side cells of unitary rectangle

    public GeographicGraph(int n, double d, boolean directed, boolean self) {
        this.n = n;
        this.d = d;

        G = new Graph(directed, self);

        while(n-- != 0) {
            G.addNode("N" + (this.n-n));
        }

        side = (int) Math.ceil(Math.sqrt(this.n));  

        for(int i=1; i<=this.n; i++) {
            for(int j=1; j<=this.n; j++) {
                if (i == j) {
                    if (G.isMultiGraph()) {
                        G.addEdge("N"+i,"N"+j);
                    }
                }
                else if (this.distance(i,j) <= this.d) {
                    G.addEdge("N"+i,"N"+j);
                }
            }
        }
    }

    public double distance(int n1, int n2) {
        int x0 = n1 % this.side;
        int y0 = (int) Math.floor(n1 / this.side);
        int x1 = n2 % this.side;
        int y1 = (int) Math.floor(n2 / this.side);

        double dist = Math.sqrt(Math.pow(x1-x0,2) + Math.pow(y1-y0,2) );
        dist = dist/this.side;
        return dist;
    }

    public void printEdges(){
        this.G.printEdges();
    }

    public void graphToFile(String filename) throws IOException{
        try {
            this.G.graphToFile(filename);
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Graph BFS(String s){
        return this.G.BFS(s);
    }
    
    public static void main(String[] args) {
        GeographicGraph g = new GeographicGraph(500,0.1,false,false);
        
        try {
            g.graphToFile("Geo500(0.1).csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}