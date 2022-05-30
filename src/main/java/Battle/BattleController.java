package Battle;

import Entitys.*;
import GUI.BattleInterface;
import GUI.GUI;
import items.Action;
import items.EquipmentItem;

import java.util.ArrayList;
import java.util.List;

public class BattleController {
	private Entity player;
	private List<NPC> enemies;
	private List<NPC> allies;
	private final BattleInterface BI;
	private GUI gui;

	public BattleController(Entity player, List<String> enemies, List<NPC> allies, NPCManager npcM, GUI gui) {
		this.BI = new BattleInterface(gui);
		this.gui=gui;
		this.player = player;
		this.enemies = new ArrayList<>();
		for (String enemy : enemies) {
			this.enemies.add(npcM.getNPC(enemy, Team.ENEMY).clone());
		}
		if (allies == null) {
			this.allies = new ArrayList<>();
		} else {
			this.allies = allies;
		}

	}

	public boolean startBattle() {
		Team team = startingTeam();
		battleRound(team);
		while (loseCondition() && winCondition()) {

			battleRound(team);
			team=Team.nextTeam(team);
			gui.clearOutput();

		}
		if (winCondition()){
			return false;}
		return true;
	}

	private void battleRound(Team currentTeam) {
		gui.setHealthStatusBar(player.hp.getHp_max(),player.hp.getHp());
		gui.setArmourStatusBar(player.equipment.getProtection());
		gui.setBaseDamageStatusBar(player.equipment.getBase_damage());
		List<Entity> currentTeamEntities = getTeam(currentTeam);
		List<Entity> enemyTeam = getEnemies(currentTeam);
		for (Entity entity : currentTeamEntities) {
			entity.resetBlocking();
			if (currentTeam == Team.PLAYER) {
				gui.clearOutput();
				displayEnemyTeamHP(enemyTeam);
				Action action = BI.chooseAction(player.equipment.actions);

				if (action.aoe) {
					List<Entity> target = enemyTeam;
					parseAction(target, action, player);
				} else {
					Entity target = BI.chooseTarget(enemyTeam);
					parseAction(target, action, player);
				}
				continue;
			}
			NPC currentNpc = entity instanceof NPC ? ((NPC) entity) : null; //todo remove this null introduce exception
			Entity target = currentNpc.chooseTarget(enemyTeam);
			Action action = currentNpc.actionChoice();
			if (action.aoe) {
				List<Entity> targets = enemyTeam;
				parseAction(targets, action, player);
			}else{
				parseAction(target, action, currentNpc);
			}
		}
	}

	private List<Entity> getEnemies(Team currentTeam) {
		ArrayList<Entity> enemyTeam = new ArrayList();
		switch (currentTeam) {
			case PLAYER, ALLY -> enemyTeam.addAll(enemies);
			case ENEMY -> {
				enemyTeam.addAll(allies);
				enemyTeam.add(player);
			}
			default -> enemyTeam.clear();
		}
		return enemyTeam;
	}

	private List<Entity> getTeam(Team currentTeam) {
		List<Entity> currentTeamEntities = new ArrayList<Entity>();
		switch (currentTeam) {
			case PLAYER -> currentTeamEntities.add(player);
			case ALLY -> currentTeamEntities.addAll(allies);
			case ENEMY -> currentTeamEntities.addAll(enemies);
		}
		return currentTeamEntities;
	}

	private void parseAction(Entity choosenTarget, Action choosenAction, Entity player) {
		// this methode is used to parse what the action does, in case of an attack starts the damage calculation method
		// and does the damage to the target
		EquipmentItem equipmentItem = player.equipment.getEquipmentWithAction(choosenAction);
		switch (choosenAction.actionType) {
			case ATTACK -> attackAction(choosenTarget, choosenAction, player,equipmentItem);
			case HEAL -> healingAction(choosenTarget, choosenAction, player, equipmentItem);
			case DEFEND -> defendAction(choosenAction, player, equipmentItem);
		}
	}

	private void parseAction(List<Entity> choosenTargets, Action choosenAction, Entity player) {
		// this methode is used to parse what the action does, in case of an attack starts the damage calculation method
		// and does the damage to the target
		for (Entity choosenTarget : choosenTargets) {
			parseAction(choosenTarget,choosenAction,player);
		}
	}

	private void healingAction(Entity choosenTarget, Action choosenAction, Entity player, EquipmentItem equipment) {
		double healamount = 0.0;
		healamount += choosenAction.baseDamage;
		healamount += choosenAction.damageScaling * (player.stats.getStat(choosenAction.damageType.corrospondingStat()) - 10);
		choosenTarget.hp.heal(healamount);
		String text=player.name + " has healed "+choosenTarget.name+ " for "+healamount+" health";
		gui.printTextToOutput(text);
		BI.battleLog(text);
	}

	private void attackAction(Entity choosenTarget, Action choosenAction, Entity player,EquipmentItem equipItem) {
		final double damage = DamageCalculation.calculateDamage(choosenAction, choosenTarget, player,equipItem);
		choosenTarget.hp.damage(damage);
		String text=player.name +" attacked "+choosenTarget.name+" using the action "+choosenAction.name+" and did "+damage+ " Damage";
		gui.printTextToOutput(text);
		BI.battleLog(text);
	}

	private void defendAction(Action choosenAction, Entity player, EquipmentItem equipment) {
		player.blocking = true;
		String text=player.name+ " is defending";
		gui.printTextToOutput(text);
		BI.battleLog(text);
	}

	private boolean loseCondition() {
		return player.hp.alive();
	}

	private boolean winCondition() {
		long numberAlive = enemies.stream().filter(e -> e.hp.alive()).count();
		if (numberAlive > 0) {
			return true;
		} else {
			return false;
		}

	}
	public void displayEnemyTeamHP(List<Entity> enemies){
		StringBuilder builder=new StringBuilder();
		for (Entity ent:enemies) {
			builder.append(ent.name + ": "+ent.hp.getHp());
			builder.append("\n\r");
		}
		gui.printTextToOutput("Enemies Health:\n\r"+builder.toString());
	}

	private Team startingTeam() {
		return Team.PLAYER;
	}
}
