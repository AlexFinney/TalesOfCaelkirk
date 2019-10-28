package skeeter144.toc.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.TOCPlayer;

public class Database {

	static String user = "ZkCYQE6thv";
	static String pass = "IgeKU0szPG";

	public static void createPlayerInDatabase(EntityPlayer player) {
		Database.executeUpdate(String.format("insert ignore into Players values(\"" + player.getUniqueID() + "\", NOW(), NOW(), 20, 12, %s)", 
				player.getDisplayName().toString()));
		insertPlayerLevels(player.getUniqueID());
	}

	public static boolean playerExists(EntityPlayer player) {
		if (getUserObject(player) != null)
			return true;
		else
			createPlayerInDatabase(player);
		return false;
	}

	public static TOCPlayer getPlayer(EntityPlayer player) {
		return getUserObject(player);
	}

	public static void savePlayer(TOCPlayer player) {
		Database.executeUpdate(String.format(
				"update Players SET LastOnline = NOW(), CurrentHP = %s, CurrentMP = %s, DisplayName = %s WHERE UUID = \'"
						+ player.mcEntity.getUniqueID().toString() + "\'",
				player.getHealth(), player.getMana(), player.mcEntity.getDisplayName().getString()));

		updatePlayerLevels(player.levels);
	}

	private static TOCPlayer getUserObject(EntityPlayer player) {
		String s = String.format("SELECT * FROM Players WHERE UUID = \"%s\"", player.getUniqueID().toString());
		ArrayList<ArrayList<String>> rows = Database.executeQuery(s);

		TOCPlayer tocPlayer = null;
		if (rows.size() > 0) {
			ArrayList<String> row = rows.get((0));
			
			s = String.format("SELECT * FROM PlayerLevels WHERE UUID = \"%s\"", player.getUniqueID().toString());
        	rows = Database.executeQuery(s);
			ArrayList<Integer> levelsXp = new ArrayList<>();

			if (rows.size() > 0) {
				for (int i = 1; i < rows.get(0).size(); ++i) {
					levelsXp.add(new Integer(rows.get(0).get(i)));
				}
			} else {
				return null;
			}
			
			tocPlayer = new TOCPlayer(player, new EntityLevels(levelsXp, player.getUniqueID()), new Integer(row.get(row.size() - 3)),
					new Integer(row.get(row.size() - 2)));
		}
		return tocPlayer;
	}

	public static ArrayList<ArrayList<String>> executeQuery(String query) {
		Connection con = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/ZkCYQE6thv", user, pass);
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			ArrayList<ArrayList<String>> returnObjects = new ArrayList<ArrayList<String>>();
			int colCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				ArrayList<String> row = new ArrayList<>();
				int i = 1;
				while (i <= colCount)
					row.add(rs.getString(i++));
				returnObjects.add(row);
			}
			
			if(con != null) con.close();
			if(rs != null) rs.close();
			
			return returnObjects;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void executeUpdate(String query) {
		Thread t = new Thread(()-> {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/ZkCYQE6thv", user, pass);
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}});
		t.start();
	}

	public static void insertPlayerLevels(UUID uuid) {
		String query = "insert into PlayerLevels values(\"%s\", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);";
		Database.executeUpdate(String.format(query, uuid.toString()));
	}
	
	public static void updatePlayerLevels(EntityLevels entityLevels) {
		String query = "update PlayerLevels set AttackXP = %s, StrengthXP = %s, DefenseXP = %s, MagicXP = %s, WoodcuttingXP = %s, MiningXP = %s, "
				+ "SmithingXP = %s, CraftingXP = %s, VitalityXP = %s, FishingXP = %s, RangedXP = %s WHERE UUID = \""
				+ entityLevels.uuid.toString() + "\"";
		
		ArrayList<Integer> xps = entityLevels.getXps();
		query = String.format(query, xps.get(0), xps.get(1), xps.get(2), xps.get(3), 
				xps.get(4), xps.get(5), xps.get(6), xps.get(7), xps.get(8), xps.get(9), 
				xps.get(10));
		
		Database.executeUpdate(query);
	}
}
