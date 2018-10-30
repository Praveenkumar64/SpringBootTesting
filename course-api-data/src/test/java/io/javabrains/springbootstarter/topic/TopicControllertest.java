package io.javabrains.springbootstarter.topic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TopicController.class, secure = false)
public class TopicControllertest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TopicService topicService;

	Topic mockTopic = new Topic("js", "js framework", "describe js");
	String exampleCourseJson = "{\"id\":\"js\",\"name\":\"jsframework\",\"description\":\"describe js\"}";

  // GET
	@Test
	public void GETtopic() throws Exception {

		Mockito.when(topicService.getTopic(Mockito.anyString())).thenReturn(mockTopic);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/topics/id").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "{\"id\":\"js\",\"name\":\"js framework\",\"description\":\"describe js\"}";

		// {"id":"Course1","name":"Spring","description":"10 Steps"}
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(),false);
		

	}
	
 //GET
	
	@Test
	public void GETbyId() throws Exception {
		mockMvc.perform(get("/topics/id").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", Matchers.is("js")))
		.andExpect(jsonPath("$.name",Matchers.is("js framework")))
		.andExpect(jsonPath("$.description",Matchers.is("describe js")));	
		//.andDo(MockMvcResultHandlers.print());
	}

 // POST

	@Test
	public void addTopictest() throws Exception {
		String json= "{\n" +
				" \"id\":\"js\",\n" +
	            " \"name\":\"js framework\",\n" +
				" \"description\":\"describe js\"\n" +
	            "}";
		mockMvc.perform(post("/topics/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.id",Matchers.is("js")))
			.andExpect(jsonPath("$.name",Matchers.is("js framework")))
			.andExpect(jsonPath("$.description",Matchers.is("describe js")));
			
			
	}

 //	SAMPLE_POST_CHECK
	@Test
	public void shouldFindTheResource() throws Exception{
		this.mockMvc
		.perform(
				post("/topics").content(exampleCourseJson)
						.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is(200));
	}

 //	UPDATE
	@Test
	public void testJsonController() throws Exception {
		String id = "js";
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/topics/" + id)
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(getnameInJson(id));
		this.mockMvc.perform(builder).andExpect(status().is(200))
				.andExpect(jsonPath("$.id", Matchers.is("js")))
				.andExpect(jsonPath("$.name", Matchers.is("js framework")))
				.andExpect(jsonPath("$.description", Matchers.is("describe js")))

				.andDo(MockMvcResultHandlers.print());
	}

	private String getnameInJson(String id) {

		return "{\"id\":\"" + id + "\", \"name\":\"js framework\",\"description\":\"describe js\"}";
	}
	

 //	DELETE 
	 @Test
	public void deleteTopictest() throws Exception {
		this.mockMvc.perform(delete("/topics/{id}","").contentType(MediaType.APPLICATION_JSON))
	 				.andExpect(status().is(200));

	 }
}
