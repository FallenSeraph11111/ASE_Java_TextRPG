package items;

import Entitys.Stat;

public enum DamageType {
    HOLY(Stat.WIS),
    BLEEDING(Stat.DEX),
    SLASHING(Stat.STR),
    BLUNT(Stat.STR),
    CURSED(Stat.STR),
	MAGIC(Stat.WIS)
    ;
    final Stat corrospondingStat;
    DamageType(Stat corrospondingStat){this.corrospondingStat=corrospondingStat;};
    public Stat corrospondingStat(){
        return this.corrospondingStat;
    }
}
