<%@page import="com.edu.springboot.member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 중복확인 팝업창으로 전달되는 아이디를 받는다.
String nickname = request.getParameter("nickname");
MemberDAO dao = new MemberDAO();

/*
musthave 계정의 regist_member 테이블에 입력한 아이디가 존재하는지 
확인한 후 페이지를 작성하세요. 
*/
//만약 중복된 아이디가 없어 사용할 수 있다면 true를 반환
//중복된 아이디가 있다면 false 반환
boolean isExist = dao.idOverlap(nickname);
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script> 
    //재입력한 아이디를 부모창으로 전송한다.
    function idUse(){
        //opener속성을 통해 부모창의 DOM을 선택할 수 있다. 
        opener.document.joinForm.nickname.value = document.overlapFrm.retype_nickname.value;
        self.close();
    }
</script>
</head>
<body>
    <h2>아이디 중복확인</h2>
    <div>
        입력한 닉네임 : <%= nickname %>
<%
if(isExist==true) {
%>
        <p>
        	입력한 아이디는 사용할 수 있습니다. <br />
        	<input type="button" value="아이디 사용하기" onclick="idUse();" />
        </p>
        <form name="overlapFrm">
            <input type="hidden" name="retype_nickname" value="<%=nickname %>" />
        </form>
<% 
} else {
%>
        <p>
            아이디가 중복되어 사용할 수 없습니다. <br>
            다시 검색해 주세요.
        </p>
        <form name="overlapFrm">
            <input type="text" name="nickname" size="20" />
            <input type="submit" value="아이디 중복확인" />
        </form>
<%
}
%>
    </div>
</body>
</html>