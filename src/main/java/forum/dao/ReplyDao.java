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

public class ReplyDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    //开启驼峰映射
    private BeanProcessor beanProcessor = new GenerousBeanProcessor();
    private RowProcessor processor = new BasicRowProcessor(beanProcessor);
    public int countTotalReply(int tId) {
        String sql = "select count(*) from reply where topic_id=?";
        Long count = null;
        try {
            count = (Long)queryRunner.query(sql,new ScalarHandler<>(),tId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count.intValue();
    }

    public List<Reply> findReply(int tId, int from, int pageSize) {
        String sql = "select * from reply where topic_id=? order by create_time asc limit ?,?";
        List<Reply> list = null;
        try {
            list = queryRunner.query(sql,new BeanListHandler<>(Reply.class,processor),tId,from,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int save(Reply reply) {
        String sql = "insert into reply(topic_id,floor,content,user_id,username,user_img,create_time,update_time,`delete`) "+
                "values(?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                reply.getTopicId(),
                reply.getFloor(),
                reply.getContent(),
                reply.getUserId(),
                reply.getUsername(),
                reply.getUserImg(),
                reply.getCreateTime(),
                reply.getUpDateTime(),
                reply.getDelete()
        };
        int rows = 0;
        try {
            rows = queryRunner.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
