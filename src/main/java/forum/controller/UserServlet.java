package forum.controller;

import forum.domain.User;
import forum.service.UserService;
import forum.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "userServlet", urlPatterns = {"/user"})
public class UserServlet extends BaseServlet{
    private UserService userService = new UserServiceImpl();
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String phone = req.getParameter("phone");
        String pwd = req.getParameter("pwd");
        User user = userService.login(phone,pwd);
        if(user!=null){
            req.getSession().setAttribute("loginUser",user);
            //跳转页面
            resp.sendRedirect("/topic?method=list&c_id=1");
        }else{
            req.setAttribute("msg","用户名或密码错误");
            req.getRequestDispatcher("/user/login.jsp").forward(req,resp);
        }
    }
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect("/topic?method=list&c_id=1");
        //页面跳转
    }
    public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        Map<String,String[]> map = req.getParameterMap();//映射req内容到user类
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        int i = userService.register(user);
        if(i>0){
            //注册成功,跳转到登录界面 TODO
            req.getRequestDispatcher("/user/login.jsp").forward(req,resp);
        }else {
            //注册失败，跳转到注册界面 TODO
            req.getRequestDispatcher("/user/register.jsp").forward(req,resp);
        }
    }
}
