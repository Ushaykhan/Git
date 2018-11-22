package bellmanfordalgorithm;
import java.util.Scanner;
import java.util.Random;
import java.util.Calendar;
/*
 *
 * @ Muhammad Ushay
 * 
 */
public class BellmanFordAlgorithm {

    public static class Edge
    {
        int source, destination, weightage;
        Edge() 
        {
            source = 0;
            destination = 0;
            weightage = 0;
        }
        Edge(int source , int destination , int weight)
        {
            this.source = source;
            this.destination = destination;
            this.weightage = weight;
        }    
    }
    public static class Graph
    {
        int noOfVertices;
        int noOfEdges;
        Edge edge[];
        Graph()
        {
            noOfVertices = 0;
            noOfEdges = 0;
            edge= new Edge[noOfEdges];
        }
        Graph(int noOfVertices , int noOfEdges)
        {
            this.noOfVertices = noOfVertices;
            this.noOfEdges = noOfEdges;
            edge = new Edge[noOfEdges];
            
            for(int i=0 ; i<noOfEdges ; i++)
                edge[i] = new Edge();
        }
    }
    public static long bellmanFord(Graph graph , int source)
    {
        long startTime = System.nanoTime();
        int noOfVertices = graph.noOfVertices;
        int noOfEdges = graph.noOfEdges;
        
        int[] shortestPath = new int[noOfVertices];
        
        for(int i=0 ; i<noOfVertices ; i++)
            shortestPath[i] = Integer.MAX_VALUE;
        
        shortestPath[source] = 0;
        
        for (int i=1; i<noOfVertices; ++i)
        {
            for (int j=0; j<noOfEdges; ++j)
            {
                int u = graph.edge[j].source;
                int v = graph.edge[j].destination;
                int weight = graph.edge[j].weightage;
                if (shortestPath[u]!=Integer.MAX_VALUE && shortestPath[u]+weight<shortestPath[v])
                    shortestPath[v]=shortestPath[u]+weight;
            }
        }
        checkForNegativeWeightCycles(graph , shortestPath);
        long endTime = System.nanoTime();
        printShortestPaths(shortestPath , noOfVertices);
        
        return endTime - startTime;
        
    }
    public static void checkForNegativeWeightCycles(Graph graph , int shortestPath[])
    {
        for (int j=0; j<graph.noOfEdges; ++j)
        {
            int u = graph.edge[j].source;
            int v = graph.edge[j].destination;
            int weight = graph.edge[j].weightage;
            if (shortestPath[u] != Integer.MAX_VALUE &&
                shortestPath[u]+weight < shortestPath[v])
             System.out.println("Graph contains negative weight cycle..");
        }
    }
    public static void printShortestPaths(int shortestPath[] , int noOfVertices)
    {
        System.out.println("Vertex   Distance from Source");
        for (int i=0; i<noOfVertices; ++i)
            System.out.println(i+"\t\t"+shortestPath[i]);
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice =0;
        
        System.out.println("Press '1' to generate nodes and edges manually..");
        System.out.println("Press '2' to generate nodes and edges randomly..");
        choice = input.nextInt();
        
        System.out.println("Enter number of vertices for the graph..");
        int noOfVertices = input.nextInt();
        System.out.println("Enter number of edges for the graph..");
        int noOfEdges = input.nextInt();
        if(noOfVertices == 0 || noOfEdges == 0)
        {
            System.out.println("No graph formed..");
            return ;
        }
        
        Graph graph = new Graph(noOfVertices , noOfEdges);
        
        if (choice == 1)
        {    
            int source = 0 , destination = 0 , weightage = 0;
            System.out.println("Enter graphs information..");
            System.out.println("Vertices will be numbered from 0 -> "+(noOfVertices-1)+"..");
            for(int i=0 ; i<noOfEdges ; i++)
            {
                System.out.println("Enter the source of the edge -->"+(i+1));
                source = input.nextInt();
                if(source>noOfVertices-1)
                {
                    System.out.println("Source will always be less than noOfVertices..");
                    i--;
                    continue;
                }
                else if(source<0)
                {
                    System.out.println("Source can never be less than 0 in a graph 0->"+(noOfVertices-1));
                    i--;
                    continue;
                }
                System.out.println("Enter the destination of the edge -->"+(i+1));
                destination = input.nextInt();
                if(destination>noOfVertices-1)
                {
                    System.out.println("Destination will always be less than noOfVertices..");
                    i--;
                    continue;
                }
                else if(source<0)
                {
                    System.out.println("Destination can never be less than 0 in a graph 0->"+(noOfVertices-1));
                    i--;
                    continue;
                }
                System.out.println("Enter the weightage of the edge..");
                weightage = input.nextInt();
            
                graph.edge[i].source = source;
                graph.edge[i].destination = destination;
                graph.edge[i].weightage = weightage;
            }
        
            while(true)
            {
                System.out.println("Enter the source of the shortest path..");
                source = input.nextInt();
                if(source>noOfVertices)
                    System.out.println("Source can never be greater than number of vertices..");
                else if (source<0)
                    System.out.println("Source can never be less than 0 in a graph 0->"+(noOfVertices-1));
                break;
            }
        
        long time = bellmanFord(graph , source);
        System.out.println("Total execution time of bellman ford algorithm is "+time+" ms.");
       }
       if(choice == 2)
       {
           Random rand = new Random();
           int randNum=0 , source=0;
           if(noOfVertices != 1)
           {
               graph.edge[0].destination=0;
               graph.edge[0].source=0;
               graph.edge[0].weightage = rand.nextInt(10)+1;
               long time = bellmanFord(graph,source);
               System.out.println("Total execution time of bellman ford algorithm is "+time+" ms.");
           }
           for(int i=0 ; i<noOfEdges ; i++)
           {
               randNum=rand.nextInt(noOfVertices-1);
               graph.edge[i].source=randNum;
               
               randNum=rand.nextInt(noOfVertices-1);
               graph.edge[i].destination=randNum;
               
               randNum=rand.nextInt(10)+1;
               graph.edge[i].weightage=randNum;
           }
           source = rand.nextInt(noOfVertices-1);
           long time= bellmanFord(graph , source);
           System.out.println("Total execution time of bellman ford algorithm is "+time+" ms.");
       }
        
    }
    
}
