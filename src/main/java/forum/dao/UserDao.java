package forum.dao;

import forum.domain.User;
import forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    private BeanProcessor beanProcessor = new GenerousBeanProcessor();
    private RowProcessor processor = new BasicRowProcessor(beanProcessor);
    public int save(User user) throws Exception {
        String sql = "insert into user (phone,pwd,sex,img,create_time,role,username) values(?,?,?,?,?,?,?)";
        Object[] params = {
              user.getPhone(),user.getPwd(),user.getSex(),user.getImg(),user.getCreateTime(),user.getRole(),user.getUsername()
        };
        int i;
        try {
            i=queryRunner.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception();
        }
        return i;
    }

    public User findByPhoneAndPwd(String phone, String md5pwd) {
        String sql = "select * from user where phone=? and pwd = ?";
        User user = null;
        try {
            user = queryRunner.query(sql,new BeanHandler<>(User.class,processor),phone,md5pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
