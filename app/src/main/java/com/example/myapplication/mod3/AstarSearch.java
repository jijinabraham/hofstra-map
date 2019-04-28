package com.example.myapplication.mod3;

import java.util.*;
import com.example.myapplication.mod1.*;

//Class for search
public class AstarSearch
{
	// PriorityQueue for frontier
	protected PriorityQueue<NodeSearch> frontier;
	
	// List for interior
	protected LinkedList<NodeSearch> interior;
	
	//
	protected NodeSearch start, end;
	protected LinkedList<NodeSearch> route;
	//private boolean found = false;
	
	// Debug Flags
		boolean debugLines = false;											// Set to true if you want to see debug output
		boolean searchOut = true;											// Set to true to see debug search messages
		boolean valueOut = true;											// Set to true to see values for debug purposes
		boolean expandOut = false;
	
	public AstarSearch() {
		start = null;
		end = null;
		
		frontier = null;
		end = null;
	}
	public AstarSearch(Node start, Node end) {
		this((NodeSearch)start, (NodeSearch)end);
	}
	public AstarSearch(NodeSearch start, NodeSearch end)
	{
		this.start = start;
		this.end = end;
		
		frontier = new PriorityQueue<NodeSearch>();
		interior = new LinkedList<NodeSearch>();
		
		search();
	}
	
	/**
	 * Used in the event a search has multiple potential start and/or end points.
	 * This method will select the pair with the least estimated distance before searching.
	 * @param loc
	 * @param target
	 * @return
	 */
	public LinkedList<NodeSearch> search(List<NodeSearch> list_start, List<NodeSearch> list_target){
		// Check if lists are valid
		if(list_start == null || list_target == null || list_start.isEmpty() || list_target.isEmpty()) {
			return null;
		}

		// Select closest pair
		NodeSearch to_start = list_start.get(0);
		NodeSearch to_end = list_target.get(0);
		double to_est = to_start.getEstimatedCost(to_end);
		
		for(NodeSearch ns : list_start) {
			for(NodeSearch nt : list_target) {
				double temp_est = ns.getEstimatedCost(nt);
				if(temp_est < to_est) {
					to_start = ns;
					to_end = nt;
					to_est = temp_est;
				}
			}
		}
		
		// Set start and end nodes
		this.start = to_start;
		this.end = to_end;
		
		// Search then return route
		return search();
	}
	
	/**
	 * Begins a new search using new start and target nodes.
	 * @param loc
	 * @param target
	 * @return
	 */
	public LinkedList<NodeSearch> search(NodeSearch start, NodeSearch target){
		// Set start and end nodes
		this.start = start;
		this.end = target;
		
		// Search then return route
		return search();
	}
	
	/**
	 * Runs the search then returns the route (contains only the nodes along the path)
	 * @return
	 */
	public LinkedList<NodeSearch> search(){
		_search();
		return build_route();
	}
	
	/**
	 * Search algorithm. Returns interior.
	 * @return
	 */
	public LinkedList<NodeSearch> _search()
	{
		if(frontier == null || !frontier.isEmpty()) {
			frontier = new PriorityQueue<NodeSearch>();
		}
		if(interior == null || !interior.isEmpty()) {
			interior = new LinkedList<NodeSearch>();
		}	
		
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
	
	/**
	 * Builds a list containing only nodes along the path, and stores it as route.
	 * @return
	 */
	public LinkedList<NodeSearch> build_route(){
		if(interior != null) {
			NodeSearch cn = interior.getLast();
			LinkedList<NodeSearch> route = new LinkedList<NodeSearch>();
			
			while(cn.pathParent != null) {
				route.addFirst(cn);
				cn = cn.pathParent;
			}
			
			this.route = route;
			return route;
		}
		else {
			return null;
		}
	}

	public String toString() {		
		String out = "";
		if(interior != null) {
			out += "Route:\n";
	
			if(route == null) {
				build_route();
			}
	
			double total_dist = 0;
			
			for(int i = 0; i < route.size(); i++) {
				out += i+1;
				out += ".\t";
				
				NodeSearch cns = route.get(i);
				double step_dist = cns.neighbours()					// Get the HashMap of neighbours
										.get(cns.pathParent.id())	// Gets the Link to pathParent
										.dist();					// Gets the length of the link
				
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
	
}
