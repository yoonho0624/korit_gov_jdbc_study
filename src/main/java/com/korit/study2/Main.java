package com.korit.study2;

import com.korit.study2.dto.GetUserListRespDto;
import com.korit.study2.dto.SigninReqDto;
import com.korit.study2.dto.SignupReqDto;
import com.korit.study2.entity.User;
import com.korit.study2.service.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("[ 회원관리 ]");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 전체회원 조회");
            System.out.println("4. 회원 검색");
            System.out.println("q. 종료");
            System.out.print(">> ");
            String selectMenu = scanner.nextLine();
            if ("q".equalsIgnoreCase(selectMenu)) {
                System.out.println("프로그램 종료");
                break;
            } else if ("1".equals(selectMenu)) {
                System.out.println("[ 회원가입 ]");
                SignupReqDto signupReqDto = new SignupReqDto();
                while (true) {
                    System.out.print("Username: ");
                    signupReqDto.setUsername(scanner.nextLine());
                    //아이디 중복확인
                    if (!userService.isValidDuplicatedUsername(signupReqDto.getUsername())) {
                        break;
                    }
                    System.out.println("이미 사용중인 Username입니다.");
                }
                System.out.print("비밀번호: ");
                signupReqDto.setPassword(scanner.nextLine());
                while (true) {
                    System.out.print("email: ");
                    signupReqDto.setEmail(scanner.nextLine());
                    //이메일 중복확인
                    if (!userService.isValidDuplicatedEmail(signupReqDto.getEmail())) {
                        break;
                    }
                    System.out.println("이미 사용중인 Email입니다.");
                }
                userService.signup(signupReqDto);
                System.out.println("[[ 회원가입 완료 ]]");
                // todo : 회원가입 메소드 호출
            } else if ("2".equals(selectMenu)) {
                System.out.println("[ 로그인 ]");
                SigninReqDto signinReqDto = new SigninReqDto();
                while (true) {
                    System.out.print("Username: ");
                    signinReqDto.setUsername(scanner.nextLine());
                    if (!userService.isEmpty(signinReqDto.getUsername())) {
                        break;
                    }
                    System.out.println("다시 입력하세요.");
                }
                while (true) {
                    System.out.print("Password: ");
                    signinReqDto.setPassword(scanner.nextLine());
                    if (!userService.isEmpty(signinReqDto.getPassword())) {
                        break;
                    }
                    System.out.println("다시 입력하세요.");
                }
                //유효성 검사 완료 후 signin() 호출
                userService.signin(signinReqDto);
                // todo : 로그인 메소드 호출
            } else if ("3".equals(selectMenu)) {
                System.out.println("[ 전체회원 조회");
                List<GetUserListRespDto> getUserListRespDto = userService.getUserList();
                getUserListRespDto.forEach(System.out::println);
                // todo : 전체회원 조회 메소드 호출
            } else if ("4".equals(selectMenu)) {
                System.out.println("[ 회원 검색 ]");
                GetUserListRespDto getUserListRespDto = new GetUserListRespDto();
                while (true) {
                    System.out.print("회원 검색 : ");
                    getUserListRespDto.setUsername(scanner.nextLine());
                    if (!userService.isEmpty(getUserListRespDto.getUsername())) {
                        break;
                    }
                    System.out.println("다시 입력하세요.");
                }
                userService.searchUser(getUserListRespDto);
                // todo : 회원 검색 메소드 호출
            }
        }
    }
}
