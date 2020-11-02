package poly.persistance.mapper;

import config.Mapper;
import poly.dto.OcrDTO;

@Mapper("OcrMapper")
public interface IOcrMapper {

	void getReadforImageText(OcrDTO pDTO);

}
