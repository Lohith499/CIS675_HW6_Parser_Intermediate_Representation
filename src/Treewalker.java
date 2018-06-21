import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Lohith Nimmala
 */
public class Treewalker {

    ArrayList<String> visited = new ArrayList<>();

    public Treewalker() {
    }

    //determines what nodes in the graph / tree are leaf nodes.
    public void findLeaves(ArrayList<ASTree> NodeList) {
        
    	if(NodeList.isEmpty()) { }
    	else {
        for (ASTree node : NodeList) 
        {
            if (!isVisited(node)) 
            {
            	//Lohith
            	  
                //if (node.childnodes.isEmpty()) 
                if (node.isNil()) 
                {
                    System.out.println(node.name + " is a leaf node");
                }
                
            	
                visited.add(node.name+" "+node.hashCode());
                if (!(node.isNil())) { 
                findLeaves(node.childnodes);
                }
            }
        }
        if (visited.equals(NodeList)) {
            visited.clear();
        }
    	}
    }

    
public void findParents(ArrayList<ASTree> NodeList) {
        
    	if(NodeList.isEmpty()) { }
    	else {
        for (ASTree node : NodeList) 
        {
            if (!isVisited(node)) 
            {
            	//Lohith
            	  
                //if (node.childnodes.isEmpty()) 
                if (!(node.isNil())) 
                {
                    System.out.println(node.name + " is a Parent node");
                }
                
            	
                visited.add(node.name+" "+node.hashCode());
                if (!(node.isNil())) { 
                	findParents(node.childnodes);
                }
            }
        }
        if (visited.equals(NodeList)) {
            visited.clear();
        }
    	}
    }

    //does a breadth first search at the root of a tree
    public void bfs(ASTree root)
	{
		Queue queue = new LinkedList();
                ASTree Start = root;
                

                root.visit();
                queue.add(root);
//		System.out.println("added "+ root.name);
                
		while(!queue.isEmpty()) {
			ASTree node = (ASTree)queue.remove();
                        System.out.println("Parent:\t"+node.name);
                        //Lohith
			        if(!node.isNil()) {               
                        for(ASTree kid: node.childnodes){
                            if(!kid.visited){
                                kid.visit();
                                System.out.println("\tChild:\t "+kid.name);
                                
                                node = kid;
                                queue.add(kid);
                            }
                        }
					}
		}
            
                // Clear visited property of nodes
//		resetVisits(Start);
	}    
    
    //prints out the fragmented parts of the tree / graph.
    public void findFragements(ArrayList<ASTree> nodes)
    {
        for(ASTree node: nodes){
        	
            if(node.visited==false){System.out.println(node.name + " is a fragment");}
        }
    }
    
    //resets the visit attribute of Nodes in the explored graph.
    private void resetVisits(ASTree root) {
        Queue unVisitQueue = new LinkedList();

                root.unvisit();
                unVisitQueue.add(root);
//		System.out.println("added "+ root.name);
                
		while(!unVisitQueue.isEmpty()) {
			ASTree node = (ASTree)unVisitQueue.remove();
//                        System.out.println(node.name);
			                        
                        for(ASTree kid: node.childnodes){
                            if(kid.visited){
                                kid.unvisit();
//                                System.out.println(kid.name);
                                node = kid;
                                unVisitQueue.add(kid);
                            }
                        }
                        System.out.println(" ");
		}
    }
    
    //prints which node has the most childnodes
    public void mostchildnodesOutput(ArrayList<ASTree> NodeList) {
        String output = mostchildnodes(NodeList);
        System.out.println(output);
    }
    
    //helper method to determine node with most childnodes
    private String mostchildnodes(ArrayList<ASTree> NodeList) {

        int maxConnections = 0;
        String nodeName = "";
        for (ASTree node : NodeList) {
            if (!isVisited(node)) {
            	//lets check if its Nil
            	if (!node.isNil() ) {
                if (node.childnodes.size() > maxConnections) {
                    maxConnections = node.childnodes.size();
                    nodeName = node.name;
                }
                visited.add(node.name+" "+node.hashCode());
                if (!(node.isNil())) { 
                findLeaves(node.childnodes);
                }
            	}
            }
        }
        if (visited.equals(NodeList)) {
            visited.clear();
        }
//        visited.clear();
        return nodeName + " has the most childnodes @" + maxConnections;
    }

    //unused
    //for use with visitor array
    public boolean isVisited(ASTree node) {

        if (visited.contains(node.name+" "+node.hashCode())) {
            return true;
        }
        return false;
    }

    //unused
    //returns an unvisited child node of the parent
    public ASTree getUnvisitedChildNode(ArrayList<ASTree> parent){
        
        for (ASTree child: parent)
        {
            if(child.visited == false)
            {
            return child;    
            }
                }
        return null;
    }
    //unused
    public ASTree getVisitedChildNode(ArrayList<ASTree> parent){
        
        for (ASTree child: parent)
        {
            if(child.visited == true)
            {
            return child;    
            }
                }
        return null;
    }
    
}
