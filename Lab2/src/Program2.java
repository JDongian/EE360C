/*
 * Name: Joshua Dong
 * EID: jid295
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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

    private HashMap<Vertex, HashMap<Vertex, Edge>> hubs;

    Program2() {
        super();
        updateMap();
    }
    
    Program2(String locationFile) {
        super(locationFile);
        updateMap();
    }
    
    Program2(String locationFile, double transmissionRange) {
        super(locationFile, transmissionRange);
        updateMap();
    }
    
    Program2(double transmissionRange, String locationFile) {
        super(transmissionRange, locationFile);
        updateMap();
    }

    public void setTransmissionRange(double transmissionRange) {
        this.transmissionRange = transmissionRange;
        updateMap();
    }
    
    private void updateMap() {
        hubs = new HashMap<Vertex, HashMap<Vertex, Edge>>();

        for (Vertex v: location) {
            hubs.put(v, new HashMap<Vertex, Edge>());
        }

        for (Edge e: edges) {
            Vertex u = location.get(e.getU());
            Vertex v = location.get(e.getV());

            if (u.distance(v) <= transmissionRange) {
                hubs.get(u).put(v, e);
                hubs.get(v).put(u, e);
            }
        }
    }

    private HashMap<Vertex, Edge> getNeighbors(Vertex center) {
        return hubs.get(center);
    }

    private Vertex getClosest(Vertex source, Vertex sink) {
        HashMap<Vertex, Edge> neighbors = getNeighbors(source);
        Vertex bestTarget = null;
        double maxDistance = source.distance(sink);

        for (Vertex neighbor: neighbors.keySet()) {
            double distance = neighbor.distance(sink);
    
            if (distance < maxDistance) {
                maxDistance = distance;
                bestTarget = neighbor;
            }
        }
        
        return bestTarget;
    }

    private ArrayList<Vertex> gpsrPath(Vertex source, Vertex sink,
            ArrayList<Vertex> path) {
        if (source.equals(sink)) {
            return path;
        } else {
            Vertex closestVertex = getClosest(source, sink);
            if (closestVertex != null) {
                path.add(closestVertex);
                return gpsrPath(closestVertex, sink, path);
            } else {
                return EMPTY_PATH;
            }
        }
    }

    /* This method returns a path from a source at location sourceIndex 
       and a sink at location sinkIndex using the GPSR algorithm. An empty
       path is returned if the GPSR algorithm fails to find a path. */
    public ArrayList<Vertex> gpsrPath(int sourceIndex, int sinkIndex) {
        ArrayList<Vertex> init = new ArrayList<Vertex>();
        Vertex start = location.get(sourceIndex);
        Vertex end = location.get(sinkIndex);
        init.add(start);

        return gpsrPath(start, end, init);
    }
    
    private ArrayList<Vertex> nearestTraversal(HashMap<Vertex, Vertex> previous,
            Vertex source, Vertex sink) {
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        Vertex current = sink;

         while (current != source) {
            path.add(current);
            current = previous.get(current);
            if (current == null) {
                return EMPTY_PATH;
            }
        }

        return path;
    }

    public interface Measure {
        public double distance(Vertex a, Vertex b);
    }

    private ArrayList<Vertex> dijkstra(ArrayList<Vertex> graph,
            Vertex source, Vertex sink,
            Measure measure) {
        HashMap<Vertex, Double> dist = new HashMap<Vertex, Double>();
        HashMap<Vertex, Vertex> previous = new HashMap<Vertex, Vertex>();

        for (Vertex v: graph) {
            dist.put(v, Double.POSITIVE_INFINITY);
            previous.put(v, null);
        }
        dist.put(source, 0.0);

        ArrayList<Vertex> Q = new ArrayList<Vertex>(graph.size());
        for(Vertex v: graph) {
            Q.add(v); // TODO: check this (clone?)
        }

        while (!Q.isEmpty()) {
            Vertex u = Q.stream().min((a, b) ->
                    Double.compare(dist.get(a), dist.get(b))).get();

            if (dist.get(u) == Double.POSITIVE_INFINITY) {
                break;
            }
            Q.remove(u);
            // Vertex does not implement .equals, so this will compare Objects
            // This should work anyway, since we don't make any new Verticies
            // and only pass around the references.
            if (u.equals(sink)) {
                break;
            }

            for (Vertex v: getNeighbors(u).keySet()) {
                double alt = dist.get(u) + measure.distance(u, v);

                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    previous.put(v, u);
                    // Reorder v in the Queue
                    // decrease-key neighbor in Q;
                }
            }
        }

        return nearestTraversal(previous, source, sink);
    }

    /* This method returns a path (shortest in terms of latency) from a source at
       location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
       An empty path is returned if Dijkstra's algorithm fails to find a path. */
    /* The following code is meant to be a placeholder that simply 
       returns an empty path. Replace it with your own code that 
       implements Dijkstra's algorithm. */
    public ArrayList<Vertex> dijkstraPathLatency(int sourceIndex, int sinkIndex) {
        Vertex source = location.get(sourceIndex);
        Vertex sink = location.get(sinkIndex);

        return dijkstra(location, source, sink, (u, v) -> {
            return hubs.get(u).get(v).getW();
        });
    }
    
    /* This method returns a path (shortest in terms of hops) from a source at
       location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
       An empty path is returned if Dijkstra's algorithm fails to find a path. */
    /* The following code is meant to be a placeholder that simply 
       returns an empty path. Replace it with your own code that 
           implements Dijkstra's algorithm. */
    public ArrayList<Vertex> dijkstraPathHops(int sourceIndex, int sinkIndex) {
       Vertex source = location.get(sourceIndex);
        Vertex sink = location.get(sinkIndex);

        return dijkstra(location, source, sink, (u, v) -> {
            hubs.get(u).get(v); // this is a horrible assert
            return 1;
        });
    }
}
