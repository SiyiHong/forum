package forum.dao;

import forum.domain.Category;
import forum.util.DataSourceUtil;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtil.getDataSource());
    //开启驼峰映射
    private BeanProcessor beanProcessor = new GenerousBeanProcessor();
    private RowProcessor processor = new BasicRowProcessor(beanProcessor);

    //根据id找分类
    public Category findById(int id){
        String sql = "select * from category where id=?";
        Category category = null;
        try {
            category = queryRunner.query(sql,new BeanHandler<>(Category.class,processor),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
    public List<Category> list(){
        String sql = "select * from category order by weight desc";
        List<Category> list = new ArrayList<>();
        try {
            list = queryRunner.query(sql,new BeanListHandler<>(Category.class,processor));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
