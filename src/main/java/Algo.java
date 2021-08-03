import java.util.ArrayList;
import java.util.Arrays;

public class Algo {

    // Path will be stored here
    String connections;
    // Shortest path will be stored here
    ArrayList<Integer> path;
    // Shortest distance will be stored here
    int distance;

    public Algo(){
        path = new ArrayList<Integer>();
        distance = 0;
        connections = "";
    }

    public void Dijkstra(int graph[][], int source, int destination){
        int size = graph[0].length;

        path.add(source);

        // Stores prev vertices
        int prev[] = new int[size];

        // Marks vertices
        Boolean markedVertex[] = new Boolean[size];

        // Stores all the distances
        int distances[] = new int[size];

        // Initialize all markedVertex to false and all distances to maximum values
        Arrays.fill(markedVertex, false);
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        // Source to source = 0
        distances[source] = 0;

        for (int i=0; i<size-1; i++) {
            int mini = getMinimumDistance(distances, markedVertex, size);
            // Marks vertex with minimum value as true
            markedVertex[mini] = true;
            // change distance of adjacent vertices of the selected vertex
            for (int j=0; j<size; j++){
                if(!markedVertex[j] && graph[mini][j]!=0 &&
                        distances[mini]!=Integer.MAX_VALUE &&
                        distances[mini] + graph[mini][j] < distances[j]) {
                    prev[j] = mini;
                    distances[j] = distances[mini] + graph[mini][j];
                    System.out.println(distances[j]);
                }
            }
        }
        getActualDistance(distances, prev, source, destination, size);

    }

    // Method to find the vertex with minimum distance from vertices only not marked
    private int getMinimumDistance(int distances[], Boolean markedVertex[], int size) {
        int maximum = Integer.MAX_VALUE;
        int min_index = -1;
        for (int i=0; i<size; i++)
            if (markedVertex[i] == false && distances[i] <= maximum) {
                maximum = distances[i];
                min_index = i;
                System.out.println("min_index: "+ min_index);
            }
        return min_index;
    }

    // To get the actual distance from source to destination only
    private void getActualDistance(int distances[], int prev[], int source, int destination, int size) {
        for (int k=0; k<size; k++){
            if (k == destination){
                System.out.println(source + "->" + k + " is " + distances[k]);
                distance = distances[k];
                System.out.println("asd: " + distance);
                printPath(prev, k, destination);
            }
        }
        printIt(path, destination);
    }

    // Add path to ArrayList without destination
    private void printPath(int prev[], int x, int destination) {
        if(prev[x] == -1)
            return;

        printPath(prev, prev[x], destination);

        if (x == destination)
            return;

        path.add(x);
    }

    // Verify the path and add to destination
    private void printIt(ArrayList<Integer> path, int destination){
        path.add(destination);

        // print path
        for (int l=0; l<path.size(); l++)
            connections += " " + path.get(l);
    }

    public void reset(){
        path.clear();
        distance = 0;
        connections = "";
    }
}