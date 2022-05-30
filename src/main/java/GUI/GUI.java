package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class GUI extends JFrame implements IO {
	protected JTextArea textOut;
	protected JTextArea textIn;
	protected JTextArea inventoryOutput;
	protected JTextArea battleLog;
	protected JLabel inputTextLabel;
	private String answer="";
	private String oldAnswer="";
	private JTextField[] playerState;
	public GUI(String title) throws HeadlessException {
		super(title);
		this.textOut =  new JTextArea(
				"This is the Game Output field"
		);;
		this.battleLog=new JTextArea();
		this.textIn = new JTextArea();
		this.inventoryOutput = inventoryOutput;
		this.inputTextLabel = new JLabel("This Text will show more concrete instructions of what is allowed input");
	}

	private void createAndShowGUI() {
		//Make sure we have nice window decorations.
		//Create and set up the window.
		JFrame frame = new JFrame("HelloWorldSwing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel pane = new JPanel(new GridBagLayout());


		//creating the text output area
		GridBagConstraints output = new GridBagConstraints();
		output.weightx = 0.75;
		output.weighty = 0.75;
		output.gridx = 0;
		output.gridy = 0;
		output.fill = GridBagConstraints.BOTH;
		textOut.setFont(new Font("Serif", Font.PLAIN, 15));
		textOut.setLineWrap(true);
		textOut.setWrapStyleWord(true);
		textOut.setEditable(false);
		JScrollPane outputAreaScrollPane = new JScrollPane(textOut);
		outputAreaScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		outputAreaScrollPane.setPreferredSize(new Dimension(250, 250));
		outputAreaScrollPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder("Game Output"),
								BorderFactory.createEmptyBorder(5, 5, 5, 5)),
						outputAreaScrollPane.getBorder()));
		//creating input area
		GridBagConstraints inputConstrain = new GridBagConstraints();
		inputConstrain.fill = GridBagConstraints.BOTH;
		inputConstrain.weightx = 0.75;
		inputConstrain.weighty = 0.5;

		textIn.setLineWrap(true);
		textIn.setWrapStyleWord(true);
		textIn.setFont(new Font("Serif", Font.PLAIN, 15));
		JPanel textInPane = new JPanel(new GridBagLayout());

		JButton textInButton = new JButton("Submit");
		textInButton.setAction( new SubmitAction("Submit"));
		inputConstrain.gridx= 0;
		inputConstrain.gridy= 0;
		inputConstrain.gridwidth=2;
		textInPane.add(inputTextLabel,inputConstrain);
		inputConstrain.gridwidth=1;
		inputConstrain.gridy= 1;
		textInPane.add(textIn,inputConstrain);
		inputConstrain.gridx= 1;
		textInPane.add(textInButton,inputConstrain);
		textInPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder("Input Area"),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		inputConstrain.gridx = 0;
		inputConstrain.gridy = 1;
		//Battle log
		GridBagConstraints BattleLogStatusConstrains = new GridBagConstraints();
		BattleLogStatusConstrains.fill = GridBagConstraints.BOTH;
		BattleLogStatusConstrains.weightx = 1;
		BattleLogStatusConstrains.weighty = 1;
		BattleLogStatusConstrains.gridx = 1;
		BattleLogStatusConstrains.gridy = 0;
		BattleLogStatusConstrains.gridheight = 1;
		JScrollPane battleLogPanel=new JScrollPane(battleLog);
		battleLog.setFont(new Font("Serif", Font.PLAIN, 15));
		battleLog.setLineWrap(true);
		battleLog.setWrapStyleWord(true);
		battleLog.setEditable(false);
		battleLogPanel.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder("Battle Log"),
								BorderFactory.createEmptyBorder(5, 5, 5, 5)),
						battleLogPanel.getBorder()));

		//status area Player
		GridBagConstraints playerStatusConstrains = new GridBagConstraints();
		playerStatusConstrains.fill = GridBagConstraints.BOTH;
		playerStatusConstrains.weightx = 1;
		playerStatusConstrains.weighty = 1;
		playerStatusConstrains.gridx = 1;
		playerStatusConstrains.gridy = 1;
		playerStatusConstrains.gridheight = 1;
		JPanel playerStatusPanel=new JPanel(new GridLayout(3,2));
		JLabel hptextFieldLabel= new JLabel("HP Max / HP Cur : ");
		JTextField hp=new JTextField(10);
		hp.setEditable(false);
		hptextFieldLabel.setLabelFor(hp);
		JLabel armourtextFieldLabel= new JLabel("Armour : ");
		JTextField armour=new JTextField(10);
		armour.setEditable(false);
		armourtextFieldLabel.setLabelFor(armour);
		JLabel damagetextFieldLabel= new JLabel("Base Damage : ");
		JTextField base_damage=new JTextField(10);
		base_damage.setEditable(false);
		damagetextFieldLabel.setLabelFor(base_damage);
		this.playerState= new JTextField[]{hp, armour, base_damage};
		playerStatusPanel.add(hptextFieldLabel);
		playerStatusPanel.add(hp);
		playerStatusPanel.add(damagetextFieldLabel);
		playerStatusPanel.add(base_damage);
		playerStatusPanel.add(armourtextFieldLabel);
		playerStatusPanel.add(armour);

		playerStatusPanel.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder("Player Status"),
						BorderFactory.createEmptyBorder(5,5,5,5)));
		//playerStatusPanel.setPreferredSize(new Dimension(400, 500));
		pane.add(outputAreaScrollPane, output);
		pane.add(textInPane, inputConstrain);
		pane.add(battleLogPanel,BattleLogStatusConstrains);
		pane.add(playerStatusPanel, playerStatusConstrains);
		//----------------------------
		frame.getContentPane().add(pane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void main() {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});


	}

	public void health(double hp_max,double hp_cur){
		this.playerState[0].setText(hp_max+ " / "+hp_cur);
	}
	public void armour(double armour){
		this.playerState[1].setText(""+armour);
	}
	public void damage(double damage){
		this.playerState[2].setText(""+damage);
	}
	@Override
	public void printText(String outputText) {
		textOut.append("\n\r");
		textOut.append( outputText);
	}
	@Override
	public void clearOutput(){
		textOut.setText("");
		textIn.setText("");
	}

	@Override
	public void setInputLabel(String s) {
		inputTextLabel.setText(s);
	}

	@Override
	public void printMap(String map){
		this.textOut.setText(map);
	}
	@Override
	public String waitForInput() {
		try{
		while(true){
			//System.out.println("old:" + oldAnswer+"  new:"+answer);
			if(this.answer != this.oldAnswer){
				oldAnswer=answer;
				return answer;
			}
			Thread.sleep(200);
		}}
		catch (InterruptedException i){
			return null;
		}
	}
	public void setBattleLog(String s){
		this.battleLog.setText(s);
	}
	@Override
	public void changeHP(double hp_cur, double hp_max) {

	}

	@Override
	public void changeArmour(double armour) {

	}

	@Override
	public void changeStats(int lvl, int stSTR, int stDEX, int stCON, int stINT, int stWIS, int stCHA) {

	}
	class SubmitAction extends AbstractAction{
		public SubmitAction(String name){
			super(name);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			oldAnswer="";
			answer=textIn.getText();
		}
	}
}
