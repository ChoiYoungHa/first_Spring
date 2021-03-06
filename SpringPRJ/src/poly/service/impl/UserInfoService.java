package poly.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import poly.dto.MailDTO;
import poly.dto.UserinfoDTO;
import poly.persistance.mapper.IUserInfoMapper;
import poly.service.IMailService;
import poly.service.IUserInfoService;
import poly.util.CmmUtil;
import poly.util.EncryptUtil;

import static poly.util.CmmUtil.nvl;

@Service("UserInfoService")
public class UserInfoService implements IUserInfoService {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "UserInfoMapper")
	private IUserInfoMapper userInfoMapper;
	
	@Resource(name = "MailService")
	private IMailService mailService;

	@Override
	public int insertUserInfo(UserinfoDTO pDTO) throws Exception {
		
		int res = 0;
		
		if(pDTO == null) {
			pDTO = new UserinfoDTO();
		}
		
		UserinfoDTO rDTO = userInfoMapper.getUserExists(pDTO);
		
		if(rDTO == null) {
			rDTO = new UserinfoDTO();
		}
		
		//중복된 회원정보가 있는 경우 결과값을 2로 변경하고 , 더이상 작업을 진행 안함
		if(nvl(rDTO.getExists_yn()).equals("Y")) {
			res = 2;
		}else {
			
			//회원가입
			int success = userInfoMapper.insertUserInfo(pDTO);
			
			if(success > 0) {
				res = 1;
				
				MailDTO mDTO = new MailDTO();
				
				mDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));
				
				mDTO.setTitle("회원가입을 축하드립니다.");
				
				mDTO.setContents(CmmUtil.nvl(pDTO.getUser_name() + "님의 회원가입을 진심으로 축하드립니다."));
				
				mailService.doSendMail(mDTO);
			}else {
				res = 0;
				
			}
			
		}
		
		return res;
	}
	
	/*
	 * 로그인을 위해 아이디 비밀번호가 일치하는지 확인하기
	 * 
	 * @param UserInfoDTO 로그인을 위한 회원아이디, 비밀번호
	 * @return UserInfoDTO 로그인된 회원아이디 정보,
	 * 
	 */
	
	@Override
	public int getUserLoginCheck(UserinfoDTO pDTO) throws Exception{
		
		// 로그인 성공 1. 실패 0
		int res = 0;
		log.info("userService start");
		UserinfoDTO rDTO = userInfoMapper.getUserLoginCheck(pDTO);
		
		if(rDTO == null) {
			rDTO = new UserinfoDTO();
		}
		
		/*
		 * #####################################
		 * 		로그인 성공 여부 체크 시작!!
		 * #####################################
		 * 
		 * user InfoMapper로 부터 SELECT 쿼리의 결과로 회원 아이디를 받아왔다면,
		 * 로그인 성공
		 * 
		 * DTO의 변수에 값이 있는지 확인하기 처리속도 측면에서 가장 좋은 방법은 변수의 길이를
		 * 가져오는 것이다. 따라서, .length() 함수를 통해 회원아이디의 글자수를 가져와
		 * 0보다 큰지 비교한다. 0보다 크다면, 글자가 존재하는 것이기 때문에
		 * 존재한다.
		 */
		
		if(CmmUtil.nvl(rDTO.getUser_id()).length() > 0 ) {
			res = 1;
		}
		
		/*
		 * 
		 * ###############################
		 * 로그인 성공 여부 체크 끝!
		 * ###############################
		 */
		log.info("userService end");
		return res;
		
	}
	
	

}
