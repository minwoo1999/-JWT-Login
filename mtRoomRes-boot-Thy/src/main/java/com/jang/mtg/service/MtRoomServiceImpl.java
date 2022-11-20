package com.jang.mtg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jang.doc.utils.PageHelper;
import com.jang.mtg.mapper.MtRoomMapper;
import com.jang.mtg.model.MrResTimeVO;
import com.jang.mtg.model.MrReserveVO;
import com.jang.mtg.model.MtRoomVO;
import com.jang.mtg.model.SearchVO;



@Service(value="mtRoomService")
public class MtRoomServiceImpl implements MtRoomService {
	
	@Autowired
	private MtRoomMapper mtRoomMapper;
	
	PageHelper pageHelper = new PageHelper();
	
	@Override
	public List<MtRoomVO> getMtRoomList(int startRow, int endRow) {
		// TODO Auto-generated method stub
		return this.mtRoomMapper.getMtRoomList(startRow, endRow);
	}

	@Override
	public List<MtRoomVO> getMtRoomList(SearchVO searchVO) {
		// TODO Auto-generated method stub
		int currentPage = searchVO.getPage();
		int startRow = (currentPage - 1) * 10 + 1; // pagesize=10 °¡Á¤
		int endRow = currentPage * 10; // pagesize=10
		searchVO.setStartRow(startRow);
		searchVO.setEndRow(endRow);
		return mtRoomMapper.getMtRoomList(searchVO);
	}
	
	@Override
	public StringBuffer getPageUrl(SearchVO searchVO) {
		// TODO Auto-generated method stub
		int totalRow = mtRoomMapper.getTotalRow2(searchVO);
		System.out.println(totalRow);
		return pageHelper.getPageUrl(searchVO, totalRow);
	}

	@Override
	public MtRoomVO getMtRoom(int mrNo) {
		// TODO Auto-generated method stub
		return mtRoomMapper.getMtRoom(mrNo);
	}

	@Override
	public int insertMtRoom(MtRoomVO mtRoomVO) {
		// TODO Auto-generated method stub
		return mtRoomMapper.insertMtRoom(mtRoomVO);
	}

	@Override
	public int updateMtRoom(MtRoomVO mtRoomVO) {
		// TODO Auto-generated method stub
		return mtRoomMapper.updateMtRoom(mtRoomVO);
	}

	@Override
	public int deleteMtRoom(int mrNo) {
		// TODO Auto-generated method stub
		return mtRoomMapper.deleteMtRoom(mrNo);
	}

	@Override
	public List<MrReserveVO> getMrReserveList(MrResTimeVO mrResTimeVO1) {
		// TODO Auto-generated method stub
		return mtRoomMapper.getMrReserveList(mrResTimeVO1);
	}

	@Override
	public MrReserveVO getMrReserve(int reNo) {
		// TODO Auto-generated method stub
		return mtRoomMapper.getMrReserve(reNo);
	}

	@Override
	public int insertMrReserve(MrReserveVO mrReserveVO) {
		// TODO Auto-generated method stub
		return mtRoomMapper.insertMrReserve(mrReserveVO);
	}

	@Override
	public int updateMrReserve(MrReserveVO mrReserveVO) {
		// TODO Auto-generated method stub
		return mtRoomMapper.updateMrReserve(mrReserveVO);
	}

	@Override
	public int deleteMrReserve(int reNo) {
		// TODO Auto-generated method stub
		return mtRoomMapper.deleteMrReserve(reNo);
	}

	@Override
	public int resDupCeck(MrReserveVO mrReserveVO) {
		// TODO Auto-generated method stub
		return mtRoomMapper.resDupCeck(mrReserveVO);
	}

	@Override
	public MrReserveVO getMrReserveByTime(MrReserveVO mrReserveVO) {
		// TODO Auto-generated method stub
		
		System.out.println(mrReserveVO.getMrNo());
		System.out.println(mrReserveVO.getReserve_Day());
		System.out.println(mrReserveVO.getReserve_Start());
		
		
		return mtRoomMapper.getMrReserveByTime(mrReserveVO);
	}

	@Override
	public List<MtRoomVO> Search_getMtRoomList(String searchKeyword) {
		// TODO Auto-generated method stub
		return mtRoomMapper.Search_getMtRoomList(searchKeyword);
	}

	@Override
	public int getTotalRow(SearchVO searchVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalRow2(SearchVO searchVO) {
		// TODO Auto-generated method stub
		return 0;
	}




	

}
