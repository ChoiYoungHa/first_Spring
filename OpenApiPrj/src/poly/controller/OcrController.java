package poly.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import poly.dto.OcrDTO;
import poly.service.IOcrService;
import poly.util.CmmUtil;
import poly.util.DateUtil;
import poly.util.FileUtil;

@Controller
public class OcrController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	/*
	 * 비지니스 로직(중요 로직을 수행하기 위해 사용되는 서비스를 메모리에 적재(싱글톤 패턴 적용)
	 */
	
	@Resource(name = "OcrService")
	private IOcrService orcService;
	
	// 업로드되는 파일이 저장되는 기본폴더 설정(자바에서 경로는 /로 표현함)
	final private String FILE_UPLOAD_SAVE_PATH = "c:/upload";
	
	/*
	 * 이미지 인식을 위한 파일 업로드 화면 호출
	 */
	@RequestMapping(value="ocr/imageFileUpload")
	public String Index() {
		log.info("imageFileUpload!");
		return "/ocr/ImageFileUpload";
	}
	
	//@RequestParam이 multiparfile에 저장되는 name명을 담아줌
	@RequestMapping(value="/ocr/getReadforImageText")
	

	public String ReadforImageText(ServletRequest request, ServletResponse response, ModelMap model,
		@RequestParam(value="fileUpload") MultipartFile mf) throws Exception{
		
		//ocr 실행결과
		String res ="";
		
		//업로드하는 실제 파일명
		//다운로드 기능 구현 시, 임의로 정의된 파일명을 원래대로 만들어주기 위한 목적
		String originalFileName = mf.getOriginalFilename();
		String reg_id = "ChoiYoungHa";
		
		//파일 확장자 가져오기
		String ext = originalFileName.substring(originalFileName.lastIndexOf(".")+1, originalFileName.length()).toLowerCase();
		
		//이미지 파일만 실행되도록 함
		if(ext.equals("jpeg") || ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {
			
			// 웹서버에 저장되는 파일이름
			// 업로드하는 파일 이름에 한글, 특수문자들이 저장될수있기 때문에 강제로 영어와 숫자로 구성된 파일명으로 변경해서 저장한다.
			// 리눅스나 유닉스 등 운영체제는 다국어 지원에 취약하기 때문이다.
			
			String saveFileName = DateUtil.getDateTime("24hhmmss") + "." + ext;
			
			//웹서버에 업로드한 파일 저장하는 물리적 경로
			String saveFilePath = FileUtil.mkdirForDate(FILE_UPLOAD_SAVE_PATH);
			
			String fullFileInfo = saveFilePath + "/" + saveFileName;
			
			//정상적으로 값이 생성되었는지 찍어서 확인
			log.info("ext : " + ext);
			log.info("saveFileName : " + saveFileName);
			log.info("saveFilePath : " + saveFilePath);
			log.info("fullFileInfo : " + fullFileInfo);
			
			// 업로드 되는 파일을 서버에 저장
			mf.transferTo(new File(fullFileInfo));
			
			OcrDTO pDTO = new OcrDTO();
			
			pDTO.setFileName(saveFileName); // 서버에저장될 파일이름
			pDTO.setFilePath(saveFilePath); // 서버에 저장될 파일경로
			pDTO.setOrg_file_name(originalFileName); // 원래 파일이름
			pDTO.setExt(ext); // 파일확장자
			pDTO.setReg_id(reg_id); //작성자
			
			
			
			OcrDTO rDTO = orcService.getReadforImageText(pDTO);
			
			if(rDTO == null) {
				rDTO = new OcrDTO();
			}
			
			res = CmmUtil.nvl(rDTO.getTextFromImage());
			
			rDTO = null;
			pDTO = null;
			
		}else {
			res = "이미지 파일이 아니라서 인식이 불가능합니다.";
		}
		
		// 크롤링 결과 넣어주기
		model.addAttribute("res",res);
		
		
		log.info("getReadForImageText end!");
		
		return "ocr/TextFromImage";
	

}
	
}
