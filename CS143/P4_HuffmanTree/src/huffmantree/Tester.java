/*
 * Tester.java
 */
package huffmantree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * File name:   Tester.java <br>      
 * Class Name:  Tester <br>
 * Description: Huffman Tree Tester class - This class is used for production
 * testing only, and not intended to be implemented in the distributed
 * program. <br>
 * @version     1.0 <br>
 * @author      Brandan Haertel, Bryce Fenske <br>
 * Environment: PC, Windows 8 64-bit, jdk 1.7 <br>
 * History:     06/09/2013 1.0 completed <br>
 */
public class Tester {

    /**
     * Main Constructor - Tester Class
     */
    public Tester() throws ClassNotFoundException, IOException{
        main();
    }

    /**
     * Main Class - Runs lineraly through code used to test various items of
     * the huffman tree program.
     */
    public void main() throws ClassNotFoundException, IOException {
        Tree tree = null;


        ObjectInputStream in;
        File inFile = new File("C:\\Users\\Brandan\\Dropbox\\Java II\\P4_HuffmanTree\\Alice_In_Wonderland.huf");
        Object temp = null;

            try{
                in = new ObjectInputStream(new FileInputStream(inFile));
                temp = in.readObject();
                tree = (Tree) temp;
            }
            catch(IOException e){
                System.out.println("Failed at readin");
            }
       
            Scanner Keyin = new Scanner(System.in);
            System.out.println("Type 0 to move left, 1 to move right\nType anything else to quit\n");
            while (Keyin.hasNext()){
                int val = Integer.valueOf(Keyin.nextLine());
                if(val == 0){
                    Node nl = tree.getLeftNode();
                    System.out.println(nl.code + nl.getData() + " : " + nl.getFrequency());
                }
                else if(val == 1){
                    Node nr = tree.getRightNode();
                    System.out.println(nr.code + " : " + nr.getData() + " : " + nr.getFrequency());
                }
            }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        /*
        ArrayList<Node> nodeList = new ArrayList<>();
        String[] stringArray = new String[]{
            "A", "B", "E",
            "R", "r", "W",
            "C", "T", "N"
        };
        
        int[] intArray = new int[]{
            5,8,6,7,2,9,3,4,8
        };
        
        for(int i = 0; i < stringArray.length; i++){
            Node n = new Node(stringArray[i], intArray[i], null, null);
            nodeList.add(n);
        }
        
        Scanner in = new Scanner(System.in);
        Tree tree = new Tree(nodeList);
        
        System.out.println(tree.getMapValue('A'));
        
        boolean cont = true;
        System.out.println("Type 0 to move left, 1 to move right\nType anything else to quit\n");
        while (in.hasNext()){
            int val = Integer.valueOf(in.nextLine());
            if(val == 0){
                Node nl = tree.getLeftNode();
                System.out.println(nl.code + nl.getData() + " : " + nl.getFrequency());
            }
            else if(val == 1){
                Node nr = tree.getRightNode();
                System.out.println(nr.code + nr.getData() + " : " + nr.getFrequency());
            }
                
            else
                cont = false;
        }
        
        */
    }
    
}
