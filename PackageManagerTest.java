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

import org.json.simple.parser.ParseException;
import org.junit.After;
import java.io.FileNotFoundException;
import java.io.IOException;

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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * @author nvsmith
 *
 */
public class PackageManagerTest {
	static Graph graphObject;
	static PackageManager manage;
    /** Initialize empty graph to be used in each test */
    @BeforeEach
    public void setUp() throws Exception {
         manage = new PackageManager();
    }

    /** make sure that variables are reset     */
    @AfterEach
    public void tearDown() throws Exception {
        manage = null;
    }
    
	@Test
	public void test000_construct() throws FileNotFoundException, IOException, ParseException {
			manage.constructGraph("valid.json");
			
	}
	
	@Test
	public void test001_construct_graph_test_content()throws FileNotFoundException, IOException, ParseException{
		manage.constructGraph("valid.json");
		Set<String>packages = new HashSet<String>();
		packages = manage.getAllPackages();
		
		if(!packages.contains("A")||!packages.contains("B")||!packages.contains("E")||!packages.contains("D"))
			fail("package construction failed");
	}
	
	@Test
	public void test002_getInstallationOrderForAll()throws FileNotFoundException, IOException, ParseException, CycleException{
		manage.constructGraph("valid.json");
		List<String> order = new ArrayList<String>();
		order  = manage.getInstallationOrderForAllPackages();
		String[] expected = {"A","B","C","D"};
		assertArrayEquals(expected,order.toArray());
	}
	
}


