package com.mbk.app.features.platform.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Service
public class CSVService {
	

	@Autowired
    CSVHelper csvHelper;
    //public HashMap<String, List<String>> save(MultipartFile file)
	public  List<List<String>> save(MultipartFile file) {
    try {
      return csvHelper.csvToSongDetails(file.getInputStream());
    } catch (Exception e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }
}