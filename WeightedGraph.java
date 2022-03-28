// Graph Class
// Implements (partially) a directed graph with weighted edges.
// Vertices are Objects.
// Edge weights are integers.
public class WeightedGraph implements WeightedGraphInterface {
    public static int NULL_EDGE = 0;
    private int numVertices;
    private int maxVertices;
    private Object[] vertices;
    private int[][] edges;
    private boolean[] marks;  // marks[i] is mark

    // for vertices[i]

    public WeightedGraph()
    // Post: Arrays of size 50 are dynamically //allocated for
    // marks and vertices, and of size 50 X 50 for //edges
    // numVertices is set to 0; maxVertices is set //to 50
    {
        numVertices = 0;
        maxVertices = 50;
        vertices = new Object[50];
        marks = new boolean[50];
        edges = new int[50][50];
    }

    public WeightedGraph(int maxV)
    // Post: Arrays of size maxV are dynamically 
    // allocated for
    // marks and vertices, and of size maxV X maxV //for edges
    // numVertices is set to 0; maxVertices is set to //maxV
    {
        numVertices = 0;
        maxVertices = maxV;
        vertices = new Object[maxV];
        marks = new boolean[maxV];
        edges = new int[maxV][maxV];
    }

    public void addVertex(Object vertex)
    // Post: vertex has been stored in vertices.
    // Corresponding row and column of edges has      
    //	been set to NULL_EDGE.
    // numVertices has been incremented
    {
        vertices[numVertices] = vertex;
        for (int index = 0; index < numVertices; index++)
        {
            edges[numVertices][index] = NULL_EDGE;
            edges[index][numVertices] = NULL_EDGE;
        }
        numVertices++;
    }

    public void addEdge(Object fromVertex, Object toVertex, int weight)
    // Post: Edge (fromVertex, toVertex) is stored //in edges
    {
        int row;
        int column;

        row = indexIs(fromVertex);
        column = indexIs(toVertex);
        edges[row][column] = weight;
    }

    private int indexIs(Object vertex)
    // Post: Returns the index of vertex in vertices
    {
        int index = 0;
        // while (!vertex.equals(vertices[index])) {
        while (vertex != vertices[index]) {
            index++;
        }
        return index;
    }

    public int weightIs(Object fromVertex, Object toVertex)
    // Post: Returns the weight associated with the edge
    // fromVertex, toVertex)
    {
        int row;
        int column;

        row = indexIs(fromVertex);
        column = indexIs(toVertex);
        return edges[row][column];
    }

    public QueueInterface getToVertices(Object vertex)
    // Returns a queue of the vertices that are //adjacent from vertex.
    {
        QueueInterface adjVertices = new LinkedQueue();
        int fromIndex;
        int toIndex;
        fromIndex = indexIs(vertex);
        for (toIndex = 0; toIndex < numVertices; toIndex++) {
            if (edges[fromIndex][toIndex] != NULL_EDGE) {
                adjVertices.enqueue(vertices[toIndex]);
            }
        }
        return adjVertices;
    }

    public boolean isEmpty()
    // Determines whether this graph is empty.
    {
        return numVertices == 0;
    }

    public boolean isFull()
    // Determines whether this graph is full.
    {
        return numVertices == 50;
    }

    public void clearMarks()
    // Sets marks for all vertices to false.
    {
        for (int i = 0; i < maxVertices; i++) {
            marks[i] = false;
        }
    }

    public void markVertex(Object vertex)
    // Sets mark for vertex to true.
    {
        marks[indexIs(vertex)] = true;
    }

    public boolean isMarked(Object vertex)
    // Determines if vertex has been marked.
    {
        return marks[indexIs(vertex)];
    }

    public void removeEdge(Object fromVertex, Object toVertex, int weight) {
        int fromIndex = indexIs(fromVertex);
        int toIndex = indexIs(toVertex);
        edges[fromIndex][toIndex] = NULL_EDGE;
    }

    public boolean isExistVertex(Object vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertex == vertices[i]) {
                return true;
            }
        }
        return false;
    }

    public String bfsHelper(Object vertex) {
        String bfsOutput = "";
        if (vertex == null) {
            return "";
        }
        QueueInterface queue = new LinkedQueue();
        queue.enqueue(vertex);
    
        while (!queue.isEmpty()) {
            Object obj = queue.dequeue();
            if (isMarked(obj)) {
                continue;
            }
            markVertex(obj);
            bfsOutput += obj + ",";
    
            QueueInterface allNeighbors = getToVertices(obj);
            if (allNeighbors == null) {
                continue;
            }
            while (!allNeighbors.isEmpty()) {
                Object recentNeighbor = allNeighbors.dequeue();
                if (!isMarked(recentNeighbor)) {
                    queue.enqueue(recentNeighbor);
                }
            }
        }
        return bfsOutput;
    }

    public String BFS(Object node) {
        String bfsOutput = bfsHelper(node);
    
        for (Object vertex : vertices) {
            if (!isMarked(vertex)) {
                bfsOutput += bfsHelper(vertex);
            }
        }
        return bfsOutput;
    }
}