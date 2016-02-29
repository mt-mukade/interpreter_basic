package newlang4;

public class Block extends Node {

	public static Node isMatch(Environment env,LexicalUnit first) {
		Node node = IFNode.isMatch(env, first);
		if(node != null) return node;

		node = DoNode.isMatch(env, first);
		if(node != null) return node;

		node = WHILENode.isMatch(env, first);
		if(node != null) return node;

		return null;
	}
}