package com.edu.springboot.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.springboot.community.BoardDTO;
import com.edu.springboot.community.CommentsDTO;
import com.edu.springboot.community.IBoardService;
import com.edu.springboot.community.IPhotoboardService;
import com.edu.springboot.community.ParameterDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import utils.PagingUtil;



@Controller
public class ServiceController {
	 
	@Autowired
	INotiboardService notidao;
	
	@Autowired
	InquiryBoardService inquirydao;

	@Autowired
	IPhotoboardService photoboarddao;	
	
	@Autowired
	IBoardService boardDAO;
	
	//공지사항 목록
	@RequestMapping("/service/notiboard.do")
	public String notiboard(Model model, HttpServletRequest req, ParameterDTO parameterDTO, HttpSession httpSession) {
		   
	      int totalCount = boardDAO.getTotalCount(parameterDTO);
	      
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
	         
	      ArrayList<NotiDTO> lists = notidao.listPage(parameterDTO);
	      model.addAttribute("lists", lists);
	      System.out.println(lists.size());
	      
	      String pagingImg = PagingUtil.pagingImg(totalCount, pageSize, blockPage, pageNum, req.getContextPath()+"./notiboard.do?");
	      model.addAttribute("pagingImg", pagingImg);
	   
		return "service/notiboard";
	}
	
	   
	//글쓰기 페이지로 이동한다. 
	@GetMapping("/service/notiboard_write.do")
	public String notiboardWriteGet(Model model, Principal principal) {
		
		return "service/notiboard_write";
	}
	
	
	@PostMapping("/service/notiboard_write.do")
   	public String notiboardWrite(Model model, HttpServletRequest req, Principal principal) {
      String title= req.getParameter("title");
      String content= req.getParameter("content");
      //폼값을 개별적으로 전달한다.
      int result = notidao.write(title, content);
      System.out.println("글쓰기 결과:" +result);
//      String nickname = dao.getnickname(email);
//      System.out.println("nickname:결과"+nickname);
//      model.addAttribute("nickname",nickname); 

      return "redirect:notiboard.do";
   }
	   

	@RequestMapping("/service/notiboard_view.do")
   	public String freeboardView(Model model,NotiDTO notiDTO,HttpServletRequest req,ParameterDTO parameterDTO) {
		notidao.update(notiDTO);
		notiDTO = notidao.view(notiDTO);
		notiDTO.setContent(notiDTO.getContent().replace("\r\n", "<br>"));
		model.addAttribute("notiDTO", notiDTO);
		
		return "service/notiboard_view";
   }

	//1:1 문의게시판 리스트
	@RequestMapping("/service/inquiryboard.do")
	public String inquiryboard(Model model, Principal principal) {

		if(principal!=null) {
		 String email = principal.getName();
	     String nickname= boardDAO.getnickname(email);
	      
	      
	      
	      System.out.println(email);
	      model.addAttribute("email", email);
	      model.addAttribute("nickname", nickname);
		}
		return "service/inquiryboard";
	}
	
	// 1:1문의 뷰페이지 
   @RequestMapping("/service/inquiryboard_view.do")
   
   public String freeboardView(Model model, InquiryDTO inquiryDTO, HttpServletRequest req,Principal principal) {
	   
	   System.out.println("문의 뷰페이지 컨트롤러 들어오나?");
	   System.out.println(inquiryDTO);
	   inquiryDTO = inquirydao.view(inquiryDTO);
	   System.out.println(inquiryDTO);
	   inquiryDTO.setContent(inquiryDTO.getContent().replace("\r\n", "<br>"));
      
	   // boardDTO에서 idx값을 가져와서 commentsDTO에 채우기
      CommentsDTO commentsDTO = new CommentsDTO();
      try {	    	  
    	  commentsDTO.setIdx(Integer.parseInt(inquiryDTO.getInquiryboard_idx()));
      } catch (Exception e) {
    	  System.out.println("idx가 null입니다.");
    	  e.printStackTrace();
      }
      
      model.addAttribute("inquiryDTO", inquiryDTO);
        // 코멘트 테이블 전부다  얻어와서 저장하기  
      ArrayList<CommentsDTO> commentsLists = boardDAO.CommentsPage(commentsDTO);
	   
      System.out.println("댓글 디비에 있는거 가저오는거 성공?");
      System.out.println(commentsLists);
	
      model.addAttribute("CommentsLists", commentsLists);

	  	if(principal!=null) {
  			String email = principal.getName();
  	         String nickname= boardDAO.getnickname(email);
  	         System.out.println(email);
  	         model.addAttribute("email", email);
  	         model.addAttribute("nickname",nickname); 
  		}
      return "member/inquiryboardView";
   }
	
	
	//1:1 문의  게시판 글쓰기
	@PostMapping("/service/inquiryboard_write.do")
	public String freeboardWrite(Model model, HttpServletRequest req, Principal principal, InquiryDTO inquiryDTO) {
		System.out.println("문의 게시판 글쓰기 컨트롤러 들어오나? ");
		System.out.println(inquiryDTO);
		String email= principal.getName();
		String nickname= boardDAO.getnickname(email);
		String title= req.getParameter("title");
		String content= req.getParameter("content");
		String category = req.getParameter("category");
		System.out.println(category);
      
		//폼값을 개별적으로 전달한다.
		int result = inquirydao.write(email,category,title, content, nickname);
      	System.out.println("글쓰기 결과:" +result);
      	System.out.println("nickname:결과"+nickname);
      	model.addAttribute("nickname1",nickname); 
   
      	return "redirect:/member/mypage.do";
	}
	
	//문의게시판 댓글 
	@RequestMapping("/service/inquiryboard_comment.do")
	@ResponseBody
	public CommentsDTO boardCommentPost(Model model, HttpServletRequest req, CommentsDTO commentsDTO, Principal principal) {
		int idx = commentsDTO.getIdx();
        String content = commentsDTO.getContent();
        String email =  principal.getName();
        String nickname = boardDAO.getnickname(email);
        
        System.out.println(idx);
        System.out.println(content);
        System.out.println(nickname);
        System.out.println(email);

        int result = boardDAO.writeConmments(idx, content, nickname, email);

        System.out.println("성공?");
        System.out.println(commentsDTO);
        System.out.println("글쓰기결과:" + result);
        // 코멘트 테이블 전부다  얻어와서 저장하기  
		ArrayList<CommentsDTO> commentsLists = photoboarddao.CommentsPage(commentsDTO);
		
		System.out.println("댓글 디비에 있는거 가저오는거 성공?");
		System.out.println(commentsLists);
		
		
		model.addAttribute("CommentsLists", commentsLists);

        
        return commentsDTO;
	}
	
	//자유게시판 댓글 삭제하기 
	@PostMapping("/service/inquiryboard_Comments_delete.do")
	public String boardDeleteComments(HttpServletRequest req,CommentsDTO commentsDTO) {
	   	System.out.println("댓글 삭제 확인을 위해 댓글 불러오기 테스트 ");
	   	System.out.println(commentsDTO);
	    String idx = req.getParameter("idx");
	   	System.out.println(req.getParameter("comments_idx"));
	   	int result = boardDAO.deleteComments(req.getParameter("comments_idx"));
	   	System.out.println("comments_idx");
   		System.out.println("글삭제결과:"+result);
	   	
	   	return "redirect:inquiryboard_view.do?inquiryboard_idx="+idx;
	}
	
	

	
	
	@RequestMapping("/service/faq.do")
	public String faq() {
		return "service/faq";
	}
	
}
