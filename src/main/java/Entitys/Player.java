package Entitys;

public class Player extends Entity{
    public Inventory inventory;

    public Player(String name, Health hp, Stats stats, Equipment equipment, Team team, Inventory inventory) {
        super(name, hp, stats, equipment, team);
        this.inventory = inventory;
    }
}
