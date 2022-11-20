package com.jang.mtg.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;
import com.jang.mtg.model.MrResTimeVO;
import com.jang.mtg.model.MrReserveVO;
import com.jang.mtg.model.MtRoomVO;
import com.jang.mtg.model.SearchVO;
import com.jang.mtg.service.MtRoomService;

@Controller
public class MtRoomController {

	
	String uploadPath = "C:\\upload\\"; // file upload path 전역변수로 파일 저장소 지정
	
	@Autowired
	private MtRoomService mtRoomService;
	
	@RequestMapping(value="/index")
	public String indexView() throws Exception{
		return "index";
	}
	
	@RequestMapping(value = "/mtRoomList")
	public String mtRoomList(@ModelAttribute("mtRoomVO") MtRoomVO mtRoomVO,
			@ModelAttribute("searchVO") SearchVO seachVO, Model model, HttpSession session, HttpServletRequest request)
			throws Exception {

		String searchKeyword = request.getParameter("searchKeyword");
		StringBuffer pageUrl = mtRoomService.getPageUrl(seachVO);
		System.out.println("들어온키워드"+searchKeyword);
		if (searchKeyword == null || searchKeyword == "") {
			List<MtRoomVO> mtRoomList = mtRoomService.getMtRoomList(seachVO);
			model.addAttribute("mtRoomList", mtRoomList);
			model.addAttribute("pageHttp", pageUrl);

			return "mtRoomList";
		} else {
			List<MtRoomVO> mtRoomList = mtRoomService.Search_getMtRoomList(searchKeyword);

			model.addAttribute("mtRoomList", mtRoomList);
			model.addAttribute("pageHttp", pageUrl);

			return "mtRoomList";
		}

	}
	@RequestMapping(value = "/insertMtRoom", method = RequestMethod.GET)
	public String insertMtRoomView(Model model) throws Exception{
		model.addAttribute("mtRoomVO", new MtRoomVO());
		return "mtRoomRegist";
	}
	
	
	
	@RequestMapping(value="insertMtRoom", method = RequestMethod.POST)
	public String insertMtRoomOn(@ModelAttribute("mtRoomVO")MtRoomVO mtRoomVO, Model model, HttpSession session,
			MultipartHttpServletRequest mRequest) throws Exception{
		
		String userId = (String)session.getAttribute("userId");
		
//		ServletContext servletContext = mRequest.getSession().getServletContext();
//		String webappRoot = servletContext.getRealPath("/");
//		String relativeForder = File.separator + "resources/static" + File.separator + "images" + File.separator;
		
		String webappRoot = System.getProperty("user.dir");
		String relativeForder =File.separator + "src/main/resources/static"+File.separator+"images" + File.separator;
		
		mtRoomVO.setFirst_Reg_ID(userId);
		
		List<MultipartFile> fileList = mRequest.getFiles("file_1");
		
		for(MultipartFile mf: fileList) {
			if(!mf.isEmpty()) {
				String originFileName = mf.getOriginalFilename();
				long fileSize = mf.getSize();
				
				mtRoomVO.setPicture(originFileName);
				System.out.println("New Pic=" + mtRoomVO.getPicture());
				
				String safeFile = webappRoot + relativeForder + originFileName;
				
				String safeFile2 = uploadPath + originFileName; // 디스크에 파일 저장
				
				try {
					mf.transferTo(new File(safeFile2)); // 디스크에 파일 저장
				}catch(IllegalStateException e) {
					e.printStackTrace();
				}catch(IOException e) {
					e.printStackTrace();
					
				}
			}
		}
		if(this.mtRoomService.insertMtRoom(mtRoomVO)!=0) {
			model.addAttribute("mtRoomVO", mtRoomVO);
			model.addAttribute("msgCode","�벑濡� �꽦怨�!");
			return "redirect:mtRoomList";
		}
		else {
			model.addAttribute("msgCode", "�벑濡� �떎�뙣! �솗�씤 �썑 �떎�떆 �떆�룄 �븯�꽭�슂!");
			return "mtRoomRegist";
		}
	}
	
	@RequestMapping(value="/mtRoomDetail", method = RequestMethod.POST)
	public String getMtRoomView(@RequestParam("mrNo") int mrNo, Model model)throws Exception{
		
		MtRoomVO mtRoomVO = mtRoomService.getMtRoom(mrNo);
		
		model.addAttribute("mtRoomVO",mtRoomVO);
		
		return "mtRoomDetail";
	}
	
	@RequestMapping(value="/deleteMtRoom", method = RequestMethod.GET)
	public String deleteMtRoom(@RequestParam("mrNo") int mrNo, Model model)throws Exception{
		
		if(this.mtRoomService.deleteMtRoom(mrNo)!= 0) {
			model.addAttribute("errCode", 3);
			return "redirect:mtRoomList";
		}
		else {
			model.addAttribute("errCode", 5);
			return "redirect:getMtRoomManage";
		}
	}
	
	@RequestMapping(value="/updateMtRoom", method = RequestMethod.GET)
	public String getMtRoomUpView(@RequestParam("mrNo")int mrNo, Model model)throws Exception{
		
		MtRoomVO mtRoomVO = mtRoomService.getMtRoom(mrNo);
		
		System.out.println("No="+ mtRoomVO.getMrNo());
		System.out.println("pic=" + mtRoomVO.getPicture());
		
		model.addAttribute("mtRoomVO", mtRoomVO);
		return "mtRoomUpdate";
	}
	
	@RequestMapping(value="/updateMtRoom", method = RequestMethod.POST)
	public String updateMtRoomOn(@ModelAttribute("mtRoomVO") MtRoomVO mtRoomVO, Model model, HttpSession session,
			MultipartHttpServletRequest mRequest) throws Exception{
		
		
		String userId = (String)session.getAttribute("userId");
		
		ServletContext servletContext = mRequest.getSession().getServletContext();
		String webappRoot = servletContext.getRealPath("/");
		
		String relativeForder = File.separator + "resources" + File.separator + "images" + File.separator;
		
		mtRoomVO.setFirst_Reg_ID(userId);
		
		List<MultipartFile> fileList = mRequest.getFiles("file_1");
		
		for(MultipartFile mf : fileList) {
			if(!mf.isEmpty()) {
				if(!(mtRoomVO.getPicture().equals(null))) {
					String safeFile = webappRoot + relativeForder + mtRoomVO.getPicture();
					File removeFile = new File(safeFile);
					removeFile.delete();
				}
				
				String originFileName = mf.getOriginalFilename();
				long fileSize = mf.getSize();
				
				mtRoomVO.setPicture(originFileName);
				
				String safeFile = webappRoot + relativeForder + originFileName;
				
				try {
					mf.transferTo(new File(safeFile));
				}catch(IllegalStateException e) {
					e.printStackTrace();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(this.mtRoomService.updateMtRoom(mtRoomVO) !=0) {
			model.addAttribute("mtRoomVO", mtRoomVO);
			model.addAttribute("errCode", 3);
			return "redirect:mtRoomList";
		}
		else {
			model.addAttribute("errCode", 5);
			return "redirect:mtRoomUpdate";
		}
	}
	
	//�쉶�쓽�떇 �삁�빟愿�由� 濡쒖쭅
	
	@RequestMapping(value = "/mtResList")
	public String mrReserveList(@ModelAttribute("reserve_Day") String reserve_Day,
			@ModelAttribute("mtRoomVO") MtRoomVO mtRoomVO, @ModelAttribute("searchVO") SearchVO seachVO, Model model)
			throws Exception {

		String strSearchDay = "";

		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		strSearchDay = now.format(formatter);

		if (reserve_Day.isEmpty()) {
			reserve_Day = strSearchDay;
		}

		List<MtRoomVO> mtRoomList = mtRoomService.getMtRoomList(seachVO);

		List<MrResTimeVO> mrResTimeList = new ArrayList<MrResTimeVO>();

		for (MtRoomVO mtRoom : mtRoomList) {

			MrResTimeVO mrResTimeV01 = new MrResTimeVO();
			System.out.println("예약일" + reserve_Day);
			System.out.println("번호" + mtRoom.getMrNo());
			System.out.println("이름" + mtRoom.getMr_Name());
			mrResTimeV01.setReserve_Day(reserve_Day);
			mrResTimeV01.setMrNo(mtRoom.getMrNo());
			mrResTimeV01.setMr_Name(mtRoom.getMr_Name());

			List<MrReserveVO> mrDayReserveList = mtRoomService.getMrReserveList(mrResTimeV01);
			System.out.println(mrDayReserveList);
			for (MrReserveVO mrDayReserve : mrDayReserveList) {

				String sst = mrDayReserve.getReserve_Start().replace(":", "");
				String set = mrDayReserve.getReserve_End().replace(":", "");

				int st = Integer.parseInt(sst);
				int et = Integer.parseInt(set);

				int num;

				for (int i = st; i <= et; i += 50) {
					if (i % 100 > 30) {
						num = i - 20;
					} else {
						num = i;
					}

					if (num % 100 == 60) {
						num += 40;
					}

					String resveTn = "setResveTemp" + num;

					Object obj = mrResTimeV01;

					Class<?> cls = Class.forName(obj.getClass().getName());
					Method m = cls.getMethod(resveTn, String.class);

					m.invoke(obj, "1");
				}

			}
			mrResTimeList.add(mrResTimeV01);
		}
		System.out.println(reserve_Day);
		model.addAttribute("mrResTimeList", mrResTimeList);
		model.addAttribute("reserve_Day", reserve_Day);

		return "mtResList";
	}
	
	@RequestMapping(value="/mtResRegist", method = RequestMethod.POST)
	public String insertResView(@ModelAttribute("mrReserveVO")MrReserveVO mrReserveVO, Model model, HttpSession session)throws Exception{
		
		int intValue1 = Integer.parseInt(mrReserveVO.getReserve_Start())+100;
		String endTime = Integer.toString(intValue1);
		mrReserveVO.setReserve_End(endTime);
		
		session.setAttribute("userId", "Test20");
		String userId = (String) session.getAttribute("userId");
		mrReserveVO.setBookerID(userId);
		
		MtRoomVO mtRoomVO = mtRoomService.getMtRoom(mrReserveVO.getMrNo());
		
		model.addAttribute("mtRoomVO", mtRoomVO);
		model.addAttribute("mrReserveVO", mrReserveVO);
		
		return "mtResRegist";
	}
	
	@RequestMapping(value="/insertReserve", method = RequestMethod.POST)
	public String insertResOn(@ModelAttribute("mrReserveVO")MrReserveVO mrReserveVO, Model model, HttpSession session,
			RedirectAttributes rea)throws Exception{
		String userId =(String)session.getAttribute("userId");
		
		mrReserveVO.setFirst_Reg_ID(userId);
		mrReserveVO.setBookerID(userId);
		
		if(this.mtRoomService.insertMrReserve(mrReserveVO)!=0) {
			rea.addFlashAttribute("reserve_Day", mrReserveVO.getReserve_Day());
			return "redirect:mtResList";
		}
		else {
			model.addAttribute("errCode", 5); //�삁�빟 �떎�뙣
			return "mtResRegist";
		}
	}
	@ResponseBody
	@RequestMapping(value="resDupCheck") 
	public String mtgResDupCheck(@ModelAttribute("mrReserveVO")MrReserveVO mrReserveVO, Model model,HttpServletRequest request) throws Exception{
		
		String reserve_Day=request.getParameter("reserve_Day");
		String reserve_Start=request.getParameter("reserve_Start");
		String reserve_End=request.getParameter("reserve_End");
		int mrNo=Integer.parseInt(request.getParameter("mrNo"));
		
		mrReserveVO.setReserve_Day(reserve_Day);
		mrReserveVO.setReserve_Start(reserve_Start);
		mrReserveVO.setReserve_End(reserve_End);
		mrReserveVO.setMrNo(mrNo);
		JsonObject obj = new JsonObject();
		
		int dupcheck = this.mtRoomService.resDupCeck(mrReserveVO);
		
		if(	dupcheck !=0) {
			obj.addProperty("dup", "false");
			return obj.toString();
		}else{
			obj.addProperty("dup", "true");
			return obj.toString();
		}
	}
	
	@RequestMapping(value = "/updateResView", method = RequestMethod.POST)
	public String updateResView(@ModelAttribute("mrReserveVO") MrReserveVO mrReserveVO, Model model, HttpSession session)throws Exception{
		
		session.setAttribute("userId", "test20");
		
		MrReserveVO mrReserveVO1 = mtRoomService.getMrReserveByTime(mrReserveVO);
		
		MtRoomVO mtRoomVO = mtRoomService.getMtRoom(mrReserveVO.getMrNo());
		
		model.addAttribute("mtRoomVO",mtRoomVO);
		model.addAttribute("mrReserveVO", mrReserveVO1);
		
		return "mtResUpdate";
		
		
	}
	
	@RequestMapping(value = "/updateReserve", method = RequestMethod.POST)
	public String updateReserve(@ModelAttribute("mrReserveVO")MrReserveVO mrReserveVO, RedirectAttributes rea) throws Exception{
		
		LocalDate now = LocalDate.now(); //�룷留룹젙�쓽
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String strSearchDay = now.format(formatter);
		
		mrReserveVO.setLast_Upd_ID(mrReserveVO.getBookerID());
		mrReserveVO.setLast_Upd_Date(strSearchDay);
		
		if(this.mtRoomService.updateMrReserve(mrReserveVO)!=0) {
			rea.addFlashAttribute("reserve_Day", mrReserveVO.getReserve_Day());
			return "redirect:mtResList";
		}else {
			rea.addFlashAttribute("errCode", 5);
			rea.addFlashAttribute("reserve_Day", mrReserveVO.getReserve_Day());
			
			return "redirect:mtResList";
		}
	}
	
	@RequestMapping(value = "/deleteReserve", method = RequestMethod.POST)
	public String deleteRes(@ModelAttribute("mrReserveVO") MrReserveVO mrReserveVO, RedirectAttributes rea)throws Exception{
		
		
		System.out.println("1");
		
		int reNo = mrReserveVO.getReNo();
		
		if(this.mtRoomService.deleteMrReserve(reNo)!=0) {
			rea.addFlashAttribute("reserve_Day", mrReserveVO.getReserve_Day());
			return "redirect:mtResList";
		}else {
			rea.addFlashAttribute("errCode", 5);
			rea.addFlashAttribute("reserve_Day", mrReserveVO.getReserve_Day());
			
			return "redirect:mtResList";
		}
	}
}
