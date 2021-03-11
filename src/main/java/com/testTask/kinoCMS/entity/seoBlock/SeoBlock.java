package com.testTask.kinoCMS.entity.seoBlock;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "seo_block")
public class SeoBlock {
    @Hidden
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seoURL;
    private String seoTitle;
    private String seoKeywords;
    private String seoDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof SeoBlock) {
            SeoBlock otherObject = (SeoBlock) o;
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
