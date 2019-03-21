package zad1;

public class Node {

    private String value;
    private Node leftNode;
    private Node rightNode;

    public Node(String value, Node leftNode, Node rightNode) {
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public Node(String value) {
        this(value, null, null);
    }


    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public String getValue() {
        return value;
    }

}
