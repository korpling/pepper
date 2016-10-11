package org.corpus_tools.pepper.service.adapters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;

/**
 * FIXME: This needs a pepper base object to be implemented first.
 * @author klotzmaz
 *
 */
@XmlRootElement
@XmlSeeAlso({PepperJobReportMarshallable.PathProgressCollection.class})
public class PepperJobReportMarshallable implements PepperMarshallable<PepperJobReportMarshallable.PepperJobReport>{
	
	public PepperJobReportMarshallable() {
		this.progressByPath = new PathProgressCollection();
	}
	
	public PepperJobReportMarshallable(String jobId, Map<String, Double> progressByModule){
		Set<PathProgress> prgSet = new HashSet<PathProgress>();
		for (Entry<String, Double> e : progressByModule.entrySet()){
			prgSet.add(new PathProgress(e.getKey(), e.getValue()));
		}
		this.progressByPath = new PathProgressCollection(prgSet);
		this.id = jobId;
	}

	private String id;
	
	@XmlElement
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	private PathProgressCollection progressByPath;
	
	@XmlElement
	public PathProgressCollection getProgressByPath(){
		return this.progressByPath;
	}
	
	public void setProgressByPath(PathProgressCollection progressByPath){
		this.progressByPath = progressByPath;
	}

	@Override
	public PepperJobReport getPepperObject() {
		return null;
	}
	
	/*
	 * TODO dummy class, maybe useful later, remove to pepper-framework 
	 */ 
	public class PepperJobReport {}
	
	@XmlRootElement
	public static class PathProgress{
		public PathProgress() {}
		
		public PathProgress(String path, double progress){
			this.path = path;
			this.progress = progress;
		}
		
		private String path;
		
		@XmlElement
		public String getPath(){
			return this.path;
		}
		
		public void setPath(String path){
			this.path = path;
		}
		
		private double progress;
		
		@XmlElement
		public double getProgress(){
			return this.progress;
		}
		
		public void setProgress(double progress){
			this.progress = progress;
		}
		
		@Override
		public String toString(){
			return (new StringBuilder()).append("{")
					.append(path)
					.append(": ")
					.append(Double.toString(progress))
					.append("}")
					.toString();
		}
	}
	
	@XmlRootElement
	@XmlSeeAlso({PepperJobReportMarshallable.PathProgress.class})
	public static class PathProgressCollection{
		public PathProgressCollection() {
			this.collection = new HashSet<PathProgress>();
		}
		
		public PathProgressCollection(Collection<PathProgress> progressCollection){
			this.collection = progressCollection;
		}
		
		private Collection<PathProgress> collection;
		
		@XmlElements({ @XmlElement(name = "PathProgress", type = PepperJobReportMarshallable.PathProgress.class) })
		@XmlElementWrapper(name = "ProgressReport")
		public Collection<PathProgress> getCollection(){
			return this.collection;
		}
	}
}
