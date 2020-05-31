import java.util.HashMap;
//import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

//import org.w3c.dom.Node;

public class Graph {
    HashMap<String, Node> nodes;
    HashMap<String, Edge> edges;
    boolean directed;
    boolean self;

    public Graph(boolean directed, boolean self) {
        this.nodes = new HashMap<String, Node> ();
        this.edges = new HashMap<String, Edge> ();
        this.directed = directed;
        this.self = self;
    }

    public boolean isDirected() {
        return this.directed;
    }

    public boolean isMultiGraph(){
        return this.self;
    }

    public boolean isEdge(String n1, String n2){
        return this.edges.containsKey(n1 + "-" + n2);
    }
    public void addNode(String label){
        //if (!this.nodes.containsKey(label)) {
            Node aux = new Node(label);
            this.nodes.put(label, aux);
        //}
    }

    public int getDegree(String label){
        return this.nodes.get(label).getDegree();
    }

    public void updateNode(String label) {
        this.nodes.get(label).upDegree();
    }

    // Create an edge if two nodes exists
    public void addEdge(String n1, String n2) {
        if (this.nodes.containsKey(n1) && this.nodes.containsKey(n2)){
            Edge aux = new Edge(n1,n2);
            this.updateNode(n2);
            this.edges.put(aux.getId(), aux);
            if( !this.directed ){
                Edge aux2 = new Edge(n2,n1);
                this.updateNode(n1);
                this.edges.put(aux2.getId(), aux2);
            }
        }
    }

    public void printEdges(){
        this.edges.forEach((key,value) -> System.out.println( key ));
    }

    public void graphToFile(String filename) throws IOException {
        FileWriter file = new FileWriter(filename);
        
        /*this.nodes.forEach((key,value) ->  {
            try {
                file.write(value.getLabel() + ";" + value.getLabel() + "\n");
                
            } catch(IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            
        });*/

        this.edges.forEach((key,value) ->  {
            try {
                file.write(value.getFirst() + ";" + value.getSecond() + "\n");
                
            } catch(IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            
        });
        file.close();
    }

    public List<String> getAdj(String label){
        
        List<String> lj_aux = new ArrayList<>();
        this.edges.forEach((key,value) -> { 
            //System.out.println(value.getFirst()+" "+label);   
            if(value.getFirst().equals(label)){
                lj_aux.add(value.getSecond());
            }
        } );
        return lj_aux;
    }

    public Graph BFS(String s) {
        // Container for the tree generated 
        Graph T = new Graph(this.directed, this.self);
        List<String> li = new  ArrayList<>();
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        
        this.nodes.forEach((key,value) -> {
            visited.put(key, false);
            T.addNode(key);
        });
        
        //String aux_label = s.getLabel();
        String aux_label = s;
        li.add(aux_label);
        visited.replace(aux_label, true);
        
        while(!li.isEmpty()){
            
            aux_label = li.get(li.size()-1);
            li.remove(li.size()-1);

            List<String> lj = this.getAdj(aux_label);
            //System.out.println(lj.size());

            for (String x: lj) {
                if(!visited.get(x)){
                    visited.replace(x,true);
                    li.add(x);
                    T.addEdge(aux_label, x);
                } 
            }
        }

        return T;
    }

    public Graph DFS_I(String s) {
        // Container for the tree generated 
        Graph T = new Graph(this.directed, this.self);
        List<String> li = new  ArrayList<>();
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

        this.nodes.forEach((key,value) -> {
            visited.put(key, false);
            T.addNode(key);
        });
        
        String aux_label = s;
        li.add(aux_label);
        visited.replace(aux_label, true);
        
        while(!li.isEmpty()){
            
            aux_label = li.get(0);
            li.remove(0);

            List<String> lj = this.getAdj(aux_label);
            //System.out.println(lj.size());

            for (String x: lj) {
                if(!visited.get(x)){
                    visited.replace(x,true);
                    li.add(x);
                    T.addEdge(aux_label, x);
                } 
            }
        }

        return T;
    }

    public void DFS_aux(String s, HashMap<String, Boolean> visited, Graph T){
        String aux_label = s;
        visited.replace(aux_label, true);

        List<String> lj = this.getAdj(aux_label);
            //System.out.println(lj.size());

            for (String x: lj) {
                if(!visited.get(x)){
                    T.addEdge(aux_label, x);
                    DFS_aux(x, visited, T);
                } 
            }
        
    }

    public Graph DFS_R(String s) {
        // Container for the tree generated 
        Graph T = new Graph(this.directed, this.self);
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

        this.nodes.forEach((key,value) -> {
            visited.put(key, false);
            T.addNode(key);
        });

        DFS_aux(s, visited, T);

        return T;
    }

    public void RandomEdgeValues(double min, double max) {
        this.edges.forEach((key,value) ->  {
            double w = (max-min)*Math.random()+min;
            w = Math.floor(w*100d)/100d;
            value.setWeight(w);
        });
    }

    public Graph Dijkstra(String s) {
        // Container for the tree generated 
        Graph T = new Graph(this.directed, this.self);
        HashMap<String, Double> dist = new HashMap<String, Double>();
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

        this.nodes.forEach((key,value) -> {
            dist.put(key, Double.MAX_VALUE);
            visited.put(key, false);
        });

        dist.replace(s, 0.0);
        PriorityQueue<Node> q = new PriorityQueue<Node>();
        
        Node aux = new Node(s);
        aux.setDist(0);
        q.add(aux);
        
        while (!q.isEmpty()){
            Node aux2 = q.poll();
            String aux_label = aux2.getLabel();
            if (visited.get(aux_label) == true)
                continue;
            visited.replace(aux_label, true);
            T.addNode(aux_label + "(" + dist.get(aux_label) + ")");

            List<String> lj = this.getAdj(aux_label);
            //System.out.println(lj.size());

            for (String x: lj) {
                double weight = this.edges.get(aux_label + "-" + x).getWeight();
                double aux_dist = dist.get(aux_label);
                if (aux_dist + weight < dist.get(x) ) {
                    dist.replace(x, aux_dist+ weight);
                    Node aux_adj = new Node(x);
                    aux_adj.setDist(dist.get(x));
                    q.add(aux_adj);
                    T.addNode(x + "(" + dist.get(x) + ")");
                    T.addEdge(aux_label + "(" + dist.get(aux_label) + ")", x + "(" + dist.get(x) + ")");
                }
            }
        }
        return T;
    }

    public static void main(String[] args){
        Graph g = new Graph(true, false);
        g.addNode("n1");
        g.addNode("n4");
        g.addNode("n3");
        g.addNode("n2");
        g.addNode("n6");
        g.addNode("n5");
        g.addEdge("n1", "n2");
        g.addEdge("n1", "n3");
        g.addEdge("n1", "n4");
        g.addEdge("n3", "n5");
        g.addEdge("n3", "n6");
        g.addEdge("n4", "n6");
        g.RandomEdgeValues(0, 10);
        Graph T = g.Dijkstra("n1");
        g.printEdges();
        T.printEdges();
        try {
            g.graphToFile("test/mygraph.csv");
            T.graphToFile("test/dijkstra.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
