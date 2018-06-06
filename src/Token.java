public class Token {
	public int add;
	public String type;
	public String text;
	public Token(int add,String type, String text) {this.add = add;this.type=type; this.text=text;}
	public String toString() {
		return "add="+add+",type="+type+",text="+text;
	}
}