package GUI;

public interface IO {
	public void printTextToOutput(String outputText);
	public String waitForInput();

	public void setHealthStatusBar(double hp_cur,double hp_max);
	public void setArmourStatusBar(double armour);
	public void setBaseDamageStatusBar(double damage);
	public void changeStats(int lvl,int stSTR, int stDEX,int stCON,int stINT,int stWIS,int stCHA);
	public void printMap(String map);
	public void clearOutput();
	public void setInputLabel(String s);
	public void addToBattleLog(String s);
}
