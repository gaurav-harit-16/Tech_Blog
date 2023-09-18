package com.techBlog.dao;

import java.io.PrintWriter;
import java.sql.*;

public class LikeDao {

    Connection con;

    public LikeDao(Connection con) {
        this.con = con;
    }

    public boolean insertLike(int pid, int uid) {
        boolean f = false;
        boolean x=true;
        try {
			String q="select * from liked where pid=? and uid=?";
			PreparedStatement pst=this.con.prepareStatement(q);
			pst.setInt(1, pid);
			pst.setInt(2, uid);
			ResultSet set=pst.executeQuery();
			if(set.next()) {
				String q1="DELETE FROM liked WHERE pid =? AND uid =?";
				PreparedStatement delet=this.con.prepareStatement(q1);
				delet.setInt(1, pid);
				delet.setInt(2, uid);
				delet.executeUpdate();
				x=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        if (x) {
	        try {
	            String q = "insert into liked(pid,uid)values(?,?)";
	            PreparedStatement p = this.con.prepareStatement(q);
	            //values set...
	            p.setInt(1, pid);
	            p.setInt(2, uid);
	            p.executeUpdate();
	            f = true;
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        }
	        return f;
    }
    public int countLikeOnPost(int pid) {
        int count = 0;

        String q = "select count(*) from liked where pid=?";
        try {
            PreparedStatement p = this.con.prepareStatement(q);
            p.setInt(1, pid);
            ResultSet set = p.executeQuery();
            if (set.next()) {
//                count = set.getInt("count(*)");
            	count = set.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public boolean isLikedByUser(int pid, int uid) {
        boolean f = false;
        try {
            PreparedStatement p = this.con.prepareStatement("select * from liked where pid=? and uid=?");
            p.setInt(1, pid);
            p.setInt(2, uid);
            ResultSet set = p.executeQuery();
            if (set.next()) {
                f = true;
            }

        } catch (Exception e) {
        }
        return f;
    }

    public boolean deleteLike(int pid, int uid) {
        boolean f = false;
        try {
            PreparedStatement p = this.con.prepareStatement("delete from liked where pid=? and uid=? ");
            p.setInt(1, pid);
            p.setInt(2, uid);
            p.executeUpdate();
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }
}