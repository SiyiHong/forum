package forum.service;

import forum.domain.Reply;
import forum.domain.Topic;
import forum.domain.User;
import forum.dto.PageDTO;

public interface TopicService {
    PageDTO<Topic> listTopicByCid(int cid, int page,int pageSize);

    Topic findTopicById(int topicId);

    PageDTO<Reply> findReply(int topicId, int page, int pageSize);

    int addTopic(User user, String title, String content, int cId);

    int replyByTid(User user, int tId, String content);

    void addOnePV(int topicId);
}
