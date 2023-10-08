package test.utils;
import domini.utils.Node;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class NodeTest {

    @Test
    public void creadoraNodeTest() {
        Node node = new Node("node");
        assertNotNull(node);
        assertEquals("node", node.getData());
    }

    @Test
    public void getDataTest() {
        Node node = new Node("node");
        assertEquals("node", node.getData());
    }

    @Test
    public void getDretaTest() {
        ArrayList<Node> nodes = new ArrayList<>();
        Node a = new Node("a");
        nodes.add(a);
        Node b = new Node("b");
        nodes.add(b);
        Node c = new Node("&");
        nodes.add(c);
        Node n = new Node(nodes);

        assertEquals(a, n.getDreta());
    }

    @Test
    public void getEsquerreTest() {
        ArrayList<Node> nodes = new ArrayList<>();
        Node a = new Node("a");
        nodes.add(a);
        Node b = new Node("b");
        nodes.add(b);
        Node c = new Node("&");
        nodes.add(c);
        Node n = new Node(nodes);

        assertEquals(b, n.getEsquerre());
    }
}
