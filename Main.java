/**************************************************************
 * Carlos Vargas
 * Cal Poly Pomona -- CS 3310
 * Fall 2025
 * Programming Assignment 3
 * ------------------------------------------------------------
 * File: Main.java
 * Purpose: This is the entry point of the program. 
 * I utilize the FloydsClass in order to demonstrate 
 * computing optimal post-to-post costs on a river graph using 
 **************************************************************/


import java.util.Scanner;



public class Main {
    /*********************************************************
     * Function main; run this function to execute the river
     * routing program. Essentially turning a graph class 
     * into a class appropriate for this problem.
     * 
     * @param args optional command-line arguments; if args[0]
     *             is present, it is used as the input filename
     *********************************************************/
    public static void main(String[] args) {
        String filename = "inputA"; // class file 
        if (args.length != 0)
            // in the case where the user inputs just the filename
        filename = args[0];


        // DijkstrasClass riverInstance = new DijkstrasClass(filename);
        FloydsClass riverInstance = new FloydsClass(filename);
        riverInstance.displayResults();
        
        // Now let the user test routes between posts 
        testRoutes(riverInstance);
        
        System.out.println("\n-------------------------------------------------");
    }


    /*********************************************************
     * Function testRoutes; repeatedly prompts the user to 
     * test optimal routes between pairs of posts. When the 
     * user chooses to continue, they enter a pair of post 
     * indices (u and v), and this function displays the 
     * optimal distance and path.
     * 
     * @param riverInstance a FloydsClass object that has 
     *        already computed all shortest-path information
     *********************************************************/
    static void testRoutes(FloydsClass riverInstance) { 
        int u = -1, v = -1;
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
                    
                    riverInstance.displayPath(u, v);
                } catch (Exception e) { 
                    System.out.println("Invalid post input: " + e);
                }
            }
        }
    }
}