package org.corpus_tools.pepper.gui.components;

import java.util.Set;

public interface ProgressDisplay {
	public void setProgress(String elementName, float progress);
	public Set<String> getElementNames();
	public void addElement(String elementName);
	public void addElements(Set<String> elementNames);
	public void removeElement(String elementName);
	public void removeAllElements();
	public void showDetails(boolean showDetails);
}
