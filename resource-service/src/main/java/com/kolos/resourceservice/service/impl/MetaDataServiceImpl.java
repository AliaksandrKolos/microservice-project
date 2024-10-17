package com.kolos.resourceservice.service.impl;

import com.kolos.resourceservice.service.MetaDataService;
import com.kolos.resourceservice.service.dto.MetaDataDto;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;

@Service
public class MetaDataServiceImpl implements MetaDataService {

    @Override
    public MetaDataDto getMetaData(byte[] data) {
        try {
            Metadata metadata = new Metadata();

            Mp3Parser mp3Parser = new Mp3Parser();
            mp3Parser.parse(new ByteArrayInputStream(data), new BodyContentHandler(), metadata);
            MetaDataDto metaDataDto = new MetaDataDto();
            metaDataDto.setResourceName(metadata.get("dc:title"));
            metaDataDto.setArtist(metadata.get("xmpDM:albumArtist"));
            metaDataDto.setAlbum(metadata.get("xmpDM:album"));
            metaDataDto.setLength(getDuration(metadata));
            metaDataDto.setYear(getYear(metadata));
            return metaDataDto;

        } catch (TikaException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer getYear(Metadata metadata) {
        String year = metadata.get("xmpDM:releaseDate");
        if (year != null) {
            return Integer.parseInt(year);
        }
        return null;
    }

    private String getDuration(Metadata metadata) {
        String duration = metadata.get("xmpDM:duration");
        if (duration == null) {
            return null;
        }
        double seconds = Double.parseDouble(duration);
        Duration time = Duration.ofSeconds((long) seconds);

        return String.format("%02d:%02d:%02d", time.toHours(), time.toMinutes() % 60, time.getSeconds() % 60);
    }



}
