/*
 * Tree.java
 */
package huffmantree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * File name:   Tree.java <br>      
 * Class Name:  Tree <br>
 * Description: Huffman Tree Node class - Character Node for Huffman Tree
 * from a stored huffman tree.<br>
 * @version     1.0 <br>
 * @author      Brandan Haertel, Bryce Fenske <br>
 * Environment: PC, Windows 8 64-bit, jdk 1.7 <br>
 * History:     06/09/2013 1.0 completed <br>
 */
public class Tree implements Serializable{
    Node current;
    Node head;

    /**
     * Main Constructor - makes instance of Tree, Constructor sorts Node list.
     * @param in unsorted ArrayList<String> of Node
     */
    public Tree(ArrayList<Node> in){
        ArrayList<Node> nodeList = new ArrayList<>();
        if(in == null)
            throw new IllegalArgumentException();
        if(in.size() < 1)
            throw new IndexOutOfBoundsException();
        
        Node[] inArray = new Node[in.size()];
        inArray = in.toArray(inArray);
        Arrays.sort(inArray);
        for(Node n : inArray)
            nodeList.add(n);
        
        makeTree(nodeList);
    }
    
    /**
     * Main Constructor - makes instance of Tree, Constructor sorts Node array.
     * @param in unsorted String[] of Node
     */
    public Tree(Node[] in){
        ArrayList<Node> nodeList = new ArrayList<>();
        if(in == null)
            throw new IllegalArgumentException();
        if(in.length < 1)
            throw new IndexOutOfBoundsException();
        
        Arrays.sort(in);
        for(Node n : in)
            nodeList.add(n);
        
        makeTree(nodeList);
    }
    
    /**
     * Build Tree from Node List
     * @param in Sorted ArrayList<String> to build Tree.
     */
    private void makeTree(ArrayList<Node> in){
        map = new Node[256];
        ArrayList<Node> keepList = new ArrayList<>();
        for(Node n : in)
            keepList.add(n);
        while(in.size() > 1){
            Node parent = null;
            Node right = in.remove(1);
            Node left = in.remove(0);
            parent = new Node(null, left.getFrequency() 
                    + right.getFrequency(), left, right);
            in.add(parent);
            right.setParent(parent);
            left.setParent(parent);
            Collections.sort(in);
        }
        head = in.get(0);
        head.setData("HEAD NODE");
        current = head;
        head.BuildCode(0,"");
        
        makeMap(keepList);
    }
    
    /**
     * Makes code map for nodes.
     * @param in ArrayList<Node> w/ tree node references 
     * to create code mapping
     */
    public void makeMap(ArrayList<Node> in){
        for(Node n : in){
            String s = String.valueOf(n.getData());
            int i = (s.charAt(0));
            map[i] = n;
        }
    }
    
    /**
     * get code sequence for character in Tree.
     * @param c char to get map of.
     * @return String of code sequence
     */
    public String getMapValue(char c){
        String code = "";
        Node n = map[c];
        return n.code;
    }
    
    /**
     * prints the node array in Data : Frequency format
     * @param in ArrayList<Node> to print
     */
    public void printArray(ArrayList<Node> in){
        for(Node n : in){
            System.out.println(n.getData() + " : " + n.getFrequency());
        }
        System.out.println();
    }
    
    /**
     * Tree Iterator, returns left node of current node, sets current node
     * to left node, if returned node isLeaf, set current to head for
     * circular iteration.
     * @return left node of current.
     */
    public Node getLeftNode(){
        Node returned = null;
        current = current.getLeftChild();
        returned = current;
        if(returned.isLeaf())
            current = head;
        return returned;
    }
    
    /**
     * Tree Iterator, returns right node of current node, sets current node
     * to right node, if returned node isLeaf, set current to head for
     * circular iteration.
     * @return right node of current.
     */
    public Node getRightNode(){
        Node returned = null;
        current = current.getRightChild();
        returned = current;
        if(current.isLeaf())
            current = head;
        return returned;
    }
    
    /**
     * set current Node to head.
     */
    public void resetCurrentNodePosition(){
        current = head;
    }
    
    /**
     * return current Node.
     * @return current Node.
     */
    public Node getCurrentNode(){
        return current;
    }
    
    private Node[] map;
}
