package com.korit.study3;

import com.korit.study2.dto.GetUserListRespDto;
import com.korit.study3.entity.User;
import com.korit.study3.dto.Board;
import com.korit.study3.service.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("[ 게시물 ]");
            System.out.println("1. 게시물 추가");
            System.out.println("2. 게시물 단건 조회");
            System.out.println("3. 전체 조회");
            System.out.println("4. 게시물 키워드 조회");
            System.out.println("5. 게시물 username 조회");
            System.out.println("q. 종료");
            System.out.print(">> ");
            String selectMenu = scanner.nextLine();
            if ("q".equalsIgnoreCase(selectMenu)) {
                System.out.println("프로그램 종료");
                break;
            } else if ("1".equals(selectMenu)) {
                System.out.println("[ 게시물 추가 ]");
                while (true) {
                    System.out.print("제목 : ");
                    board.setTitle(scanner.nextLine());
                    if (!userService.isValidDuplicatedTitle(board.getTitle())) {
                        break;
                    }
                    System.out.println("이미 사용중인 제목입니다.");
                }
                System.out.print("내용 : ");
                board.setContent(scanner.nextLine());
                System.out.print("닉네임 : ");
                board.setUsername(scanner.nextLine());
                userService.boardin(board);
            } else if ("2".equals(selectMenu)) {
                System.out.println("[ 단건 조회 ]");
                System.out.print("id 입력 : ");
                int userId = scanner.nextInt();
                scanner.nextLine();
                userService.id(userId);
            } else if ("3".equals(selectMenu)) {
                System.out.println("[ 전체 조회");
                List<User> userList = userService.getUserAllList();
                userList.forEach(System.out::println);
            } else if ("4".equals(selectMenu)) {
                System.out.println("게시물 키워드 조회");
                System.out.print("내용 검색 : ");
                String keyword = scanner.nextLine();
                List<User> userList = userService.getUserListKeyword(keyword);
                userList.forEach(System.out::println);
            } else if ("5".equals(selectMenu)) {
                System.out.println("[ 게시물 username 조회 ]");
                System.out.print("username 입력 : ");
                String username = scanner.nextLine();
                List<String> userList = userService.findByUsername(username);
                userList.forEach(System.out::println);
            }
        }
    }
}
