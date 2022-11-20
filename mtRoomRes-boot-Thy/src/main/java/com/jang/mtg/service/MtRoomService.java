package com.jang.mtg.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jang.mtg.model.MrResTimeVO;
import com.jang.mtg.model.MrReserveVO;
import com.jang.mtg.model.MtRoomVO;
import com.jang.mtg.model.SearchVO;



public interface MtRoomService {

	
	
	List<MtRoomVO> Search_getMtRoomList(String searchKeyword); //?“±ë¡ëœ ?šŒ?˜?‹¤ ? „ì²? ?½ê¸?
	
	MtRoomVO getMtRoom(int mrNo); //?Š¹? •?šŒ?‹¤ ?½ê¸?
	
	int insertMtRoom(MtRoomVO mtRoomVO); //?šŒ?˜?‹¤ ?“±ë¡?
	
	int updateMtRoom(MtRoomVO mtRoomVO); // ?šŒ?˜?‹¤ ? •ë³´ìˆ˜? •
	
	int deleteMtRoom(int mrNo); //?šŒ?˜?‹¤ ?“±ë¡ì·¨?†Œ
	
	List<MrReserveVO> getMrReserveList(MrResTimeVO mrResTimeVO1);// ?¼ë³„íšŒ?˜?‹¤ë³? ?˜ˆ?•½ëª©ë¡ ?½ê¸?
	
	MrReserveVO getMrReserve(int reNo); //?Š¹? • ?˜ˆ?•½ ?½ê¸?
	
	int insertMrReserve(MrReserveVO mrReserveVO);//?˜ˆ?•½ ?“±ë¡?
	
	int updateMrReserve(MrReserveVO mrReserveVO); //?˜ˆ?•½ ?ˆ˜? •
	
	int deleteMrReserve(int reNo); //?˜ˆ?•½ì·¨ì†Œ
	
	int resDupCeck(MrReserveVO mrReserveVO);//?˜ˆ?•½ ì¤‘ë³µì²´í¬
	
	MrReserveVO getMrReserveByTime(MrReserveVO mrReserveVO); //?Š¹? •?‹œê°? ?˜ˆ?•½ ?½ê¸?
	
	int getTotalRow(SearchVO searchVO);// ? „ì²´ê? ?ˆ˜ ì¡°íšŒ
	
	int getTotalRow2(SearchVO searchVO);// ? „ì²´ê? ?ˆ˜ ì¡°íšŒ
	
	
	StringBuffer getPageUrl(SearchVO searchvo);//?˜?´ì§?
	
	List<MtRoomVO> getMtRoomList(@Param(value = "startRow") int startRow, @Param(value = "endRow") int endRow);
	
	List<MtRoomVO> getMtRoomList(SearchVO searchVO); //?“±ë¡ëœ ?šŒ?˜?‹¤ ? „ì²? ?½ê¸?
}
