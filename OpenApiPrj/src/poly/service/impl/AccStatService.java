package poly.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import poly.dto.AccStatDTO;
import poly.persistance.mapper.IAccStatMapper;
import poly.service.IAccStatService;

@Service("AccStatService")
public class AccStatService implements IAccStatService {
	
	@Resource(name="AccStatMapper")
	private IAccStatMapper accStatMapper;
	
	//로그 파일 생성 및 로그 출력을 위한 log4j 프레임워크의 자바객체
	private Logger log = Logger.getLogger(this.getClass());
	
	/*
	 * 교통사고 정보 가져오기
	 */

	@Override
	public List<AccStatDTO> getAccStatInfo(AccStatDTO pDTO) throws Exception {
		
		log.info("AccStatInfo Service Start!");
		
		//교통사고 조회
		List<AccStatDTO> rList = accStatMapper.getAccStatInfo(pDTO);
		
		//참조형 변수는 무조건 오류 방지를 위해 null 처리한다.
		if(rList == null) {
			rList = new ArrayList <AccStatDTO>();
		}
		
		
		//로그찍기 
		log.info("AccStat Service End!");

		return rList;
	}
	


}
