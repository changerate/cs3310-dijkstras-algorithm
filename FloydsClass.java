/**************************************************************
 * Carlos Vargas
 * Cal Poly Pomona -- CS 3310
 * Fall 2025
 * Programming Assignment 3
 * ------------------------------------------------------------
 * File: FloydsClass.java
 * TODO: FILL OUT THE COMMENTS TO FIT FLOYDS
 * Purpose: this class loads a weighted directed 
 * graph from an input file, constructs an adjacency list and 
 * 2D distance matrix, and applies Dijkstra’s algorithm from 
 * each source node to compute optimal shortest-path costs.
 *
 * The class stores both the optimal distances and parent 
 * pointers for each run of Dijkstra’s algorithm, allowing 
 * reconstruction and printing of optimal routes between 
 * arbitrary node pairs.
 **************************************************************/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;




public class FloydsClass {
    private String filename; // holds the filename where the original 2D array graph comes from
    private int numNodes; // holds the total number of nodes in the original 2D array graph
    private int[][] graphMatrix; // the 2d array to hold the distances between each node 
    private List<List<Edge>> graph = new ArrayList<>(); // holds the adjacency list version of the graph
    private List<List<int[]>> distAndParents = new ArrayList<>(); // holds the distances and parent arrays for each source to v pairing




    public FloydsClass(String inputFilename) { 
        setFilename(inputFilename);
        readFile(); // initialize information needed 
        floydsAlgorithm();
        // printGraph();
        // applyDijkstras();
    }



    

    private void floydsAlgorithm() {
        int[][] shortestPaths = deepCopy(graphMatrix);
        System.out.println("Before shortest paths:");
        printGraphMatrix(shortestPaths);
        
        for (int k = 0; k < graphMatrix.length; k++) { 
            for (int i = 0; i < graphMatrix.length; i++) { 
                for (int j = 0; j < graphMatrix.length; j++) {
                    if (shortestPaths[i][k] >= 0 && shortestPaths[k][j] >= 0) {
                        int throughK = shortestPaths[i][k] + shortestPaths[k][j];

                        // Case 1: no path i -> j yet  → just set it
                        if (shortestPaths[i][j] == -1) {
                            shortestPaths[i][j] = throughK;
                        }
                        // Case 2: we found a shorter path
                        else if (throughK < shortestPaths[i][j]) {
                            shortestPaths[i][j] = throughK;
                        }
                    }
                }
            }
        }
        
        System.out.println("After finding shortest paths:");
        printGraphMatrix(shortestPaths);
    }




    /*********************************************************
     * Function readFile; reads the graph data from the input 
     * file specified by filename. The first line contains the 
     * number of nodes, and each subsequent line contains the 
     * upper-triangular portion of the adjacency matrix. The 
     * method initializes both a 2D distance matrix and an 
     * adjacency list representation of the graph.
     *
     * File format:
     *   Line 1: numNodes
     *   Line 2+: distances from node i to all nodes j ≥ i
     *
     * @throws IOException if the input file cannot be read
     *********************************************************/
    private void readFile() {
        int row = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (row == 0) { 
                    // get the number of nodes (posts) in this graph (river)
                    // initialize a 2D array of distances 
                    setNumNodes(Integer.parseInt(line.trim())); 
                    graphMatrix = new int[numNodes][numNodes]; 
                    for (int i = 0; i < graphMatrix.length; i++) {
                        // now initialize all the values to negative value:
                        Arrays.fill(graphMatrix[i], -1);   
                    }
                    // initialize the list graph 
                    for (int i = 0; i < numNodes; i++)
                        graph.add(new ArrayList<>());
                } else { 
                    // collect the distances of the nodes in the adjacency list graph
                    String[] dists = line.trim().split("\\s+");   // split on one or more spaces
                    int distIndex = 0;
                    for (int i = row; i < numNodes; i++) {
                        // fill out the distance matrix with either empty distances, or the dists from the file
                        graph.get(row - 1).add(new Edge(i, Integer.parseInt(dists[distIndex])));
                        graphMatrix[row - 1][i] = Integer.parseInt(dists[distIndex]);
                        distIndex += 1;
                    }
                }
                row += 1; 
            }
        } catch (IOException e) {
            System.out.println("\nError reading file: " + e);
            // return;
        }
    }




    /*********************************************************
     * Function printOptimalRouteInfo; prints both the optimal 
     * distance as an integer and the full shortest path from 
     * source node u to destination node v. This method 
     * reconstructs the path using the stored parent pointers
     * and prints the path in order from source to destination.
     *
     * @param u the source node
     * @param v the destination node
     *********************************************************/
    public void printOptimalRouteInfo(int u, int v) { 
        if (u > numNodes || v > numNodes) {
            System.out.println("u or v is not a node.");
            return;
        }

        int optimalDistance = distAndParents.get(u).get(0)[v];
        int[] parents = distAndParents.get(u).get(1); // get the list of parents TO node u
        int parent = v;

        Deque<Integer> stack = new ArrayDeque<>();

        int i = 0;
        for (; i < parents.length; i++) {
            stack.push(parent);
            if (parents[parent] != -1) { 
                parent = parents[parent];
            } else {
                i = parents.length;
            }
        }

        System.out.println("Optimal distance: " + optimalDistance);
        System.out.print("Optimal path:     ");

        while (!stack.isEmpty()) { 
            System.out.print(stack.pop());
            if (!stack.isEmpty())
                System.out.print(" -> ");
        }
        System.out.println();
    }




    //=====================================================================
    //=====================================================================
    // UTILITIES 
    //=====================================================================
    //=====================================================================

    /*********************************************************
     * Function printGraph; prints the adjacency list 
     * representation of the graph, showing each source node and 
     * its outgoing edges with corresponding weights.
     *********************************************************/
    public void printGraph() { 
        for (int src = 0; src < numNodes; src++) {
            System.out.println("The list for source " + Integer.toString(src) + ": ");
            for (Edge e : graph.get(src)) {
                System.out.println("to: " + e.to + ", weight: " + e.weight);
            }
        }
    }


    /*********************************************************
     * Function printOptimalGraph; prints a formatted matrix of 
     * optimal distances produced by Dijkstra’s algorithm. Only 
     * the upper-triangular portion is displayed (excluding 0 
     * and infinity entries) to reflect the structure of the 
     * input graph.
     *********************************************************/
    public void printOptimalGraph() { 
        System.out.println("\nOptimal Cost Matrix");

        System.out.print(" ");

        for (int i = 0; i < numNodes; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();

        for (int srcNode = 0; srcNode < distAndParents.size(); srcNode++) {
            int[] optimalArray = distAndParents.get(srcNode).get(0);

            System.out.print(srcNode);
            for (int i = 0; i < srcNode+1; i++)
                System.out.print("    ");

            for (int weight: optimalArray) {
                if (weight == -1 || weight == 0) { continue; }
                if (weight >= 100) { 
                    System.out.print(weight + " ");
                } else if (weight < 100 && weight >= 10) { 
                    System.out.print(weight + "  ");
                } else if (weight < 10) { 
                    System.out.print(weight + "   ");
                }
            }
            System.out.println();
        }
    }


    /*********************************************************
     * Function printIntArray; prints the contents of an integer 
     * array in comma-separated format.
     *
     * @param arr the array to print
     *********************************************************/
    public void printIntArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }


    public void printGraphMatrix() {
        for (int i = 0; i < graphMatrix.length; i++) {
            for (int j = 0; j < graphMatrix.length; j++) {
                if (graphMatrix[i][j] == -1) { 
                    System.out.print("  ,");
                } else { System.out.print(graphMatrix[i][j] + ","); }
            }
            System.out.println();
        }
        System.out.println();
    }


    public void printGraphMatrix(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i][j] == -1) { 
                    System.out.print("  ,");
                } else { System.out.print(arr[i][j] + ","); }
            }
            System.out.println();
        }
        System.out.println();
    }


    private static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        int[][] copy = new int[original.length][];

        for (int i = 0; i < original.length; i++) {
            // Copy each row (creates a new array, not a reference!)
            copy[i] = original[i].clone();
        }

        return copy;
    }


    /*********************************************************
     * Function printBoolArray; prints the contents of a boolean 
     * array in comma-separated format.
     *
     * @param arr the boolean array to print
     *********************************************************/
    public void printBoolArray(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
    }


    //=====================================================================
    //=====================================================================
    // SETTERS 
    //=====================================================================
    //=====================================================================
    /*********************************************************
     * Function setFilename; stores the input filename used to
     * read the graph data.
     *
     * @param inputFilename the name of the file to load
     *********************************************************/
    private void setFilename(String inputFilename) { 
        filename = inputFilename;
    }

    /*********************************************************
     * Function setNumNodes; records the number of nodes in the 
     * graph as extracted from the input file.
     *
     * @param inputNum number of nodes in the graph
     *********************************************************/
    private void setNumNodes(int inputNum) { 
        numNodes = inputNum;
    }
}