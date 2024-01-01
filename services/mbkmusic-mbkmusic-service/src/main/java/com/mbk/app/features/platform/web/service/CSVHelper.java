package com.mbk.app.features.platform.web.service;


import com.amazonaws.services.s3.AmazonS3;
import com.mbk.app.features.platform.data.model.experience.song.CreateSongRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.nio.charset.StandardCharsets;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Service
 @Slf4j
public class CSVHelper {


    public static String TYPE = "text/csv";
    @Autowired
    SongService songService;
    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;


    public static boolean hasCSVFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }
   // HashMap<String,
    public  List<List<String>> csvToSongDetails(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.US_ASCII));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();


            int numberOfRecordsInserted = 0;
            /*0 title
             * 1 singer
             * 2 uploadtype
             * 3 upload url/file name
             * 4 media audio/video
             * 5 composer
             * 6 recordigdate
             * 7 raaga
             * 8 taala
             * 9 song type
             * 10 description
             * 11 lyrics
             * 12 mridangam
             * 13 voilin
             * 14 morsing
             * 15 ghatam
             * 16 kanjeera
             * 17 flute
             * */


            // HashMap<String,List<String>>  logs =  new HashMap<>();
            List<List<String>> list = new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {
               // List<String> list2 = new ArrayList<>();
                CreateSongRequest payload = new CreateSongRequest();
                boolean flag = true;
                String message = "";

                //Title of the song
                if (csvRecord.get(0).length() > 0) {
                    payload.setTitle(csvRecord.get(0));
                } else {
                    flag = false;
                    message +="Title should not be empty.";
                    // message = "Title should not be empty, title: : " + csvRecord.get(0) ;
                   // list2.add(message);
                    //logs.putkey(0);
                    //logs.putvalue(message);

                }

                //singer of the song
                if (csvRecord.get(1).length() > 0) {
                    payload.setSinger(csvRecord.get(1));
                } else {
                    flag = false;
                    message += "Singer should not be empty.";
                //    list2.add(message);
                }

                //Upload type of the song
                String uploadType = "" + csvRecord.get(2);

                //URL/File name
                String Url = "";

                //Media type (audio/video)
                String media_description = "";

                if (uploadType.equalsIgnoreCase("YouTube")) {
                    if (csvRecord.get(3).length() > 0 && csvRecord.get(3).contains("youtube")) {
                        if (csvRecord.get(4).equalsIgnoreCase("audio")) {
                            Url = csvRecord.get(3);
                            media_description = csvRecord.get(4);
                        } else if (csvRecord.get(4).equalsIgnoreCase("video")) {
                            Url = csvRecord.get(3);
                            media_description = csvRecord.get(4);
                        } else {
                            flag = false;
                            message += "Media type should be either audio or video empty.";
                            //list2.add(message);
                        }
                    } else {
                        flag = false;
                        message += "Please provide valid youtube URL and upload type.";
                       // list2.add(message);
                    }
                } else if (uploadType.equalsIgnoreCase("Audio upload")) {
                    if (csvRecord.get(3).length() > 0 && (csvRecord.get(3).endsWith(".mp3") || csvRecord.get(3).endsWith(".MP3")) && csvRecord.get(4).equalsIgnoreCase("Audio") && s3Client.doesObjectExist(bucketName, csvRecord.get(3))) {

                        Url = String.valueOf(s3Client.getUrl(bucketName, csvRecord.get(3)));
                        media_description = csvRecord.get(4);

                    } else {
                        flag = false;
                        message += "Please provide valid filename, upload type, Media.";
                     //   list2.add(message);
                    }
                } else if (uploadType.equalsIgnoreCase("Video upload")) {
                    if (csvRecord.get(3).length() > 0 && (csvRecord.get(3).endsWith(".mp4") || csvRecord.get(3).endsWith(".MP4")) && csvRecord.get(4).equalsIgnoreCase("Video") && s3Client.doesObjectExist(bucketName, csvRecord.get(3))) {

                        Url = String.valueOf(s3Client.getUrl(bucketName, csvRecord.get(3)));
                        media_description = csvRecord.get(4);
                    } else {
                        flag = false;
                        message += "Please provide valid filename, upload type, Media.";
                        //list2.add(message);
                    }
                } else {
                    flag = false;
                    message += "Please provide the upload type.";
                    //list2.add(message);
                }
                //composer
                payload.setComposer(csvRecord.get(5));

                //recording date

                if (csvRecord.get(6).length() > 0) {
                    payload.setRecordingDate(new SimpleDateFormat("dd/MM/yyyy").parse(csvRecord.get(6)));
                } else {
                    flag = false;
                    message += "RecordingDate should not be empty.";
                   // list2.add(message);
                    // message = "RecordingDate should not be empty, Line:" + csvRecord.get(0);

                }

                //Raaga

                payload.setRaagaId(csvRecord.get(7));
//
                //Taala

                payload.setTaalaId(csvRecord.get(8));
//

                //Song Type

                payload.setSongTypeId(csvRecord.get(9));
//

                //Description
                if (csvRecord.get(10).length() > 0) {
                    payload.setDescription(csvRecord.get(10));
                } else {
                    flag = false;
                    message += "Description should not be empty.";
                    //list2.add(message);
                }
                //Lyrics
                payload.setLyrics(csvRecord.get(11));

                //Mridangam
                payload.setMridangamAccompaniments(csvRecord.get(12));
                //Violion
                payload.setViolionAccompaniments(csvRecord.get(13));
                //Morsing
                payload.setMorsingAccompaniments(csvRecord.get(14));
                //Ghatam
                payload.setGhatamAccompaniments(csvRecord.get(15));
                //kanjeera
                payload.setKanjeeraAccompaniments(csvRecord.get(16));
                //Flute
                payload.setFluteAccompaniments(csvRecord.get(17));

                payload.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                if (flag) {
                    songService.createSong(payload, media_description, Url);
                    numberOfRecordsInserted++;
                } else {
                    String key = "";
                    List<String>  list2 =  new ArrayList<>();
                    if (csvRecord.get(0).length() > 0) {
                        // logs.put(csvRecord.getRecordNumber()+". "+csvRecord.get(0), list);
                        list2.add(" Line Number " +csvRecord.getRecordNumber() + ". " + csvRecord.get(0) +":- "+message );
                        list2.addAll(csvRecord.toMap().values());
                    } else {
                        // logs.put(csvRecord.getRecordNumber() + ". No Title", list);
                        list2.add(" Line Number "+csvRecord.getRecordNumber() + ". No Title :- " +message );
                        list2.addAll(csvRecord.toMap().values());
                    }

                    list.add(list2);
                    CSVHelper.LOGGER.info("Record skipped : {}" + message);
                }

            }

            CSVHelper.LOGGER.info("successfully loaded data of records, Line: : {}", numberOfRecordsInserted);
      /*if(list2.isEmpty())
     {
         String string="successfully loaded data";
         list1.add(string);
         return list1;
     }
     else {
         list1.addAll(list2);
         return list1;
     }*/
           if(list.size()<=0){
              List<String> list2 = new ArrayList<>();
              list2.add("Successfully loaded data");
               list.add(list2);


           }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }

    }


}