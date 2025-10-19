package com.example.spring.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 게시글 관련 요청을 처리하는 웹 컨트롤러 클래스
 * 사용자 요청을 받아 서비스 계층과 연결하고 뷰로 데이터를 전달함
 */
@Controller // Spring MVC에서 이 클래스가 컨트롤러임을 명시
@RequestMapping("/posts")
public class PostController {

    @Autowired // PostService 객체를 자동으로 주입
    PostService postService;

    /**
     * 게시글 목록 화면 요청 처리 (GET 방식)
     *
     * - 사용자가 검색 조건(searchType, searchKeyword)을 입력하면 해당 조건에 따라 게시글을 필터링
     * - 검색 조건이 없으면 전체 게시글을 조회
     * - 페이지 번호(page) 파라미터를 통해 해당 페이지의 게시글만 조회 (기본값은 1)
     * - 게시글 목록, 검색 조건, 페이지네이션 정보를 모델에 담아 뷰로 전달
     *
     * @param searchType 검색 기준 ("title", "content", "username", "all" 등), null 허용
     * @param searchKeyword 검색어, null 또는 빈 문자열 허용
     * @param currentPage 현재 페이지 번호 (기본값: 1)
     * @param model 뷰에 전달할 데이터를 담는 객체
     * @return 게시글 목록을 출력할 뷰 이름 ("post/list.jsp")
     */
    @GetMapping("")
    public String listGet(
        @RequestParam(required = false) String searchType,
        @RequestParam(required = false) String searchKeyword,
        @RequestParam(value = "page", defaultValue = "1") int currentPage,
        Model model
    ) {
        int listCountPerPage = 10;  // 한 페이지에서 불러올 게시글 수
        int pageCountPerPage = 5;   // 하단에 보여질 페이지 수 (예: [1][2][3][4][5])

        // 서비스 계층을 통해 게시글 목록 + 검색 조건 + 페이징 정보를 조회
        Map<String, Object> result = postService.list(
            currentPage, listCountPerPage, pageCountPerPage, searchType, searchKeyword
        );

        // 모델에 조회된 데이터 전달 (뷰에서 활용)
        model.addAttribute("posts", result.get("posts"));               // 게시글 목록
        model.addAttribute("pagination", result.get("pagination"));     // 페이지네이션 정보
        model.addAttribute("searchType", result.get("searchType"));     // 검색 기준
        model.addAttribute("searchKeyword", result.get("searchKeyword")); // 검색어

        // post/list.jsp 뷰 렌더링
        return "post/list";
    }

    /**
     * 게시글 등록 화면 요청 처리 (GET 방식)
     * 사용자가 글을 작성할 수 있는 입력 폼 화면을 보여줌
     * @return "post/create" 뷰 이름 (예: post/create.jsp)
     */
    @GetMapping("/create")
    public String createGet() {
        // 단순히 글쓰기 화면만 보여주는 기능이므로 별도의 데이터 전달 없음
        return "post/create";
    }

    /**
     * 게시글 등록 요청 처리 (POST 방식)
     * @param post 사용자가 작성한 게시글 정보(PostDto)
     * @param redirectAttributes 리다이렉트 시 전달할 메시지를 담는 객체
     * @return 등록 성공 시 글 보기로 리다이렉트, 실패 시 글쓰기 화면으로 이동
     */
    @PostMapping("/create")
    public String createPost(PostDto post, RedirectAttributes redirectAttributes) {
        // 서비스 계층을 통해 게시글 등록 처리
        int createdId = postService.create(post);

        if (createdId > 0) {
            // 등록 성공 시 메시지를 플래시 속성으로 전달하고 등록된 글 보기로 리다이렉트
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 등록되었습니다.");
            return "redirect:/posts/" + createdId;
        }

        // 등록 실패 시 에러 메시지를 플래시 속성으로 전달하고 글쓰기 화면으로 리다이렉트
        redirectAttributes.addFlashAttribute("errorMessage", "게시글 등록에 실패했습니다.");
        return "redirect:/posts/create";
    }

    /**
     * 게시글 상세보기 요청 처리 (GET 방식)
     * @param id 상세 조회할 게시글 ID
     * @param model 뷰에 전달할 게시글 데이터를 담는 객체
     * @return 상세보기 화면 뷰 이름 ("post/read.jsp")
     */
    @GetMapping("/{id}")
    public String readGet(@PathVariable("id") int id, Model model) {
        // 서비스 계층을 통해 게시글 ID에 해당하는 게시글 데이터 조회
        PostDto post = postService.read(id);

        // 조회한 게시글 데이터를 모델에 담아 뷰로 전달
        model.addAttribute("post", post);

        // 게시글 상세보기 화면 렌더링
        return "post/read";
    }

    /**
     * 게시글 수정 화면 요청 처리 (GET 방식)
     * @param id 수정할 게시글의 ID
     * @param model 수정할 게시글 데이터를 뷰로 전달하기 위한 모델 객체
     * @return "post/update" 뷰 이름 (예: post/update.jsp)
     */
    @GetMapping("/{id}/update")
    public String updateGet(@PathVariable("id") int id, Model model) {
        PostDto post = postService.read(id);
        model.addAttribute("post", post);
        return "post/update";
    }

    /**
     * 게시글 수정 요청 처리 (POST 방식)
     * @param id 수정할 게시글 ID
     * @param post 수정된 게시글 정보 (비밀번호 포함)
     * @param redirectAttributes 결과 메시지 전달용 객체
     * @return 수정 성공 시 상세 페이지로, 실패 시 수정 페이지로 리다이렉트
     */
    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable("id") int id, PostDto post, RedirectAttributes redirectAttributes) {
        // URL 경로에서 받은 ID를 post 객체에 설정
        post.setId(id);

        // 게시글 수정 처리
        if (postService.update(post)) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 수정되었습니다.");
            return "redirect:/posts/" + id;
        }

        // 실패 시 메시지 전달 후 수정 페이지로 이동
        redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정에 실패했습니다. (비밀번호 확인)");
        return "redirect:/posts/" + id + "/update";
    }

    /**
     * 게시글 삭제 요청 처리 (POST 방식)
     */
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") int id, PostDto post, RedirectAttributes redirectAttributes) {
        post.setId(id);
        boolean deleted = postService.delete(post);

        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 삭제되었습니다.");
            return "redirect:/posts";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패했습니다. (비밀번호 확인)");
        return "redirect:/posts/" + id;
    }
}
