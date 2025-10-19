package com.example.spring.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostDao {

    private static final Logger logger = LoggerFactory.getLogger(PostDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 게시글 목록을 조회하는 메서드 (검색 기능 포함)
     * 검색 조건이 주어지면 해당 조건(title, content, username)에 따라 필터링된 결과를 반환하고,
     * 검색 조건이 없으면 전체 게시글을 조회함
     *
     * @param searchType 검색 유형 ("title", "content", "username" 중 하나)
     * @param searchKeyword 검색어 (빈 문자열 또는 null이면 전체 조회)
     * @return 게시글(PostDto) 리스트, 조회 실패 시 null 또는 빈 리스트 반환
     */
    public List<PostDto> list(String searchType, String searchKeyword) {
        Map<String, Object> params = new HashMap<>();
        params.put("searchType", searchType);
        params.put("searchKeyword", searchKeyword);

        List<PostDto> posts = null;

        try {
            // MyBatis 매퍼(postMapper.xml)의 list 쿼리를 실행하여 게시글 목록을 조회함
            posts = sqlSessionTemplate.selectList("postMapper.list", params);
        } catch (DataAccessException e) {
            // 데이터 조회 중 오류가 발생한 경우 로그 출력
            logger.error("게시글 목록 오류 : {}", e.getMessage(), e);
        }

        return posts;
    }

    /**
     * 게시글을 데이터베이스에 저장하는 메서드 (MyBatis 기반)
     * @param post 사용자가 작성한 게시글 데이터
     * @return 삽입된 게시글 ID (성공 시 post.getId()에 자동 주입됨, 실패 시 -1)
     */
    public int create(PostDto post) {
int result = -1;
        try {
            // MyBatis 매퍼의 postMapper.create 구문 실행
            // useGeneratedKeys="true"와 keyProperty="id"가 설정되어 있어 post.id에 자동으로 삽입된 ID가 주입됨
            result = sqlSessionTemplate.insert("postMapper.create", post);

        } catch (DataAccessException e) {
            logger.error("게시글 작성 오류 : {}", e.getMessage(), e);
        }
        return result;
    }



    /**
     * 게시글 ID를 기준으로 단건 조회하는 메서드
     * MyBatis 매퍼(postMapper.read)를 호출하여 게시글 1건을 조회함
     *
     * @param id 조회할 게시글의 ID
     * @return PostDto 객체 (조회된 게시글), 실패 시 null 반환
     */
    public PostDto read(int id) {
        PostDto post = null;

        try {
            // postMapper.xml에 정의된 <select id="read"> 구문 실행
            post = sqlSessionTemplate.selectOne("postMapper.read", id);
        } catch (DataAccessException e) {
            // SQL 실행 중 예외 발생 시 로그 출력
            logger.error("게시글 보기 오류 : {}", e.getMessage(), e);
        }

        return post;
    }
        /**
     * 게시글을 수정하는 메서드
     * MyBatis 매퍼(postMapper.update)를 호출하여 게시글 정보를 DB에 반영함
     *
     * @param post 수정할 게시글 정보 (ID 포함)
     * @return 수정된 행 수 (성공 시 1, 실패 또는 오류 시 -1)
     */
    public int update(PostDto post) {
        int result = -1;

        try {
            // postMapper.xml의 <update id="update"> 구문 실행
            result = sqlSessionTemplate.update("postMapper.update", post);
        } catch (DataAccessException e) {
            // SQL 실행 중 오류 발생 시 로그 출력
            logger.error("게시글 수정 오류 : {}", e.getMessage(), e);
        }

        return result;
    }
    /**
     * 게시글을 삭제하는 메서드
     * MyBatis 매퍼(postMapper.delete)를 호출하여 ID에 해당하는 게시글을 삭제함
     *
     * @param id 삭제할 게시글의 ID
     * @return 삭제된 행 수 (성공 시 1, 실패 또는 예외 시 -1)
     */
    public int delete(int id) {
        int result = -1;

        try {
            // postMapper.xml의 <delete id="delete"> 구문 실행
            result = sqlSessionTemplate.delete("postMapper.delete", id);
        } catch (DataAccessException e) {
            // 예외 발생 시 로그 출력
            logger.error("게시글 삭제 오류 : {}", e.getMessage(), e);
        }

        return result;
    }
}
