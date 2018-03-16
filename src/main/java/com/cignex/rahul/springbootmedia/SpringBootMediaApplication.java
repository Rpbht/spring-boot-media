package com.cignex.rahul.springbootmedia;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
@Controller
@RequestMapping("/")
public class SpringBootMediaApplication {

	@GetMapping
	public String home() {
		return "/user/upload";
	}

	@PostMapping("/abc")
	public String save(@RequestParam("file") MultipartFile file, Model model, HttpServletRequest httpServletRequest) {
		System.out.println(file);

		if (!file.isEmpty()) {
			try {
				byte[] data = file.getBytes();
				HttpSession session = httpServletRequest.getSession();
				String rootPath = session.getServletContext().getRealPath("/");
				
				File dir = new File(rootPath + File.separator + "Temp");
				
				if (!dir.exists()) {
					dir.mkdirs();
				}
				
				String filename = "IMG"+System.currentTimeMillis()+".jpg";
				String filePath  = dir.getPath()+File.separator+filename;
				System.out.println(filePath);
				
				File datafile = new File(filePath);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(datafile));
				bufferedOutputStream.write(data);
				bufferedOutputStream.close();
				
				
				System.out.println("uploaded: "+filename);
				model.addAttribute("images", filename);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return "/user/show";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMediaApplication.class, args);
	}
}
