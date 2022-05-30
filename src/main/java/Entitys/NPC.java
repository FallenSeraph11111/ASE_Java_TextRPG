package Entitys;

import items.Action;

import java.util.List;
import java.util.Random;

public class NPC extends Entity{

    public Double xp;

    public NPC(String name, Health hp, Stats stats, Equipment equipment, Team team, Double xp) {
        super(name, hp, stats, equipment, team);
        this.xp = xp;
    }

    public Action actionChoice(){
        List<Action> actions =equipment.actions;
        Random rand = new Random();
        int choice=rand.nextInt(actions.size());
        return actions.get(choice);
    }
    public Entity chooseTarget(List<Entity> enemies){
		for (Entity e: enemies) {
			if(e.hp.alive()){
				return e;
			}
	    }
        return null;
    }

	public NPC clone(){
		NPC clone =new NPC(name,new Health(hp.getHp_base(),stats.getStat(Stat.CON)),stats.clone(),equipment,team,xp);
		return clone;
	}

}
