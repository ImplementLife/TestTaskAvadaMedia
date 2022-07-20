package com.testTask.kinoCMS.entity.movie.dto;

import com.testTask.kinoCMS.entity.movie.Session;
import com.testTask.kinoCMS.entity.movie.Status;
import com.testTask.kinoCMS.entity.movie.Tag;
import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import lombok.Data;

import java.util.List;

@Data
public class MovieParentDTO {
    private Long id;
    private String name;
    private String description;
    private String trailerURL;

    private Status status;
    private List<Tag> tags;
    private List<Session> sessions;
    private SeoBlock seoBlock;
    private boolean showInMain;

    private Long dateCreate;
    private Long dateUpdate;
    private Long datePublic;
    private Long dateHide;
}
