package com.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersitenceUnitTests {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		emf =  Persistence.createEntityManagerFactory("MD");
	    em = emf.createEntityManager();
	
	}

	@After
	public void tearDown() throws Exception {
	
		em.close();
	}

	@Test
	public void testModeloSinSpring() {		
		Assert.assertTrue("Entity Manager is not opened.", em.isOpen());
	}

}
