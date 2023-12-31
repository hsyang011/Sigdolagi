<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>식도라기 - 글쓰기</title>
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <!-- 아이콘 -->
    <script src="https://kit.fontawesome.com/98401b861d.js" crossorigin="anonymous"></script>
    <!-- 전역 설정 css 링크  -->
    <%@ include file="../include/global_head.jsp" %>
    <link rel="stylesheet" href="../css/common_board.css">

    <style>

		
		/* 화면크기 최소 481px 부터 최대 660px까지 */
		@media screen and (min-width: 481px) and (max-width: 662px) {
		    .foot_logo{ display: none; }
		    #footer-nav li { margin: 0 10px; }
		    .container-fluid { line-height: 1.3; }
		    #row_footer{ text-align: center;}
		    #img_footer_icon{  display: none;}
		    .table { font-size: 12px;}
		    .table thead th,.table tbody td { white-space: nowrap;}
		    #table_wrap table {width: auto;}
		     #content {width: 100%;}
		     .cm_input textarea {width: 90%;}
		    .cm_input .btns {width: 100%; height: 50px; margin-bottom: 2px; background-color:  #FF7A00; border:none;}
		}
		
		@media screen and (min-width:300px) and (max-width:480px) {
		    .foot_logo{ display: none; }
		    #mobile_navbar { font-size: 0.60em; }
		    /* #footer-nav li a { font-size: 0.5em; } */
		    #footer-nav li { margin: 0; }
		    .container-fluid { line-height: 1.3; }
		    #row_footer{ text-align: center;}
		    #img_footer_icon{  display: none;}
		    .catemenu li {font-size: 18px;}
		 
		    #write_frm_table tr:first-child {width: 100%;}
		    .table { font-size: 12px; /* 테이블 내 글자 크기를 줄임 */}
		    .table thead th,.table tbody td { white-space: nowrap; /* 테이블 셀 내의 글자가 줄지 않고 잘리지 않도록 함 */ }
		    #table_wrap table tbody tr td { padding-left: 0;padding-right: 0;}
		    #content {width: 100%;}
		    .cm_input textarea {width: 90%;}
		    .cm_input .btns {width: 100%; height: 50px; margin-bottom: 2px; background-color:  #FF7A00; border:none;}
		    .searchWord_list{height: 40px; border: 1px solid #FF7A00; font-family: 'NPSfontRegular'; }
		    .searchField_list{ height: 40px;  border: 1px solid #FF7A00;font-family: 'NPSfontRegular';}
		    #free_write_frm_table tr td{padding-left: 0; padding-right: 0; }
		    #free_write_frm_table tr td:first-child {font-size: 15px; padding: 5px 10px;}
		    .faq_searchWord_list{  height: 50px; width: 60%;  border: 1px solid #FF7A00;font-family: 'NPSfontRegular';}
		    .btn_td button{margin: 0 5px 5px 0; border-radius: 20px;  padding: 0px 10px 0 10px; height: 30px; font-size: 15px;}
		}
        /*main nav_location 설정*/
        .nav_locat {font-family: 'NPSfontRegular'; margin-left: -10px;}
        .nav_locat li a{color: #FF7A00;}
        .nav_locat li a:hover{color: #484848;}
        .nav_locat li:first-child::before {            
            /* 첫번째 list에 적용 */
            content: " ";
        }
        .nav_locat li::before {   
            /* 두번째 list 앞에 적용 */
            content: ">";
            float: left;
            color: #ccc;
            margin-top: 9px;
            margin-left: -3px;
        }

        /* 쇼핑몰 카테고리 */
        .catemenu {font-size: 1.6em; padding: 0; margin: 20px 0;}
        .catemenu li{border-bottom: 3px solid #999999;  padding:10px; margin-right: 10px;}
        .catemenu a{color:gray;  padding-bottom: 5px;}
        .catemenu .on {border-bottom: 3px solid #FF7A00;}
        .mealk_cate {background-color: #dadada; color: white;}
        .category .on {background-color: #FF7A00; color: white;}

        /* main seach바 */
        .meal_seach_bar { border: 3px solid #FF7A00; border-radius: 30px; background-color: white; padding: 5px; margin: 50px auto; width: 40%;}
        .meal_seach_bar input{ border: 0px solid white; width: 100%; height: 40px; }
        .meal_seach_bar input::placeholder{ font-family: 'NPSfontRegular'; text-align: center; margin-left: -75px; } /* 검색하기 텍스트 중앙 정렬 맞추기 위해 margin-left로 맞춤 */
        .meal_seach_bar button{ border: 0px solid white; background-color: white; border-radius: 50px;}
        .meal_seach_bar i { color: #FF7A00; margin-right: 5px; width: 60px;}

        /* main 설정 */
        main > * { margin: 50px 0; }

        /* 12/01 수정사항 - 한서 */
        @media screen and (min-width: 1201px) {
            .custom-col { flex: 0 0 25%; }
        }
        /* 모바일 환경에서 카테고리를 횡스크롤로 구현 */
        @media screen and (max-width: 1200px) {
            .category { overflow: auto; white-space: nowrap; flex-wrap: nowrap; } /* 횡스크롤 구현 */
            .category::-webkit-scrollbar { display: none; } /* 크롬, 사파리, 오페라, 엣지에서 스크롤바 안보이게 처리 */
            .custom-col { flex: 0 0 25%; }
            .meal_seach_bar { width: 100%; } /* 모바일에선 검색창의 길이를 100%로 처리 */
        }
        @media screen and (max-width: 768px) {
            .custom-col { flex: 0 0 50%; }
            .mealk_cate {font-size: 0.8em;}/* 밀키트 카테고리 사이즈 */
        }
        
    #deleteFileBtn {
        display: none;
    }

    #thumbnail {
        display: none; /* 초기에 숨김 */
        max-width: 100%; /* 이미지 크기를 부모 요소에 맞게 조절 */
        height: auto;
    }
    </style>

   <script>
        $(document).ready(function () {
            // (기존 코드 중 일부 생략)

            // 파일 선택 시 미리보기 기능 추가
             $("#ofile").change(function () {
              readURL(this);
              $("#deleteFileBtn").show(); // 파일을 선택하면 버튼을 보이도록 함
              $("#thumbnail").show();
          });
            
            
            
            $("#deleteFileBtn").click(function () {
                // 파일 삭제를 서버에 요청
                $.ajax({
                    url: "/community/deleteFile.do",
                    type: "POST",
                    data: { fileName: $("#ofile").val() },  // 파일명을 서버에 전달
                    success: function (data) {
                        // 성공적으로 파일 삭제된 경우
                        $("#ofile").val("");
                         $('#thumbnail').attr('src', '#');
                         $("#thumbnail").hide(); // 파일 삭제 후 버튼을 다시 숨김
                         console.log("파일 삭제 성공");
                    },
                    error: function (e) {
                        console.log("파일 삭제 실패: " + e.responseText);
                    }
                });
            });
            
            
        });

        
        // 파일 선택 시 미리보기 함수
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#thumbnail').attr('src', e.target.result);
                }

                reader.readAsDataURL(input.files[0]);
            }
        }
       
        
        
    </script>
    
    
    <script>
   function validateForm(form){
      if(form.title.value==""){
         alert("제목을 입력하세요");
         form.title.focus();
         return false;
      }
      
      
      if(form.content.value==""){
         alert("내용은 필수 입력입니다.");
         return false;
      }
      
      if(form.ofile.value==""){
         alert("첨부파일은 필수 입력입니다.");
         return false;
      }
      
      
   }
</script>
    
    
    
</head>
<body>
    <!-- wrapper 시작 -->
    <div class="container-fluid" id="wrap">

        <!-- header, nav 추가 -->
        <%@ include file="../include/top.jsp" %>
        
        <br><br><br>
        <!-- 배너 시작 -->
        <div id="banner" class="mt-3">
            <div id="banner_contents" class="container d-flex align-items-center">
                <div id="info">
                    <h4>자유롭게 소통하세요</h4>
                    <div id="info_title" class="d-flex">
                        <h2>커뮤니티</h2>
                    </div>
                </div>
                <img id="page_icon" src="../images/com.png">
            </div>
        </div>
        <!-- 배너 끝 -->
        <!-- main 시작 -->
        <main>
            <!-- 컨테이너 안쪽 컨텐츠 -->
            <div class="container mt-3">
                <!-- 컨텐츠 헤더 -->
                <div class="top_title">
                    <!-- 네비로케이션 -->
                    <div class="nav_location">
                        <ul class="nav_locat nav">
                            <li class="nav-item">
                                <a class="nav-link" href="#">홈</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">커뮤니티</a>
                            </li>
                        </ul>
                    </div>
                    <!-- 네비로케이션 끝 -->
                    <!-- 헤더제목 -->
                    <ul class="catemenu d-flex">
                        <li><a href="./freeboard_list.do">자유게시판</a></li>
                        <li class="on"><a href="./photoboard_list.do">사진게시판</a></li>
                    </ul>
                </div>
                <!-- 컨텐츠 헤더 끝 -->
                <div class="board_write container mt-5" >
                    <!-- 글쓰기 폼 -->
                    <div class="container">
                        <div class="freeboard_write_frm" >
                            <!-- 게시판 들어가는 부분 (시작) -->
                            <form name="writeFrm" method="post" onsubmit="return validateForm(this);" 
                                class="writeFrm" enctype="multipart/form-data"
                                action="/community/photoboard_writeprocess.do">
                                <input type="hidden" name="tname"  />
                                <table class="table table-bordered" id="free_write_frm_table" width="100%" >
                                    <tr>
                                        <td>제목</td>
                                        <td>
                                            <input type="text" name="title" id="title" />
                                            <input type="hidden" name= "email" id="email" value="${email}">
                                            <input type="hidden" name="nickname" id="nickname"  value="${nickname}" />
                                        </td>
                                    </tr>
                                    
                                    <tr>
                                        <td>내용</td>
                                        <td>
                                             <textarea id="content" name="content"  ></textarea> 
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>파일첨부</td>
                                        <td>
                                            <input type="file" id="ofile" name="ofile" onchange="readURL(this)">
                                            <img id="thumbnail" src="#" alt="미리보기" />
                                             <button type="button" id="deleteFileBtn">파일 삭제</button>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center" class="btn_td">
                                            <button type="submit" class="writeFrm_end" id="saveBtn">작성 완료</button>
                                            <button type="reset" class="writeFrm_reset">다시 입력</button>
                                            <button type="button" class="writeFrm_list">목록 보기</button>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!-- main 끝 -->
        
        <!-- footer 추가 -->
        <%@ include file="../include/footer.jsp" %>
        
    </div>
    <!-- wrapper 끝 -->
</body>
</html>