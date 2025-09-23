package com.example.spring.bbs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 게시글 관련 요청을 처리하는 웹 컨트롤러 클래스
 * 사용자 요청을 받아 서비스 계층과 연결하고 뷰로 데이터를 전달함
 */
@Controller // Spring MVC에서 이 클래스가 컨트롤러임을 명시
public class BbsController {

    @Autowired // BbsService 객체를 자동으로 주입
    BbsService bbsService;

    /**
     * 게시글 목록 화면 요청 처리 (GET 방식)
     * @param model 뷰에 데이터를 전달하기 위한 객체
     * @return "bbs/list" 뷰 이름 (bbs/list.jsp)
     */
    @RequestMapping(value = "/bbses", method = RequestMethod.GET)
    public String listGet(Model model) {
        // 서비스 계층을 통해 게시글 목록을 가져옴
        List<BbsDto> bbses = bbsService.list();

        // "bbses"라는 이름으로 게시글 목록 데이터를 모델에 담아 뷰로 전달
        model.addAttribute("bbses", bbses);

        // bbs/list.jsp 의 화면을 렌더링
        return "bbs/list";
    }
}
