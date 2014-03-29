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
import eis.iilang.Percept;

public class test {

	static ArrayList explorers = new ArrayList<explorerAgent>();
	static explorerAgent agent = new explorerAgent();
	static Map<String, Collection<Percept>> percepts;
	static Collection<Percept> ret;


	public static void main(String[] args) throws IOException, AgentException, RelationException, ManagementException, PerceiveException, NoEnvironmentException, InterruptedException {
		EnvironmentInterfaceStandard ei1 = null;
		EnvironmentInterfaceStandard ei2 = null;
		EnvironmentInterfaceStandard ei3 = null;
		EnvironmentInterfaceStandard ei4 = null;
		EnvironmentInterfaceStandard ei5 = null;
		EnvironmentInterfaceStandard ei6 = null;
		EnvironmentInterfaceStandard ei7 = null;
		EnvironmentInterfaceStandard ei8 = null;
		String cn = "massim.eismassim.EnvironmentInterface";
		ei1 = EILoader.fromClassName(cn);
		ei2 = EILoader.fromClassName(cn);
		ei3 = EILoader.fromClassName(cn);
		ei4 = EILoader.fromClassName(cn);
		ei5 = EILoader.fromClassName(cn);
		ei6 = EILoader.fromClassName(cn);
		ei7 = EILoader.fromClassName(cn);
		ei8 = EILoader.fromClassName(cn);

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
		
		ei1.registerAgent("a1");
		ei1.associateEntity("a1", "connectionA1");
		ei1.start();
		ei2.registerAgent("a2");
		ei2.associateEntity("a2", "connectionA2");
		ei2.start();
		ei3.registerAgent("a3");
		ei3.associateEntity("a3", "connectionA3");
		ei3.start();
		ei4.registerAgent("a4");
		ei4.associateEntity("a4", "connectionA4");
		ei4.start();
		ei5.registerAgent("a5");
		ei5.associateEntity("a5", "connectionA5");
		ei5.start();
		ei6.registerAgent("a6");
		ei6.associateEntity("a6", "connectionA6");
		ei6.start();
		ei7.registerAgent("a7");
		ei7.associateEntity("a7", "connectionA7");
		ei7.start();
		ei8.registerAgent("a8");
		ei8.associateEntity("a8", "connectionA8");
		ei8.start();

//		for (int i = 0; i < explorers.size(); i++) {
//			agent = (explorerAgent) explorers.get(i);
//			tempName = agent.getname();
//			ei.registerAgent(tempName);
//			ei.associateEntity(tempName,"connectionA" + tempName.charAt(1));
//			agent.start();
//		}	

		percepts = ei1.getAllPercepts("a1");
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		}
		a1.recieveInput(ret);
		percepts = ei2.getAllPercepts("a2");
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		} 
		a2.recieveInput(ret);
		percepts = ei3.getAllPercepts("a3");
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		} 
		a3.recieveInput(ret);
		percepts = ei4.getAllPercepts("a4");
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		} 
		a4.recieveInput(ret);
		percepts = ei5.getAllPercepts("a5");
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		} 
		a5.recieveInput(ret);
		percepts = ei6.getAllPercepts("a6");
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		} 
		a6.recieveInput(ret);
		percepts = ei7.getAllPercepts("a7");
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		} 
		a7.recieveInput(ret);
		percepts = ei8.getAllPercepts("a8");
		ret = new LinkedList<Percept>();
		for ( Collection<Percept> ps : percepts.values() ) {
			ret.addAll(ps);
		} 
		a8.recieveInput(ret);

//		for (int i = 0; i < explorers.size(); i++) {
//			agent = (explorerAgent) explorers.get(i);
//			tempName = agent.getname();
//			
//			ret = new LinkedList<Percept>();
//			for ( Collection<Percept> ps : percepts.values() ) {
//				ret.addAll(ps);
//			} 
//			System.out.println(ret);
//		}

	}

}