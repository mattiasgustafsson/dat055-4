package zombieinfection.view.highscore;
import java.sql.*;
import javax.swing.table.*;
import java.util.*;

    /**
     * This class updates the table containing the highscores.
     * @author David.S
     *
     */

class Table extends DefaultTableModel {


	static String[][] list = new String[5][2];
        
        public Table(){
             setColumnIdentifiers(new Object[]{"Name","Time"});
        }
	
    /**
     * Updates the table by sending an SQL-query to the Postgres server, fetching the data and then updates the table.
     */
        
	public void updateTable()
	{
		try
		{
			Connection conn;
			conn = DriverManager.getConnection("jdbc:postgresql://176.126.70.189:22224/","postgres","<Hn$dY3._BG2M7#N");
			PreparedStatement st = conn.prepareStatement("SELECT * FROM highscores ORDER BY score LIMIT 5");
			ResultSet rs = st.executeQuery();	
			
			int row = 0;
			while(rs.next())
			{
				list[row][0] = rs.getString("name");
                int sec = rs.getInt("score");
                String seconds = String.format("%02d:%02d", sec/60, sec%60);
				list[row][1] = seconds;
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
	
	@Override
    public String getColumnName(int column){
            return "Name";
    }


}
