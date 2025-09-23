package com.example.spring.post;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component // 해당 클래스가 Spring의 Bean으로 등록되도록 지정
public class PostDao {

    // 로그 출력을 위한 Logger 객체 생성
    private static final Logger logger = LoggerFactory.getLogger(PostDao.class);

    @Autowired // Spring이 JdbcTemplate 객체를 자동으로 주입
    JdbcTemplate jdbcTemplate;

    /**
     * 게시글 목록을 데이터베이스에서 조회하는 메서드
     * @return 게시글(PostDto) 리스트
     */
    public List<PostDto> list() {
        // 게시글을 ID 내림차순으로 정렬하여 전체 조회하는 SQL 쿼리
        String query = "SELECT id, title, content, username, password, created_at, updated_at FROM post ORDER BY id DESC";

        List<PostDto> posts = null;

        try {
            // 쿼리 실행 후 결과를 PostDto 객체 목록으로 매핑
            posts = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PostDto.class));
        } catch (DataAccessException e) {
            // 데이터베이스 조회 중 오류 발생 시 로그 출력
            logger.error("게시글 목록 오류 : {}", e.getMessage(), e);
        }

        return posts;
    }

    /**
     * 게시글을 데이터베이스에 저장하는 메서드
     * @param post 사용자가 작성한 게시글 데이터
     * @return 삽입된 행 수 (성공 시 1, 실패 시 -1)
     */
    public int create(PostDto post) {
        // 게시글의 제목, 내용, 작성자, 비밀번호만 저장하며, 작성일시는 DB에서 자동 처리
        String query = "INSERT INTO post (title, content, username, password) VALUES (?, ?, ?, ?)";
        int result = -1;

        try {
            // 쿼리 실행 후 삽입 결과 행 수 반환
            result = jdbcTemplate.update(query,
                    post.getTitle(),
                    post.getContent(),
                    post.getUsername(),
                    post.getPassword());
        } catch (DataAccessException e) {
            // 예외 발생 시 로그 출력
            logger.error("게시글 등록 오류 : {}", e.getMessage(), e);
        }

        return result;
    }

    /**
     * 게시글 ID를 기준으로 게시글을 조회하는 메서드
     * @param id 조회할 게시글의 ID
     * @return 게시글 정보(PostDto), 조회 실패 시 null 반환
     */
    public PostDto read(int id) {
        // 게시글 단건 조회 SQL 쿼리
        String query = "SELECT id, title, content, username, password, created_at, updated_at FROM post WHERE id = ? LIMIT 1";

        PostDto post = null;

        try {
            // ID에 해당하는 게시글 조회 후 PostDto 객체로 반환
            post = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(PostDto.class), id);
        } catch (DataAccessException e) {
            // 예외 발생 시 로그 출력
            logger.error("게시글 조회 오류 (ID: {}): {}", id, e.getMessage(), e);
        }

        return post;
    }
}
