package com.testTask.kinoCMS.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.testTask.kinoCMS.entity.news.News;
import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "CreateNewsDTO")
public class CreateNewsDTO {
    public News news;
    public SeoBlock seoBlock;
}
