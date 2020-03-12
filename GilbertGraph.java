import java.io.IOException;

public class GilbertGraph {
    Graph G;    // Inner graph
    int n;  // Number of nodes
    double p;    // Probability of every arist

    public GilbertGraph(int n, double p, boolean directed, boolean self) {
        G = new Graph(directed, self);
        this.n = n;
        this.p = p;

        while(n-- != 0) {
            G.addNode("N" + (this.n-n));
        }

        for(int i = 1; i<=this.n; i++) {
            for(int j = 1; j<=this.n; j++) {
                double rand = Math.random();
                if ( i == j){
                    if (this.G.isMultiGraph() ) {
                        this.addEdge(rand, "N"+i, "N"+j);
                    }
                }
                else {
                    this.addEdge(rand, "N"+i, "N"+j);
                }
            } 
        }
    }

    public void addEdge(double p, String n1, String n2){
        if (p <= this.p ){
            this.G.addEdge(n1,n2);
        }
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
    public static void  main(String[] args){
        GilbertGraph g = new GilbertGraph(500, 0.001, true, false);
        
        try {
            g.graphToFile("Gilbert500(0.001).csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}