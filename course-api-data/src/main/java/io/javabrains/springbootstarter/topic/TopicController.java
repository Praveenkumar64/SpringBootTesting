package io.javabrains.springbootstarter.topic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {
	@Autowired
	private TopicService topicService;

	@GetMapping(value = "/topics/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Topic topics() {
		return new Topic("js", "js framework", "describe js");
	}

	@RequestMapping("/topics")
	public List<Topic> getAllTopics() {
		return topicService.getAllTopics();
	}

	@RequestMapping("/topics/{id}")
	public Topic getTopic(@PathVariable String id) {
		return topicService.getTopic(id); 
		//return new Topic("js", "js framework", "describe js"); //Testing
		
	}

	@RequestMapping(method = RequestMethod.POST, value = "/topics")
	public Topic addTopic(@RequestBody Topic topic) {
		
		topicService.addTopic(topic);
		return topic;  //Testing
		
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/topics/{id}")
	public Topic UpdateTopic(@RequestBody Topic topic, @PathVariable String id) {
		topicService.updateTopic(id, topic);
		return topic;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/topics/{id}")
	public void deleteTopic(@PathVariable String id) {
		topicService.deleteTopic(id);
	}

}
