package fileupload.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	private static String SAVE_PATH = "/Users/eunjung/Desktop/poscodx2024/DoWork/fileupload-files"; 		// 맥은 주소 지정 필요
	private static String URL_PATH = "/images"; 					// 리소스 맵핑, 내부에 있는 리소스를 특정 url로 맵핑, 이 url로 들어오면 -> 이 디렉토리에서 찾도록 함
	
	public String restore(MultipartFile file) {
		String url = null;		// 목적은 이 url을 만들어 내는 것
		
		try {
			File uploadDirectory = new File(SAVE_PATH);
			
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}
			
			if(file.isEmpty()) {
				return url;
			}
			
			/* 여기서부터는 쭉 저장하는 과정 */
			
			// 저장 전에 multipart에서 정보 몇가지 가져오기
			String originFilename = file.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".")+1);
			String saveFilename = generateSaveFilename(extName);		
			Long fileSize = file.getSize();
			
			System.out.println("#######" + originFilename);
			System.out.println("#######" + saveFilename);
			System.out.println("#######" + fileSize);
			
			byte[] data = file.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();
			
			url = URL_PATH + "/" + saveFilename;
			
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
		
		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(calendar.YEAR);
		filename += calendar.get(calendar.MONTH);
		filename += calendar.get(calendar.DATE);
		filename += calendar.get(calendar.HOUR);
		filename += calendar.get(calendar.MINUTE);
		filename += calendar.get(calendar.SECOND);
		filename += calendar.get(calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}

	
}
