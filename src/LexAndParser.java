import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Lohith Nimmala
 */

public class LexAndParser {
	private static BufferedReader br;
	static Map<Integer,String> ASTokens = new HashMap<Integer, String>();
	public static void main(String[] args) throws IOException {
		lex l1 = new lex();
		// Fetching input file

		File file = new File("input.txt");
		br = new BufferedReader(new FileReader(file));
		String st;
		//System.out.println("Input");
		while ((st = br.readLine()) != null) {
			//System.out.println(st);
		}
		int count=1;
		// Initiating List variable to capture all the tokens from lexer

		Map<Integer,String> Tokens = new HashMap<Integer, String>();

		//System.out.println("Output of lexer(tokens)");
		br = new BufferedReader(new FileReader(file));
		while ((st = br.readLine()) != null) {
			st = st.replaceAll("<.*>", " ID "); // Replacing all the text in between ".." as ID
			st = st.replaceAll("\".*\"", " ID "); // Replacing all the text in between < > as ID
			st = st.replaceAll("/\\*.*\\*/", " "); // Replacing all the text in between /* */ as blank since those are
			st = st.replaceAll("\t", "");	
			// comment
			List<String> kk = l1.lex(st);
			for (String s : kk) {
				//System.out.printf(count+"."+s+" ");
				ASTokens.put(count,s); // adding all the tokens received in the Tokens list variable
				count += 1;
			}
			//System.out.println();
		}
		String ast="";
		
		count=1;
		for (Map.Entry<Integer,String> entry : ASTokens.entrySet()) {
			String[] s = entry.getValue().split(",");
			Tokens.put(count,s[0]);
			count=count+1;
		}
		Parser2 p1 = new Parser2();
		p1.pTokens = Tokens;
		ASTree rootASTree = p1.v_parser();
		Treewalker tree = new Treewalker();
		System.out.println("Breadth first search at the root of a tree");
		//tree.bfs(rootASTree);
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("Displaying Fragments of Root Node");
		Treewalker tree1 = new Treewalker();
		tree1.findFragements(rootASTree.childnodes);
		System.out.println("\n");
		System.out.println("Displaying all Nodes which are leaf nodes");
		Treewalker tree2 = new Treewalker();
		//Prints all Nodes which are leaf nodes
		tree2.findLeaves(rootASTree.childnodes);
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("Displaying all Nodes which are branches having leaves");
		//Prints all Nodes which are branches having leaves
		tree.findParents(rootASTree.childnodes);
		System.out.println("\n");
		System.out.println("Displaying all Childnode which has most childnodes");
		Treewalker tree3 = new Treewalker();
		tree3.mostchildnodesOutput(rootASTree.childnodes);
		ASTree Stmtlist=new ASTree();
		//Getting a node from the tree using its name
		
		Stmtlist = rootASTree.findNode("Stmt_list");


	}
}
