package hydraulic;

public abstract class ElementExt extends Element{

	protected double max_flow;
	public ElementExt(String name) {
		super(name);
		max_flow = -1;
	}

	public void setMaxFlow(double maxFlow) {
		this.max_flow = maxFlow;
	}
	
	public void Rsimulate(SimulationObserverExt observer, double flow, boolean enableMaxFlowCheck){
			observer.notifyFlow("Element", this.getName(), flow, flow);
			if(enableMaxFlowCheck && ((ElementExt)this).max_flow != -1 && ((ElementExt)this).max_flow <flow) {
				observer.notifyFlowError("Element", this.getName(), flow, ((ElementExt)this).max_flow);
			}
			this.getOutput().Rsimulate(observer, flow, enableMaxFlowCheck);
		
	}
}
