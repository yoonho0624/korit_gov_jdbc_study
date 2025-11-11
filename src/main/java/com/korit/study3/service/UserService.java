package com.korit.study3.service;

import com.korit.study2.dto.GetUserListRespDto;
import com.korit.study2.util.ConnectionFactory;
import com.korit.study3.dao.UserDao;
import com.korit.study3.dto.Board;
import com.korit.study3.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static UserService instance;
    private UserDao userDao;
    private UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    public static UserService getInstance() {
        if (instance == null) instance = new UserService(UserDao.getInstance());
        return instance;
    }

    public boolean isValidDuplicatedTitle(String title) {
        Optional<User> user = userDao.findByTitle(title);
        return user.isPresent();
    }

    public void boardin(Board board) {
        userDao.addUser(board.toEntity());
    }

    public void id(int id) {
        User foundUser = userDao.findById(id);
        System.out.println(foundUser);
    }

    public List<User> getUserAllList() {
        String sql = "select user_id, title, content, username, create_dt from board";
        List<User> userList = new ArrayList<>();
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                userList.add(userDao.toUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public List<String> findByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
    public List<User> getUserListKeyword(String keyword) {
        return userDao.toUserListKeyword(keyword);
    }
}
