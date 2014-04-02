package com.miniaturesolutions.aquire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.miniaturesolutions.aquire.Corporation.Status;

public class AdviserTest {
	
	private AquireGame game;
	private Adviser adviser;
	

	@Test
	public void getStockMarket() {

		game = new AquireGame();
		adviser = game.getAdviser();
		
		//no active corporations... no available corporations
		//never show unincorporated

		Map<Corporation,StockQuote> stockMarket = adviser.getStockMarket();
		assertEquals("Should be empty market",0,stockMarket.size());

		//need to create a corporation that is active...
		CorporationImpl corp = game.getCorporation(Corporation.AMERICAN);
		corp.setStatus(Status.ACTIVE);

		stockMarket = adviser.getStockMarket();
		assertEquals("Should 1 entry",1,stockMarket.size());

		StockQuote quote = stockMarket.get(Corporation.AMERICAN);
		assertNotNull("Should have a live quote",quote);

        assertEquals("Should have correct value for the shares",
                corp.getCurrentStockPrice(),
                quote.getStockPrice());

        assertEquals("Should have correct corporation",
                corp.getCorporation(),
                quote.getCorporation());


        assertEquals("Should have correct no of avail shares",
                corp.getRemainingShareCount(),
                quote.getAvailableShares());
        
        assertEquals("Should have correct tile count",
                corp.getTileCount(),
                quote.getCorporationTileCount());

	}
	
	@Test
	public void getAvailableCorporations() {
		game = new AquireGame();
		adviser = game.getAdviser();
		List<StockQuote> availableCorporations = adviser.availableCorporations();
		
		for(StockQuote quote: availableCorporations) {
			assertFalse("dont list unincorporated", quote.getCorporation() == Corporation.UNINCORPORATED);
		}
	}
	
	@Test
	public void willTileCauseMerge() {
		Board board = new Board();
		game = new AquireGame(board);
		adviser = game.getAdviser();
		board.placeTile(new Tile(1,0));
		board.placeTile(new Tile(0,1));

		board.placeTile(new Tile(8,10));
		board.placeTile(new Tile(7,11));  
		
		List<Tile> validMerges = new LinkedList<>();
		validMerges.add(new Tile(0,0));
		validMerges.add(new Tile(2,0));
		validMerges.add(new Tile(1,1));
		validMerges.add(new Tile(0,2));

		validMerges.add(new Tile(8,11));
		validMerges.add(new Tile(7,10));
		validMerges.add(new Tile(6,11));
		validMerges.add(new Tile(8,9));

		List<Tile> noMerges = new LinkedList<>();
		noMerges.add(new Tile(0,3));
		noMerges.add(new Tile(1,2));
		noMerges.add(new Tile(2,1));
        noMerges.add(new Tile(1,11));
        noMerges.add(new Tile(8,1));
        noMerges.add(new Tile(5,1));
        noMerges.add(new Tile(5,0));

		for(Tile tile: validMerges) {
			assertTrue("should trigger merge " + tile.toString(), adviser.willTileCauseMerger(tile));
		}

		for(Tile tile: noMerges) {
			assertFalse("should not trigger merge "+ tile.toString(), adviser.willTileCauseMerger(tile));
		}
	}	


}
