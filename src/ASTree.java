import java.util.ArrayList;
import java.util.List;
/**
*
* @author Lohith Nimmala
*/
public class ASTree {
	String name ; 
	ArrayList <ASTree> childnodes; // operands
	boolean visited = false;
	public ASTree(String node) { this.name = node; }
	public ASTree() { this.name = ""; }
	public void addChild(ASTree t) {
		if ( childnodes==null ) childnodes = new ArrayList<ASTree>();
		childnodes.add(t);
	}

	public String toString() {

		return name.toString() ; 
	}

	public Boolean isNil() {
		if ( childnodes==null || childnodes.size()==0 )  {
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	public ASTree findNode(String s) {
		ASTree a = new ASTree();
		
		for (int i=0; i<this.childnodes.size(); i++ ) {
			if (this.childnodes.get(i).name.compareTo(s)==0) {
				return this.childnodes.get(i);
			}
		}
		
		return a;
	}
	public String toStringTree() {
		StringBuilder buf = new StringBuilder(); 
		if ( childnodes==null || childnodes.size()==0 ) return this.toString(); 
		if ( !isNil() ) { 
			buf.append("("); 
			buf.append(this.toString()); 
			buf.append(' '); }
			//buf.append("(");
		for (int i=0; childnodes!=null && i < childnodes.size(); i++) 	{
			
			ASTree t = (ASTree)childnodes.get(i);
			if ( i>0 ) buf.append(' '); 
			buf.append(t.toStringTree());
		}
		if ( !isNil() ) {
			buf.append(")");
			}
			//buf.append(")");
	 
		return buf.toString(); 
	}

	
	
	 public void visit(){visited = true;}
	   
	   public void unvisit(){ visited = false;}
}






