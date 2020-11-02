package poly.persistance.mapper;

import java.util.List;

import config.Mapper;
import poly.dto.AccStatDTO;

@Mapper("AccStatMapper")
public interface IAccStatMapper {
	
	//조회할 교통사고 정보
	List<AccStatDTO> getAccStatInfo(AccStatDTO pDTO) throws Exception;
	
	

}
