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
    public static void  main(String[] args){
        GilbertGraph g1 = new GilbertGraph(30, 0.1, false, false);
        g1.RandomEdgeValues(5, 20);
        GilbertGraph g2 = new GilbertGraph(100, 0.08, false, false);
        g2.RandomEdgeValues(5, 20);
        GilbertGraph g3 = new GilbertGraph(300, 0.01, false, false);
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
            g1.graphToFile("csv/Gilbert1.csv");
            g2.graphToFile("csv/Gilbert2.csv");
            g3.graphToFile("csv/Gilbert3.csv");
            T1_1.graphToFile("csv/GilbertKruskal_D-1.csv");
            T1_2.graphToFile("csv/GilbertKruskal_D-2.csv");
            T1_3.graphToFile("csv/GilbertKruskal_D-3.csv");
            T2_1.graphToFile("csv/GilbertKruskal_I-1.csv");
            T2_2.graphToFile("csv/GilbertKruskal_I-2.csv");
            T2_3.graphToFile("csv/GilbertKruskal_I-3.csv");
            T3_1.graphToFile("csv/GilbertPrim-1.csv");
            T3_2.graphToFile("csv/GilbertPrim-2.csv");
            T3_3.graphToFile("csv/GilbertPrim-3.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}