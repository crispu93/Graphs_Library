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

    public Graph Kruskal_D() {
        return this.G.Kruskal_D();
    }

    public Graph Kruskal_I() {
        return this.G.Kruskal_I();
    }

    public Graph Prim() {
        return this.G.Prim();
    }
    public static void main(String[] args) {
        GeographicGraph g1 = new GeographicGraph(30,0.333,false,false);
        g1.RandomEdgeValues(5, 20);
        GeographicGraph g2 = new GeographicGraph(100,0.1,false,false);
        g2.RandomEdgeValues(5, 20);
        GeographicGraph g3 = new GeographicGraph(300,0.08,false,false);
        g3.RandomEdgeValues(5, 20);
        g1.G.removeDisconnected();
        g2.G.removeDisconnected();
        g3.G.removeDisconnected();

        Graph T1_1 = g1.Kruskal_D();
        Graph T1_2 = g2.Kruskal_D();
        Graph T1_3 = g3.Kruskal_D();

        Graph T2_1 = g1.Kruskal_I();
        Graph T2_2 = g2.Kruskal_I();
        Graph T2_3 = g3.Kruskal_I();

        Graph T3_1 = g1.Prim();
        Graph T3_2 = g2.Prim();
        Graph T3_3 = g3.Prim();
        try {
            g1.graphToFile("csv/Geographical1.csv");
            g2.graphToFile("csv/Geographical2.csv");
            g3.graphToFile("csv/Geographical3.csv");
            T1_1.graphToFile("csv/GeographicalKruskal_D-1.csv");
            T1_2.graphToFile("csv/GeographicalKruskal_D-2.csv");
            T1_3.graphToFile("csv/GeographicalKruskal_D-3.csv");
            T2_1.graphToFile("csv/GeographicalKruskal_I-1.csv");
            T2_2.graphToFile("csv/GeographicalKruskal_I-2.csv");
            T2_3.graphToFile("csv/GeographicalKruskal_I-3.csv");
            T3_1.graphToFile("csv/GeographicalPrim-1.csv");
            T3_2.graphToFile("csv/GeographicalPrim-2.csv");
            T3_3.graphToFile("csv/GeographicalPrim-3.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }        
    }

}