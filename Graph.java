import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Filename:   Graph.java
 * Project:    p4
 * Authors:    
 * 
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {
	private List<Graphnode<String>>nodes; //list of nodes in graph
	private int numEdges; //number of edges in graph
	private int numVert; //number of vertices in graph
	private class Graphnode<String> {
		
		String name; //name of node
		public List<Graphnode<String>> successors; //list of successors
		public List<Graphnode<String>> predecessors; //list of predecessors
		
		public Graphnode(Graphnode<String> successor,String name) {
			successors = new ArrayList<Graphnode<String>>();
			successors.add(successor);
			this.name = name;
		}
		
		public Graphnode(String name) {
			this.name = name;
			successors = new ArrayList<Graphnode<String>>();
			predecessors = new ArrayList<Graphnode<String>>();

		}
		
		public void addSuccessor(Graphnode<String> successor ) {
			successors.add(successor);
		}
		
		public void addPredecessor(Graphnode<String>predecessor) {
			predecessors.add(predecessor);
		}
		
	}
	/*
	 * Default no-argument constructor
	 */ 
	public Graph() {
		nodes =  new ArrayList<Graphnode<String>>();
		numEdges  = 0;
		numVert = 0;
	}

	/**
     * Add new vertex to the graph.
     *
     * If vertex is null or already exists,
     * method ends without adding a vertex or 
     * throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void addVertex(String vertex) {
		if(vertex!=null) {
			if(findNode(vertex)==null) {
				nodes.add( new Graphnode<String>(vertex));
				numVert++;
			}
		}
	}

	private Graphnode<String> addVertexPriv(String vertex){
		Graphnode<String> v = new Graphnode<String>(vertex);
		nodes.add(v);
		return v;
	}
	/**
     * Remove a vertex and all associated 
     * edges from the graph.
     * 
     * If vertex is null or does not exist,
     * method ends without removing a vertex, edges, 
     * or throwing an exception.
     * 
     * Valid argument conditions:
     * 1. vertex is non-null
     * 2. vertex is not already in the graph 
     */
	public void removeVertex(String vertex) {
		Graphnode<String> v = findNode(vertex);
		
		if(v !=null) { //tests if vertex exists
			for(Graphnode<String> pred : v.predecessors) {
				if(pred !=null)//could be old vertexes that have been removed
					pred.successors.remove(v);//remove edges directed to vertex
			}
		
			nodes.remove(v); //remove vertex 
			numVert--;
		}
		
			
	}

	/**
     * Add the edge from vertex1 to vertex2
     * to this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * add vertex, and add edge, no exception is thrown.
     * If the edge exists in the graph,
     * no edge is added and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge is not in the graph
	 */
	public void addEdge(String vertex1, String vertex2) {
		Graphnode<String> v1 = findNode(vertex1);
		Graphnode <String> v2 = findNode(vertex2);
		
		if(v1!=null && v2!=null) {//make sure both vertices are in graph to check edges
			for(Graphnode<String> succ: v1.successors ) {
				if(succ.name.equals(vertex2))//if edge exists then return
					return;
			}
			
		}
		
		if(v1 == null) 
			v1 = addVertexPriv(vertex1); //add vertex and set to v1

		if(v2 == null) 
			v2 = addVertexPriv(vertex2);//add vertex and set to v2


		
		v1.addSuccessor(v2);//add edge
		v2.addPredecessor(v1);
		numEdges++;
	}
	
	private Graphnode<String> findNode(String name) {
		for(Graphnode<String> curr: nodes) {
			if(curr.name.equals(name))
				return curr;
		}
		return null;
	}
	
	/**
     * Remove the edge from vertex1 to vertex2
     * from this graph.  (edge is directed and unweighted)
     * If either vertex does not exist,
     * or if an edge from vertex1 to vertex2 does not exist,
     * no edge is removed and no exception is thrown.
     * 
     * Valid argument conditions:
     * 1. neither vertex is null
     * 2. both vertices are in the graph 
     * 3. the edge from vertex1 to vertex2 is in the graph
     */
	public void removeEdge(String vertex1, String vertex2) {
		Graphnode<String> v1 = findNode(vertex1);
		Graphnode <String> v2 = findNode(vertex2);
		
		if(v1!=null && v2!=null) { //both vertices in graph
			for(Graphnode<String> succ: v1.successors ) {
				if(succ.name.equals(vertex2)) {//if edge exists remove it
					v1.successors.remove(succ);
					v2.predecessors.remove(v1);
					numEdges--;
				}
			}
			
		}
	}	

	/**
     * Returns a Set that contains all the vertices
     * 
	 */
	public Set<String> getAllVertices() {
		Set<String> allVerts = new HashSet<String>();
		for(Graphnode<String> curr: nodes)
			allVerts.add(curr.name);
		
		return allVerts;
			
	}

	/**
     * Get all the neighbor (adjacent) vertices of a vertex
     *
	 */
	public List<String> getAdjacentVerticesOf(String vertex) {
		List<String> adj = new ArrayList<String>();
		Graphnode<String> v = findNode(vertex);
		/**
		for(Graphnode<String> pred : v.predecessors) {
			if(pred!=null)
				adj.add(pred.name);
		}
		*/
		for(Graphnode<String> succ : v.successors)
			adj.add(succ.name);
			
		return adj;
	}
	
	/**
     * Returns the number of edges in this graph.
     */
    public int size() {
        return numEdges;
    }

	/**
     * Returns the number of vertices in this graph.
     */
	public int order() {
        return nodes.size();
    }
}
