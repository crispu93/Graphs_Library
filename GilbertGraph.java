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
    public static void  main(String[] args){
        GilbertGraph g1 = new GilbertGraph(30, 0.1, true, false);
        GilbertGraph g2 = new GilbertGraph(100, 0.1, true, false);
        GilbertGraph g3 = new GilbertGraph(500, 0.1, true, false);
    
        Graph T1_1 = g1.BFS("N1");
        Graph T1_2 = g1.DFS_I("N1");
        Graph T1_3 = g1.DFS_R("N1");

        Graph T2_1 = g2.BFS("N1");
        Graph T2_2 = g2.DFS_I("N1");
        Graph T2_3 = g2.DFS_R("N1");

        Graph T3_1 = g3.BFS("N1");
        Graph T3_2 = g3.DFS_I("N1");
        Graph T3_3 = g3.DFS_R("N1");
        try {
            g1.graphToFile("csv/Gilbert1.csv");
            g2.graphToFile("csv/Gilbert2.csv");
            g3.graphToFile("csv/Gilbert3.csv");
            T1_1.graphToFile("csv/GilbertTree1-1.csv");
            T1_2.graphToFile("csv/GilbertTree1-2.csv");
            T1_3.graphToFile("csv/GilbertTree1-3.csv");
            T2_1.graphToFile("csv/GilbertTree2-1.csv");
            T2_2.graphToFile("csv/GilbertTree2-2.csv");
            T2_3.graphToFile("csv/GilbertTree2-3.csv");
            T3_1.graphToFile("csv/GilbertTree3-1.csv");
            T3_2.graphToFile("csv/GilbertTree3-2.csv");
            T3_3.graphToFile("csv/GilbertTree3-3.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}