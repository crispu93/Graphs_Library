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
    public static void main(String[] args) {
        ErdosGraph g1 = new ErdosGraph(30, 60 , true, false);
        g1.RandomEdgeValues(5, 20);
        ErdosGraph g2 = new ErdosGraph(100, 200 , true, false);
        g2.RandomEdgeValues(5, 20);
        ErdosGraph g3 = new ErdosGraph(500, 3000, true, false);
        g3.RandomEdgeValues(5, 20);

        Graph T1_1 = g1.Dijkstra("N1");

        Graph T2_1 = g2.Dijkstra("N1");

        Graph T3_1 = g3.Dijkstra("N1");
        try {
            g1.graphToFile("csv/Erdos1.csv");
            g2.graphToFile("csv/Erdos2.csv");
            g3.graphToFile("csv/Erdos3.csv");
            T1_1.graphToFile("csv/ErdosTree1-1.csv");
            T2_1.graphToFile("csv/ErdosTree2-1.csv");
            T3_1.graphToFile("csv/ErdosTree3-1.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}