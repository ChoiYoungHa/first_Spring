package poly.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import poly.service.INewsCollectService;
import poly.service.IWordAnalysisService;
import poly.util.CmmUtil;

@Service("WordAnalysisService")
public class WordAnalysisService implements IWordAnalysisService {

	// 로그 파일 생성 및 로그 출력을 위한 log4j 프레임워크의 자바 객체
	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "NewsCollectService")
	private INewsCollectService newsCollectService;
	
	// 자연어 처리-형태소 분석기인 Komoran를 메모리에 올리기 위해 WordAnalysisService 클래스 내 전역 변수로 설정함
	Komoran nlp = null;

	// 생성자 사용함 - 톰캣에서 부팅할 때 @Service를 모두 메모리에 올림
	// 톰캣이 메모리에 올릴 떄, 생성자에 선언한 Komoran도 같이 메모리에 올라가도록 생성자에 코딩함
	// 생성자에서 Komoran을 메모리에 올리면, 매번 메모리에 올려서 호출하는 것이 아니라,
	// 메모리에 올라간 객체만 불러와서 사용할 수 있기 때문에 처리 속도가 빠름
	public WordAnalysisService() {

		log.info(this.getClass().getName() + ".WordAnalysisService creator Start!");

		// NLP 분석 객체 메모리 로딩
		this.nlp = new Komoran(DEFAULT_MODEL.LIGHT); // 학습 데이터 경량화 버전(웹 서비스에 적합함
//		this.nlp = new Komoran(DEFAULT_MODEL.FULL); //학습 데이터 전체 버전(일괄처리 : 배치 서비스에 적합함)

		log.info("난 톰캣이 부팅되면서 스프링 프레임워크가 자동 실행되었고, 스프링 실행될 때 nlp 변수에 Komoran 객체를 생성하여 저장하였다.");

		log.info(this.getClass().getName() + ".WordAnalysisService creater End!");
	}

	@Override
	public List<String> doWordNouns(String text) throws Exception {

		log.info(this.getClass().getName() + ".doWordAnalysis Start!");

		log.info("분석할 문장 : " + text);

		// 분석할 문장에 대해 정제(쓸데없는 특수문자 제거)
		String replace_text = text.replaceAll("[^가-힣a-zA-Z0-9]", " ");

		log.info("한국어, 영어, 숫자 제어 단어 모두 한칸으로 변환시킨 문장 : " + replace_text);

		// 분석할 문장의 앞 뒤에 존재할 수 있는 필요없는 공백제거
		String trim_text = replace_text.trim();

		log.info("분석할 문장의 앞 뒤에 존재할 수 있는 필요없는 공백제거 : " + trim_text);

		// 형태소분석 시작
		KomoranResult analyzeResultList = this.nlp.analyze(trim_text);

		// 형태소 분석 결과 중 명사만 가져오기
		List<String> rList = analyzeResultList.getNouns();

		if (rList == null) {
			rList = new ArrayList<String>();
		}

		// 분석 결과 확인을 위한 로그 찍기
		Iterator<String> it = rList.iterator();

		while (it.hasNext()) {

			// 추출된 명사
			String word = CmmUtil.nvl(it.next());

			log.info("word : " + word);
		}

		log.info(this.getClass().getName() + ".doWordAnalysis End!");

		return rList;
	}

	@Override
	public Map<String, Integer> doWordCount(List<String> pList) throws Exception {

		log.info(this.getClass().getName() + ".doWordCount Start!");

		if (pList == null) {
			pList = new ArrayList<String>();
		}

		// 단어 빈도수(사과, 3) 결과를 저장하기 위해 Map 객체 생성함
		Map<String, Integer> rMap = new HashMap<>();

		// List에 존재하는 중복되는 단어들의 중복제거를 위해 Set 데이터타입에 데이터를 저장함
		// rSet 변수는 중복된 데이터가 저장되지 않기 때문에 중복되지 않은 단어만 저장하고 나머지는 자동 삭제됨
		Set<String> rSet = new HashSet<String>(pList);

		// 중복이 제거된 단어 모음에 빈도수를 구하기 위해 반복문 사용함
		Iterator<String> it = rSet.iterator();

		while (it.hasNext()) {

			// 중복 제거된 단어
			String word = CmmUtil.nvl(it.next());

			// 단어가 중복 저장되어 있는 pList로부터 단어의 빈도수 가져오기
			int frequency = Collections.frequency(pList, word);

			log.info("word : " + word);
			log.info("frequency" + frequency);

			rMap.put(word, frequency);

		}

		log.info(this.getClass().getName() + ".doWordCount End!");

		return rMap;
	}

	@Override
	public Map<String, Integer> doWordAnalysis(String text) throws Exception {

		//네이버 뉴스 내용을 가져오기
		String newContext = newsCollectService.doNaverNewsContents(
				"https://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=101&oid=001&aid=0011955578");
		
			//문장의 명사를 추출하기 위한 형태소 분석 실행
		List<String> rList = this.doWordNouns(newContext);
		
		if (rList == null) {
			rList = new ArrayList<String>();
		}
		
		//추출된 명사 모음(리스트)의 명사 단어별 빈도수 계산
		Map<String, Integer> rMap = this.doWordCount(rList);
		
		if(rMap == null) {
			rMap = new HashMap<String, Integer>();
		}
		
		return rMap;
	}

}
