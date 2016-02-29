package newlang4;

public class Stmt extends Node {

	static Node isMatch(Environment env,LexicalUnit unit) {
		switch (unit.getType()) {
		case NAME:
			return new NameNode(env);
		case FOR:
			return new FORNode(env);
		case END:
			return new ENDNode(env);
		default:
			return null;
		}
	}
}
