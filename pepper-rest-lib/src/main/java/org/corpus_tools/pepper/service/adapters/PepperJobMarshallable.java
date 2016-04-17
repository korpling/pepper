package org.corpus_tools.pepper.service.adapters;

import java.util.List;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.corpus_tools.pepper.common.JOB_STATUS;
import org.corpus_tools.pepper.common.PepperJob;
import org.corpus_tools.pepper.common.StepDesc;
import org.corpus_tools.pepper.core.PepperJobImpl;
import org.corpus_tools.pepper.service.interfaces.PepperMarshallable;
import org.eclipse.emf.common.util.URI;

/** TODO
 * this class might not be used. We only need a job id to get a status report.
 * this id will be created, when the stepdescs are posted to create a PepperJob-POJO
 * @author klotzmaz
 *
 */
@Deprecated
@XmlRootElement
@XmlSeeAlso({StepDescMarshallable.class})
public class PepperJobMarshallable implements PepperMarshallable<PepperJob>{

	@Override
	public PepperJob getPepperObject() {
		if (pepperObj!=null){
			return this.pepperObj;
		}
		
		PepperJob obj = new PepperJobImpl(this.id);		
		for (StepDescMarshallable desc : stepDescs){
			obj.addStepDesc(desc.getPepperObject());
		}
		
		obj.setBaseDir(URI.createURI(this.basedirURI));
		
		
		return null;
	}
	
	private final PepperJob pepperObj;
	
	/**
	 * This constructor is used by pepper as preparation for marshalling.
	 * @param job
	 */
	public PepperJobMarshallable(PepperJob job){
		this.pepperObj = job;
		this.basedirURI = job.getBaseDir().toString();
		this.id = job.getId();
		this.jobStatus = job.getStatus();
		for (StepDesc desc : job.getStepDescs()){
			this.stepDescs.addElement(new StepDescMarshallable(desc));
		}		
	}
	
	/**
	 * This constructor is used by JAXB when unmarshalling.
	 */
	public PepperJobMarshallable(){
		this.pepperObj = null;
	}
	
	private String id;
	
	@XmlElement
	public void setId(String id){
		this.id = id;
	}
		
	private String basedirURI;
	
	@XmlElement
	public void setBasedirURI(String uri){
		this.basedirURI = uri;
	}
	
	private JOB_STATUS jobStatus;
	
	@XmlElement
	public void setJobStatus(JOB_STATUS status){
		this.jobStatus = status;
	}
	
	private Vector<StepDescMarshallable> stepDescs = new Vector<StepDescMarshallable>();
	
	@XmlElement
	public List<StepDescMarshallable> getSteps(){
		return this.stepDescs;
	}

}
