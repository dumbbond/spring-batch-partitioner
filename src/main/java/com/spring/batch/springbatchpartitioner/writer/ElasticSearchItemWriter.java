package com.spring.batch.springbatchpartitioner.writer;

import java.util.ArrayList;
import java.util.List;

import com.spring.batch.springbatchpartitioner.dto.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

/**
 * Created by NassiB on 18/01/2018.
 */
public class ElasticSearchItemWriter implements ItemWriter<User> {

    public ElasticSearchItemWriter(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Autowired
    public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void write(List<? extends User> list) throws Exception {
        System.out.println("ElasticSearchItemWriter " + list.size());
        elasticsearchTemplate.bulkIndex(getIndexQueries(list));
        elasticsearchTemplate.refresh(User.class);

    }


    private List<IndexQuery> getIndexQueries(List<? extends User> sampleEntities) {

        List<IndexQuery> indexQueries = new ArrayList<>();
        for (User sampleEntity : sampleEntities) {

            indexQueries.add(new IndexQueryBuilder()
                    .withId(String.valueOf(sampleEntity.getId()))
                    .withObject(sampleEntity).build());
        }
        return indexQueries;
    }
}
