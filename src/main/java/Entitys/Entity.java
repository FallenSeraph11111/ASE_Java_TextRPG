package Entitys;

import items.DamageType;

public abstract class Entity {
    public String name;
    public final Health hp;
    public final Stats stats;
    public final Equipment equipment;
    public Team team;
    public Boolean blocking=false;
    public Entity(String name, Health hp, Stats stats, Equipment equipment, Team team) {
        this.name = name;
        this.hp = hp;
        this.stats = stats;
        this.equipment = equipment;
        this.team = team;
    }
    public void resetBlocking(){
        blocking=false;
    }

	public double resistanceTo(DamageType damageType) {
		return 100.0;
	}

}
