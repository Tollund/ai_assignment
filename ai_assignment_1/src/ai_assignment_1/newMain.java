package ai_assignment_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import eis.EILoader;
import eis.EnvironmentInterfaceStandard;
import eis.exceptions.AgentException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.Percept;


public class newMain {
	
	static ArrayList explorers = new ArrayList<explorerAgent>();
	static explorerAgent agent = new explorerAgent();
	semaphore s1 = new semaphore(0);
	static Map<String, Collection<Percept>> percepts;
	static Collection<Percept> ret;
	
	public static void main(String[] args) throws AgentException, RelationException, ManagementException, PerceiveException, NoEnvironmentException {
		inputThread it = new inputThread();
		it.start();
		EnvironmentInterfaceStandard ei = null;
		String position = "";
		try {
			String cn = "massim.eismassim.EnvironmentInterface";
			ei = EILoader.fromClassName(cn);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		
		explorerAgent a1 = new explorerAgent("a1", "Explorer");
		explorerAgent a2 = new explorerAgent("a2", "Explorer");
		explorerAgent a3 = new explorerAgent("a3", "Explorer");
		explorerAgent a4 = new explorerAgent("a4", "Explorer");
		explorerAgent a5 = new explorerAgent("a5", "Explorer");
		explorerAgent a6 = new explorerAgent("a6", "Explorer");
		explorerAgent a7 = new explorerAgent("a7", "Explorer");
		explorerAgent a8 = new explorerAgent("a8", "Explorer");
		explorers.add(a1);
		explorers.add(a2);
		explorers.add(a3);
		explorers.add(a4);
		explorers.add(a5);
		explorers.add(a6);
		explorers.add(a7);
		explorers.add(a8);
		
		String tempName = "";
		for (int i = 0; i < explorers.size(); i++) {
			agent = (explorerAgent) explorers.get(i);
			agent.start();
			tempName = agent.getname();
			ei.registerAgent(tempName);
			System.out.println("Agent " + tempName + " registered");
			ei.associateEntity(tempName,"connectionA" + Integer.toString(i+1));
		}
		
		ei.start();			
		
		for (int i = 0; i < explorers.size(); i++) {
			agent = (explorerAgent) explorers.get(i);
			System.out.println(agent.getname());
			percepts = ei.getAllPercepts(agent.getname());
			ret = new LinkedList<Percept>();
			for ( Collection<Percept> ps : percepts.values() ) {
				ret.addAll(ps);
			}
			agent.recieveInput(ret);
		}
		
	}

	private static void getPercept(String string) {
		// TODO Auto-generated method stub
		
	}

	public static void getOrder(String name, Action action) {
		// TODO Auto-generated method stub
		
	}
	
	public void allReady() throws InterruptedException{
		s1.P();
		s1.P();
		s1.P();
		s1.P();
		s1.P();
		s1.P();
		s1.P();
		s1.P();
//		domagic();
	}
	
	public void ready(){
		s1.V();
	}
}
