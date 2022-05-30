package items;

/*public enum Actions {
    ATTACK,
    SLASH,
    DEFEND,
    RANGEDATTACK;
    public Enum<>type
            //das wird ausgelagert in eine JSON
}*/
public enum ActionType{
    ATTACK(1,"attack"),
    DEFEND(2,"defend"),
    HEAL(3,"heal");
	public final int ID;
	public final String lowerName;
	ActionType(int id, String lowerName){

		ID = id;
		this.lowerName = lowerName;
	}
	public static ActionType getByID(int id){
		for (ActionType type: ActionType.values()) {
			if(type.ID == id){
				return type;
			}
		}
		return null;
	}
	public static ActionType getByName(String name){
		String namesmall=name.toLowerCase();
		for (ActionType type: ActionType.values()) {
			if(type.lowerName.equals(namesmall)){
				return type;
			}
		}
		return null;
	}
}
