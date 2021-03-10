package com.testTask.kinoCMS.entity.movie;

import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
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

    @Transient
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    private MultipartFile mainImage;

    @Transient
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    private MultipartFile[] images;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String mainImageName;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
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
