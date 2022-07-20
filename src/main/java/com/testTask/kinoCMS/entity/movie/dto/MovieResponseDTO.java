package com.testTask.kinoCMS.entity.movie.dto;

import lombok.Data;

@Data
public class MovieResponseDTO extends MovieParentDTO {
    private String mainImageName;
    private String[] imagesNames;
}
