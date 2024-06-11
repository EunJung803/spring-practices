package fileupload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fileupload.service.FileUploadService;

@Controller
public class FileUploadContorller {
	
	private FileUploadService fileUploadService;
	
	public FileUploadContorller(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	@RequestMapping({"", "/form"})
	public String form() {
		return "form";
	}
	
	@RequestMapping("/upload")
	public String upload(
		@RequestParam(value="email") String email,
		@RequestParam(value="file1") MultipartFile file,
		Model model) {
		
		String url = fileUploadService.restore(file);
		model.addAttribute("url", url);		// 그 파일에 접근할 수 있는 url을 넘겨주기 같이
		
		return "result";
	}
}
