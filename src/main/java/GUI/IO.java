package GUI;

public interface IO {
	public void printText(String outputText);
	public String waitForInput();

	public void changeHP(double hp_cur,double hp_max);
	public void changeArmour(double armour);
	public void changeStats(int lvl,int stSTR, int stDEX,int stCON,int stINT,int stWIS,int stCHA);
	public void printMap(String map);
	public void clearOutput();
	public void setInputLabel(String s);
}
