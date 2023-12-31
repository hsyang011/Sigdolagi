package com.edu.springboot.community;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.edu.springboot.member.MemberDTO;
import com.edu.springboot.restaurant.RestaurantDTO;

@Mapper
public interface IPhotoboardService {
	
	public int photoGetTotalCount(ParameterDTO parameterDTO);
	public ArrayList<PhotoBoardDTO> PhotoListPage(ParameterDTO parameterDTO);
	
	//마이포토리스트 
	public ArrayList<PhotoBoardDTO> MyPhotoListPage(ParameterDTO parameterDTO);
	
	
	
	public PhotoBoardDTO photoview(PhotoBoardDTO photoBoardDTO);
	public int photoedit(PhotoBoardDTO photoBoardDTO);
	public int photodelete(String idx);
	public int writeConmments(
			@Param("idx") int idx,
			@Param("content") String content,
			@Param("nickname") String nickname,
			@Param("email") String email,
			@Param("starRating") String starRating
			);
   	//자유게시판 조회수 업데이트 
	public int photoupdate(PhotoBoardDTO photoBoardDTO);
	
	
	//커멘트테이블 받아오기
	public ArrayList<CommentsDTO> CommentsPage(CommentsDTO commentsDTO);
	

	//관리자 포토게시판목록
	public List<PhotoBoardDTO> adminPhotoSelect();
	//관리자 자유게시판삭제
	public int adminPhotoDelete(String idx);
	
	//댓글 삭제
	public int deletecomment(String comments_idx);
	
	// 평균 값 구해오기 
	public double avgStar(PhotoBoardDTO photoBoardDTO);
	
	
	
	
	
}
