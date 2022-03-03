/**
 * @author Nikhil Kashyap
 */

import java.io.*;
import java.util.Arrays;

/**
 * Summary of tasks CourseGraph is responsible for:
 *      a) Read the input file, extract courses from it.
 *      b) Generate a graph where each vertex is a course
 *      c) Generate adjacency matrix for the graph
 *      d) Generate readable final exam schedule
 *      e) Write the results to a text file
 *
 * Note:
 * During construction of graph, we make use of dictionaries to only insert unique elements into graph. A RedBlackTree
 * is used as a dictionary.
 */
public class CourseGraph {
    /**
     * Matrix - Holds the adjacency matrix for the graph of courses
     * colors - a boolean matrix which indicates colors available for a particular vertex
     * vertices - an integer array where index is the vertex and value at that index is its color
     * uniqueCount - an integer value associated with every course, also indicates the count of nodes
     * RB_Dictionary - a dictionary which keeps track of unique elements to be inserted to the graph
     * resultFile - a text file where the output will be appended
     */
    int[][] Matrix;
    boolean[][] colors;
    int[] vertices;
    public int uniqueCount;
    RedBlackTree RB_Dictionary;
    File resultFile;

    /**
     * Initialises all the class variables, calls methods to generate and display:
     *      a) Graph (In the form of adjacency matrix)
     *      b) courses
     *      c) Final Exam schedule
     * @preconditons
     *  A file with "filename" exists in a location accessible by the program.
     * @param filename
     *   Name of file from which input for the program is read.
     */
    public CourseGraph(String filename) throws IOException {
        uniqueCount = 0;
        Matrix = new int[20][20];
        RB_Dictionary = new RedBlackTree();
        readfile(filename);
        createResultFile();
        vertices = new int[uniqueCount];
        colors = new boolean[uniqueCount][uniqueCount];
        colorGraph();
        printSchedule();
    }

    /**
     * From: https://www.andrew.cmu.edu/user/mm6/95-771/Homeworks/homework3/S22Homework3.pdf
     * Reads the input from a text file
     * @preconditons
     *  A file with "filename" exists in a location accessible by the program. Also, we assume that input format is as
     *  expected by the program.
     * @postcondition
     *  A graph is generated where each vertex is a course and edge connecting 2 vertices indicates a student is taking
     *  them simultaneously.
     */
    public void readfile(String filename){
        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line;

            line = in.readLine();
            while(line != null) {
                generateMatrix(line);
                line = in.readLine();
            }
        }
        catch(IOException e) {
            System.out.println("IO Exception");
        }
    }

    /**
     * Creates a text file where the results are printed directed.
     */
    public void createResultFile(){
        try {
             resultFile= new File("result.txt");
             resultFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Creates an adjacency matrix for the graph where:
     *  a) Each vertex of the graph represents a single course
     *  b) An edge between 2 vertices represent that they have been taken simultaneously by a student
     * @param line
     *  A single line from a file where the input is in the form: "firstName,LastName N course-1 course-2 course-N"
     * @preconditons
     *  The input string is of the format mentioned above. A dictionary implementation using RedBlack Tree is defined.
     * @postcondition
     *  An adjacency matrix is populated based on the input read from the input file.
     */
    public void generateMatrix(String line) {
       String[] tokens = line.split("\\s+");

       //inserting elements into hashmap with unique integer value
       for(int i = 2; i < tokens.length; i++){
           if(!checkIfPresent(tokens[i])){
               RB_Dictionary.insert(tokens[i], uniqueCount);
               uniqueCount = uniqueCount + 1;
           }
       }

       //generating adjacency matrix
       for(int j = 2; j < tokens.length; j++){
           for(int k = j+1; k < tokens.length; k++){
                   Matrix[RB_Dictionary.get(tokens[j])][RB_Dictionary.get(tokens[k])] =
                           Matrix[RB_Dictionary.get(tokens[k])][RB_Dictionary.get(tokens[j])] = 1;
           }
       }
    }

    /**
     * We make use of another boolean matrix to track which color is available for a particular vertex. Further, we color
     * each vertex and populate the Integer Array vertices, where its index is vertices and its value is its color.
     *  a) We initialize a 20 X 20 boolean matrix with True - Worst case we will need 20 colors for 20 courses
     *  b) We color first vertex with first color & note down all vertices this vertex is connected to.
     *  c) We mark the first color unavailable to all the connected vertices.
     *  d) We move to the second vertex and do the same.
     * @preconditons
     *  An adjacency matrix for the graph is generated.
     * @postcondition
     *  Each vertex of the graph is colored such that no 2 vertices connected with an edge is colored same.
     */
    public void colorGraph(){
        //Initially all colors are available
        int nodes = uniqueCount;

        //All colors are available in the start
        for(int i = 0; i < nodes; i++){
            for(int j = 0; j < nodes; j++){
                colors[i][j] = true;
            }
        }

        //Coloring each vertex
        for(int i = 0; i < nodes; i++){
            for(int j = 0; j < nodes; j++){
                if(colors[i][j]){
                    vertices[i] = j;
                    break;
                }
            }

            for(int k = 0; k < nodes; k++){
                if(Matrix[i][k] == 1){
                    colors[k][vertices[i]] = false;
                }
            }
        }
    }

    /**
     * @preconditons
     *  Adjacency matrix, vertices have been created, initialized and populated with results.
     *  result.txt has been created
     * @postcondition
     *  The output terminal displays adjacency matrix and schedule for the input file. The output is also appended to
     *  result.txt file
     * @throws IOException
     */
    public void printSchedule() throws IOException {
        FileWriter myWriter = new FileWriter("result.txt", true);
        String[] schedule = new String[uniqueCount];
        StringBuilder sb = new StringBuilder();
        int max_color = -1;

        Arrays.fill(schedule,"");

        if(resultFile.length() == 0)
            myWriter.write("nikhilka\n");

        //Print Matrix
        for(int i = 0; i < uniqueCount; i++){
            for(int j = 0; j < uniqueCount; j++){
                System.out.print(Matrix[i][j]);
                sb.append(Matrix[i][j]);
            }
            System.out.println();
            sb.append("\n");
        }
        myWriter.write(sb.toString());
        myWriter.write("\n");
        System.out.println();

        for(int i = 0; i < vertices.length; i++){
            schedule[vertices[i]] = schedule[vertices[i]] + RB_Dictionary.getKey(i) + " ";
            max_color = Math.max(max_color, vertices[i]);
        }

        //print schedule
        for(int i = 0; i <= max_color; i++){
            int j = i+1;
            String s = "Final Exam Period " + j +" => "+ schedule[i];
            System.out.println(s);
            myWriter.write(s);
            myWriter.write("\n");
        }
        myWriter.write("\n");
        myWriter.close();
    }

    public boolean checkIfPresent(String course){
        return RB_Dictionary.contains(course);
    }

    public static void main(String[] args) throws IOException {
        new CourseGraph(args[0]);
    }
}