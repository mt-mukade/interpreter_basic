package newlang4;

import java.util.ArrayList;
import java.util.List;

public class StatementList extends Node {

	Node node;
	List<Node> stmt_list = new ArrayList<Node>();
	LexicalAnalyzer lex;

	public static Node isMatch(Environment env, LexicalUnit first){
		LexicalType ft = first.getType();
		if(ft == LexicalType.IF || ft == LexicalType.DO|| ft == LexicalType.NAME
				|| ft == LexicalType.WHILE || ft == LexicalType.FOR || ft == LexicalType.END){
			return new StatementList(env);
		}
		return null;
	}

	private StatementList(Environment env){
		this.env = env;
		lex = env.getInput();
	}
	@Override
	public boolean Parse() {
		LexicalUnit lu;

		while(true){
			lu = lex.get();
			while(lu.getType() == LexicalType.NL){
				lu = lex.get();
			}
			if(Stmt.isMatch(env, lu) != null){
				node = Stmt.isMatch(env, lu);
				stmt_list.add(node);
				lex.unget(lu);
				if(!(node.Parse())){
					return false;
				}
			}else if(Block.isMatch(env,lu) != null){
				node = Block.isMatch(env, lu);
				stmt_list.add(node);
				lex.unget(lu);
				if(!(node.Parse())){
					return false;
				}
				lex = env.getInput();
				lu = lex.get();
				if(lu.getType() == LexicalType.LOOP){
					lex.unget(lu);
					break;
				}
				if(StatementList.isMatch(env, lu) != null){
					node = StatementList.isMatch(env, lu);
					stmt_list.add(node);
					lex.unget(lu);
					if(!(node.Parse())){
						return false;
					}
				} else {
					lex.unget(lu);
				}
			} else if (StatementList.isMatch(env, lu) != null){
				node = StatementList.isMatch(env, lu);
				stmt_list.add(node);
				lex.unget(lu);
				if(!(node.Parse())){
					return false;
				}
			} else {
				lex.unget(lu);
				return true;
			}
			if(lu.getType() == LexicalType.END){
				break;
			}
		}
		return true;
	}
	@Override
	public String toString(){
		String buffer = "";
		for(Node node_toString: stmt_list){
			buffer += node_toString.toString();
		}
		return buffer;
	}
	@Override
	public Value getValue(){
		for(Node node_getValue: stmt_list){
			
			node_getValue.getValue();
		}
		return null;
	}
}
