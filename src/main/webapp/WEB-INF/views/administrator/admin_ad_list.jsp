<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<title>관리자 AD목록</title>
	
    <!-- Custom fonts for this template -->
    <link href="../bootstrap/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css" />
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="../bootstrap/css/sb-admin-2.min.css" rel="stylesheet" />

    <!-- Custom styles for this page -->
    <link href="../bootstrap/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet" />
</head>
<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

		<!-- 사이드메뉴 추가 -->
        <%@ include file="../include/admin_leftmenu.jsp" %>

        <!-- 메인 Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- 탑네비 추가 -->
                <%@ include file="../include/admin_topnav.jsp" %>

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">AD목록</h1>
                    <p class="mb-4">
                    	AD목록 관리하는 곳 입니다.
                    </p>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3" style="text-align: right;">
                            <a class="btn btn-primary float-end" href="../administrator/admin_maket_write.do">
                                <i class="fas fa-edit"></i> 광고 작성
                            </a>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
	                                <colgroup>
	                                	<col width="8%" /><col width="8%" /><col width="30%" /><col width="10%" /><col width="10%" />
	                                	<col width="10%" /><col width="10%" /><col width="10%" />
	                                </colgroup>
                                    <thead>
                                        <tr>
                                            <th>번호</th>
                                            <th>분류</th>
                                            <th>제목(루트)</th>
                                            <th>닉네임</th>
                                            <th>작성일</th>
                                            <th>조회수</th>
                                            <th>대표이미지</th>
                                            <th>삭제</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>번호</th>
                                            <th>분류</th>
                                            <th>제목(루트)</th>
                                            <th>닉네임</th>
                                            <th>작성일</th>
                                            <th>조회수</th>
                                            <th>대표이미지</th>
                                            <th>삭제</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>AD</td>
                                            <td><a href="">천지연 폭포의 아름다움!</a></td>
                                            <td>서귀포시</td>
                                            <td>2011/04/25</td>
                                            <td>0</td>
                                            <td>
                                                <img src="../images/1572507528895QeeKZ94w4s.jpg" alt="" width="100px" height="100px">
                                            </td>
                                            <td>
                                            	<a href="#" class="btn btn-warning btn-icon-split">
                                                    <span class="text">수정</span>
                                                </a>
                                            	<a href="#" class="btn btn-danger btn-icon-split">
                                                    <span class="text">삭제</span>
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>플래너</td>
                                            <td><a href="">커피를 즐기세요!</a></td>
                                            <td>낭만커피</td>
                                            <td>2023/04/25</td>
                                            <td>36</td>
                                            <td>
                                                <img src="../images/750_750_20210307074729929_photo_2c2906c7be9f.jpg" alt="" width="100px" height="100px">
                                            </td>
                                            <td>
                                            	<a href="#" class="btn btn-warning btn-icon-split">
                                                    <span class="text">수정</span>
                                                </a>
                                            	<a href="#" class="btn btn-danger btn-icon-split">
                                                    <span class="text">삭제</span>
                                                </a>
                                            </td>
                                        </tr>
                                        
                                       
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

			<!-- footer 추가 -->
            <%@ include file="../include/admin_footer.jsp" %>

        </div>
        <!-- 메인 Wrapper 끝 -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- madal,scroll button 추가 -->
    <%@ include file="../include/admin_modal.jsp" %>

    <!-- Bootstrap core JavaScript-->
    <script src="../bootstrap/vendor/jquery/jquery.min.js"></script>
    <script src="../bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="../bootstrap/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="../bootstrap/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="../bootstrap/vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="../bootstrap/vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="../bootstrap/js/demo/datatables-demo.js"></script>

</body>
</html>