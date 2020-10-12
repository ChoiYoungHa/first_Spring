package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import poly.service.IMovieService;

@Controller
public class MovieController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="MovieService")
	IMovieService iMovieService;
	
	/*
	 * CGV 영화 수집을 위한 url 호출
	 */
	
	@RequestMapping(value="movie/getMovieInfoFromWEB")
	public String getMovieInfoFromWEB(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		
		log.info("getMovieInfoFromWEB Start!");
		
		int res = iMovieService.getMovieInfoFromWEB();
		
		// 크롤링 결과 넣어주기
		model.addAttribute("res",String.valueOf(res));
		
		log.info("getMovieInfoFromWEB end!");
		
		return "/Movie/RankForWEB";
	}
	
	
	
	

}
