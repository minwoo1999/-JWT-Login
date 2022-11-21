package com.jang.doc.utils;

import org.springframework.stereotype.Component;

import com.jang.mtg.model.SearchVO;







@Component
public class PageHelper {

	private int pageSize = 10; // �븳 �럹�씠吏��떦 蹂댁씠�뒗 寃뚯떆臾� �닔
	private int blockSize = 10; //�븳 釉붾윮�떦 蹂댁씠�뒗 �럹�씠吏� �닔
	public int getPageSize() {
	return pageSize;
	}
	public int getBlockSize() {
	return blockSize;
	}
	// �쁽�옱 �럹�씠吏��� �쟾泥� �젅肄붾뱶�닔瑜� �엯�젰 諛쏆븘 �럹�씠吏� �몴�떆 臾몄옄�뿴�쓣 諛섑솚�븳�떎.
	public StringBuffer getPageUrl(SearchVO searchVO, int totalRow) {
		
		StringBuffer pageHtml = new StringBuffer();
		
		int currentPage=searchVO.getPage();
		String key=searchVO.getKeyword();
		String type=searchVO.getType();
		
		String smsg=null;
		if (key != "") {
			smsg="&type="+type+"&keyword="+key;
		}
		else{
			smsg="";
		}
		int startPage = 0;
		int endPage = 0;
		//�몴�떆�븷 �떆�옉�럹�씠吏��� �걹�럹�씠吏� 怨꾩궛
		startPage = ((currentPage-1) / blockSize) * blockSize + 1;
		endPage = startPage + blockSize - 1;
		//�쟾泥� �럹�씠吏� �닔�� 釉붾윮�닔 怨꾩궛
		int totalPage = totalRow/pageSize + 1;
		int totalBlock = totalPage/blockSize + 1;
		int currentBlock = currentPage/blockSize + 1; 
		
		
		
		//�몴�떆�릺�뒗 �쁽�옱 釉붾윮�쓽 �걹 �럹�씠吏�媛� �쟾泥� 留덉�留� �럹�씠吏��씤 寃쎌슦- �쟾泥� �럹�씠吏�蹂대떎 �겙 寃쎌슦 諛쒖깮�떆 議곗젙 : 223�뻾/10 + 1 = 23�럹�씠吏�
		if( endPage > (totalRow / pageSize ) ) {
		endPage = (totalRow /pageSize) + 1;
		}
		//�쁽�옱 釉붾윮�씠 2 �씠�긽�씤 寃쎌슦 [�씠�쟾] �몴�떆
		if(currentBlock > 1){
		pageHtml.append("<span><a href=\"mtRoomList?page=" + (currentPage-1) + smsg+ "\"><�씠�쟾></a>&nbsp;&nbsp;");
		}else{
		pageHtml.append("<span>");
		}
		//�쁽�옱 釉붾윮�븞�뿉 �룷�븿�맆 �럹�씠吏� �몴�떆
		for(int i = startPage ; i <= endPage ; i++) {
		if(i == currentPage){
		pageHtml.append(".&nbsp;<strong>");
		pageHtml.append( i );
		pageHtml.append("&nbsp;</strong>");
		} else {
		pageHtml.append(".&nbsp;<a href=\"mtRoomList?page=" +i+smsg+ "\" class=\"page\">" + i + "</a>&nbsp;");
		}
		}
		//�쁽�옱 釉붾줉 �븞�씠 �쟾泥� 釉붾윮 蹂대떎 �옉�쑝硫� [�떎�쓬] �몴�떆
		if(currentBlock < totalBlock){
		pageHtml.append(".&nbsp;&nbsp;<a href=\"mtRoomList?page=" + (currentPage+1) +smsg+ "\"><�떎�쓬></a></span>");
		}else{
		pageHtml.append(".</span>");
		}
		return pageHtml; 
	}
}
