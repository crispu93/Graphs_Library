public class Edge {
    String id;
    String first;
    String second;

    public Edge(String first, String second) {
        this.first = first;
        this.second = second;
        this.id = this.first + '-' + this.second;
    }

    public String getId() {
        return this.id;
    }

    public String getFirst() {
        return this.first;
    }

    public String getSecond() {
        return this.second;
    }
    public static void main(String[] args) {
        /*Node n_1 = new Node("N1");
        Node n_2 = new Node("N2");

        Edge e_1 = new Edge(n_1, n_2);
        String eid = e_1.getId();
        System.out.println(eid);*/
    }
}