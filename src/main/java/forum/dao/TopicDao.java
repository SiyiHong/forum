package forum.dao;

import forum.domain.Reply;
import forum.domain.Topic;
import forum.dto.PageDTO;
import forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class TopicDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    //开启驼峰映射
    private BeanProcessor beanProcessor = new GenerousBeanProcessor();
    private RowProcessor processor = new BasicRowProcessor(beanProcessor);
    public int countTotalTopicByCid(int cId) {
        String sql = "select count(*) from topic where c_id=? and 'delete'=0";
        Long count = null;
        try {
            count = (Long)queryRunner.query(sql,new ScalarHandler<>(),cId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    public List<Topic> findListByCid(int cId, int from, int pageSize) {
        String sql = "select * from topic where c_id=? and 'delete'=0 order by update_time desc limit ?,?";
        List<Topic> list = null;
        try {
            list = queryRunner.query(sql,new BeanListHandler<>(Topic.class,processor),cId,from,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Topic findTopicById(int topicId) {
        String sql = "select * from topic where id=?";
        Topic topic = null;
        try {
            topic = queryRunner.query(sql,new BeanHandler<>(Topic.class,processor),topicId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topic;
    }

    public int save(Topic topic) throws Exception {
        String sql = "insert into topic(c_id,title,content,pv,user_id,username,user_img,create_time,update_time,hot,`delete`) "+
                "values(?,?,?,?,?,?,?,?,?,?,?)";

        Object[] params = {
                topic.getcId(),
                topic.getTitle(),
                topic.getContent(),
                topic.getPv(),
                topic.getUserId(),
                topic.getUsername(),
                topic.getUserImg(),
                topic.getCreateTime(),
                topic.getUpdateTime(),
                topic.getHot(),
                topic.getDelete()
        };
        int i=0;
        try {
            i = queryRunner.execute(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return i;
    }

    public int findLatestFloor(int tId) {
        Long floor = null;
        String sql = "select count(*) from reply where topic_id=?";
        try {
            floor = (Long)queryRunner.query(sql,new ScalarHandler<>(),tId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return floor.intValue()+1;
    }

    public int updatePV(int topicId, int newPV, int pv) {
        String sql = "update topic set pv=? where pv=? and id=?";
        int rows = 0;
        try {
            rows = queryRunner.update(sql,newPV,pv,topicId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
