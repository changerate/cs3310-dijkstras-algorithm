/**************************************************************
 * Carlos Vargas
 * Cal Poly Pomona -- CS 3310
 * Fall 2025
 * Programming Assignment 3
 * ------------------------------------------------------------
 * File: Node.java
 * Purpose: This class is used in Dijkstra's algorithm for the 
 * priority queue. It implements the Comparable class in order 
 * to write its own comparison based on the distance member. 
 **************************************************************/


class Node implements Comparable<Node> {
    int id;
    int dist;

    
    /*********************************************************
     * Function Node; constructor initializes a Node object
     * with a specified vertex id and a corresponding current
     * distance value. These values are used during Dijkstra's
     * algorithm when updating and retrieving priority queue
     * entries.
     *
     * @param id   the index of the graph vertex
     * @param dist the tentative shortest distance from the source
     *********************************************************/
    Node(int id, int dist) {
        this.id = id;
        this.dist = dist;
    }


    /*********************************************************
     * Function compareTo; this method compares this Node to
     * another Node based on their distance values. It is used
     * by the priority queue to determine which Node should be
     * processed first during Dijkstra's algorithm.
     *
     * @param other the Node to compare against
     * @return a negative value if this Node has a smaller
     *         distance, zero if equal, and a positive value
     *         if this Node has a larger distance
     *********************************************************/
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.dist, other.dist);
    }
}
