package org.example.labreservationsystem.repository;
import org.example.labreservationsystem.dto.NewsDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News,String> {
    @Query("""
    select title,author,content,update_time from news
""")
    List<NewsDTO> findAllNews();

}