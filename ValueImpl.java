package newlang4;

public class ValueImpl implements Value {// 値と型を一個だけとっておくオブジェクト、値がどういうものか定義する場所.

	private ValueType type;// 型が入っている.
	private String s;
	private int i;
	private boolean b;
	private double d;

	public ValueImpl(String svalue) {
		type = ValueType.STRING;
		s = svalue;
	}

	public ValueImpl(int ivalue) {
		type = ValueType.INTEGER;
		i = ivalue;
	}

	public ValueImpl(double dvalue) {
		type = ValueType.DOUBLE;
		d = dvalue;
	}

	public ValueImpl(boolean bvalue) {
		type = ValueType.BOOL;
		b = bvalue;
	}

	public String getSValue() {// 全てのタイプにおいて返すべきもの.
		if (type == ValueType.STRING)
			return s;

		if (type == ValueType.INTEGER)
			return String.valueOf(i);

		if (type == ValueType.BOOL)
			return String.valueOf(b);

		if (type == ValueType.DOUBLE)
			return String.valueOf(d);
		return null;
	}

	public int getIValue() {// 各型の値だけ変えればよい.
		return i;
	}

	public double getDValue() {
		return d;
	}

	public boolean getBValue() {
		return b;
	}

	public ValueType getType() {//
		return type;
	}
	public String toString(){
		return getSValue();
	}
}
