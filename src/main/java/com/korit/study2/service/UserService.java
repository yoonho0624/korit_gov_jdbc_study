package com.korit.study2.service;

import com.korit.study2.dao.UserDao;
import com.korit.study2.dto.GetUserListRespDto;
import com.korit.study2.dto.SigninReqDto;
import com.korit.study2.dto.SignupReqDto;
import com.korit.study2.entity.User;
import com.korit.study2.util.ConnectionFactory;
import com.korit.study2.util.PasswordEncoder;

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

    // username 중복확인
    public boolean isValidDuplicatedUsername(String username) {
        Optional<User> foundUser = userDao.findUserByUsername(username);
        return foundUser.isPresent();
    }
    // email 중복확인
    public boolean isValidDuplicatedEmail(String email) {
        Optional<User> foundUser = userDao.findUserByEmail(email);
        return foundUser.isPresent();
    }
    // 회원가입
    public void signup(SignupReqDto signupReqDto) {
        userDao.addUser(signupReqDto.toEntity());
    }

    public boolean isEmpty(String str) {
        if (str == null || str.isBlank()) {
            return true;
        }
        return false;
    }
    // 로그인
    public void signin(SigninReqDto signinReqDto) {
        Optional<User> foundUser = userDao.findUserByUsername(signinReqDto.getUsername());
        User user = foundUser.get();
        if (foundUser == null) {
            System.out.println("사용자 정보를 다시 확인하세요.");
        }
        if (!PasswordEncoder.match(signinReqDto.getPassword(), user.getPassword())) {
            System.out.println("사용자 정보를 다시 확인하세요.");
        }
        System.out.println("로그인 성공");
        System.out.println(foundUser);
    }
    // 회원 전체 조회
    public List<GetUserListRespDto> getUserList() {
        String sql = "select user_id, username, email, create_dt from user2_tb";
        List<GetUserListRespDto> userList = new ArrayList<>();
        try(Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                userList.add(userDao.toGetUserListRespDto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    // 회원 검색
    public List<GetUserListRespDto> getUserListKeyword(String keyword) {
        return userDao.getUserListKeyword(keyword);
    }
}
