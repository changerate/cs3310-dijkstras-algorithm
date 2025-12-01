/**************************************************************
 * Carlos Vargas
 * Cal Poly Pomona -- CS 3310
 * Fall 2025
 * Programming Assignment 3
 * ------------------------------------------------------------
 * File: Main.java
 * Purpose: This is the entry point of the program. 
 * We utilize the _______________ class in order to demonstrate 
 * _________________________________.
 **************************************************************/


import java.util.Scanner;



public class Main {
    /*********************************************************
     * Function: main; run this function in order to see the 
     * ________________________________ in action. 
     * @param args
     *********************************************************/
    public static void main(String[] args) {
        String filename = "inputA"; // class file 
        int u = -1, v = -1;
        if (args.length != 0)
            // in the case where the user inputs just the filename
        filename = args[0];


        DijkstrasClass riverInstance = new DijkstrasClass(filename);
        riverInstance.printOptimalGraph();
        
        //------------------------------------------
        // Now let the user test routes between posts 
        boolean contin = true; 
        Scanner sc = new Scanner(System.in);
        while (contin) { 
            System.out.print("\nTest another post-to-post cost (y/n)? ");
            String line = sc.nextLine(); 
            if ("n".equals(line.toLowerCase())) {
                contin = false;
            }
            else if ("y".equals(line.toLowerCase())) {
                try { 
                    System.out.print("Choose the from and to posts, seperated by a space: ");
                    line = sc.nextLine(); 
                    String[] uv = line.trim().split("\\s+");
                    u = Integer.parseInt(uv[0]);
                    v = Integer.parseInt(uv[1]);
                    
                    riverInstance.printOptimalRouteInfo(u, v);
                } catch (Exception e) { 
                    System.out.println("Invalid post input: " + e);
                }
            }
        }

        
        System.out.println("\n-------------------------------------------------");
    }
}