package com.trade.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trade.domain.TradeEvent;
import com.trade.domain.TradePosition;
import com.trade.service.impl.TradePositioningServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TradePositioningServiceImpl.class })
public class TradePositioningServiceTest {

	private static final Logger LOG = LoggerFactory.getLogger(TradePositioningServiceTest.class);

	@Autowired
	private ApplicationContext context; 

	private Map<String, TradePosition> expectedTradePositions = new HashMap<>();

	private TradePositioningService tradePositioningService = null;

	@Before
	public void init() {
		LOG.info("Start - Initializing Expected Output()");
		expectedTradePositions.put("ACC-1234-XYZ",
				new TradePosition("ACC-1234", "XYZ", 150, new HashSet<Integer>(Arrays.asList(1234))));
		expectedTradePositions.put("ACC-2345-QED",
				new TradePosition("ACC-2345", "QED", 0, new HashSet<Integer>(Arrays.asList(5678))));
		expectedTradePositions.put("ACC-3456-RET",
				new TradePosition("ACC-3456", "RET", 0, new HashSet<Integer>(Arrays.asList(2233))));
		expectedTradePositions.put("ACC-4567-YUI",
				new TradePosition("ACC-4567", "YUI", 200, new HashSet<Integer>(Arrays.asList(6638, 8896))));
		expectedTradePositions.put("ACC-5678-HJK",
				new TradePosition("ACC-5678", "HJK", 50, new HashSet<Integer>(Arrays.asList(6363, 7666))));
		expectedTradePositions.put("ACC-6789-FVB",
				new TradePosition("ACC-6789", "FVB", 200, new HashSet<Integer>(Arrays.asList(8686, 9654))));
		expectedTradePositions.put("ACC-6789-GBN",
				new TradePosition("ACC-6789", "GBN", 100, new HashSet<Integer>(Arrays.asList(8686))));
		expectedTradePositions.put("ACC-7789-JKL",
				new TradePosition("ACC-7789", "JKL", 100, new HashSet<Integer>(Arrays.asList(1025, 1036))));
		expectedTradePositions.put("ACC-8877-JKL",
				new TradePosition("ACC-8877", "JKL", -100, new HashSet<Integer>(Arrays.asList(1025))));
		expectedTradePositions.put("ACC-9045-KLO",
				new TradePosition("ACC-9045", "KLO", 300, new HashSet<Integer>(Arrays.asList(1122, 1144, 1155))));
		expectedTradePositions.put("ACC-9045-HJK",
				new TradePosition("ACC-9045", "HJK", 0, new HashSet<Integer>(Arrays.asList(1122))));
		LOG.info("Complete - Initializing Expected Output()");
	}

	@Test
	public void testOrderedTradeEvents() {
		LOG.info("Start - testOrderedTradeEvents()");
		tradePositioningService = context.getBean(TradePositioningService.class);
		tradePositioningService.placeTradeEvent(new TradeEvent(1234, 1, "XYZ", 100, "BUY", "ACC-1234", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1234, 2, "XYZ", 150, "BUY", "ACC-1234", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(5678, 1, "QED", 200, "BUY", "ACC-2345", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(5678, 2, "QED", 0, "BUY", "ACC-2345", "CANCEL"));
		tradePositioningService.placeTradeEvent(new TradeEvent(2233, 1, "RET", 100, "SELL", "ACC-3456", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(2233, 2, "RET", 400, "SELL", "ACC-3456", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(2233, 3, "RET", 0, "SELL", "ACC-3456", "CANCEL"));
		tradePositioningService.placeTradeEvent(new TradeEvent(8896, 1, "YUI", 300, "BUY", "ACC-4567", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(6638, 1, "YUI", 100, "SELL", "ACC-4567", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(6363, 1, "HJK", 200, "BUY", "ACC-5678", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(7666, 1, "HJK", 200, "BUY", "ACC-5678", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(6363, 2, "HJK", 100, "BUY", "ACC-5678", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(7666, 2, "HJK", 50, "SELL", "ACC-5678", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(8686, 1, "FVB", 100, "BUY", "ACC-6789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(8686, 2, "GBN", 100, "BUY", "ACC-6789", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(9654, 1, "FVB", 200, "BUY", "ACC-6789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1025, 1, "JKL", 100, "BUY", "ACC-7789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1036, 1, "JKL", 100, "BUY", "ACC-7789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1025, 2, "JKL", 100, "SELL", "ACC-8877", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1122, 1, "KLO", 100, "BUY", "ACC-9045", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1122, 2, "HJK", 100, "SELL", "ACC-9045", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1122, 3, "KLO", 100, "SELL", "ACC-9045", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1144, 1, "KLO", 300, "BUY", "ACC-9045", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1144, 2, "KLO", 400, "BUY", "ACC-9045", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1155, 1, "KLO", 600, "SELL", "ACC-9045", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1155, 2, "KLO", 0, "BUY", "ACC-9045", "CANCEL"));
		Map<String, TradePosition> actualTradePositions = tradePositioningService.processTradePositions();
		Assert.assertTrue(actualTradePositions.equals(expectedTradePositions));
		LOG.info("Complete - testOrderedTradeEvents() - Actual Matched Expected Output.\n\n");
	}

	@Test
	public void testUnOrderedTradeEvents1() {
		LOG.info("Start - testUnOrderedTradeEvents1()");
		tradePositioningService = context.getBean(TradePositioningService.class);
		tradePositioningService = new TradePositioningServiceImpl();
		tradePositioningService.placeTradeEvent(new TradeEvent(1234, 2, "XYZ", 150, "BUY", "ACC-1234", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1234, 1, "XYZ", 100, "BUY", "ACC-1234", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(5678, 2, "QED", 0, "BUY", "ACC-2345", "CANCEL"));
		tradePositioningService.placeTradeEvent(new TradeEvent(5678, 1, "QED", 200, "BUY", "ACC-2345", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(2233, 2, "RET", 400, "SELL", "ACC-3456", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(2233, 3, "RET", 0, "SELL", "ACC-3456", "CANCEL"));
		tradePositioningService.placeTradeEvent(new TradeEvent(2233, 1, "RET", 100, "SELL", "ACC-3456", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(8896, 1, "YUI", 300, "BUY", "ACC-4567", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(6638, 1, "YUI", 100, "SELL", "ACC-4567", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(6363, 1, "HJK", 200, "BUY", "ACC-5678", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(7666, 1, "HJK", 200, "BUY", "ACC-5678", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(6363, 2, "HJK", 100, "BUY", "ACC-5678", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(7666, 2, "HJK", 50, "SELL", "ACC-5678", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(8686, 2, "GBN", 100, "BUY", "ACC-6789", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(8686, 1, "FVB", 100, "BUY", "ACC-6789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(9654, 1, "FVB", 200, "BUY", "ACC-6789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1025, 1, "JKL", 100, "BUY", "ACC-7789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1036, 1, "JKL", 100, "BUY", "ACC-7789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1025, 2, "JKL", 100, "SELL", "ACC-8877", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1122, 2, "HJK", 100, "SELL", "ACC-9045", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1122, 1, "KLO", 100, "BUY", "ACC-9045", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1122, 3, "KLO", 100, "SELL", "ACC-9045", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1144, 1, "KLO", 300, "BUY", "ACC-9045", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1144, 2, "KLO", 400, "BUY", "ACC-9045", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1155, 1, "KLO", 600, "SELL", "ACC-9045", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1155, 2, "KLO", 0, "BUY", "ACC-9045", "CANCEL"));
		Map<String, TradePosition> actualTradePositions = tradePositioningService.processTradePositions();
		Assert.assertTrue(actualTradePositions.equals(expectedTradePositions));
		LOG.info("Complete - testOrderedTradeEvents1() - Actual Matched Expected Output.\n\n");
	}

	@Test
	public void testUnOrderedTradeEvents2() {
		LOG.info("Start - testUnOrderedTradeEvents2()");
		tradePositioningService = context.getBean(TradePositioningService.class);
		tradePositioningService.placeTradeEvent(new TradeEvent(1155, 2, "KLO", 0, "BUY", "ACC-9045", "CANCEL"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1036, 1, "JKL", 100, "BUY", "ACC-7789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(2233, 3, "RET", 0, "SELL", "ACC-3456", "CANCEL"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1234, 1, "XYZ", 100, "BUY", "ACC-1234", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(5678, 2, "QED", 0, "BUY", "ACC-2345", "CANCEL"));
		tradePositioningService.placeTradeEvent(new TradeEvent(5678, 1, "QED", 200, "BUY", "ACC-2345", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(2233, 2, "RET", 400, "SELL", "ACC-3456", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(2233, 1, "RET", 100, "SELL", "ACC-3456", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(8896, 1, "YUI", 300, "BUY", "ACC-4567", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(6638, 1, "YUI", 100, "SELL", "ACC-4567", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(6363, 1, "HJK", 200, "BUY", "ACC-5678", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(7666, 1, "HJK", 200, "BUY", "ACC-5678", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(6363, 2, "HJK", 100, "BUY", "ACC-5678", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(7666, 2, "HJK", 50, "SELL", "ACC-5678", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(8686, 2, "GBN", 100, "BUY", "ACC-6789", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(8686, 1, "FVB", 100, "BUY", "ACC-6789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(9654, 1, "FVB", 200, "BUY", "ACC-6789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1025, 1, "JKL", 100, "BUY", "ACC-7789", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1122, 1, "KLO", 100, "BUY", "ACC-9045", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1025, 2, "JKL", 100, "SELL", "ACC-8877", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1122, 2, "HJK", 100, "SELL", "ACC-9045", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1144, 2, "KLO", 400, "BUY", "ACC-9045", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1155, 1, "KLO", 600, "SELL", "ACC-9045", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1122, 3, "KLO", 100, "SELL", "ACC-9045", "AMEND"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1144, 1, "KLO", 300, "BUY", "ACC-9045", "NEW"));
		tradePositioningService.placeTradeEvent(new TradeEvent(1234, 2, "XYZ", 150, "BUY", "ACC-1234", "AMEND"));
		Map<String, TradePosition> actualTradePositions = tradePositioningService.processTradePositions();
		Assert.assertTrue(actualTradePositions.equals(expectedTradePositions));
		LOG.info("Complete - testOrderedTradeEvents2() - Actual Matched Expected Output.\n\n");

	}
}