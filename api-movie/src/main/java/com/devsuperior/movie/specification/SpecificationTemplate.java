package com.devsuperior.movie.specification;

import com.devsuperior.movie.entities.Genre;
import com.devsuperior.movie.entities.Movie;
import com.devsuperior.movie.entities.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @And(
            @Spec(path = "email", spec = Like.class)
    )
    public interface UserSpec extends Specification<User>{
    }

    @And({
            @Spec(path = "title", spec = Like.class),
            @Spec(path = "subTitle", spec = Like.class),
            @Spec(path = "yearOfRelease", spec = Equal.class)
    })
    public interface MovieSpec extends Specification<Movie>{
    }

    @And(
            @Spec(path = "name", spec = Like.class)
    )
    public interface GenreSpec extends Specification<Genre>{
    }
}
