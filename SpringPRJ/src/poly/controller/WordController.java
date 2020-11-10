package poly.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.service.IWordAnalysisService;

/*
 * Controller 선언해야만 Spring 프레임워크에서 Controller인지 인식 가능
 * 자바 서블릿 역할 수행
 * */
@Controller
public class WordController {
	private Logger log = Logger.getLogger(this.getClass());

	/*
	 * 비즈니스 로직(중요 로직을 수행하기 위해 사용되는 서비스를 메모리에 적재(싱글톤패턴 적용됨)
	 * */
	@Resource(name = "WordAnalysisService")
	private IWordAnalysisService wordAnalysisService;
	
	@RequestMapping(value = "word/analysis")
	@ResponseBody
	public Map<String, Integer> analysis() throws Exception {
		log.info(this.getClass().getName() + ".inputForm!");
		
		//분석할 문장
		String text = "한국폴리텍대학 서울강서캠퍼스 데이터분석과는 최고야!";
		
		//신조어 및 새롭게 생겨난 가수 및 그룹명은 제대로된 분석이 불가능함
		//새로운 명사 단어들은 어떻게 데이터를 처리해야 할까? => 정답은 데이터 사전의 주기적인 업데이트
	//String text = "난 아이오아이가 최고의 가수라고 생각해. 올해 가수는 당연히 아이오아이지";
		
		Map<String, Integer> rMap = wordAnalysisService.doWordAnalysis(text);
		
		if(rMap == null) {
			rMap = new HashMap<String, Integer>();
			
		}
		
		return rMap;
	}
}
