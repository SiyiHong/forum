package forum.controller;

import forum.domain.Reply;
import forum.domain.Topic;
import forum.domain.User;
import forum.dto.PageDTO;
import forum.service.CategoryService;
import forum.service.TopicService;
import forum.service.impl.CategoryServiceImpl;
import forum.service.impl.TopicServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "topicServlet", urlPatterns = {"/topic"})
public class TopicServlet extends BaseServlet{
    private TopicService topicService = new TopicServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();
    private static final int pageSize=2;
    //查询某一类别下的话题列表
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cId = Integer.parseInt(req.getParameter("c_id"));
        int page = 1;
        String current = req.getParameter("page");
        if(current!=null&&current!=""){
            page=Integer.parseInt(current);
        }
        PageDTO<Topic> pageDTO = topicService.listTopicByCid(cId,page,pageSize);
        System.out.println(pageDTO.toString());
        req.getSession().setAttribute("categoryList",categoryService.list());
        req.setAttribute("topicPage",pageDTO);
        //跳转到首页
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }
    //查询某一话题及其回复
    public void findDetailById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //拿到topic相关信息
        int topicId = Integer.parseInt(req.getParameter("topic_id"));
        Topic topic = topicService.findTopicById(topicId);

        //处理浏览量，按session区分
        String sessionReadKey = "is_read_"+topicId;
        Boolean isRead = (Boolean) req.getSession().getAttribute(sessionReadKey);
        if(isRead==null){//未读过，则浏览量+1并设置Key=true
            req.getSession().setAttribute(sessionReadKey,true);
            topicService.addOnePV(topicId);
        }

        //获取评论信息（分页展示）
        int page = 1;
        String current = req.getParameter("page");
        if(current!=null&&current!=""){
            page=Integer.parseInt(current);
        }
        PageDTO<Reply> pageDTO = topicService.findReply(topicId,page,pageSize);
        System.out.println(pageDTO.toString());
        req.setAttribute("topic",topic);
        req.setAttribute("replyPage",pageDTO);
        req.getRequestDispatcher("/topic_detail.jsp").forward(req,resp);
    }
    public void addTopic(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("loginUser");
        if(user==null){
            req.setAttribute("msg","请登录");
            resp.sendRedirect("/user/login.jsp");
            return;
        }
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        int cId = Integer.parseInt(req.getParameter("c_id"));
        topicService.addTopic(user, title, content, cId);
        //发布成功
        resp.sendRedirect("/topic?method=list&c_id="+cId);
    }
    public void replyByTid(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute("loginUser");
        if(user==null){
            req.setAttribute("msg","请登录");
            resp.sendRedirect("/user/login.jsp");
            return;
            //页面跳转 TODO
        }
        int tId = Integer.parseInt(req.getParameter("topic_id"));
        String content = req.getParameter("content");

        topicService.replyByTid(user,tId,content);
        resp.sendRedirect("/topic?method=findDetailById&topic_id="+tId);
    }
}
