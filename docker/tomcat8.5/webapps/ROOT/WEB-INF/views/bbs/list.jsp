<%@ page language="java" contentType="text/html; charset=UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
  <%@ include file="../base/head.jsp" %>
  <body>
    <div class="container">
      <%-- 네비게이션 --%> <%@ include file="../base/navbar.jsp" %> <%--// 네비게이션 --%> <%-- 페이지 제목 --%> <%@
      include file="../base/title.jsp" %> <%--// 페이지 제목 --%> <%-- 메시지 --%> <%@ include
      file="../base/message.jsp" %> <%--// 메시지 --%> <%-- 페이지 내용 --%>
      <div class="row">
        <div class="col-12">
          <%-- 게시글 등록 버튼 --%>
          <div class="mb-3">
            <a href="/bbses/create" class="btn btn-primary">등록</a>
          </div>
          <%--// 게시글 등록 버튼 --%> <%-- 게시글 목록 --%>
          <table class="table table-striped table-hover table-bordered">
            <thead>
              <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>생성일시</th>
                <th>수정일시</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${bbses}" var="bbs">
                <tr>
                  <td>${bbs.id}</td>
                  <td><a href="/bbses/${bbs.id}/" style="text-decoration: none">${bbs.title}</a></td>
                  <td>${bbs.username}</td>
                  <td><fmt:formatDate value="${bbs.createdAt}" pattern="yyyy-MM-dd HH:mm" /></td>
                  <td><fmt:formatDate value="${bbs.updatedAt}" pattern="yyyy-MM-dd HH:mm" /></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <%--// 게시글 목록 --%>
        </div>
      </div>
      <%--// 페이지 내용 --%>
    </div>

    <%-- 자바스크립트 --%> <%@ include file="../base/script.jsp" %> <%--// 자바스크립트 --%>
  </body>
</html>
