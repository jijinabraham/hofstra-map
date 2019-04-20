package mod_3;

/**
Xiangpu Chen sudo code for A* pathfinder
**/

/**
Expect Node Classes
Expect Interface
Expect Database
**/
import java.util.*;

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
	    float otherValue = ((AstarNode)other).getCost();

	    float v = thisValue - otherValue;
//	    return (v>0) ? 1 : (v<0) ? -1 : 0; // sign function
	    return (int) Math.signum(v);
	} 
	
	//Overwrite for contains
	public abstract boolean equals(Object other);
	
	//Get cost of the adjacent node
	public abstract double getCost(AstarNode other);
	// Call getCost(Node to);
	
	//Get cost from current to a specific node
	public abstract double getEstimatedCost(AstarNode other);
	//h(n) = 6(shortest distance between two points) * shortest # of nodes to goal
	
	//Get the list of all child aka neighbors
	public abstract List getNeighbors();
	// Call node.getNeighbours();
}

