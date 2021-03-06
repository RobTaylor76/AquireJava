package com.miniaturesolutions.aquire;

import java.util.List;

public class TestAquireFactory implements AquireFactory {

	private AquireBoard board;
	private List<Corporation> corporationImpls;
	private AquireFactory factory;
	
	public TestAquireFactory() {
		this.factory = new AquireFactoryImpl();
		this.board = factory.createBoard();
		this.corporationImpls = factory.createCorporations();
	}
	/**
	 * Will return the same corporations every time...
	 */
	@Override
	public List<Corporation> createCorporations() {
		return corporationImpls;
	}

	/**
	 * will return the same board every time
	 */
	@Override
	public AquireBoard createBoard() {
		return 	board;
	}
	/**
	 * Get the current AquireBoard
	 * @return
	 */
	protected AquireBoard getAquireBoard() {
		return board;
	}
	
	/**
	 * Set the current AquireBoard 
	 * 
	 * @param boardImpl
	 */
	protected void setAquireBoard(AquireBoard boardImpl) {
		this.board = boardImpl;
	}

}
