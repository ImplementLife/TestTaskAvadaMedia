package com.testTask.kinoCMS.entity.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.testTask.kinoCMS.entity.image.Image;
import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.IndexColumn;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "movies")
@Schema(name = "Movie")
public class Movie {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @Transient
    private MultipartFile mainImage;

    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @Transient
    private MultipartFile[] images;

    @Hidden
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @IndexColumn(name = "images_id")
    private Image[] imagesPaths;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Transient
    private String mainImageName;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Transient
    private String[] imagesNames;

    private String trailerURL;

    private boolean type2d;
    private boolean type3d;
    private boolean typeIMAX;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "seo_block_id")
    private SeoBlock seoBlock;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Movie) {
            Movie otherObject = (Movie) o;
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
