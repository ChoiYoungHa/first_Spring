package poly.service;

public interface INewsCollectService {

	/*
	 * 네이버 뉴스 기사 크롤링으로 가져오기
	 * 
	 * @param 네이버 기사 URL
	 * @return 네이버 기사 내용
	 * 
	 * */
	String doNaverNewsContents(String url) throws Exception;
}
