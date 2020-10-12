package poly.service;

import poly.dto.UserinfoDTO;



public interface IUserInfoService {
	
	// 회원 가입 하기
	int insertUserInfo(UserinfoDTO pDTO) throws Exception;

	// 로그인 하기
	int getUserLoginCheck(UserinfoDTO pDTO) throws Exception;

}
