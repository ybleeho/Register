package service;

import beans.UserBean;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");

        UserDao userDao = new UserDao();
        if(username != null && !username.isEmpty()) {
            //用户名不存在可以注册
            if(userDao.isUserExist(username) && userDao.isPasswordConfirm(password, repassword)&&userDao.isPasswordIllegal(password)) {
                UserBean userBean = new UserBean();
                userBean.setPassword(password);
                userBean.setUsername(username);
                userDao.saveUser(userBean);
                request.setAttribute("info", "Congratulations! Reg successfully!");
            }else {
                if(!userDao.isUserExist(username))
                    request.setAttribute("info", "Sorry! Username exists!");
                if(!userDao.isPasswordConfirm(password, repassword))
                    request.setAttribute("info", "Sorry! Password not confirmed!");
                if(!userDao.isPasswordIllegal(password))
                    request.setAttribute("info", "Sorry! Password is illegal!");
            }
        }
        //forward to message.jsp
        request.getRequestDispatcher("message.jsp").forward(request, response);
    }
}