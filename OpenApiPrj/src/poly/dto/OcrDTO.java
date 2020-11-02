package poly.dto;

public class OcrDTO {
	
	private String filePath; // 저장된 이미지 파일의 저장경로
	private String fileName; // 저장된 이미지 파일 이름
	private String textFromImage; // 저장된 이미지로부터 읽은 글씨
	private String org_file_name;
	private String ext;
	private String ocr_text;
	private String reg_id;
	private String reg_dt;
	private String chg_id;
	private String chg_dt;
	
	
	public String getOrg_file_name() {
		return org_file_name;
	}
	public void setOrg_file_name(String org_file_name) {
		this.org_file_name = org_file_name;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getOcr_text() {
		return ocr_text;
	}
	public void setOcr_text(String ocr_text) {
		this.ocr_text = ocr_text;
	}
	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	public String getChg_id() {
		return chg_id;
	}
	public void setChg_id(String chg_id) {
		this.chg_id = chg_id;
	}
	public String getChg_dt() {
		return chg_dt;
	}
	public void setChg_dt(String chg_dt) {
		this.chg_dt = chg_dt;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTextFromImage() {
		return textFromImage;
	}
	public void setTextFromImage(String textFromImage) {
		this.textFromImage = textFromImage;
	}
	
	

}
