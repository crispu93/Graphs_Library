import java.util.HashMap;
//import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import java.util.*;

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

    public void removeEdge(String n1, String n2) {
        //if(this.edges.containsKey(label)) {
        this.edges.remove(n1 + "-" + n2);
        if( !this.directed ){
            this.edges.remove(n2 + "-" + n1);
        }
        //}
    }

    public void printEdges(){
        this.edges.forEach((key,value) -> System.out.println( key + " " + value.getWeight() ));
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
            this.edges.get(value.getSecond()+"-"+value.getFirst()).setWeight(w);
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
    //Aux function for finding cycle. Returns the representative of element s
    String find(HashMap<String, String> link, String s) {
        while(s != link.get(s)) {
            s = link.get(s);
        }
        return s;
    }

    boolean same(HashMap<String, String> link, String a, String b) {
        return find(link, a) == find(link, b);
    }

    void unite(HashMap<String, String> link,HashMap<String, Integer> size, String a, String b) {
        String x = find(link, a);
        String y = find(link, b);
        if (size.get(x) < size.get(y)) {
            String aux = x;
            x = y;
            y = aux;
        }
        size.replace(x,size.get(x)+size.get(y));
        link.replace(y,x);
    }

    public Graph Kruskal_D() {
        double res = 0;
        // Container for the tree generated 
        Graph T = new Graph(this.directed, this.self);
        HashMap<String, String> link = new HashMap<String, String>();
        HashMap<String, Integer> size = new HashMap<String, Integer>();
        ArrayList<Pair <String, Double> > edg = new ArrayList<Pair <String, Double> >();

        this.nodes.forEach((key,value) -> {
            T.addNode(key);
            link.put(key, key);
            size.put(key, 1);
        });
        this.edges.forEach((key,value) -> {
            edg.add( new Pair<String, Double> (key, value.getWeight()) );
        });
        Collections.sort(edg, Comparator.comparing(p -> p.getR()));

        // Number of edges in MST and counter of edges of the graph
        int i=0, j=0;
        while(j < this.nodes.size()-1 && i<this.edges.size()) {
            Edge actual = this.edges.get(edg.get(i).getL());
            String n1 = find(link, actual.getFirst());
            String n2 = find(link, actual.getSecond());
            
            // Not a cycle
            if(!n1.equals(n2)) {
                T.addEdge(n1,n2);
                T.edges.get(n1 + "-" + n2).setWeight(actual.getWeight());
                T.edges.get(n2 + "-" + n1).setWeight(actual.getWeight());
                res = res + actual.getWeight();
                unite(link, size, n1, n2);
                j++;
            }

            i++;
            
        }
        System.out.println("El peso total del árbol de expansión mínima generado por Kruskal_D es " + res);
        return T;
    }

    // Remove diconnected vertices
    public void removeDisconnected() {
        ArrayList<String> nod = new ArrayList<String >();
        this.nodes.forEach((key,value) -> {
            if (this.getAdj(key).size() == 0){
                nod.add(key);
            }
        });
        nod.forEach(x -> {
            this.nodes.remove(x);
        });
    }
    public int countConnected(String n1){
        this.removeDisconnected();
        Graph T = this.DFS_I(n1);
        return T.edges.size();
    }

    public boolean isConnected(String n1){
        Graph Tr = this.DFS_R(n1);
        return Tr.edges.size()/2==(Tr.nodes.size()-1);
    }

    public Graph Kruskal_I() {
        double res = 0;
        // Container for the tree generated 
        Graph T = new Graph(this.directed, this.self);
        //HashMap<String, String> link = new HashMap<String, String>();
        //HashMap<String, Integer> size = new HashMap<String, Integer>();
        ArrayList<Pair <String, Double> > edg = new ArrayList<Pair <String, Double> >();

        this.nodes.forEach((key,value) -> {
            T.addNode(key);
            //link.put(key, key);
            //size.put(key, 1);
        });
        this.edges.forEach((key,value) -> {
            T.addEdge(value.getFirst(), value.getSecond());
            edg.add( new Pair<String, Double> (key, value.getWeight()) );
        });
        Collections.sort(edg, Comparator.comparing(p -> -p.getR()));

        // counter of edges in graph
        int i=0;
        while(i <this.edges.size()) {
            String l_actual = edg.get(i).getL();
            Edge actual = this.edges.get(l_actual);
            String n1 = actual.getFirst();
            String n2 = actual.getSecond();
            res = res + actual.getWeight();

            int con1 = T.countConnected(n1);
            T.removeEdge(n1,n2);
            int con2 = T.countConnected(n1);
            //boolean con= T.isConnected(n2);
            
            //System.out.println(con1 + " " + con2);
            // Connected
            if(con1 == con2) {
                res = res - actual.getWeight();
            }
            else{
                T.addNode(n1);
                T.addNode(n2);
                T.addEdge(n1, n2);
                T.edges.get(n1 + "-" + n2).setWeight(actual.getWeight());
                T.edges.get(n2 + "-" + n1).setWeight(actual.getWeight());
            }
            i++;
        }
        System.out.println("El peso total del árbol de expansión mínima generado por Kruskal_I es " + res/2);
        return T;
    }

    public Graph Prim() {
        double res = 0;
        // Container for the tree generated 
        Graph T = new Graph(this.directed, this.self);
        HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
        
        //choose a node
        String actual = this.nodes.keySet().toArray(new String[this.nodes.size()])[0];
        
        this.nodes.forEach((key,value) -> {
            T.addNode(key);
            visited.put(key, false);
        });
        visited.replace(actual, true);

        int i = 0;
        while(i < this.nodes.size()-1) {
            //variables for saving nodes
            String aux1="", aux2="";  

            double min = Double.MAX_VALUE;

            for (Map.Entry<String, Node> entry : this.nodes.entrySet()) {
                String key = entry.getKey();
                Node value = entry.getValue();
                if (visited.get(key)){
                    List<String> lj = this.getAdj(key);
                    for (String x: lj) {
                        if (!visited.get(x)){
                            double weight = this.edges.get(key + "-" + x).getWeight();
                            if ( min >  weight){
                                min = weight;
                                aux1 = key;
                                aux2 = x;
                            }
                        }
                    }
                }
            }
            /*if(aux1.compareTo("") && aux2.compareTo("")){

            }*/
            T.addEdge(aux1, aux2);
            double w = this.edges.get(aux1 + "-" + aux2).getWeight();
            T.edges.get(aux1 + "-" + aux2).setWeight(w);
            T.edges.get(aux2 + "-" + aux1).setWeight(w);

            res = res + w;
            visited.replace(aux2, true);
            i++;
        }
        
        System.out.println("El peso total del árbol de expansión mínima generado por Prim es " + res);
        return T;
    }

    public static void main(String[] args){
        Graph g = new Graph(false, false);
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
        Graph T_1 = g.Kruskal_D();
        Graph T_2 = g.Kruskal_I();
        Graph T_3 = g.Prim();
        g.printEdges();
        System.out.println("\nEdges Kruskal_D");
        T_1.printEdges();
        System.out.println("\nEdges Kruskal_I");
        T_2.printEdges();
        System.out.println("\nEdges Prim");
        T_3.printEdges();
        try {
            g.graphToFile("test/mygraph.csv");
            T_1.graphToFile("test/kruskal_d.csv");
            T_2.graphToFile("test/kruskal_i.csv");
        }catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
