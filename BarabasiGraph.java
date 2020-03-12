import java.io.IOException;

public class BarabasiGraph {
    Graph G;
    int n;
    int d;
    int m;

    public BarabasiGraph(int n, int d, boolean directed, boolean self){
        this.n = n;
        this.d = d;
        this.m = 0;

        G = new Graph(directed, self);

        while(d-- != 0) {
            G.addNode("N" + (this.d-d));
            for (int i=1; i<(this.d-d); i++){
                G.addEdge("N" + (this.d-d), "N" + i);
                this.m++;
            }
        }
        

        for(int i=this.d+1; i<=this.n; i++){
            G.addNode("N" + i);
            
            for(int j=1; j<i; j++){
                double p = (double) G.getDegree("N"+j)/this.m;
                double rand = Math.random();
                //System.out.println(rand);
                if (rand < p) {
                    G.addEdge("N" + i, "N" + j);
                    this.m++;
                }
            }
            /*for(int j=1, aux=0; j<d && aux<this.n; j++, aux++){
                double rand = Math.random();
                if (rand < p) {
                    G.addEdge("N" + i, "N" + j);
                    this.m++;
                }
                else {
                    j--;
                }
            }*/
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
    
    public static void main(String[] args) {
        BarabasiGraph g = new BarabasiGraph(500, 5, false, false);

        try {
            g.graphToFile("Barabasi500(5).csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}