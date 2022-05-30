package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
		GridBagConstraints output = createBasicGridBagConstraints(0,0);
		textOut=createOutputTextArea();
		JScrollPane outputAreaScrollPane=createJScrollPane("Game Text",textOut);
		//creating input area
		GridBagConstraints inputConstrain = createBasicGridBagConstraints(0,1);
		JPanel textInPane= createInputPanel();
		inputConstrain.gridx = 0;
		inputConstrain.gridy = 1;
		//Battle log
		GridBagConstraints BattleLogStatusConstrains = createBasicGridBagConstraints(1,0);
		battleLog=createOutputTextArea();
		JScrollPane battleLogPanel=createJScrollPane("Battle Log",battleLog);
		//status area Player
		GridBagConstraints playerStatusConstrains = createBasicGridBagConstraints(1,1);
		JPanel playerStatusPanel= createStatusPanel();
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
	private JPanel createInputPanel(){
		GridBagConstraints inputConstrain = createBasicGridBagConstraints(0,1);
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
		return textInPane;
	}
	private JPanel createStatusPanel(){
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
		return playerStatusPanel;
	}

	private GridBagConstraints createBasicGridBagConstraints(int Gridx, int Gridy){
		GridBagConstraints output=new GridBagConstraints();
		output.weightx = 0.75;
		output.weighty = 0.75;
		output.gridx = Gridx;
		output.gridy = Gridy;
		output.fill = GridBagConstraints.BOTH;
		return output;
	}
	private JTextArea createOutputTextArea(){
		JTextArea textArea=new JTextArea();
		textArea.setFont(new Font("Serif", Font.PLAIN, 15));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		return textArea;
	}
	private JScrollPane createJScrollPane(String label,JTextArea textArea){
		JScrollPane outputAreaScrollPane = new JScrollPane(textArea);
		outputAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		outputAreaScrollPane.setPreferredSize(new Dimension(250, 250));
		outputAreaScrollPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder(label),
								BorderFactory.createEmptyBorder(5, 5, 5, 5)),
						outputAreaScrollPane.getBorder()));
		return outputAreaScrollPane;
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

	public void setHealthStatusBar(double hp_max, double hp_cur){
		this.playerState[0].setText(hp_max+ " / "+hp_cur);
	}
	public void setArmourStatusBar(double armour){
		this.playerState[1].setText(""+armour);
	}
	public void setBaseDamageStatusBar(double damage){
		this.playerState[2].setText(""+damage);
	}
	public void printTextToOutput(String outputText) {
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
	public void addToBattleLog(String s){
		this.battleLog.setText(s);
	}

	@Override
	public void changeStats(int lvl, int stSTR, int stDEX, int stCON, int stINT, int stWIS, int stCHA) {
		//todo display these somewhere????
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
