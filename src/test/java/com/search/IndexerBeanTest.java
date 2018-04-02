package com.search;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Array;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.search.docs.DocumentIndexer;
import com.search.utils.DirectoryWalker;

/**
 * Test to see that the autowired beans are created
 * @author oonyimadu
 *
 */
//Automatically creates a spring app context for the test
@RunWith(SpringJUnit4ClassRunner.class)
//Because AppConfig includes contextCompoinent scan annotation, 
//it should create the beans we need
@ContextConfiguration(classes=AppConfig.class)
public class IndexerBeanTest
{
	@Autowired private DocumentIndexer docIndexer;
	@Autowired private DirectoryWalker dwalker;

	
	@Test
	public void checkDocIndexerProperty() 
	{
		String prop1 = "http://localhost:8983/solr";//solr.index.local.url=
		//String prop2 = "";
		assertEquals(prop1, docIndexer.getIndexUrl());
	}
	
	@Test
	public void checkDirectoryProperty() 
	{
		String prop1 = "C:/users/oonyimadu/Documents";//solr.index.local.url=
		//String prop2 = "";
		assertEquals(prop1, dwalker.get_index_directory());
	}

	@Test
	public void checkPropertyFileTypeIsArray()
	{
		assertThat(dwalker.get_filesToIndex(), instanceOf(String[].class));
	}
	
	
	@Test
	public void checkDocIndexerIsNotNull() 
	{
		assertNotNull(docIndexer);
	}
	
	@Test
	public void checkWalkerIsNotNull() 
	{
		assertNotNull(dwalker);
	}
}
