/**
Xiangpu Chen sudo code for A* pathfinder
**/

/**
Expect Node Classes
Expect Interface
Expect Database
**/
import java.util.*

//Abstract Class for Node*
public abstract class AstarNode implements Comparable
{
	protected AstarNode pathParent; //Parent Node
	public double costFromStart;  //G value 
	public double estimatedCostToGoal;  //H value
	
	//Return F value, the cost of the node
	public double getCost()
	{
		return costFromStart + estimatedCostToGoal;
	}
	
	//overwrite compareTo
	public int compareTo(Object other) 
	{
	    float thisValue = this.getCost();
	    float otherValue = ((AStarNode)other).getCost();

	    float v = thisValue - otherValue;
	    return (v>0)?1:(v<0)?-1:0; // sign function
	} 
	
	//Overwrite for contains
	public abstract boolean equals(Object other);
	
	//Get cost of the adjacent node
	public abstract double getCost(AstarNode other);
	// Call getCost(Node target);
	
	//Get cost from current to a specific node
	public abstract double getEstimatedCost(AstarNode other);
	//h(n) = 6(shortest distance between two points) * shortest # of nodes to goal
	
	//Get the list of all child aka neighbors
	public abstract List getNeighbors();
	// Call node.getNeighbours();
}

//Class for search
public class AstarSearch
{
	//PriorityQueue for frointer
	protected PriorityQueue<AstarNode> frontier; = new PriorityQueue<AstarNode>();
	//List for interior
	protected List<AstarNode> interior; = new List<AstarNode>();
	
	protected AstarNode start, end;
	
	private boolean found = false;
	
	public AstarSearch(AstarNode start, AstarNode end)
	{
		this.start = start;
		this.end = end;
		
		frontier = new PriorityQueue<AstarNode>();
		interior = new List<AstarNode>();
		search;
	}
	
	public List<AstarNode> search()
	{
		start.costFromStart = 0;
		start.estimatedCostToGoal = start.getEstimatedCost(end);
		start.pathParent = null; //starting node doesnt have parent
		
		//add the start node to frontier
		frontier.add(start);
		
		while(frontier.size() != 0)
		{
			//create current node from frontier
			AstarNode current = frontier.poll();
			
			//return interior if current node is the end node
			if (current == end) 
			{
				// construct the path from start to goal
				return interior;
			}
			
			//create neighbors for current node
			List<AstarNode> neighbors = current.getNeighbors();
			for (int i = 0; i < neighbors.size(); i++)
			{
				//determine neighbors property
				AstarNode neighborNode = neighbors.get(i);
				boolean isFrontier = frontier.contains(neighborNode); //Is this node in frontier
				boolean isInterior = interior.contains(neighborNode); //Is this node in interior
				
				//Calculate the cost from start for this neighborNode
				double costFromStart = current.costFromStart + current.getCost(neighborNode);
				
				//If is not in both list, or it has lower cost, calculate
				if((!isFrontier && !isInterior) || costFromStart < neighborNode.costFromStart)
				{
					neighborNode.pathParent = current; //set current as the parent node
					neighborNode.costFromStart = costFromStart; //set cost from start for neightborNode
					neighborNode.estimatedCostToGoal = neighborNode.getEstimatedCost(end); //set estimate cost
					
					if(isInterior)
					{
						interior.remove(neighborNode);
					}
					if(!isFrontier)
					{
						frontier.add(neighborNode);
					}
				}
			}
			interior.add(current);
		}
		return null;
	}
	
	
}
