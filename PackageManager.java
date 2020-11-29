import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename:   PackageManager.java
 * Project:    p4
 * Authors:    
 * 
 * PackageManager is used to process json package dependency files
 * and provide function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own
 * entry in the json file.  
 * 
 * Package dependencies are important when building software, 
 * as you must install packages in an order such that each package 
 * is installed after all of the packages that it depends on 
 * have been installed.
 * 
 * For example: package A depends upon package B,
 * then package B must be installed before package A.
 * 
 * This program will read package information and 
 * provide information about the packages that must be 
 * installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with
 * our own Test classes.
 */

public class PackageManager {
    
    private Graph graph;
    private Package[] packages;
    /*
     * Package Manager default no-argument constructor.
     */
    public PackageManager() {
        graph = new Graph();
        packages = null;
    }
    
    /**
     * Takes in a file path for a json file and builds the
     * package dependency graph from it. 
     * 
     * @param jsonFilepath the name of json data file with package dependency information
     * @throws FileNotFoundException if file path is incorrect
     * @throws IOException if the give file cannot be read
     * @throws ParseException if the given json cannot be parsed 
     */
    public void constructGraph(String jsonFilepath) throws FileNotFoundException, IOException, ParseException {
    	
    	/**package parsing basic method from https://pages.cs.wisc.edu/~deppeler/cs400/code/JSON_Example/Main.java*/
        Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
        JSONObject jo = (JSONObject) obj;
        JSONArray packagesArray = (JSONArray) jo.get("packages");
        
        packages = new Package[packagesArray.size()];
        for(int i=0;i<packagesArray.size();i++) {
        	JSONObject jsonPackage = (JSONObject) packagesArray.get(i);
        	String packageName = (String) jsonPackage.get("name"); //name of package on each loop through json file
        	JSONArray packageDependencies = (JSONArray) jsonPackage.get("dependencies");
        	
        	String[] dependencies = new String[packageDependencies.size()];
        	for(int j = 0; j<packageDependencies.size();j++) {//loop through dependencies for each package and add to array
        		dependencies [j] = (String) packageDependencies.get(j);
        	}
        	
        	Package currPackage = new Package(packageName,dependencies);//create new package from parsed data
        	packages[i] = currPackage;//add new package to array of packages 
        }
        
        /**create graph from packages parsed*/
        for(int i =0;i<packages.length;i++) {
        	String [] dependencies = packages[i].getDependencies();
        	if(dependencies.length == 0)//if there are no dependencies then just add vertex
        		graph.addVertex(packages[i].getName());
        	else {
	        	for(int j =0;j<dependencies.length;j++) {//add edges to graph based on dependencies for each package
	        		graph.addEdge(dependencies[j], packages[i].getName());
	        	}
        	}
        }
        
    }
    
    /**
     * Helper method to get all packages in the graph.
     * 
     * @return Set<String> of all the packages
     */
    public Set<String> getAllPackages() {
        return graph.getAllVertices();
    }
    
    /**
     * Given a package name, returns a list of packages in a
     * valid installation order.  
     * 
     * Valid installation order means that each package is listed 
     * before any packages that depend upon that package.
     * 
     * @return List<String>, order in which the packages have to be installed
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the installation order for a particular package. Tip: Cycles in some other
     * part of the graph that do not affect the installation order for the 
     * specified package, should not throw this exception.
     * 
     * @throws PackageNotFoundException if the package passed does not exist in the 
     * dependency graph.
     */
    public List<String> getInstallationOrder(String pkg) throws CycleException, PackageNotFoundException {
       
        return null;
        
    }
    
    /**
     * Given two packages - one to be installed and the other installed, 
     * return a List of the packages that need to be newly installed. 
     * 
     * For example, refer to shared_dependecies.json - toInstall("A","B") 
     * If package A needs to be installed and packageB is already installed, 
     * return the list ["A", "C"] since D will have been installed when 
     * B was previously installed.
     * 
     * @return List<String>, packages that need to be newly installed.
     * 
     * @throws CycleException if you encounter a cycle in the graph while finding
     * the dependencies of the given packages. If there is a cycle in some other
     * part of the graph that doesn't affect the parsing of these dependencies, 
     * cycle exception should not be thrown.
     * 
     * @throws PackageNotFoundException if any of the packages passed 
     * do not exist in the dependency graph.
     */
    public List<String> toInstall(String newPkg, String installedPkg) throws CycleException, PackageNotFoundException {
        return null;
    }
    
    /**
     * Return a valid global installation order of all the packages in the 
     * dependency graph.
     * 
     * assumes: no package has been installed and you are required to install 
     * all the packages
     * 
     * returns a valid installation order that will not violate any dependencies
     * 
     * @return List<String>, order in which all the packages have to be installed
     * @throws CycleException if you encounter a cycle in the graph
     */
    public List<String> getInstallationOrderForAllPackages() throws CycleException {
        int size = graph.order();
        int indegree[] = new int[size];
        List<String> adj = new ArrayList<String>();
        for(int i =0; i<size;i++) {
        	
        	indegree[i] =  graph.getAdjacentVerticesOf(packages[i].getName()).size();
        	
        }
        
        Queue<Package> q = new LinkedList<Package>();
        for(int i =0;i<size;i++){
        	if(indegree[i]==0)
        		q.add(packages[i]);
        }
        
        int cnt = 0;
        
        List<String> order = new ArrayList<String>();
        while(!q.isEmpty()) {
        	Package curr = q.poll();
        	order.add(curr.getName());
        	
        	adj = graph.getAdjacentVerticesOf(curr.getName());
        	for(int i=0;i<adj.size();i++) {
        		if(--indegree[i] ==0)
        			q.add(packages[i]);
        	}
        	cnt++;
        }
        
        if(cnt!=size)
        	throw new CycleException();
        
        return order;
        
    }
    
    /**
     * Find and return the name of the package with the maximum number of dependencies.
     * 
     * Tip: it's not just the number of dependencies given in the json file.  
     * The number of dependencies includes the dependencies of its dependencies.  
     * But, if a package is listed in multiple places, it is only counted once.
     * 
     * Example: if A depends on B and C, and B depends on C, and C depends on D.  
     * Then,  A has 3 dependencies - B,C and D.
     * 
     * @return String, name of the package with most dependencies.
     * @throws CycleException if you encounter a cycle in the graph
     */
    public String getPackageWithMaxDependencies() throws CycleException {
        return "";
    }

    public static void main (String [] args) {
        System.out.println("PackageManager.main()");
    }
    
}
