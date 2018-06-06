import java.util.HashMap;
import java.util.Map;

public class Parser2 extends LexAndParser {
	
	Map<Integer,String> pTokens = new HashMap<Integer, String>();
	int focus = 1;
	String Error = "";
	String token = "";
	ASTree aSTree = new ASTree("GRAPH");
	Parser2(Map<Integer, String> Tokens){
		this.pTokens = Tokens;
	}
	public String v_parser() {
		v_graph();
		if(Error.compareTo("")==0) {
			System.out.println("Abstract Syntax Tree");
			System.out.println("");
			System.out.println(aSTree.toStringTree());
			System.out.println("");
			return "Program has no Syntax Errors";
		}
		return Error;
	}
	
	public void v_graph() {
		
		//graph : [ strict ] (graph | digraph) [ ID ] '{' stmt_list '}'
		v_Terminal("STRICT", aSTree);
		
		if(v_Terminal("GRAPH",aSTree) || v_Terminal("DIGRAPH",aSTree)) {
			v_Terminal("ID",aSTree);
			 if (v_Terminal("LBRACE",aSTree )) {
				 ASTree Stmt_list = new ASTree("Stmt_list");
				 v_stmt_list(Stmt_list);
				 aSTree.addChild(Stmt_list);
					 if (v_Terminal("RBRACE",aSTree)) {
						 Error ="";
					 }else {
						 Error = "Right Brace is missing at token "+ focus;
					 }
				 
			 } else {
				 Error = "Left Brace is missing at token "+ focus;
			 }
		}
	}
	//To verify the terminals
	public Boolean v_Terminal(String terminal, ASTree a) {
		token = pTokens.get(focus);
		//System.out.println("*"+ token+"***"+terminal+"*");
		if (token.compareTo(terminal)==0) {
			
			if(terminal.compareTo("ID")==0) {
				a.addChild(new ASTree (ASTokens.get(focus).substring(3,ASTokens.get(focus).length())));
				//a.addChild(new AST (terminal));
			} else {
			a.addChild(new ASTree (terminal));
			}
			focus = focus+1;
			return true; }
		else {
			Error = terminal + " is missing at token ="+focus;
			return false;
		}
	}
	
	public Boolean v_Terminal(String terminal) {
		token = pTokens.get(focus);
		//System.out.println("*"+ token+"***"+terminal+"*");
			if (token.compareTo(terminal)==0) {
				focus = focus+1;
				return true;
			}
			Error = terminal + " is missing at token ="+focus;
		return false;
	}

public Boolean v_stmt_list() {
		
		int cfocus = focus;
		token = pTokens.get(focus);
		//stmt_list : [ stmt [ ';' ] stmt_list ]
		 if (v_stmt()) {
			 focus = focus-1;
			 if (v_Terminal("SEMICOLON")) {
				 cfocus = focus;
				 if (!(v_Terminal("RBRACE"))) {
					 Error="";
					 focus = cfocus;
					 v_stmt_list();
						
					} else {
						
						focus = cfocus;
						return true;
					}
			 } else {
				 if ((v_Terminal("RBRACE"))) {
					 focus=focus-1;
					 return true;
				 } else {
					 Error = "Semi colon is missing at token "+focus;
					 return false;
				 }
			 }
		 }
		return false;
	}


public Boolean v_stmt() {
	
	token = pTokens.get(focus);
	int cfocus = focus;
	int start = focus;
	if (v_assignment() ) {
		
		if(v_Terminal("SEMICOLON")) {
			//System.out.println("Succesful statement"+start);
			return true;
		}
		else {
			Error = "Semi Colon is missing at "+focus;
			return false;
		}
	}
	else {
		//System.out.println("Unsuccesful statement");
		//System.out.println("Entered Node Verification statement");
		focus = cfocus;
		if (v_node_stmt() ) {
			if (v_Terminal("SEMICOLON")) { 
				//System.out.println("Succesful v_node_stmt"+start);
				Error="";
				return true;
			} else {
				Error = "Semi Colon is missing at "+focus;
				return false;
			}
			
		} else {
			focus = cfocus;
			//System.out.println("Entered attr_stmt Verification statement");
			if(v_attr_stmt()) {
				//System.out.println("Succesful v_attr_stmt"+focus);
				if (v_Terminal("SEMICOLON")) { 
				//	System.out.println("Succesful v_attr_stmt()"+start);
					Error="";
					return true;
				} else {
					Error = "Semi Colon is missing at "+focus;
					return false;
				}
			}
			else {
				focus = cfocus;
				//System.out.println("Entered Edge_stmt Verification statement");
				if(v_edge_stmt()) {
					//System.out.println("Succesful v_edge_stmt"+focus);
					if (v_Terminal("SEMICOLON")) { 
					//	System.out.println("Succesful Edge statement "+start);
						Error="";
						return true;
					} else {
						Error = "Semi Colon is missing at "+focus;
						return false;
					}
				} 
				else {
					focus = cfocus;
					System.out.println("Entered Subgraph Verification statement");
					if(v_subgraph()) {
						//System.out.println("Succesful v_subgraph"+focus);
						
						if (v_Terminal("SEMICOLON")) { 
						//	System.out.println("Succesful Subgraph statement "+start);
							Error="";
							return true;
						} else {
							Error = "Semi Colon is missing at "+focus;
							return false;
						}
						
					} else {
						return false;
					}
				}
			}
				
		}
	}
}

	
	
public Boolean v_stmt_list(ASTree Stmt_list) {
		ASTree s = Stmt_list;
		int cfocus = focus;
		token = pTokens.get(focus);
		//stmt_list : [ stmt [ ';' ] stmt_list ]
		ASTree stmt = new ASTree("stmt");
		 if (v_stmt(stmt)) {
			 s.addChild(stmt);
			 focus = focus-1;
			 if (v_Terminal("SEMICOLON",s)) {
				 cfocus = focus;
				 if (!(v_Terminal("RBRACE"))) {
					 Error="";
					 focus = cfocus;
					 v_stmt_list(s);
						
					} else {
						
						focus = cfocus;
						return true;
					}
			 } else {
				 if ((v_Terminal("RBRACE"))) {
					 focus=focus-1;
					 return true;
				 } else {
					 Error = "Semi colon is missing at token "+focus;
					 return false;
				 }
			 }
		 }
		return false;
	}

	public Boolean v_stmt(ASTree stmt) {
		ASTree s = stmt;
		token = pTokens.get(focus);
		int cfocus = focus;
		int start = focus;
		if (v_assignment() ) {
			
			if(v_Terminal("SEMICOLON")) {
				//System.out.println("Succesful statement"+start);
				s.addChild(new ASTree (ASTokens.get(focus-4).substring(3,ASTokens.get(focus-4).length())));
				s.addChild(new ASTree("EQUAL"));
				s.addChild(new ASTree (ASTokens.get(focus-2).substring(3,ASTokens.get(focus-2).length())));
				return true;
			}
			else {
				Error = "Semi Colon is missing at "+focus;
				return false;
			}
		}
		else {
			//System.out.println("Unsuccesful statement");
			//System.out.println("Entered Node Verification statement");
			focus = cfocus;
			ASTree node_stmt = new ASTree("Node_Stmt");
			if (v_node_stmt(node_stmt) ) {
				if (v_Terminal("SEMICOLON")) { 
					//System.out.println("Succesful v_node_stmt"+start);
					s.addChild(node_stmt);
					Error="";
					return true;
				} else {
					Error = "Semi Colon is missing at "+focus;
					return false;
				}
				
			} else {
				focus = cfocus;
				ASTree attr_stmt = new ASTree("Attr_Stmt");
				//System.out.println("Entered attr_stmt Verification statement");
				if(v_attr_stmt(attr_stmt)) {
					//System.out.println("Succesful v_attr_stmt"+focus);
					if (v_Terminal("SEMICOLON",s)) { 
					//	System.out.println("Succesful v_attr_stmt()"+start);
						Error="";
						s.addChild(attr_stmt);
						return true;
					} else {
						Error = "Semi Colon is missing at "+focus;
						return false;
					}
				}
				else {
					focus = cfocus;
					//System.out.println("Entered Edge_stmt Verification statement");
					ASTree edge_stmt = new ASTree("EDGE_Stmt");
					if(v_edge_stmt(edge_stmt)) {
						//System.out.println("Succesful v_edge_stmt"+focus);
						if (v_Terminal("SEMICOLON",s)) { 
						//	System.out.println("Succesful Edge statement "+start);
							Error="";
							s.addChild(edge_stmt);
							return true;
						} else {
							Error = "Semi Colon is missing at "+focus;
							return false;
						}
					} 
					else {
						focus = cfocus;
						System.out.println("Entered Subgraph Verification statement");
						ASTree subgraph_stmt = new ASTree("Subgraph_Stmt");
						if(v_subgraph(subgraph_stmt)) {
							//System.out.println("Succesful v_subgraph"+focus);
							
							if (v_Terminal("SEMICOLON",s)) { 
							//	System.out.println("Succesful Subgraph statement "+start);
								Error="";
								s.addChild(subgraph_stmt);
								return true;
							} else {
								Error = "Semi Colon is missing at "+focus;
								return false;
							}
							
						} else {
							return false;
						}
					}
				}
					
			}
		}
	}
	
	
	// ID = ID
	public Boolean v_assignment() {
		
		if (v_Terminal("ID") ) {
			 if (v_Terminal("EQUAL")) {
				 if (v_Terminal("ID")) {
					 //System.out.println("Succesful assignment");
					 return true;
				 } else {
					 
					 Error="Value is not assigned at token "+focus;
					 System.out.println(Error);
					 System.exit(0);
					 return false;
				 }
			 } else {
				 return false;
			 }
		} else {
			return false;
		}
	}
	
	
	public Boolean v_subgraph(ASTree Sub_graph) {
		ASTree s = Sub_graph;
		int cfocus = focus;
		Boolean prev =false;
		if (v_Terminal("SUBGRAPH",s)) {
			cfocus = focus;
			prev = true;
			if (v_Terminal("ID",s)) {
				cfocus =focus;
			} 
		} 
		Error="";
		if (v_Terminal("LBRACE" ,s)) {
			cfocus=focus;
			ASTree stmt_list = new ASTree("Subg_Stmt_list");
			if (v_stmt_list(stmt_list)) {
				s.addChild(stmt_list);
				if (v_Terminal("RBRACE",s)){
					Error="";
					return true;
					
				} else {
					System.out.println("RBRACE is missing at token="+focus);
					System.exit(0);
					return false;
				}
			
			} else {
				return false;
			}
		} else {
			if (prev)  {
				System.out.println("LBRACE is missing at token="+focus);
				System.exit(0);
				return false;
			} else
			{	
				return false;
			}
		}
		
	}
	
	
	
	
	public Boolean v_subgraph() {
		int cfocus = focus;
		Boolean prev =false;
		if (v_Terminal("SUBGRAPH")) {
			cfocus = focus;
			prev = true;
			if (v_Terminal("ID")) {
				cfocus =focus;
			} 
		} 
		Error="";
		//if (v_Terminal("LBRACE" ) && v_stmt_list() && v_Terminal("RBRACE") ) {
		
		if (v_Terminal("LBRACE" )) {
			cfocus=focus;
			
			if (v_stmt_list()) {
				
				if (v_Terminal("RBRACE")){
					Error="";
					return true;
					
				} else {
					System.out.println("RBRACE is missing at token="+focus);
					System.exit(0);
					return false;
				}
			
			} else {
				return false;
			}
		} else {
			if (prev)  {
				System.out.println("LBRACE is missing at token="+focus);
				System.exit(0);
				return false;
			} else
			{	
				return false;
			}
		}
		
	}
	
	public Boolean v_node_stmt () {
		int cnfocus = focus;
		if ( v_node_id()) {
			cnfocus = focus;
			if (v_Terminal("LBRACKET")) {
				cnfocus=focus;
				focus=focus-1;
				if (v_attr_list()) {
					return true;
				} else {
					focus =cnfocus;
					System.out.println(Error+focus);
					System.exit(0);
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	public Boolean v_node_stmt (ASTree Node_stmt) {
		ASTree s = Node_stmt;
		int cnfocus = focus;
		ASTree node_id = new ASTree("Node_id");
		if ( v_node_id(node_id)) {
			s.addChild(node_id);
			cnfocus = focus;
			if (v_Terminal("LBRACKET")) {
				cnfocus=focus;
				focus=focus-1;
				ASTree attr_list = new ASTree("Attr_List");
				if (v_attr_list(attr_list)) {
					s.addChild(attr_list);
					return true;
				} else {
					focus =cnfocus;
					System.out.println(Error+focus);
					System.exit(0);
					return true;
				}
			}
		}
		return false;
	}
	
	public Boolean v_node_id(ASTree Node_id) {
		ASTree s = Node_id;
		int cfocus =focus;
		if (v_Terminal("ID",s)) {
			cfocus = focus;
			if (v_Terminal("COLON",s)) {
				focus=focus-1;
				ASTree port = new ASTree("Port");
				if(v_port(port)) {
					s.addChild(port);
					return true;
				} else {
					return true;
				}
			} else {
				return true;
			}
 		}
		return false;
	}
	
	public Boolean v_node_id() {
		int cfocus =focus;
		if (v_Terminal("ID")) {
			cfocus = focus;
			if (v_Terminal("COLON")) {
				focus=focus-1;
				return v_port();
			} else {
				return true;
			}
 		}
		return false;
	}
	
	
	
	public Boolean v_port(ASTree Port ) {
		ASTree s = Port;
		int cfocus = focus;
		Boolean prev = false;
		if(v_Terminal("COLON",s)) {
			cfocus=focus;
			
			if (v_Terminal("ID",s)) {
				prev = true;
				if(v_Terminal("COLON",s)) {
					ASTree compass_pt = new ASTree("Compass_pt");
					if( v_compass_pt(compass_pt)) {
						s.addChild(compass_pt);
						return true;
					}
					else {
						System.out.println("Commpass_pt is missing at "+focus);
						System.exit(0);
						return false;
					}
				}
			}
			ASTree compass_pt = new ASTree("Compass_pt");
			if(v_compass_pt(compass_pt)) {
				s.addChild(compass_pt);
				return true;
			} 
			else {
				System.out.println("ID or Commpass_pt is missing at "+focus);
				System.exit(0);
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	
	
	
	
	public Boolean v_port( ) {
		int cfocus = focus;
		Boolean prev = false;
		if(v_Terminal("COLON")) {
			cfocus=focus;
			
			if (v_Terminal("ID")) {
				prev = true;
				if(v_Terminal("COLON")) {
					if( v_compass_pt()) {
						return true;
					}
					else {
						System.out.println("Commpass_pt is missing at "+focus);
						System.exit(0);
						return false;
					}
				}
			} 
			if(v_compass_pt()) {
				return true;
			} 
			else {
				System.out.println("ID or Commpass_pt is missing at "+focus);
				System.exit(0);
				return false;
			}
		}
		else {
			return false;
		}
	}
		
public Boolean v_compass_pt(ASTree Compass_pt) {
	ASTree s = Compass_pt;
		
		if (v_Terminal("N",s) || v_Terminal("NE",s) || v_Terminal("E",s) || v_Terminal("SE",s) || v_Terminal("S",s) || v_Terminal("SW",s) || v_Terminal("W",s) || v_Terminal("NW",s) || v_Terminal("C",s) || v_Terminal("UNDERSCORE",s) ) {
			Error = "";
			return true;
		} 
		return false ;
	}
	
	public Boolean v_compass_pt() {
		
		if (v_Terminal("N") || v_Terminal("NE") || v_Terminal("E") || v_Terminal("SE") || v_Terminal("S") || v_Terminal("SW") || v_Terminal("W") || v_Terminal("NW") || v_Terminal("C") || v_Terminal("UNDERSCORE") ) {
			Error = "";
			return true;
		} 
		return false ;
	}
	
	public Boolean v_attr_stmt (ASTree Attr_stmt) {
		ASTree s = Attr_stmt;
		int cfocus= focus;
		if (v_Terminal("GRAPH",s) || v_Terminal("NODE",s) || v_Terminal("EDGE",s))  {
			Error = "";
			cfocus= focus;
			ASTree attr_list = new ASTree ("Attr_list");
			if (v_attr_list(attr_list)) {
				s.addChild(attr_list);
				return true;
			} else {
				focus = cfocus;
				return false;
			}
		}
		return false;
	}
	
	
	
	
	public Boolean v_attr_stmt () {
		int cfocus= focus;
		if (v_Terminal("GRAPH") || v_Terminal("NODE") || v_Terminal("EDGE"))  {
			Error = "";
			cfocus= focus;
			if (v_attr_list()) {
				return true;
			} else {
				focus = cfocus;
				return false;
			}
		}
		return false;
	}
	
	
	public Boolean v_attr_list() {
		int cfocus= focus;
		Boolean prev = false;
		//System.out.println("Entered att list  "+cfocus);
		if (v_Terminal("LBRACKET")) {
			cfocus = focus;
			 if (v_a_list()) {
				 cfocus =focus;
				 if (v_Terminal("RBRACKET")) {
					 cfocus=focus;
					 if (v_Terminal("LBRACKET")) {
						 focus= cfocus;
						 v_attr_list();
						 return true;
					 } else {
						 return true;
					 }
					 
				 } else {
					 Error = "Right Bracket is missing";
					 System.out.println("Missing Right Brace at token"+focus);
					 System.exit(0);					 
					 return false;
					
				 }
			 } else {
				 Error = "Invalid alist";
				 focus = cfocus;
				 return false ;
			 }
		} 
		else {
			Error = "LBRACKET is missing at token "+focus;
			return false;
		}
		
	}
	
	
	
	
	
	public Boolean v_attr_list(ASTree Attr_list) {
		ASTree s = Attr_list;
		int cfocus= focus;
		Boolean prev = false;
		//System.out.println("Entered att list  "+cfocus);
		if (v_Terminal("LBRACKET",s)) {
			cfocus = focus;
			ASTree a_list = new ASTree("A_list");
			 if (v_a_list(a_list)) {
				 cfocus =focus;
				 s.addChild(a_list);
				 if (v_Terminal("RBRACKET",s)) {
					 cfocus=focus;
					 if (v_Terminal("LBRACKET")) {
						 focus= cfocus;
						 v_attr_list(s);
						 return true;
					 } else {
						 return true;
					 }
					 
				 } else {
					 Error = "Right Bracket is missing";
					 System.out.println("Missing Right Brace at token"+focus);
					 System.exit(0);					 
					 return false;
					
				 }
			 } else {
				 Error = "Invalid alist";
				 focus = cfocus;
				 return false ;
			 }
		} 
		else {
			Error = "LBRACKET is missing at token "+focus;
			return false;
		}
		
	}
	public Boolean v_a_list(ASTree A_list) {
		ASTree s = A_list;
		int cfocus = focus;
		boolean prev = false;
		//ID '=' ID [ (';' | ',') ] [ a_list ]
		
		 if (v_assignment())
		 {
			//s.addChild(new AST("ID"));
			// s.addChild(new AST("EQUAL"));
			// s.addChild(new AST("ID"));
		 	s.addChild(new ASTree (ASTokens.get(focus-3).substring(3,ASTokens.get(focus-3).length())));
		 	s.addChild(new ASTree("EQUAL"));
			//s.addChild(new AST (ASTokens.get(focus).substring(3,ASTokens.get(focus).length())));
			s.addChild(new ASTree (ASTokens.get(focus-1).substring(3,ASTokens.get(focus-1).length())));
			 cfocus=focus;
			// if (v_Terminal("SEMICOLON") || v_Terminal("COMMA")){
			if (v_Terminal("COMMA",s)){
				 prev= true;
				 cfocus=focus;
				 //System.out.println("COMMA");
				 if (v_Terminal("ID")) {
					 focus =focus-1;
					 cfocus=focus;
					  if (v_a_list(s)) {
						  return true;
					  } else {
						  focus=cfocus;
						  return false;
					  }
				 }
				 else {
					 if (v_Terminal("RBRACKET")) {
						focus = focus-1;
						cfocus=focus;
						 prev=false;
						// System.out.println("recevied RBRACKET in a list"+focus);
						 return true;	
					 } else {
						 Error = "Right Bracket is missing";
						 System.out.println("Missing Right Brace at token"+focus);
						 System.exit(0);
						 return false;
					 }
				 }
				 
			 }	else {
				 if (v_Terminal("RBRACKET")) {
						focus = focus-1;
						cfocus=focus;
						 prev=false;
						// System.out.println("recevied RBRACKET in a list"+focus);
						 return true;	
					 } else {
						 Error = "Right Bracket is missing";
						 System.out.println("Missing Right Brace at token"+focus);
						 System.exit(0);
						 return false;
					 }
			 }
		 }		 
		 return false;
	}
	
	
	
	
	
	
	public Boolean v_a_list() {
		int cfocus = focus;
		boolean prev = false;
		//ID '=' ID [ (';' | ',') ] [ a_list ]
		 if (v_assignment())
		 {
			 cfocus=focus;
			// if (v_Terminal("SEMICOLON") || v_Terminal("COMMA")){
			if (v_Terminal("COMMA")){
				 prev= true;
				 cfocus=focus;
				 //System.out.println("COMMA");
				 if (v_Terminal("ID")) {
					 focus =focus-1;
					 cfocus=focus;
					  if (v_a_list()) {
						  return true;
					  } else {
						  focus=cfocus;
						  return false;
					  }
				 }
				 else {
					 if (v_Terminal("RBRACKET")) {
						focus = focus-1;
						cfocus=focus;
						 prev=false;
						// System.out.println("recevied RBRACKET in a list"+focus);
						 return true;	
					 } else {
						 Error = "Right Bracket is missing";
						 System.out.println("Missing Right Brace at token"+focus);
						 System.exit(0);
						 return false;
					 }
				 }
				 
			 }	else {
				 if (v_Terminal("RBRACKET")) {
						focus = focus-1;
						cfocus=focus;
						 prev=false;
						// System.out.println("recevied RBRACKET in a list"+focus);
						 return true;	
					 } else {
						 Error = "Right Bracket is missing";
						 System.out.println("Missing Right Brace at token"+focus);
						 System.exit(0);
						 return false;
					 }
			 }
		 }		 
		 return false;
	}
	
	public Boolean v_edge_stmt(ASTree Edge_stmt) {
		ASTree s = Edge_stmt;
		int cfocus=focus;
		int start = focus;
		Boolean prev = false;
		ASTree node_id = new ASTree("Node_id");
		if (v_node_id(node_id)) {
			s.addChild(node_id);
			prev =true;
			//System.out.println("Edge Node pass");
		} 
		if (!prev) {
			focus=cfocus;
			ASTree subgraph = new ASTree("Subgraph");
			if(v_subgraph(subgraph)) {
				s.addChild(subgraph);
				prev =true;
				//System.out.println("Edge Subgraph pass");
			}
		}
		if (prev) {
			//System.out.println("Edge Node or Subgraphpass");
			ASTree edgerhs= new ASTree("Edge_RHS");
			if (v_edgeRHS(edgerhs)) {
				s.addChild(edgerhs);
				prev = true;
				cfocus=focus;
				if (v_Terminal("LBRACKET")) {
					focus=focus-1;
					ASTree attr_list = new ASTree("Attr_list");
					if(v_attr_list(attr_list)) {
						s.addChild(attr_list);
						return true;
					}
				} else {
					
					return true;
				}
				
			} else {
				focus=start;
				return false;
			}
		} 
		focus=start;
		return false;
	}
	
	
	
	public Boolean v_edge_stmt() {
		int cfocus=focus;
		int start = focus;
		Boolean prev = false;
		if (v_node_id()) {
			prev =true;
			//System.out.println("Edge Node pass");
		} 
		if (!prev) {
			focus=cfocus;
			if(v_subgraph()) {
				prev =true;
				//System.out.println("Edge Subgraph pass");
			}
		}
		if (prev) {
			//System.out.println("Edge Node or Subgraphpass");
			if (v_edgeRHS()) {
				prev = true;
				cfocus=focus;
				if (v_Terminal("LBRACKET")) {
					focus=focus-1;
					return v_attr_list();
				} else {
					
					return true;
				}
				
			} else {
				focus=start;
				return false;
			}
		} 
		focus=start;
		return false;
	}
	
	
	public Boolean v_edgeRHS(ASTree Edge_RHS) {
		ASTree s = Edge_RHS;
		int start=focus;
		int cfocus=focus;
		Boolean prev= false;
		if (v_Terminal("EDGEOP",s)) {
			cfocus=focus;
			ASTree node_id = new ASTree("Node_id");
			if(v_node_id(node_id)) {
				s.addChild(node_id);
				prev=true;
				cfocus=focus;
			}
			if (!prev) {
				focus=cfocus;
				ASTree subgraph = new ASTree("Sub_graph");
				if(v_subgraph(subgraph)) {
					s.addChild(subgraph);
					prev=true;
					cfocus=focus;
				} 
				else {
					System.out.println("TARGET NODE ID is missing after EDGEOP at "+cfocus);
					System.exit(0);
					return false;
				}
			}
			if(prev) {
				if(v_Terminal("EDGEOP")) {
					focus=focus-1;
					return v_edgeRHS(s);
				} 
				else {
					return true;
				}
			}
			else {
				return false;
			}
		} 
		else {
			return false;
		}
	}
	




	
	
	public Boolean v_edgeRHS() {
		int start=focus;
		int cfocus=focus;
		Boolean prev= false;
		if (v_Terminal("EDGEOP")) {
			cfocus=focus;
			if(v_node_id()) {
				prev=true;
				cfocus=focus;
			}
			if (!prev) {
				focus=cfocus;
				if(v_subgraph()) {
					prev=true;
					cfocus=focus;
				} 
				else {
					System.out.println("TARGET NODE ID is missing after EDGEOP at "+cfocus);
					System.exit(0);
					return false;
				}
			}
			if(prev) {
				if(v_Terminal("EDGEOP")) {
					focus=focus-1;
					return v_edgeRHS();
				} 
				else {
					return true;
				}
			}
			else {
				return false;
			}
		} 
		else {
			return false;
		}
	}
	
}