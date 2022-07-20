package com.testTask.kinoCMS.entity.news;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testTask.kinoCMS.entity.image.Image;
import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.IndexColumn;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String mainImageName;

    private String videoURL;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @IndexColumn(name = "images_id")
    private Image[] imagesPaths;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "seo_block_id")
    private SeoBlock seoBlock;

    @Transient
    private MultipartFile mainImage;

    @Transient
    private MultipartFile[] images;

    @Transient
    private String[] imagesNames;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof News) {
            News otherObject = (News) o;
            return this.id.equals(otherObject.id);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
