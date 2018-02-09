package zombieinfection.view.highscore;
import java.sql.*;
import javax.swing.table.*;
import java.util.*;

class Table extends AbstractTableModel {

	static String[][] list = new String[5][2];	
	
	public void updateTable()
	{
		try
		{
			Connection conn;
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/","postgres","");
			PreparedStatement st = conn.prepareStatement("SELECT * FROM highscores ORDER BY score DESC LIMIT 5");
			ResultSet rs = st.executeQuery();	
			
			int row = 0;
			while(rs.next())
			{
				list[row][0] = rs.getString("name");
				list[row][1] = rs.getString("score");
				row++;
			}
			conn.close();
			fireTableDataChanged();
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	
	//Auto-genererad kod
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return list[row][col];
	}

}
