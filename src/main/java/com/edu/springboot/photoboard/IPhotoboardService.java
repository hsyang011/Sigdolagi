package com.edu.springboot.photoboard;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPhotoboardService {
	
	public int photoGetTotalCount(ParameterDTO parameterDTO);
	public ArrayList<PhotoBoardDTO> PhotoListPage(ParameterDTO parameterDTO);
	public PhotoBoardDTO photoview(PhotoBoardDTO photoBoardDTO);
	
	
}
