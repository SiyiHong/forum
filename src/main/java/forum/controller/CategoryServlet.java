package forum.controller;

import forum.domain.Category;
import forum.service.CategoryService;
import forum.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "categoryServlet",urlPatterns = {"/category"})
public class CategoryServlet extends BaseServlet{
    private CategoryService categoryService = new CategoryServiceImpl();
    public void list(HttpServletRequest req, HttpServletResponse resp){
        List<Category> list = categoryService.list();
        System.out.println(list.toString());
        req.setAttribute("categoryList",list);
    }
}
