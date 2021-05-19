package com.howtostudyit.jbc.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.howtostudyit.jbc.entity.Item;
import com.howtostudyit.jbc.exception.RssException;

public class RssServiceTest {

	private RssService rssService;
	private Item item;

	@Before
	public void setUp() throws Exception {
		rssService = new RssService();
	}

	@Test
	public void testGetItemsFile() throws RssException {
		List<Item> items = rssService.getItems(new File("test-rss/javavids.xml"));
		assertEquals(10, items.size());
		Item firstItem = items.get(0);
//		System.out.println(firstItem.getTitle());
		assertEquals("How to solve Source not found error during debug in Eclipse", firstItem.getTitle());
		
		
		// <pubDate>Sun, 22 Jun 2014 20:35:49 +0000</pubDate>
		assertEquals("23 06 2014 08:35:49", new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(firstItem.getPublishedDate()));
	}

}
