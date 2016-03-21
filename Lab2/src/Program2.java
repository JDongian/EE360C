/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.ArrayList;

/* Your solution goes in this file.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */

public class Program2 extends VertexNetwork {
    /* DO NOT FORGET to add a graph representation and 
       any other fields and/or methods that you think 
       will be useful. 
       DO NOT FORGET to modify the constructors when you 
       add new fields to the Program2 class. */
    static final ArrayList<Vertex> EMPTY_PATH = new ArrayList<Vertex>();

    Program2() {
        super();
    }
    
    Program2(String locationFile) {
        super(locationFile);
    }
    
    Program2(String locationFile, double transmissionRange) {
        super(locationFile, transmissionRange);
    }
    
    Program2(double transmissionRange, String locationFile) {
        super(transmissionRange, locationFile);
    }

    private ArrayList<Integer> getNeighbors(int center, double range) {
        //TODO: fix this
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        return neighbors;
    }

    // TODO: empty location edge case
    private int getClosest(int source, int sink) {
        Vertex src = location.get(source);
        ArrayList<Integer> neighbors = getNeighbors(source, transmissionRange);
        Vertex dst = location.get(sink);
        int bestIndex = 0;
        Vertex bestTarget = location.get(neighbors.get(bestIndex));
        double bestDistance = dst.distance(bestTarget);

        for (int i = 1; i < neighbors.size(); ++i) {
            Vertex neighbor = location.get(neighbors.get(i));
            double distance = dst.distance(neighbor);

            if (distance < bestDistance) {
                bestDistance = distance;
                bestIndex = i;
                bestTarget = neighbor;
            }
        }

        if (dst.distance(src) > dst.distance(bestTarget)) { // TODO: check edge case (==)
            return bestIndex;
        } else {
            return -1;
        }
    }

    private ArrayList<Vertex> gpsrPath(int source,
            int sink,
            ArrayList<Vertex> path) {
        if (source == sink) {
            path.add(location.get(sink));
            return path;
        } else {
            int closestIndex = getClosest(source, sink);
            if (closestIndex >= 0) {
                Vertex closestVertex = location.get(closestIndex);
                path.add(closestVertex);
                return gpsrPath(closestIndex, sink, path);
            } else {
                return EMPTY_PATH;
            }
        }
    }

    public ArrayList<Vertex> gpsrPath(int sourceIndex, int sinkIndex) {
        /* This method returns a path from a source at location sourceIndex 
           and a sink at location sinkIndex using the GPSR algorithm. An empty
           path is returned if the GPSR algorithm fails to find a path. */
        //TODO: check this line
        setTransmissionRange(1);

        ArrayList<Vertex> start = new ArrayList<Vertex>();
        start.add(location.get(sourceIndex));
        return gpsrPath(sourceIndex, sinkIndex, start);
    }
    
    public ArrayList<Vertex> dijkstraPathLatency(int sourceIndex, int sinkIndex) {
        /* This method returns a path (shortest in terms of latency) from a source at
           location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
           An empty path is returned if Dijkstra's algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
           implements Dijkstra's algorithm. */
        return new ArrayList<Vertex>(0);
    }
    
    public ArrayList<Vertex> dijkstraPathHops(int sourceIndex, int sinkIndex) {
        /* This method returns a path (shortest in terms of hops) from a source at
           location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
           An empty path is returned if Dijkstra's algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
           implements Dijkstra's algorithm. */
        return new ArrayList<Vertex>(0);
    }
    
}

