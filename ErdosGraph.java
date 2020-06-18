import java.io.IOException;

public class ErdosGraph {
    Graph G;    // Inner graph
    int n;  // Nodes number
    int m;  // Edges number

    public ErdosGraph(int n, int m, boolean directed, boolean self){
        this.n = n;
        this.m = m;
        G = new Graph(directed, self);

        while(n-- != 0) {
            G.addNode("N" + (this.n-n));
        }

        for(int i = 0; i<this.m; i++){
            String n1 = "N" + (int) Math.floor( Math.random()*this.n + 1 );
            String n2 = "N" + (int) Math.floor( Math.random()*this.n + 1 );
            if(G.isEdge(n1,n2)) {
                i--;
                continue;
            }
            else if (n1.equals(n2)){
                if (G.isMultiGraph() ){
                    G.addEdge(n1,n2);
                    continue;
                }
                i--;
                continue;
            }
            else {
                G.addEdge(n1,n2);
            }
            
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
    public static void main(String[] args) {
        ErdosGraph g1 = new ErdosGraph(30, 60 , false, false);
        g1.RandomEdgeValues(5, 20);
        ErdosGraph g2 = new ErdosGraph(100, 200 , false, false);
        g2.RandomEdgeValues(5, 20);
        ErdosGraph g3 = new ErdosGraph(500, 1000, false, false);
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
            g1.graphToFile("csv/Erdos1.csv");
            g2.graphToFile("csv/Erdos2.csv");
            g3.graphToFile("csv/Erdos3.csv");
            T1_1.graphToFile("csv/ErdosKruskal_D-1.csv");
            T1_2.graphToFile("csv/ErdosKruskal_D-2.csv");
            T1_3.graphToFile("csv/ErdosKruskal_D-3.csv");
            T2_1.graphToFile("csv/ErdosKruskal_I-1.csv");
            T2_2.graphToFile("csv/ErdosKruskal_I-2.csv");
            T2_3.graphToFile("csv/ErdosKruskal_I-3.csv");
            T3_1.graphToFile("csv/ErdosPrim-1.csv");
            T3_2.graphToFile("csv/ErdosPrim-2.csv");
            T3_3.graphToFile("csv/ErdosPrim-3.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}