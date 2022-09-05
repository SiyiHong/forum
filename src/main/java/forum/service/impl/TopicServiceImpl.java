package forum.service.impl;

import forum.dao.CategoryDao;
import forum.dao.ReplyDao;
import forum.dao.TopicDao;
import forum.domain.Category;
import forum.domain.Reply;
import forum.domain.Topic;
import forum.domain.User;
import forum.dto.PageDTO;
import forum.service.TopicService;

import java.util.Date;
import java.util.List;

public class TopicServiceImpl implements TopicService {

    private TopicDao topicDao = new TopicDao();
    private ReplyDao replyDao = new ReplyDao();
    private CategoryDao categoryDao = new CategoryDao();
    @Override
    public PageDTO<Topic> listTopicByCid(int cId, int page, int pageSize) {
        int totalRecordNum = topicDao.countTotalTopicByCid(cId);
        int from = (page-1)*pageSize;
        List<Topic> topicList = topicDao.findListByCid(cId,from,pageSize);
        PageDTO<Topic> pageDTO = new PageDTO<>(page, pageSize,totalRecordNum);
        pageDTO.setList(topicList);
        return pageDTO;
    }

    @Override
    public Topic findTopicById(int topicId) {
        return topicDao.findTopicById(topicId);
    }

    @Override
    public PageDTO<Reply> findReply(int topicId, int page, int pageSize) {
        int totalReply = replyDao.countTotalReply(topicId);
        int from = (page-1)*pageSize;
        List<Reply> replylist = replyDao.findReply(topicId,from,pageSize);
        PageDTO<Reply> pageDTO = new PageDTO<>(page,pageSize,totalReply);
        pageDTO.setList(replylist);
        return pageDTO;
    }

    @Override
    public int addTopic(User user, String title, String content, int cId) {
        Category category = categoryDao.findById(cId);
        if(category==null){return 0;}
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setUpdateTime(new Date());
        topic.setCreateTime(new Date());
        topic.setPv(1);
        topic.setDelete(0);
        topic.setUserId(user.getId());
        topic.setUsername(user.getUsername());
        topic.setUserImg(user.getImg());
        topic.setcId(cId);
        topic.setHot(0);
        int rows = 0;
        try {
            rows = topicDao.save(topic);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rows;
    }

    @Override
    public int replyByTid(User user, int tId, String content) {
        int floor = topicDao.findLatestFloor(tId);
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setCreateTime(new Date());
        reply.setUpDateTime(new Date());
        reply.setFloor(floor);
        reply.setTopicId(tId);
        reply.setUserId(user.getId());
        reply.setUsername(user.getUsername());
        reply.setUserImg(user.getImg());
        reply.setDelete(0);
        int rows = replyDao.save(reply);
        return rows;
    }

    @Override
    public void addOnePV(int topicId) {
        Topic topic = topicDao.findTopicById(topicId);
        int newPV = topic.getPv()+1;
        topicDao.updatePV(topicId,newPV,topic.getPv());
    }
}
