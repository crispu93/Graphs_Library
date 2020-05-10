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
    public static void main(String[] args) {
        ErdosGraph g1 = new ErdosGraph(30, 60 , true, false);
        ErdosGraph g2 = new ErdosGraph(100, 200 , true, false);
        ErdosGraph g3 = new ErdosGraph(500, 3000, true, false);

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
            g1.graphToFile("csv/Erdos1.csv");
            g2.graphToFile("csv/Erdos2.csv");
            g3.graphToFile("csv/Erdos3.csv");
            T1_1.graphToFile("csv/ErdosTree1-1.csv");
            T1_2.graphToFile("csv/ErdosTree1-2.csv");
            T1_3.graphToFile("csv/ErdosTree1-3.csv");
            T2_1.graphToFile("csv/ErdosTree2-1.csv");
            T2_2.graphToFile("csv/ErdosTree2-2.csv");
            T2_3.graphToFile("csv/ErdosTree2-3.csv");
            T3_1.graphToFile("csv/ErdosTree3-1.csv");
            T3_2.graphToFile("csv/ErdosTree3-2.csv");
            T3_3.graphToFile("csv/ErdosTree3-3.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}