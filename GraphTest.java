/**
 * Filename:   TestHashTableDeb.java
 * Project:    p3
 * Authors:    Debra Deppeler (deppeler@cs.wisc.edu)
 * 
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   before 10pm on 10/29
 * Version:    1.0
 * 
 * Credits:    None so far
 * 
 * Bugs:  
 */

import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * @author nvsmith
 *
 */
public class GraphTest {
	static Graph graphObject;
	
    /** Initialize empty graph to be used in each test */
    @BeforeEach
    public void setUp() throws Exception {
        // TODO: change HashTable for final solution
         graphObject = new Graph();
    }

    /** make sure that variables are reset     */
    @AfterEach
    public void tearDown() throws Exception {
        graphObject = null;
    }
    
	@Test
	public void test000_addEdge() {
			graphObject = new Graph();
			graphObject.addVertex("nate");
			graphObject.addVertex("Smith");
			graphObject.addEdge("nate", "Smith");

	}
	@Test
	public void test001_boundary_conditions_add_Vertex() {

			graphObject.addVertex("Nate");
			graphObject.addVertex(null);
			graphObject.addVertex("Nate");
			assertEquals(1,graphObject.order());
			
	}
	
	@Test
	public void test002_boundary_conditions_removeVertex() {
		graphObject.addVertex("Nate");
		graphObject.removeVertex("Van");
		graphObject.addVertex("Smith");
		graphObject.removeVertex(null);
		assertEquals(2,graphObject.order());
	}
	
	@Test
	public void test003_boundary_conditions_addEdge() {
		graphObject.addEdge("Nate","Smith");
		assertEquals(2,graphObject.order());
		graphObject.addEdge("Nate", "Smith");
		assertEquals(1,graphObject.size());
		graphObject.addEdge("Nate", "Van");
		assertEquals(3,graphObject.order());
		assertEquals(2,graphObject.size());
	}
	
	@Test
	public void test004_boundary_condition_removeEdge() {
		graphObject.addEdge("Nate", "Smith");
		graphObject.removeEdge("Nate", "Van");
		assertEquals(1,graphObject.size());
		graphObject.removeEdge("Van","Smith");
		assertEquals(1,graphObject.size());
		graphObject.addVertex("Van");
		graphObject.removeEdge("Van", "Smith");
		assertEquals(1,graphObject.size());
	}
	
	@Test
	public void test005_getAllVertices_Returns_correctly() {
		graphObject.addEdge("Nate", "Smith");
		Set<String> test = new HashSet<String>();
		test = graphObject.getAllVertices();
		if(!test.contains("Nate")||!test.contains("Smith"))
			fail("Set doesn't contain correct vertices");
		
		graphObject.addEdge("Nate", "Van");
		graphObject.addEdge("Van", "Smith");
		test = graphObject.getAllVertices();
		if(!test.contains("Van"))
			fail("Set doesn't contain correct vertices");
		assertEquals(3,test.size());
	}
	
	@Test
	public void test006_getAdjacentVertices_returns_correctly() {
		graphObject.addEdge("Nate", "Smith");
		graphObject.addEdge("Nate", "Van");
		graphObject.addEdge("Van", "Smith");
		List<String> smith = new ArrayList<String>();
				smith = graphObject.getAdjacentVerticesOf("Smith");
				assertEquals(0,smith.size());
		List<String> nate = new ArrayList<String>();
				nate = graphObject.getAdjacentVerticesOf("Nate");
				assertEquals(2,nate.size());

		
	}
}


