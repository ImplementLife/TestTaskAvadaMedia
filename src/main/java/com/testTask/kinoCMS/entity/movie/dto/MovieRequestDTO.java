package com.testTask.kinoCMS.entity.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testTask.kinoCMS.entity.movie.Status;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MovieRequestDTO extends MovieParentDTO {
    @JsonIgnore
    private Status status;
    private Long statusId;

    //@Size(max = 1_000)
    private MultipartFile mainImage;
    private MultipartFile[] images;
}
