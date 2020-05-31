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
        Graph Tree = this.G.BFS(s);
        Tree.printEdges();
        return Tree;
    }

    public Graph DFS_I(String s){
        Graph Tree = this.G.DFS_I(s);
        Tree.printEdges();
        return Tree;
    }

    public Graph DFS_R(String s){
        Graph Tree = this.G.DFS_R(s);
        Tree.printEdges();
        return Tree;
    }
    
    public Graph Dijkstra(String s){
        Graph Tree = this.G.Dijkstra(s);
        Tree.printEdges();
        return Tree;
    }

    public void RandomEdgeValues(double min, double max) {
        this.G.RandomEdgeValues(min, max);
    }
    public static void main(String[] args) {
        GeographicGraph g1 = new GeographicGraph(30,0.3,true,false);
        g1.RandomEdgeValues(5, 20);
        GeographicGraph g2 = new GeographicGraph(100,0.3,true,false);
        g2.RandomEdgeValues(5, 20);
        GeographicGraph g3 = new GeographicGraph(500,0.3,true,false);
        g3.RandomEdgeValues(5, 20);

        Graph T1_1 = g1.Dijkstra("N1");

        Graph T2_1 = g2.Dijkstra("N1");

        Graph T3_1 = g3.Dijkstra("N1");
        try {
            g1.graphToFile("csv/Geographic1.csv");
            g2.graphToFile("csv/Geographic2.csv");
            g3.graphToFile("csv/Geographic3.csv");
            T1_1.graphToFile("csv/GeographicTree1-1.csv");
            T2_1.graphToFile("csv/GeographicTree2-1.csv");
            T3_1.graphToFile("csv/GeographicTree3-1.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }        
    }

}