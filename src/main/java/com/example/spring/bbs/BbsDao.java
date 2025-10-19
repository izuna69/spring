package com.example.spring.bbs;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BbsDao {

    private static final Logger logger = LoggerFactory.getLogger(BbsDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 게시글 목록을 데이터베이스에서 조회하는 메서드
     * MyBatis 매퍼(bbsMapper.xml)의 list 구문을 호출하여 전체 게시글을 조회함
     *
     * @return 게시글(BbsDto) 리스트, 조회 실패 시 null 또는 빈 리스트 반환
     */
    public List<BbsDto> list() {
        List<BbsDto> bbses = null;

        try {
            bbses = sqlSessionTemplate.selectList("bbsMapper.list");
        } catch (DataAccessException e) {
            logger.error("게시글 목록 오류 : {}", e.getMessage(), e);
        }

        return bbses;
    }

    /**
     * 게시글을 데이터베이스에 저장하는 메서드 (MyBatis 기반)
     * @param bbs 사용자가 작성한 게시글 데이터
     * @return 삽입된 게시글 ID (성공 시 bbs.getId()에 자동 주입됨, 실패 시 -1)
     */
    public int create(BbsDto bbs) {
int result = -1;
        try {
            // MyBatis 매퍼의 bbsMapper.create 구문 실행
            // useGeneratedKeys="true"와 keyProperty="id"가 설정되어 있어 bbs.id에 자동으로 삽입된 ID가 주입됨
            result = sqlSessionTemplate.insert("bbsMapper.create", bbs);

        } catch (DataAccessException e) {
            logger.error("게시글 작성 오류 : {}", e.getMessage(), e);
        }
        return result;
    }



    /**
     * 게시글 ID를 기준으로 단건 조회하는 메서드
     * MyBatis 매퍼(bbsMapper.read)를 호출하여 게시글 1건을 조회함
     *
     * @param id 조회할 게시글의 ID
     * @return BbsDto 객체 (조회된 게시글), 실패 시 null 반환
     */
    public BbsDto read(int id) {
        BbsDto bbs = null;

        try {
            // bbsMapper.xml에 정의된 <select id="read"> 구문 실행
            bbs = sqlSessionTemplate.selectOne("bbsMapper.read", id);
        } catch (DataAccessException e) {
            // SQL 실행 중 예외 발생 시 로그 출력
            logger.error("게시글 보기 오류 : {}", e.getMessage(), e);
        }

        return bbs;
    }
        /**
     * 게시글을 수정하는 메서드
     * MyBatis 매퍼(bbsMapper.update)를 호출하여 게시글 정보를 DB에 반영함
     *
     * @param bbs 수정할 게시글 정보 (ID 포함)
     * @return 수정된 행 수 (성공 시 1, 실패 또는 오류 시 -1)
     */
    public int update(BbsDto bbs) {
        int result = -1;

        try {
            // bbsMapper.xml의 <update id="update"> 구문 실행
            result = sqlSessionTemplate.update("bbsMapper.update", bbs);
        } catch (DataAccessException e) {
            // SQL 실행 중 오류 발생 시 로그 출력
            logger.error("게시글 수정 오류 : {}", e.getMessage(), e);
        }

        return result;
    }
    /**
     * 게시글을 삭제하는 메서드
     * MyBatis 매퍼(bbsMapper.delete)를 호출하여 ID에 해당하는 게시글을 삭제함
     *
     * @param id 삭제할 게시글의 ID
     * @return 삭제된 행 수 (성공 시 1, 실패 또는 예외 시 -1)
     */
    public int delete(int id) {
        int result = -1;

        try {
            // bbsMapper.xml의 <delete id="delete"> 구문 실행
            result = sqlSessionTemplate.delete("bbsMapper.delete", id);
        } catch (DataAccessException e) {
            // 예외 발생 시 로그 출력
            logger.error("게시글 삭제 오류 : {}", e.getMessage(), e);
        }

        return result;
    }
}
