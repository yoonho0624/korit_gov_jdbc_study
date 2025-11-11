package com.korit.study3.dao;

import com.korit.study3.util.ConnectionFactory;
import com.korit.study3.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    private static UserDao instance;
    private UserDao() {}
    public static UserDao getInstance() {
        if (instance == null) instance = new UserDao();
        return instance;
    }
    public Optional<User> findByTitle(String title) {
        String sql = "select user_id, title, content, username, create_dt from board where title = ?";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            try(ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(toUser(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    public User findById(int id) {
        String sql = "select user_id, title, content, username, create_dt from board where user_id = ?";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                return rs.next() ? toUser(rs) : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<User> toUserListKeyword(String keyword) {
        String sql = "select user_id, title, content, username, create_dt from board where content like ?";
        List<User> toUserList = new ArrayList<>();
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    toUserList.add(toUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toUserList;
    }
    public List<String> findUserByUsername(String username) {
        String sql = "select user_id, title, content, username, create_dt from board where username = ?";
        List<String> toUserList = new ArrayList<>();
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    toUserList.add(toUser(rs).getContent());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toUserList;
    }
    public int addUser(User user) {
        String sql = "insert into board(user_id, title, content, username, create_dt) values(0, ?, ?, ?, now());";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getTitle());
            ps.setString(2, user.getContent());
            ps.setString(3, user.getUsername());
            int updateInt = ps.executeUpdate();
            try(ResultSet keys = ps.getGeneratedKeys()) {
                System.out.println(keys.toString());
                if (keys.next()) {
                    int id = keys.getInt(1);
                    user.setUserid(id);
                }
            }
            return updateInt;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public User toUser(ResultSet rs) throws SQLException {
        return User.builder()
                .userid(rs.getInt("user_id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .username(rs.getString("username"))
                .createDt(rs.getTimestamp("create_dt").toLocalDateTime())
                .build();
    }
}
