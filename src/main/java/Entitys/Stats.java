package Entitys;

import items.ActionType;

public class Stats {
	private int statStr,statDex,statCon,statInt,statWis,statCha;
	public int level;
	public Stats(int level,int statStr, int statDex, int statCon, int statInt, int statWis, int statCha) {
		this.statStr = statStr;
		this.statDex = statDex;
		this.statCon = statCon;
		this.statInt = statInt;
		this.statWis = statWis;
		this.statCha = statCha;
		this.level=level;
	}
	public int getStat(Stat stat){

		return switch (stat){
			case CHA -> statCha;
			case STR -> statStr;
			case DEX -> statDex;
			case CON -> statCon;
			case INT -> statInt;
			case WIS -> statWis;
			default -> 0;
		};
	}
	public Stats clone(){
		Stats clone=new Stats(level,statStr,statDex,statCon,statInt,statWis,statCha);
		return clone;

	}

}