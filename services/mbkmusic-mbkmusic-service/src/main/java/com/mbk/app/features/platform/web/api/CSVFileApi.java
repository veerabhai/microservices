package com.mbk.app.features.platform.web.api;

import com.mbk.app.commons.web.api.AuthAbstractApi;
import com.mbk.app.features.platform.data.model.experience.song.Song;
import com.mbk.app.features.platform.web.service.CSVHelper;
import com.mbk.app.features.platform.web.service.CSVService;
import com.mbk.app.features.platform.web.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(CSVFileApi.rootEndPoint)
public class CSVFileApi extends AuthAbstractApi {

    /** Root end point. */
    public static final String rootEndPoint = "/mbkmusic-mbkmusic";

    /** Service implementation of type {@link SongService}. */


    /**
     * This API provides the capability to add a new instance of type {@link
     * com.mbk.app.features.platform.data.model.persistence.SongEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.mbk.app.features.platform.data.model.persistence.SongEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Song}.
     */
    /*This API deals with the CSV file for bulk load*/
    @Autowired
    private CSVService fileService;

    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping("/uploadCSV")
   // public HashMap<String, List<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        public  List<List<String>> uploadFile(@RequestParam("file") MultipartFile file){
       // public String[] uploadFile(@RequestParam("file") MultipartFile file)
        //String message = "";
        //HashMap<String, List<String>> logs = new HashMap<String, List<String>>();
        List<List<String>> list = new ArrayList<>();
        if (CSVHelper.hasCSVFormat(file)) {
            try{
                 list = fileService.save(file);}
            catch (Exception e){
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        }

        return  list;
    }
}