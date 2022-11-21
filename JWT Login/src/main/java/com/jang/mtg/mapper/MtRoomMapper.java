package com.jang.mtg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import com.jang.mtg.model.MrResTimeVO;
import com.jang.mtg.model.MrReserveVO;
import com.jang.mtg.model.MtRoomVO;
import com.jang.mtg.model.SearchVO;



@Mapper
public interface MtRoomMapper {

List<MtRoomVO> getMtRoomList(SearchVO searchVO); //등록된 회의실 전체 읽기
	
	List<MtRoomVO> Search_getMtRoomList(String searchKeyword); //등록된 회의실 전체 읽기
	
	MtRoomVO getMtRoom(int mrNo); //특정회실 읽기
	
	int insertMtRoom(MtRoomVO mtRoomVO); //회의실 등록
	
	int updateMtRoom(MtRoomVO mtRoomVO); // 회의실 정보수정
	
	int deleteMtRoom(int mrNo); //회의실 등록취소
	
	List<MrReserveVO> getMrReserveList(MrResTimeVO mrResTimeVO1);// 일별회의실별 예약목록 읽기
	
	MrReserveVO getMrReserve(int reNo); //특정 예약 읽기
	
	int insertMrReserve(MrReserveVO mrReserveVO);//예약 등록
	
	int updateMrReserve(MrReserveVO mrReserveVO); //예약 수정
	
	int deleteMrReserve(int reNo); //예약취소
	
	int resDupCeck(MrReserveVO mrReserveVO);//예약 중복체크
	
	MrReserveVO getMrReserveByTime(MrReserveVO mrReserveVO); //특정시간 예약 읽기
	
	StringBuffer getPageUrl(SearchVO searchVO);
	
	int getTotalRow(SearchVO searchVO);// 전체글 수 조회
	
	int getTotalRow2(SearchVO searchVO);// 전체글 수 조회
	
	List<MtRoomVO> getMtRoomList(@Param(value = "startRow") int startRow, @Param(value = "endRow") int endRow);
}
