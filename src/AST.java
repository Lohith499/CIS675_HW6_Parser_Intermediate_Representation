import java.util.ArrayList;
import java.util.List;

public class AST {
	String node ; 
	List <AST> children; // operands
	public AST(String node) { this.node = node; }
	public void addChild(AST t) {
		if ( children==null ) children = new ArrayList<AST>();
		children.add(t);
	}

	public String toString() {

		return node.toString() ; 
	}

	public Boolean isNil() {
		if ( children==null || children.size()==0 )  {
			return true;
		}
		else 
		{
			return false;
		}
	}
	public String toStringTree() {
		StringBuilder buf = new StringBuilder(); 
		if ( children==null || children.size()==0 ) return this.toString(); 
		if ( !isNil() ) { 
			buf.append("("); 
			buf.append(this.toString()); 
			buf.append(' '); }
			buf.append("(");
		for (int i=0; children!=null && i < children.size(); i++) 	{
			
			AST t = (AST)children.get(i);
			if ( i>0 ) buf.append(' '); 
			buf.append(t.toStringTree());
		}
		if ( !isNil() ) {
			buf.append(")");
			}
			buf.append(")");
	 
		return buf.toString(); 
	}

}






