package poly.service;

import java.util.List;

import poly.dto.AccStatDTO;

public interface IAccStatService {
	
	//조회할 교통사고 정보
	List<AccStatDTO> getAccStatInfo(AccStatDTO pDTO) throws Exception;
}
