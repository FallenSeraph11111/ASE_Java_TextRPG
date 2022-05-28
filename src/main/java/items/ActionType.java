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
    ATTACK(),
    DEFEND(),
    HEAL();

	ActionType(){

	}
}
