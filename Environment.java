 package newlang4;

import java.util.HashMap;
import java.util.Map;



public class Environment {
	   LexicalAnalyzer input;

	   Map library;
	   Map<String,Variable> var_table;

	    public Environment(LexicalAnalyzer my_input) {
	        input = my_input;
	        library = new HashMap();
	        library.put("PRINT", (Function) new PrintFunction());
	        var_table = new HashMap();
	    }

	    public LexicalAnalyzer getInput() {
	        return input;
	    }
	    public Function getFunction(String fname) {
	        return (Function) library.get(fname);
	    }

	    public Variable getVariable(String vname) {
	        Variable v;
	        v = (Variable) var_table.get(vname);
	        if (v == null) {
	            v = new Variable(vname);
	            var_table.put(vname, v);
	        }
	        return v;
	    }
}