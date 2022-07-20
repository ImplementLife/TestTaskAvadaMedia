package com.testTask.kinoCMS.entity.movie;

import com.testTask.kinoCMS.entity.image.Image;
import com.testTask.kinoCMS.entity.seoBlock.SeoBlock;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.IndexColumn;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {

    //region Primitive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    private Date dateCreate;

    private Date dateUpdate;

    private Date datePublic;

    private Date dateHide;

    private String mainImageName;

    private String trailerURL;

    private boolean showInMain;

    //endregion

    //region Mappings
    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn(name = "id")
    private List<Tag> tags;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "id")
    private List<Session> sessions;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "id")
    private Image[] imagesPaths;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "seo_block_id")
    private SeoBlock seoBlock;

    //endregion

    //region Transient
    @Transient
    private MultipartFile mainImage;

    @Transient
    private MultipartFile[] images;

    @Transient
    private String[] imagesNames;

    //endregion

    //region Object methods
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

    //endregion

}
