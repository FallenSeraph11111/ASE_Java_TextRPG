package items;

import Entitys.Stat;

public enum DamageType {
    HOLY(Stat.WIS,"holy"),
    BLEEDING(Stat.DEX,"bleeding"),
    SLASHING(Stat.STR,"slashing"),
    BLUNT(Stat.STR,"blunt"),
    CURSED(Stat.STR,"cursed"),
	MAGIC(Stat.WIS,"magic"),
	BASIC(Stat.CHA,"basic")
    ;
    final Stat corrospondingStat;
	public final String lowerName;
    DamageType(Stat corrospondingStat, String lowerName){this.corrospondingStat=corrospondingStat;
	    this.lowerName = lowerName;
    };
    public Stat corrospondingStat(){
        return this.corrospondingStat;
    }
	public static DamageType getByName(String name){
		String nameSmall=name.toLowerCase();
		for (DamageType type: DamageType.values()) {
			if(type.lowerName.equals(nameSmall)){
				return type;
			}
		}
		return null;
	}
}
