package dev.mrkevr.user_service.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.user_service.exception.InvalidFileException;
import dev.mrkevr.user_service.validator.ValidImageFileValidator;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	@Value("${file_dir}")
	private String fileDirectory;
	
	private final ValidImageFileValidator imageFileValidator;

	public String uploadImageFileToDirectory(String username, MultipartFile file) throws IOException {
		
//		if (file.isEmpty() || (!file.getOriginalFilename().endsWith(".png") && !file.getOriginalFilename().endsWith(".jpg"))) {
//	
//			System.out.println("HEREEE");
//		
//			throw new InvalidFileException("Please upload a valid image file(.png, .jpg)");
//		}

		if (!imageFileValidator.isValid(file, null)) {
			throw new InvalidFileException("Please upload a valid image file(png/jpg, 2mb or less, 150x150px dimension");
		}
		
		String filePath = fileDirectory + File.separator + username + this.getFileExtension(file.getOriginalFilename());
		
		try {
			Files.copy(
				file.getInputStream(),
				Paths.get(filePath),
				StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {

			// throw custom exception here
			
			e.printStackTrace();
			return null;
		}
		
		return filePath;
	}
	
	/**
	 * Removes all characters before the last 'DOT' from the name.
	 */
	private String getFileExtension(String name) {
		String extension;
		try {
			extension = name.substring(name.lastIndexOf("."));
		} catch (Exception e) {
			extension = "";
		}
		return extension;
	}
}
