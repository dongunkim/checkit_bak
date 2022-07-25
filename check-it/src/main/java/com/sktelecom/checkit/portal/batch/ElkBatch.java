package com.sktelecom.checkit.portal.batch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 관련 라이브러리
 * httpclient
 */
public class ElkBatch{

	public void elkTest() throws Exception {
		String ip="150.206.14.237";
		int port = 9200;
		String id = "";
		String pw = "";
		String index = ""; //"tman_server"/"devsecops"/"devsecops_blackduck_all";
		
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id,pw));
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
			new HttpHost(ip,port))
			.setHttpClientConfigCallback(new HttpClientConfigCallback(){
			@Override
			public HttpAsyncClientBuilder customizeHttpClient(
			HttpAsyncClientBuilder httpClientBuilder){
			return httpClientBuilder
				.setDefaultCredentialsProvider(credentialsProvider);
			}
		}));
		
		try{
			// 스크롤 조회 결과
			final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
			SearchRequest searchRequest = new SearchRequest(index);
			searchRequest.scroll(scroll);
			
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchAllQuery());
			//searchSourceBuilder.query(QueryBuilders.termQuery("server_toolType", "SAST"));
			searchSourceBuilder.size(10000); // 스크롤당 데이터 건수
			searchRequest.source(searchSourceBuilder);
			
			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			String scrollId = searchResponse.getScrollId();
			TotalHits total = searchResponse.getHits().getTotalHits(); // 총 조회 건수
			
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			while(searchHits != null &&  searchHits.length > 0){ //스크롤당 데이터 건수
				SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
				scrollRequest.scroll(scroll);
				searchResponse = client.scroll(scrollRequest,RequestOptions.DEFAULT);
				scrollId = searchResponse.getScrollId();
				searchHits = searchResponse.getHits().getHits();
//				for(SearchHit hit : searchHits){
//					System.out.println(hit.getSourceAsString()); // 조회 겨로가 데이터
//				}
			}
			
			ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
			clearScrollRequest.addScrollId(scrollId);
			ClearScrollResponse clearScrllResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
			boolean succeeded = clearScrllResponse.isSucceeded();
		
		} catch(Exception e){
			System.out.println(e);
		}finally{
			client.close();
		}
	}
}
