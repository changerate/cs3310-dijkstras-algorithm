/**************************************************************
 * Carlos Vargas
 * Cal Poly Pomona -- CS 3310
 * Fall 2025
 * Programming Assignment 3
 * ------------------------------------------------------------
 * File: Edge.java
 * Purpose: This class is used in Dijkstra's algorithm in order 
 * to keep track of the most suitable directed edges. 
 **************************************************************/


class Edge {
    int to; // the vertex that this directed edge is leading to
    int weight; // the weight that this directed edge is holding

    /*********************************************************
     * Function Edge; constructor initializes a Edge object
     * with a specified to vertex and a corresponding weight 
     * value. These values are used during Dijkstra's
     * algorithm.
     *
     * @param to     the to vertex
     * @param weight the weight of the directed edge
     *********************************************************/
    Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}
