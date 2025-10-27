<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"% language="java" %>
<!DOCTYPE html>
<html lang="ko">
  <%@ include file="head.jsp" %>
  <body>
    <div class="container">
      <%-- 네비게이션 --%> <%@ include file="navbar.jsp" %> <%--// 네비게이션 --%> <%-- 페이지 제목 --%> <%@ include
      file="title.jsp" %> <%--// 페이지 제목 --%> <%-- 메시지 --%> <%@ include file="message.jsp" %> <%--// 메시지 --%>
      <%-- 페이지 내용 --%>
      <div class="row">
        <div class="col-12">페이지 내용</div>
      </div>
      <%--// 페이지 내용 --%>
    </div>

    <%-- 자바스크립트 --%> <%@ include file="script.jsp" %> <%--// 자바스크립트 --%>
  </body>
</html>
