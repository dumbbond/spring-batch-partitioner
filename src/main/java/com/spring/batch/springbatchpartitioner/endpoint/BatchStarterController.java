package com.spring.batch.springbatchpartitioner.endpoint;

import com.spring.batch.springbatchpartitioner.init.InsertData;
import com.spring.batch.springbatchpartitioner.starter.BatchStarter;
import org.elasticsearch.action.count.CountRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BatchStarterController.PATH)
public class BatchStarterController {

    public static final String PATH = "/api/v1/batch";


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private BatchStarter batchStarter;


    @Autowired
    private InsertData insertData;

    @GetMapping
    public void startBatch() throws Exception {

        insertData.insertDataIntoUserTable();
        batchStarter.run();

    }

    @GetMapping("/count")
    public long count() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("position").build();

        return elasticsearchTemplate.count(searchQuery);

    }


}
