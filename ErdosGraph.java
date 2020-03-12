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
    public static void main(String[] args) {
        ErdosGraph g = new ErdosGraph(30, 100 , false, false);
        Graph T = g.BFS("N1");
        T.printEdges();
        try {
            T.graphToFile("ErdosTree.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}