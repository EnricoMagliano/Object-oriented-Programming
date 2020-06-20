package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * The status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends ElementExt {
	
	private double flow = 0;
	
	public double getFlow() {
		return this.flow;
	}

	public Source(String name) {
		super(name);
		//TODO: complete
	}

	/**
	 * defines the flow produced by the source
	 * 
	 * @param flow
	 */
	public void setFlow(double flow){
		if(flow < 0) {
			System.out.print("Non è possibile settare un flow negativo");
			return;
		}
		this.flow = flow;
	}
	
	public void Rsimulate(SimulationObserver observer){
		observer.notifyFlow("Source", this.getName(), SimulationObserver.NO_FLOW, this.flow);
		this.getOutput().Rsimulate(observer, this.flow);
	}
	
	public void Rsimulate(SimulationObserverExt observer, boolean enableMaxFlowCheck){
		observer.notifyFlow("Source", this.getName(), SimulationObserver.NO_FLOW, this.flow);
		this.getOutput().Rsimulate(observer, this.flow, enableMaxFlowCheck);
	}
	
	public String layout(String disposizione){
		disposizione += "[" + this.getName() + "]Source";
		disposizione = this.getOutput().layout(disposizione);
		return disposizione;
	}
	
	public void setMaxFlow(double maxFlow) {
		return;
	}
	
}
