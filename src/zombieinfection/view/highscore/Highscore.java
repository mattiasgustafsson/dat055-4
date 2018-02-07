package highscore;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;


public class Highscore extends JFrame implements ActionListener {
	
	Table tableManager;
	JTable table;
	JLabel nameText;
	JLabel scoreText;
	JTextField nameInput;
	JTextField scoreInput;
	JButton submit;
	
	public Highscore()
	{	
		JFrame frame = new JFrame("Highscore TEST");
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panel2 = new JPanel(new GridLayout(2,2));
		frame.setSize(new Dimension(300,200));
		frame.setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		tableManager = new Table();
		tableManager.updateTable();
		
		table = new JTable(tableManager);
		table.setEnabled(false);
		
		nameText = new JLabel("Enter your name:");
		scoreText = new JLabel("Enter your score:");
		
		nameInput = new JTextField(10);
		scoreInput = new JTextField(10);
	
		submit = new JButton("Submit score");
		submit.setBounds(30,200,300,30);
		submit.addActionListener(this);
		
		panel.add(table, BorderLayout.NORTH);
		panel.add(submit, BorderLayout.SOUTH);
		panel2.add(nameText);
		panel2.add(nameInput);
		panel2.add(scoreText);
		panel2.add(scoreInput);
		panel.add(panel2,BorderLayout.WEST);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == submit)
		{
			String name = nameInput.getText();
			String score = scoreInput.getText();
			
			if(name.length() > 0 && score.length() > 0)
			{
				int regscore = Integer.parseInt(score);
				try
				{
					Connection conn;
					conn = DriverManager.getConnection("jdbc:postgresql://localhost/","postgres","");
					String queryRegister = "INSERT INTO highscores VALUES (?,?)";
					PreparedStatement st = conn.prepareStatement(queryRegister);
					st.setString(1, name);
					st.setInt(2, regscore);
					st.executeUpdate();
					
					conn.close();
				}
				catch(SQLException execption)
				{
					System.out.println("ajaBAJA");
				}
				
				tableManager.updateTable();
			}
				
		}
	}
	public static void main(String[] args) {
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException e) 
		{
			System.out.println(e);
		}
		// TODO Auto-generated method stub
		new Highscore();
	}
	
}
