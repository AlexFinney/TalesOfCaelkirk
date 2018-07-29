package skeeter144.toc.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import skeeter144.toc.player.TOCPlayer;

public class Database {

	public static void savePlayer() {
		
	}
	
	public static boolean playerExists(UUID uuid) {
		return rsSize(getUserObject(uuid)) > 0;
	}
	
	public static TOCPlayer getPlayer(EntityPlayer player) {
		
		ResultSet rs = getUserObject(player.getUniqueID());
		
		if(rsSize(rs) == 0)
			return null;
		
		
		TOCPlayer pl = new TOCPlayer(player);
		
		return null;
	}
	
	
	private static ResultSet getUserObject(UUID uuid) {
		return  Database.executeQuery("SELECT * FROM users WHERE uuid = binary\"" + uuid.toString() + "\"");
	}
	
	private static int rsSize(ResultSet rs) {
		int size = 0;
		try {
			int curRow = rs.getRow();
			
			rs.last();
			size = rs.getRow();
			
			rs.absolute(curRow);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return size;
	}

	public static ResultSet executeQuery(String query) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9249031", "sql9249031", "h9p1mFvqnL");

			Statement stmt = con.createStatement();

			return stmt.executeQuery(query);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}


