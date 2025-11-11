package com.korit.study2.dao;

import com.korit.study2.dto.GetUserListRespDto;
import com.korit.study2.entity.User;
import com.korit.study2.util.ConnectionFactory;

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

    // username 조회
    public Optional<User> findUserByUsername(String username) {
        String sql = "select user_id, username, password, email, create_dt from user2_tb where username = ?";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(toUser(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    // email 조회
    public Optional<User> findUserByEmail(String email) {
        String sql = "select user_id, username, password, email, create_dt from user2_tb where email = ?";
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(toUser(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    // user 추가
    public int addUser(User user) {
        String sql = "insert into user2_tb (user_id, username, password, email, create_dt) values (0, ?, ?, ?, now());";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());

            int updateInt = ps.executeUpdate(); // 쿼리 실행 : 변경된 행의 갯수를 반환

            try (ResultSet keys = ps.getGeneratedKeys()) {
                System.out.println(keys.toString());
                if (keys.next()) {
                    int id = keys.getInt(1);
                    user.setUserId(id);
                }
            }
            return updateInt;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    // user 전체 조회
    public List<User> getUserAllList() {
        String sql = "select user_id, username, email, create_dt from user2_tb";
        List<User> userList = new ArrayList<>();
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                userList.add(toUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    // user username으로 조회
    public List<GetUserListRespDto> getUserListKeyword(String keyword) {
        String sql = "select user_id, username, email, create_dt from user2_tb where username like ?";
        List<GetUserListRespDto> userListRespDtos = new ArrayList<>();
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    userListRespDtos.add(toGetUserListRespDto(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userListRespDtos;
    }

    public User toUser(ResultSet rs) throws SQLException {
        return User.builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .email(rs.getString("email"))
                .createDt(rs.getTimestamp("create_dt").toLocalDateTime())
                .build();
    }
    public GetUserListRespDto toGetUserListRespDto(ResultSet rs) throws SQLException {
        return GetUserListRespDto.builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .createDt(rs.getTimestamp("create_dt").toLocalDateTime())
                .build();
    }
}
