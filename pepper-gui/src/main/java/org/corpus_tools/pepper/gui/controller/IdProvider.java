package org.corpus_tools.pepper.gui.controller;

public class IdProvider {
	private int next = 0;	
	
	protected IdProvider(){		
	}
	
	/** This method provides a unique prefixed identifier.
	 * @param prefix -- prefix of id, may not be null 
	 * @return prefixed id */
	public String getId(String prefix){		
		return prefix==null? null : prefix.concat(Integer.toString(next++));
	}
}
