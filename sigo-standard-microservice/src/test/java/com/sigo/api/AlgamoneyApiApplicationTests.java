package com.sigo.api;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigoApiApplication.class)
public class AlgamoneyApiApplicationTests {

	@Test
	public void contextLoads() throws IOException {

		String username = "***REMOVED***";
		String password = "***REMOVED***";
		String host = "***REMOVED***";
		int port = 443;
	

		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(new HttpHost(host,port,"https"))
		           .setHttpClientConfigCallback(httpAsyncClientBuilder -> 
		    httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
		                       .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));
		/*
		 * 
		 * CREATEEE
		 
		
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("user", "2");
		jsonMap.put("postDate", new Date());
		jsonMap.put("message",
				"{\"codigo\":1,\"orgao\":\"ABNT\",\"titulo\":\"NBR ISO 9001\",\"resumo\":\"Sistemas de gestão da qualidade -\nRequisitos\",\"descricao\":\"\",\"versao\":\"2000\",\"numeroEdicao\":\"1\",\"dataEdicao\":\"2000-12-01\",\"inicioValidade\":\"2001-01-29\",\"categoria\":\"QUALIDADE\"}");
		IndexRequest indexRequest = new IndexRequest("standards").id("1").source(jsonMap);

		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

		
		System.out.println("aquiiiiiiiiiiiiiii");
		String index = indexResponse.getIndex();
		String id = indexResponse.getId();

		System.out.println(indexResponse.getResult());//CREATED == success
*/
		
		
		/*GETTT
		
		GetRequest getRequest = new GetRequest(
		        "standards", 
		        "1"); 
		System.out.println("hereeeeeeeee");
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		String message = getResponse.getSourceAsString(); 
		System.out.println(message);
		*/
		
		
		System.out.println("hereeeeeeeee");

		
		SearchRequest searchRequest = new SearchRequest("standards"); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.from(0); 
		searchSourceBuilder.size(10); 
		searchSourceBuilder.query(QueryBuilders.termQuery("message", "qualidade")); 
		searchRequest.source(searchSourceBuilder);
		
		
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			System.out.println(sourceAsString);
		}
		
		client.close();

	}

}
