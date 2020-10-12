package poly.persistance.mapper;

import config.Mapper;
import poly.dto.UserinfoDTO;

@Mapper("UserInfoMapper")
public interface IUserInfoMapper {

	// 회원가입전 중복체크하기 (DB조회)
	UserinfoDTO getUserExists(UserinfoDTO pDTO) throws Exception;

	// 회원가입하기 회원정보 등록하기
	int insertUserInfo(UserinfoDTO pDTO) throws Exception;
	
	// 로그인을 위한 아이디와 일치하는지 확인하기
	UserinfoDTO getUserLoginCheck(UserinfoDTO pDTO) throws Exception;
	
	

}
