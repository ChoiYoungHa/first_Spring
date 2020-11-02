package poly.service.impl;

import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.dto.FoodDTO;
import poly.dto.MovieDTO;
import poly.persistance.mapper.IFoodMapper;
import poly.persistance.mapper.IMovieMapper;
import poly.service.IFoodService;
import poly.service.IMovieService;
import poly.util.CmmUtil;
import poly.util.DateUtil;

@Service("FoodService")
public class FoodService implements IFoodService{
	@Resource(name="FoodMapper")
	IFoodMapper Foodmapper;
	
	//로그 파일 생성 및 로그 출력을 위한 log4j 프레임워크의 자바객체
	private Logger log = Logger.getLogger(this.getClass());
	
	
	/*
	 * JSOUP 라이브러리를 통한 CGV 영화 순위 정보가져오기
	 */
	@Override
	public int getFoodWEB() throws Exception{
		
		//로그찍기
		log.info("getFoodWEB Start!");
		
		int res = 0;
		
		// CGV 영화 순위 정보 가져올 사이트 주소
		String url = "http://www.kopo.ac.kr/kangseo/content.do?menu=262";
		
		// JSOUP 라이브러리를 통해 사이트가 접속되면, 그 사이트의 HTML소스 저장할 변수
		Document doc = null;
		
		// 사이트 접속(http 프로토콜만 가능, https 프로토콜 보안상 안댐)
		doc = Jsoup.connect(url).get();
		
		//CGV 웹페이지의 전체 소스 중 일부 태그를 선택하기 위해 사용
		// <div class = "sect-movie-chart"> 이 태그 내에 있는 html 소스만 element에 저장
		Elements element = doc.select("table tr");
		
		//Iterator를 사용하여 영화 순위 정보를 가져오기
		//영화순위는 기본적으로 1개이상의 영화가 존재하기 때문에 태그의 반복이 존재할 수 밖에 없음
		
		Iterator<Element> day = element.select("td:nth-child(1)").iterator(); // Day
		Iterator<Element> food_nm = element.select("td:nth-child(3)").iterator(); // 음식이름
		
		FoodDTO pDTO = null;
		
		int a = 0;
		
		// 수집된 데이터 db에 저장하기
		while(day.hasNext()) {
			
			if(a == 5) {
				break;
			}
			
			pDTO = new FoodDTO();
			//수집시간을 기본 pk로 사용
			pDTO.setCollect_time(DateUtil.getDateTime("yyyyMMdd24hmmss"));
	
			//요일
			pDTO.setDay(CmmUtil.nvl(day.next().text()).trim());
			
			//영화제목
			pDTO.setFood_nm(CmmUtil.nvl(food_nm.next().text()).trim());
			
			//등록자
			pDTO.setReg_id("admin");
			
			//영화 한개씩 추가
			res += Foodmapper.InsertFood(pDTO);
			a++;
	}
		
		log.info("getFoodWEB end!");
		
		return res;
	}
		
}
