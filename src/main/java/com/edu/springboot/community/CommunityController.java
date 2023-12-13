package com.edu.springboot.community;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;import org.yaml.snakeyaml.comments.CommentLine;

import com.edu.springboot.community.BoardDTO;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import utils.MyFunctions;
import utils.PagingUtil;





@Controller
public class CommunityController {
   
	
	
	@RequestMapping("/community/photoboard_comment.do")
	@ResponseBody
	public CommentsDTO boardCommentPost(Model model, HttpServletRequest req, 
			CommentsDTO commentsDTO, PhotoBoardDTO photoBoardDTO) {
		
		
		
		
		String photoboard_idx = commentsDTO.getPhotoboard_idx();
        String content = commentsDTO.getContent();
        String email = commentsDTO.getEmail();

        System.out.println(photoboard_idx);
        System.out.println(content);
        System.out.println(email);

        int result = photoboarddao.writeConmments(photoboard_idx, content, email);

        System.out.println("성공?");
        System.out.println(commentsDTO);
        System.out.println("글쓰기결과:" + result);
        
        
     // 코멘트 테이블 전부다  얻어와서 저장하기  
		ArrayList<CommentsDTO> commentsLists = photoboarddao.CommentsPage(commentsDTO);
		//빈에 저장
		
		
		System.out.println("댓글 디비에 있는거 가저오는거 성공?");
		System.out.println(commentsLists);
		
		
		model.addAttribute("CommentsLists", commentsLists);
		model.addAttribute("photoBoardDTO",photoBoardDTO);
		model.addAttribute("result", result);
        
		
		//하나의 댓글 테이블 가져오기 
//		commentsDTO = 
//		memberDTO = memberdao.getoneMemberDTO(memberDTO);
		
        return commentsDTO;
        
	        
	        
//		JsonObject jsonObject = new JsonObject();
//		ArrayList<CommentsDTO> commentsLists = null;
//		try {
//	        System.out.println("댓글 추가 컨트롤러 들어오나? ");
//	        
//	        
//	        
//	        String photoboard_idx = commentsDTO.getPhotoboard_idx();
//	        String content = commentsDTO.getContent();
//	        String email = commentsDTO.getEmail();
//
//	        System.out.println(photoboard_idx);
//	        System.out.println(content);
//	        System.out.println(email);
//
//	        int result = photoboarddao.writeConmments(photoboard_idx, content, email);
//
//	        System.out.println("성공?");
//	        System.out.println(commentsDTO);
//	        System.out.println("글쓰기결과:" + result);
//	        
        
//	        
//	        // 코멘트 테이블 전부다 얻어와서 저장하기
//	        commentsLists = photoboarddao.CommentsPage(commentsDTO);
//	        // 빈에 저장
//
//	        System.out.println("댓글 디비에 있는거 가져오는거 성공?");
//	        System.out.println(commentsLists);
//
//	        model.addAttribute("commentsLists", commentsLists);
//	        model.addAttribute("result", result);
//
//	        
//	       
//	        
//	        // 비동기로 댓글 추가 후 응답
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	    
//	    String a = jsonObject.toString();
//        return ResponseEntity.ok(commentsLists); 
	   
	    
	}


	
	
	
	
	
   @Autowired
   IMyFileService filedao;
   
   
   
   @Autowired
   IBoardService dao;
   
   @Autowired
   IPhotoboardService photoboarddao;

   
   @RequestMapping("/community/freeboard_list.do")
   public String freeboardList(Model model, HttpServletRequest req, ParameterDTO parameterDTO, HttpSession httpSession) {
      
      int totalCount = dao.getTotalCount(parameterDTO);
      
      int pageSize = PagingUtil.getPageSize(); 
      int blockPage = PagingUtil.getBlockPage();
      
      int pageNum = (req.getParameter("pageNum")==null || req.getParameter("pageNum").equals("")) ? 1 : Integer.parseInt(req.getParameter("pageNum"));
      int start = (pageNum -1 ) * pageSize +1 ;
      int end = pageNum * pageSize;
      parameterDTO.setStart(start);
      parameterDTO.setEnd(end);
      
      
      
      
      Map<String, Object> maps = new HashMap<String, Object>();
      maps.put("totalCount", totalCount);
      maps.put("pageSize", pageSize);
      maps.put("pageNum", pageNum);
      model.addAttribute("maps", maps);
         
      ArrayList<BoardDTO> lists = dao.listPage(parameterDTO);
      model.addAttribute("lists", lists);
      System.out.println(lists.size());
      
      String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath()+"./freeboard_list.do?");
      model.addAttribute("pagingImg", pagingImg);
      return "community/freeboard_list";
   }
   
   
   
   
   //글쓰기 페이지 로딩
   @GetMapping("/community/freeboard_write.do")
   public String boardWriteGet(Model model) {
      
      
      
      return "community/freeboard_write";
   }
   
   
   
   
   
   @PostMapping("/community/freeboard_write.do")


   public String freeboardWrite(Model model, HttpServletRequest req, Principal principal) {
      String email= principal.getName();
      String nickname= dao.getnickname(email);
      String title= req.getParameter("title");
      String content= req.getParameter("content");
      //폼값을 개별적으로 전달한다.
      int result = dao.write(email,nickname,title, content);
      System.out.println("글쓰기 결과:" +result);
      System.out.println("nickname:결과"+nickname);
      model.addAttribute("nickname1",nickname); 

      return "redirect:freeboard_list.do";
   }
   
//   @PostMapping("/community/freeboard_comment_write.do")
//
//   public String freeboardCommetWrite(Model model, HttpServletRequest req, Principal principal) {
//      String nickname= req.getParameter("nickname");
//      String comment_idx = req.getParameter("comment_idx");
//      String content= req.getParameter("content");
//      //폼값을 개별적으로 전달한다.
//      int com_result = commentdao.commentInsert(comment_idx,nickname,content);
//      System.out.println("글쓰기 결과:" +com_result);
//      
//      model.addAttribute("nickname",principal.getName()); 
//      
//      return "redirect:freeboard_view.do";
//   }
   
   @RequestMapping("/community/freeboard_view.do")
   public String freeboardView(Model model, BoardDTO boardDTO,HttpServletRequest req,ParameterDTO parameterDTO) {
      dao.update(boardDTO);
      boardDTO = dao.view(boardDTO);
      boardDTO.setContent(boardDTO.getContent().replace("\r\n", "<br>"));

      model.addAttribute("boardDTO", boardDTO);

      return "community/freeboard_view";
   }

   

   
   //자유게시판 수정하기(겟)
   @GetMapping("/community/freeboard_edit.do")
   public String freeboardEdit(Model model, BoardDTO boardDTO, Principal principal) {
      System.out.println("들어오니?");
      boardDTO = dao.view(boardDTO);
      model.addAttribute("boardDTO", boardDTO);
      return "community/freeboard_edit";
   
   }
   
   //자유게시판 수정하기(포스트)
   @PostMapping("/community/freeboard_edit.do")
   public String boardEditPost(BoardDTO boardDTO, Principal principal) {
      int result = dao.edit(boardDTO);
      System.out.println("result:"+result);
      return "redirect:freeboard_view.do?freeboard_idx="+boardDTO.getFreeboard_idx();
   }
   
   
   @PostMapping("/community/freeboard_delete.do")
   public String boardDeletePost(HttpServletRequest req,Principal principal) {
      int result = dao.delete(req.getParameter("freeboard_idx"));
      System.out.println("글삭제결과:"+result);
      
      return "redirect:freeboard_list.do";
   }

   
   
   
   
   
   
   
   
   
   
   
   
   
   @RequestMapping("/community/photoboard_list.do")
      //포토  포토보드 리스트
      
   public String photoBoardList(Model model, HttpServletRequest req, ParameterDTO parameterDTO, Principal principal) {
   
      System.out.println("들어오나?");
      
      int totalCount = photoboarddao.photoGetTotalCount(parameterDTO);
      System.out.println("토탈카운트"+totalCount);
      
      int pageSize = PagingUtil.getPageSize(); 
      System.out.println(pageSize);
      int blockPage = PagingUtil.getBlockPage(); 
      System.out.println(blockPage);
      
      int pageNum = (req.getParameter("pageNum")==null || req.getParameter("pageNum").equals("")) ? 1 : Integer.parseInt(req.getParameter("pageNum"));
      int start = (pageNum -1 ) * pageSize +1 ;
      int end = pageNum * pageSize;
      parameterDTO.setStart(start);
      parameterDTO.setEnd(end);
      
      
      Map<String, Object> maps = new HashMap<String, Object>();
      maps.put("totalCount", totalCount);
      maps.put("pageSize", pageSize);
      maps.put("pageNum", pageNum);
      model.addAttribute("maps", maps);
      
      
      
      
         
      ArrayList<PhotoBoardDTO> photolists = photoboarddao.PhotoListPage(parameterDTO);
      model.addAttribute("photolists", photolists);
      System.out.println(photolists.size());
      
      String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath()+"/list.do?");
      model.addAttribute("pagingImg", pagingImg);
      
      
      return "community/photoboard_list";
   }
   
   
   
   //포토 게시판 글쓰기 페이지 이
      @GetMapping("/community/photoboard_write.do")
      public String photoboardWriteGet(Model model, Principal principal) {
    	  System.out.println("포토글쓰기 페이지이동 ");
         String email = principal.getName();
         System.out.println(email);
         model.addAttribute("email", email);
         return "community/photoboard_write";
      }
      
      
      
      
   
   
   
   
   
   
   @RequestMapping("/community/photoboard_view.do")
   public String photoboardView(Model model, PhotoBoardDTO photoBoardDTO, CommentsDTO commentsDTO) {
      
      
      photoBoardDTO = photoboarddao.photoview(photoBoardDTO);
      System.out.println(photoBoardDTO);
      photoBoardDTO.setContent(photoBoardDTO.getContent().replace("<br>", "\r\n"));
      model.addAttribute("photoBoardDTO", photoBoardDTO);
      System.out.println(photoBoardDTO);
      
      
      
      
      // 뷰 테이블에 댓글 얻어오기 추가
      // 코멘트 테이블 전부다  얻어와서 저장하기  
   		ArrayList<CommentsDTO> CommentsLists = photoboarddao.CommentsPage(commentsDTO);
   		//빈에 저장
   		
   		
   		System.out.println("댓글 디비에 있는거 가저오는거 성공?");
   		System.out.println(CommentsLists);
   		
   		
   		
   		model.addAttribute("CommentsLists", CommentsLists);
   		model.addAttribute("photoBoardDTO",photoBoardDTO);
   		
   		
      
   		
      
      
      return "community/photoboard_view";
   }
   
   
   
   
   // 싱글 파일 업로드 처리

   //멀티파일 업로드 폼 매핑
   @GetMapping("/multiFileUpload.do")
   public String multiFileUpload() {
      return "multiFileUpload";
   }
   
   
   // 파일 업로드 처리
   @PostMapping("/community/photoboard_writeprocess.do")
   public String multiUploadProcess(HttpServletRequest req, Model model, PhotoBoardDTO photoBoardDTO) {
      System.out.println(photoBoardDTO);
       try {
          System.out.println("파일업로드 컨트롤러 들어오나?");
           // 물리적 경로 얻어오기
           String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
           System.out.println("물리적 경로:" + uploadDir);

           // 파일명 저장을 위한 Map 생성. Key는 원본 파일명, value는 서버에 저장된 파일명을 저장한다.
           Map<String, String> saveFileMaps = new HashMap<>();

           // 2개 이상의 파일 이므로 getParts()메서드를 통해 폼값을 받는다. 컬렉션타입으로 받음
           Collection<Part> parts = req.getParts();

           // 폼값의 갯수만큼 반복
           for (Part part : parts) {
               // 폼값 중 파일인 경우에만 업로드 처리를 위해 continue를 걸어준다.
               // 파일이 아니라면 for문의 처음으로 돌아간다.
               if (!part.getName().equals("ofile"))
                   continue;

               
               
               // 파일명 추출을 위해 헤더값을 얻어온다.
               String partHeader = part.getHeader("content-disposition");
               System.out.println("partHeader=" + partHeader);

               String[] phArr = partHeader.split("filename=");
               // 파일명을 추출한 후 따옴푤ㄹ 제거한다.
               String originalFileName = phArr[1].trim().replace("\"", "");

               // 파일을 원본파일명으로 저장한다.
               if (!originalFileName.isEmpty()) {
                   part.write(uploadDir + File.separator + originalFileName);
               }

               // 저장된 파일명을 UUID로 생성한 새로운 파일명으로 저장한다.
               String savedFileName = MyFunctions.renameFile(uploadDir, originalFileName);

               // Map 컬렉션에 원본파일명과 저장된 파일명을 key와 value로 저장한다.
               saveFileMaps.put(originalFileName, savedFileName);
               System.out.println(savedFileName);


               photoBoardDTO.setTitle(req.getParameter("title"));
               photoBoardDTO.setOfile(originalFileName);
               photoBoardDTO.setSfile(savedFileName);

               int result2 = filedao.insertMultiFile(photoBoardDTO);
               if (result2 == 1) {
                   System.out.println("멀티성공(?)");
                   model.addAttribute("originalFileName", originalFileName);
                   model.addAttribute("saveFileMaps", saveFileMaps);
                   model.addAttribute("title", req.getParameter("title"));
                   model.addAttribute("cate", req.getParameterValues("cate"));
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
           System.out.println("업로드 실패");
       }

       // View로 전달하기 위해 Model 객체에 저장한다.
       //return "community/photoboard_list.do";
      return "redirect:photoboard_list.do";
   }
   
   
   // 파일삭제
   
    // 업로드 파일 삭제
   
   
   
   //포토게시판 수정
   
   
   //수정페이지 이동
   @GetMapping("/community/photoboard_edit.do")
   public String photoboardEdit(Model model, PhotoBoardDTO photoBoardDTO, Principal principal) {
      System.out.println("포토 수정 이 컨트롤러 들어오니?");
      photoBoardDTO = photoboarddao.photoview(photoBoardDTO);
      
      String email = principal.getName();
      System.out.println(email);
      model.addAttribute("email", email);
      model.addAttribute("photoBoardDTO", photoBoardDTO);
      return "community/photoboard_edit";
   
   }
   //포토 게시판 수정처리
   @PostMapping("/community/photoboard_edit.do")
   public String photoboardEditPost(HttpServletRequest req, Model model, PhotoBoardDTO photoBoardDTO) {
      System.out.println("포토게시판 수정처리 컨트롤러 들어오나? ");
      photoBoardDTO.setContent(photoBoardDTO.getContent().replace("<br>", "\r\n"));
      System.out.println(photoBoardDTO);
      
      
      try {
           // 물리적 경로 얻어오기
           String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
           System.out.println("물리적 경로:" + uploadDir);

           
           
           // 파일명 저장을 위한 Map 생성. Key는 원본 파일명, value는 서버에 저장된 파일명을 저장한다.
           Map<String, String> saveFileMaps = new HashMap<>();

           // 2개 이상의 파일 이므로 getParts()메서드를 통해 폼값을 받는다. 컬렉션타입으로 받음
           Collection<Part> parts = req.getParts();

           // 폼값의 갯수만큼 반복
           for (Part part : parts) {
               // 폼값 중 파일인 경우에만 업로드 처리를 위해 continue를 걸어준다.
               // 파일이 아니라면 for문의 처음으로 돌아간다.
               if (!part.getName().equals("ofile"))
                   continue;

               // 파일명 추출을 위해 헤더값을 얻어온다.
               String partHeader = part.getHeader("content-disposition");
               System.out.println("partHeader=" + partHeader);

               String[] phArr = partHeader.split("filename=");
               // 파일명을 추출한 후 따옴푤ㄹ 제거한다.
               String originalFileName = phArr[1].trim().replace("\"", "");

               // 파일을 원본파일명으로 저장한다.
               if (!originalFileName.isEmpty()) {
                   part.write(uploadDir + File.separator + originalFileName);
               }

               // 저장된 파일명을 UUID로 생성한 새로운 파일명으로 저장한다.
               String savedFileName = MyFunctions.renameFile(uploadDir, originalFileName);
               
               
               
               // Map 컬렉션에 원본파일명과 저장된 파일명을 key와 value로 저장한다.
               saveFileMaps.put(originalFileName, savedFileName);
               System.out.println(savedFileName);
               
               

               photoBoardDTO.setTitle(req.getParameter("title"));
               photoBoardDTO.setOfile(originalFileName);
               photoBoardDTO.setSfile(savedFileName);

               //여기서 부터 수정으로 고치기 
               int result2 = filedao.updateFIle(photoBoardDTO);
               
               if (result2 == 1) {
                   System.out.println("수정성공(?)");
                   model.addAttribute("originalFileName", originalFileName);
                   model.addAttribute("saveFileMaps", saveFileMaps);
                   model.addAttribute("title", req.getParameter("title"));
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
           System.out.println("업로드 실패");
       }
      
      
      
      
      
      
      //return "redirect:freeboard_view.do?freeboard_idx="+boardDTO.getFreeboard_idx();
      //int result = photoboarddao.photoedit(photoBoardDTO);
      //System.out.println("result:"+result);
      model.addAttribute("photoboard_idx", photoBoardDTO.getPhotoboard_idx());
      return "redirect:photoboard_view.do?photoboard_idx="+photoBoardDTO.getPhotoboard_idx();
      //return "main/main";
      //return "redirect:photoboard_list.do";
   }
   
   
   
   //포토 게시판 삭제처리
   @PostMapping("/community/photoboard_delete.do")
   public String photoDeletePost(HttpServletRequest req ) {
      int result = photoboarddao.photodelete(req.getParameter("photoboard_idx"));
      System.out.println("글삭제결과:"+result);
      
      return "redirect:photoboard_list.do";
   }
   
   
   
   
   
   
   
   /** 업로드 파일 삭제
    * @throws FileNotFoundException 
     */
   //"/community/photoboard_writeprocess.do"
//    @PostMapping("/community/deleteFile.do")
//    public ResponseEntity<Boolean> removeFile(String fileName) throws FileNotFoundException{
//       
//       System.out.println("파일 삭제 매핑으로 들어오나?");
//        String srcFileName = null;
//        
//        
//        try{
//           String uploadDir = ResourceUtils.getFile("classpath:static/uploads/").toPath().toString();
//            srcFileName = URLDecoder.decode(fileName,"UTF-8");
//            //UUID가 포함된 파일이름을 디코딩해줍니다.
//            File file = new File(uploadDir +File.separator + srcFileName);
//            //이게 sfile이구나
//            boolean result = file.delete();
//
//            
//            File thumbnail = new File(file.getParent(),"s_"+file.getName());
//            //이게 뭐지?
//            //getParent() - 현재 File 객체가 나태내는 파일의 디렉토리의 부모 디렉토리의 이름 을 String으로 리턴해준다.
//            return new ResponseEntity<>(result,HttpStatus.OK);
//        }catch (UnsupportedEncodingException e){
//            e.printStackTrace();
//            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    
//    //포토게시판 댓글추가
//    
//	@PostMapping("/community/photoboard_comment.do")
//	public String boardCommentPost(Model model,  HttpServletRequest req, PhotoBoardDTO photoBoardDTO ,
//			CommentsDTO commentsDTO) {
//		
//		System.out.println("댓글 추가 컨트롤러 들어오나? ");
//		
//		String photoboard_idx = req.getParameter("photoboard_idx");
//		String content = req.getParameter("content");
//		String email = req.getParameter("email");
//		
//		System.out.println(photoboard_idx);
//		System.out.println(content);
//		System.out.println(email);
//		
//		
//		
//		int result = photoboarddao.writeConmments(photoboard_idx, content ,email);
//		
//		
//		
//	
//		
//		System.out.println("성공?");
//		System.out.println(commentsDTO);
//		System.out.println("글쓰기결과:"+ result);
//		
//		// 코멘트 테이블 전부다  얻어와서 저장하기  
//		ArrayList<CommentsDTO> CommentsLists = photoboarddao.CommentsPage(commentsDTO);
//		//빈에 저장
//		
//		
//		System.out.println("댓글 디비에 있는거 가저오는거 성공?");
//		System.out.println(CommentsLists);
//		
//		
//		
//		model.addAttribute("CommentsLists", CommentsLists);
//		model.addAttribute("photoBoardDTO",photoBoardDTO);
//		model.addAttribute("result", result);
//		
//		
//	
//
//		
//		
//		return "community/photoboard_view";
//		//return "redirect:view.do";
//	}
//   
   
   
   
   
   
   
 
   
   
       
   
   
   
   
   

}



      
      
      
   
//   @RequestMapping(value="/community/photoboard_writeprocess.do", produces = "application/json; charset=utf8")
//   @ResponseBody
//   public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, PhotoBoardDTO photoBoardDTO
//         ,Model model, Principal principal)  {
//      JsonObject jsonObject = new JsonObject();
//      
//      String email = principal.getName();
//      
//      model.addAttribute("email", email);
//      System.out.println(email);
//      
//      System.out.println("포토 게시판 들어오나?");
//      
//      String title = request.getParameter("title");
//      System.out.println("타이"+title);
//      System.out.println(photoBoardDTO);
//      String files = request.getParameter("files");
//      System.out.println(files);
//        /*
//       * String fileRoot = "C:\\summernote_image\\"; // 외부경로로 저장을 희망할때.
//       */
//      
//      // 내부경로로 저장
//      //String contextRoot = request.getServletContext().getRealPath("/");
//      //String newContextRoot = contextRoot.replace("/Users/minseokkang/Desktop/Workplace/Sigdolagi/src/main/webapp/",
//      //                                             "/Users/minseokkang/Desktop/Workplace/Sigdolagi/bin/main/static/uploads/");
//      String contextRoot = null;
//      
//      try {
//       contextRoot = ResourceUtils
//            .getFile("classpath:static/uploads/").toPath().toString();
//      System.out.println("물리적 경로:" +contextRoot);
//      }catch (Exception e) {
//         e.printStackTrace();
//      }
//      
//      System.out.println("물리적 경로:" +contextRoot);
//      System.out.println("컨텍스트루트"+contextRoot);
//      String fileRoot = contextRoot;
//      System.out.println("파일루트"+fileRoot);
//      
//      String originalFileName = multipartFile.getOriginalFilename();   //오리지날 파일명
//      System.out.println("오리지날파일명"+originalFileName);
//      photoBoardDTO.setOfile(originalFileName);
//      String extension = originalFileName.substring(originalFileName.lastIndexOf("."));   //파일 확장자
//      String savedFileName = UUID.randomUUID() + extension;   //저장될 파일 명
//      System.out.println("저장된 파일명"+savedFileName);
//      photoBoardDTO.setSfile(savedFileName);
//      System.out.println(photoBoardDTO);
//      
//      
//      
//      
//      File targetFile = new File(fileRoot +"/"+ savedFileName);   
//      try {
//         InputStream fileStream = multipartFile.getInputStream();
//         FileUtils.copyInputStreamToFile(fileStream, targetFile);   //파일 저장
//         
//         jsonObject.addProperty("url", contextRoot+"resources/static/uploads/"+savedFileName); 
//         jsonObject.addProperty("responseCode", "success");
//         
//         System.out.println("저장된파일명"+savedFileName);
//         
//         Map<String, String> saveFileMaps = new HashMap<>();
//
//         
//         
//         saveFileMaps.put(originalFileName, savedFileName);
//
//         
//         
//         //db집어넣기
//         
//         
//         int result2 = filedao.insertMultiFile(photoBoardDTO);
//         if(result2 == 1) {
//            System.out.println("멀티성공(?)");
//            model.addAttribute("originalFileName", request.getParameter("originalFileName"));
//            model.addAttribute("saveFileMaps", saveFileMaps);
//            model.addAttribute("photolist", photoBoardDTO);
//            System.out.println(saveFileMaps);
//            model.addAttribute("title", request.getParameter("title"));
//            model.addAttribute("cate", request.getParameterValues("cate"));
//            
//         }
//         
//         
//         
//         
//            
//      } catch (IOException e) {
//         FileUtils.deleteQuietly(targetFile);   //저장된 파일 삭제
//         jsonObject.addProperty("responseCode", "error");
//         e.printStackTrace();
//      }
//      String a = jsonObject.toString();
//      return a; 
//      
//      
//      
//       
//   }
//   
//   
   






   

   