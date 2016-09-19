package values;

import net.abysmal.engine.utils.HugeInteger;

public enum Costs {
	
	woodenBlock(1, 0, new HugeInteger((short) 5)),
	snowCone(0, 0, new HugeInteger((short) 45))
	;
	
	/*
	 * Types:
	 * 	0:	Towers
	 * 	1:	Blocks
	 * 	2:	Research Facilities
	 * 
	 */
	
	final int type, id;
	final boolean research;
	final HugeInteger cost;
	
	private Costs(int type, int id, HugeInteger cost) {
		this.type = type;
		this.id = id;
		research = false;
		this.cost = cost;
	}
	
	private Costs(int type, int id, boolean research, HugeInteger cost) {
		this.type = type;
		this.id = id;
		this.research = research;
		this.cost = cost;
	}
}
