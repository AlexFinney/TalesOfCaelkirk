package skeeter144.toc.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;

import net.minecraft.entity.player.EntityPlayer;
import skeeter144.toc.player.EntityLevels;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.player.EntityLevels.Levels;

public class Database {

	static String user = "ZkCYQE6thv";
	static String pass = "IgeKU0szPG";

	public static void createPlayerInDatabase(UUID uuid) {
		Database.executeUpdate("insert ignore into Players values(\"" + uuid + "\", NOW(), NOW(), 20, 20)");
	}

	public static boolean playerExists(EntityPlayer player) {
		if (getUserObject(player) != null)
			return true;
		else
			createPlayerInDatabase(player.getUniqueID());
		return false;
	}

	public static TOCPlayer getPlayer(EntityPlayer player) {
		return getUserObject(player);
	}

	public static void savePlayer(TOCPlayer player) {
		Database.executeUpdate(String.format(
				"update Players SET LastOnline = NOW(), CurrentHP = %s, CurrentMP = %s WHERE UUID = \""
						+ player.mcEntity.getUniqueID().toString() + "\"",
				player.getHealth(), player.getMana()));

		String query = "update PlayerLevels set AttackXP = %s, StrengthXP = %s, DefenseXP = %s, MagicXP = %s, WoodcuttingXP = %s, MiningXP = %s, " 
											 + "SmithingXP = %s, CraftingXP = %s, VitalityXP = %s, FishingXP = %s, RangedXP = %s WHERE UUID = \"" + player.mcEntity.getUniqueID().toString() + "\"";
		
		query = String.format(query, player.levels.getLevel(Levels.ATTACK).getXp(),
				player.levels.getLevel(Levels.STRENGTH).getXp(), player.levels.getLevel(Levels.DEFENSE).getXp(),
				player.levels.getLevel(Levels.MAGIC).getXp(), player.levels.getLevel(Levels.WOODCUTTING).getXp(),
				player.levels.getLevel(Levels.MINING).getXp(), player.levels.getLevel(Levels.SMITHING).getXp(),
				player.levels.getLevel(Levels.CRAFTING).getXp(), player.levels.getLevel(Levels.HITPOINTS).getXp(),
				player.levels.getLevel(Levels.FISHING).getXp(), player.levels.getLevel(Levels.RANGED).getXp());
		
		Database.executeUpdate(query);
	}

	private static TOCPlayer getUserObject(EntityPlayer player) {
		String s = String.format("SELECT * FROM PlayerLevels WHERE UUID = \"%s\"", player.getUniqueID().toString());

		ArrayList<ArrayList<String>> rows = Database.executeQuery(s);
		ArrayList<Integer> levelsXp = new ArrayList<>();

		if (rows.size() > 0) {
			for (int i = 1; i < rows.get(0).size(); ++i) {
				levelsXp.add(new Integer(rows.get(0).get(i)));
			}
		} else {
			for (int i = 0; i < 11; ++i)
				levelsXp.add(0);
		}
		s = String.format("SELECT * FROM Players WHERE UUID = \"%s\"", player.getUniqueID().toString());
		rows = Database.executeQuery(s);

		TOCPlayer tocPlayer = null;
		if (rows.size() > 0) {
			ArrayList<String> row = rows.get((0));
			tocPlayer = new TOCPlayer(player, new EntityLevels(levelsXp), new Integer(row.get(row.size() - 2)),
					new Integer(row.get(row.size() - 1)));
		}else {
			createPlayerInDatabase(player.getUniqueID());
			tocPlayer = new TOCPlayer(player);
		}

		return tocPlayer;
	}

	public static ArrayList<ArrayList<String>> executeQuery(String query) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/ZkCYQE6thv", user, pass);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			ArrayList<ArrayList<String>> returnObjects = new ArrayList<ArrayList<String>>();
			int colCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				ArrayList<String> row = new ArrayList<>();
				int i = 1;
				while (i <= colCount)
					row.add(rs.getString(i++));
				returnObjects.add(row);
			}
			return returnObjects;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void executeUpdate(String query) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/ZkCYQE6thv", user, pass);
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
