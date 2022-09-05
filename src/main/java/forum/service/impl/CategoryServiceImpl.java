package forum.service.impl;

import forum.dao.CategoryDao;
import forum.domain.Category;
import forum.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDao();
    @Override
    public List<Category> list() {
        return categoryDao.list();
    }
}
