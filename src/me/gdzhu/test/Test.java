package me.gdzhu.test;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import me.gdzhu.inter.IUserOperation;
import me.gdzhu.models.Article;
import me.gdzhu.models.User;


/**
 * Created by gdzhu on 2017/6/25.
 */
public class Test {
	private static Reader reader;
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            reader = Resources.getResourceAsReader("Configuration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SqlSessionFactory getSession() {
        return sqlSessionFactory;
    }

    
    public void getUserList(String userName){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
            List<User> users = userOperation.selectUsers(userName);
            for(User user:users){
                System.out.println(user.getId()+":"+user.getUserName()+":"+user.getUserAddress());
            }

        } finally {
            session.close();
        }
    }
    
    public void updateUser(){
        //先得到用户,然后修改，提交。
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);
            User user = userOperation.selectUserByID(4);            
            user.setUserAddress("原来是魔都的浦东创新园区");
            userOperation.updateUser(user);
            session.commit();

        } finally {
            session.close();
        }
    }
    
    /**
     * 测试增加,增加后，必须提交事务，否则不会写入到数据库.
     */
    public void addUser(){
        User user=new User();
        user.setUserAddress("新街口");
        user.setUserName("wwce");
        user.setUserAge("28");
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);
            userOperation.addUser(user);
            session.commit();
            System.out.println("当前增加的用户 id为:"+user.getId());
        } finally {
            session.close();
        }
    }
    
    /**
     * 删除数据，删除一定要 commit.
     * @param id
     */
    public void deleteUser(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
            userOperation.deleteUser(id);
            session.commit();            
        } finally {
            session.close();
        }
    }
    
    public void getUserArticles(int userid){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
            List<Article> articles = userOperation.getUserArticles(userid);
            for(Article article:articles){
                System.out.println(article.getTitle()+":"+article.getContent()+
                        ":作者是:"+article.getUser().getUserName()+":地址:"+
                         article.getUser().getUserAddress());
            }
        } finally {
            session.close();
        }
    }
    
    public static void main(String[] args) {
    	
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            //User user = (User) session.selectOne("me.gdzhu.models.UserMapper.selectUserByID", 1);
//        	//sqlSessionFactory.getConfiguration().addMapper(IUserOperation.class);
//            IUserOperation userOperation=session.getMapper(IUserOperation.class);
//            User user = userOperation.selectUserByID(1);
//            System.out.println(user.getUserAddress());
//            System.out.println(user.getUserName());
//            
//            
//        } finally {
//            session.close();
//        }
        
//    	//测试查询map
        Test testUser=new Test();
//        testUser.getUserList("%");
    	
    	//测试添加
        //testUser.addUser();
        
        //testUser.updateUser();
        
        
        //测试删除
        //testUser.deleteUser(3);
        
        //测试多对一
        testUser.getUserArticles(1);
        
    }
}
