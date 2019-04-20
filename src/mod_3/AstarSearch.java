package mod_3;

import java.util.*;

//Class for search
public class AstarSearch
{
	//PriorityQueue for frontier
	protected PriorityQueue<NodeSearch> frontier;
	//List for interior
	protected LinkedList<NodeSearch> interior;
	
	protected NodeSearch start, end;
	
	private boolean found = false;
	
	//Debug Flags
		boolean debugLines = false;											// Set to true if you want to see debug output
		boolean searchOut = true;											// Set to true to see debug search messages
		boolean valueOut = true;											// Set to true to see values for debug purposes
		boolean expandOut = false;
	
	public AstarSearch(NodeSearch start, NodeSearch end)
	{
		this.start = start;
		this.end = end;
		
		frontier = new PriorityQueue<NodeSearch>();
		interior = new LinkedList<NodeSearch>();
		
//		search();
	}
	
	/**
	 * 
	 * @param loc
	 * @param target
	 * @return
	 */
	public LinkedList<NodeSearch> search(List<NodeSearch> loc, List<NodeSearch> target){
		
		return search();
	}
	
	public LinkedList<NodeSearch> search(NodeSearch loc, NodeSearch target){
		this.start = loc;
		this.end = target;
		return search();
	}
	
	public LinkedList<NodeSearch> search()
	{
		start.costFromStart = 0;
		start.estimatedCostToGoal = start.getEstimatedCost(end);
		start.pathParent = null; //starting node doesnt have parent
		
		//add the start node to frontier
		frontier.add(start);
		
		if(debugLines) {
			if(valueOut) {
				System.out.println("Start : \n" + start.toString());
				System.out.println("End : \n" + end.toString());
			}
			if(searchOut) {
				System.out.println("Beginning Search...");
			}
		}
		

		
		
		while(frontier.size() != 0)
		{
			//create current node from frontier
			NodeSearch current = frontier.poll();
			
			if(debugLines && valueOut) {
				System.out.println("Checking Node " + String.format("%07X", current.id()));
			}
			
			//return interior if current node is the end node
			if (current.equals(end)) 
			{
				if(debugLines && valueOut) {
					System.out.println("\tEnd Found!");
				}
				interior.add(current);
				// construct the path from start to goal
				return interior;
			}
			
			//create neighbors for current node
			List<NodeSearch> neighbors = current.getNeighbors();
			if(debugLines) {
				if(valueOut) {
//					System.out.println(frontier);			
//					System.out.println(interior);
					
					System.out.println("Expanding Node " + String.format("%07X", current.id()));
				}
				
				if(searchOut) {
					System.out.println("Begin loop on neighbours..");
				}
			}
			
			for (int i = 0; i < neighbors.size(); i++)
			{
				if(debugLines) {
					System.out.println("Loop instance : " + i);
				}
				//determine neighbors property
				NodeSearch neighborNode = neighbors.get(i);
				
				if(debugLines && valueOut) {
					System.out.println("Neighbour" + i + " : \n" + neighborNode);
				}
				
				boolean isFrontier = frontier.contains(neighborNode); //Is this node in frontier
				boolean isInterior = interior.contains(neighborNode); //Is this node in interior
				
				if(debugLines && valueOut && expandOut) {
					System.out.println("\tIs Neighbour in frontier? : " + isFrontier);
					System.out.println("\tIs Neighbour in interior? : " + isInterior);
				}
				
				//Calculate the cost from start for this neighborNode
				double costFromStart = current.costFromStart + current.getCost(neighborNode);
				
				//If is not in both list, or it has lower cost, calculate
				if((!isFrontier && !isInterior) || costFromStart < neighborNode.costFromStart)
				{
					neighborNode.pathParent = current; //set current as the parent node
					neighborNode.costFromStart = costFromStart; //set cost from start for neightborNode
					neighborNode.estimatedCostToGoal = neighborNode.getEstimatedCost(end); //set estimate cost
					
					if(!isFrontier)
					{
						if(debugLines && valueOut && expandOut) {
							System.out.println( "Is Frontier" + " null? : " + (frontier == null));
							System.out.println( "Is Neighbour" + i + " null? : " + (neighborNode == null));
						}
						frontier.add(neighborNode);
					}
					if(isInterior)
					{
						interior.remove(neighborNode);
					}
					
				}
			}
			interior.add(current);
		}
		return null;
	}
	
	public String toString() {		
		String out = "";
		if(interior != null) {
			out += "Route:\n";
			NodeSearch cn = interior.getLast();
			LinkedList<NodeSearch> route = new LinkedList<NodeSearch>();
			
			while(cn.pathParent != null) {
				route.addFirst(cn);
				cn = cn.pathParent;
			}

			double total_dist = 0;
			
			for(int i = 0; i < route.size(); i++) {
				out += i+1;
				out += ".\t";
				
				NodeSearch cns = route.get(i);
				double step_dist = cns.neighbours().get(cns.pathParent.id()).dist();
				
				out += String.format("%07X", cns.parent().id());
				out += " -> ";
				out += String.format("%07X", cns.id());
				out += ", " + step_dist + "m\n";
				
				total_dist += step_dist;
			}
			
			
			out += "approx. " + total_dist + "m\n";
			
		}
		else
			out = "null";
			
		return out;
	}
	
	public String toString_reverse() {		
		String out = "";
		if(interior != null) {
			out += "Route:\n";
			NodeSearch cn = interior.getLast();
			int i = 1;
			double total_dist = 0;
			
			while(cn.pathParent != null) {
				out += i;
				out += ".\t";
				
				double step_dist = cn.neighbours().get(cn.pathParent.id()).dist();
				
				out += String.format("%07X", cn.parent().id());
				out += " -> ";
				out += String.format("%07X", cn.id());
				out += ", " + step_dist + "m\n";
				
				total_dist += step_dist;
				
				cn = cn.pathParent;
				i++;
			}
			out += "approx. " + total_dist + "m\n";
		}
		else
			out = "null";
			
		return out;
	}
	
}
