package poly.persistance.mapper;

import config.Mapper;
import poly.dto.FoodDTO;


@Mapper("FoodMapper")
public interface IFoodMapper {
	
	//수집된 내용 db에 등록
	int InsertFood(FoodDTO pDTO) throws Exception;

	
	

}
