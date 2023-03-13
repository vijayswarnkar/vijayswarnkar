import java.util.*;

public class helloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}

class Node {
    private Set<Node> children;
    private Map<String, Node> map;
    private String val;

    Node(String ch) {
        val = ch;
        map = new HashMap<>();
        children = new HashSet<>();
    }
    Node add(String ch){
        Node node = new Node(ch);
        map.put(ch, node);
        children.add(node);
        return node;
    }

    Node add(Node node){
        children.add(node);
        return node;
    }
    Node addSame(String ch){
        Node node = new Node(ch);
        map.put(ch, node);
        children.add(node);
        return this;
    }
    Node get(String ch) {
        return map.getOrDefault(ch, null);
    }
    void remove(String ch){
        children.remove(map.get(ch));
        map.remove(ch);
    }
    static Node createDummyDAG() {
        Node root = new Node("A");
        root.add("A1").addSame("B1").addSame("B2").add("B3");
        root.add("A2").addSame("C1").add("C2").add("C3");
        root.add("A3").add("D1").add("D2");
        root.get("A3").get("D1").add("D3");
        root.get("A3").get("D1").add("D4");
        root.get("A3").get("D1").add(root.get("A2"));
//        root.get("A3").remove("D1");
        root.print();
        root.print(0);
        System.out.println();
        System.out.println(root.get("A3").get("D1").get("D4").val);
        return root;
    }

    String tabs(int l){
        StringBuilder t = new StringBuilder();
        t.append("\t".repeat(Math.max(0, l)));
        return t.toString();
    }

    String nl(int l){
        StringBuilder t = new StringBuilder();
        t.append("\n".repeat(Math.max(0, l)));
        return t.toString();
    }

    void print(int l){
        System.out.print("\n" +tabs(l) + "{" + val + "}");
        for (Node child: children){
            child.print(l+1);
        }
        System.out.print(tabs(l) + "");
    }
    void print(){
        System.out.print("{" + val);
        for (Node child: children){
            child.print();
        }
        System.out.print("}");
    }
}
// DAG - topological sorting for dependency ordering
class DAG {
    public static void main(String[] args) {
        Node root = Node.createDummyDAG();
    }
}

