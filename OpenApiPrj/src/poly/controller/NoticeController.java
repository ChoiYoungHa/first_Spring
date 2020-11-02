package poly.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import poly.dto.NoticeDTO;
import poly.service.INoticeService;

@Controller
public class NoticeController {
	@Resource(name = "NoticeService")
	INoticeService NoticeService;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@RequestMapping(value="/Notice", method=RequestMethod.GET)
	public String Notice(HttpServletRequest request, HttpServletResponse reponse,
			Model model )throws Exception {
		
		log.info("공지사항 불러오기");
		
		List<NoticeDTO> rList = NoticeService.getNoticeList();
		
		if(rList == null) {
			rList = new ArrayList<NoticeDTO>();
		}
		
		for(NoticeDTO pDTO : rList) {
			log.info("post_no : " + pDTO.getTitle());
			log.info("post_title : " + pDTO.getContents());
			log.info("post_content : " + pDTO.getChg_id());
		}
		
		model.addAttribute("rList",rList);
		
		//메모리초기화 로그찍기 잊지않기.
		rList = null;
		log.info("List 불러오기 끝!");
		
		
		
		return "/Notice/Notice";
		
	}
	

}
