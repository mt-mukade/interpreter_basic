package newlang4;

import java.util.List;

public class PrintFunction extends Function {

	public PrintFunction() {
	}
	@Override
	public Value invoke(Expr_list arg){
		List<Node> exprList = arg.getList();
		for(Node expr : exprList){
			System.out.print(expr.getValue().getSValue());
		}
			System.out.println();

		return null;
	}
}
