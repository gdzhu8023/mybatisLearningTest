package me.gdzhu.inter;

import java.util.List;

import me.gdzhu.models.Article;
import me.gdzhu.models.User;

/**
 * @ClassName:     IUserOperation.java
 * 
 * @author         gdzhu
 * @version        V1.0  
 * @Date           2017-6-25 下午3:36:41 
 */
public interface IUserOperation {
	//这里面有一个方法名 selectUserByID 必须与 User.xml 里面配置的 select 的id 对应（<select id="selectUserByID"）
    public User selectUserByID(int id);
    
    public List selectUsers(String userName);
    
    public void addUser(User user);
    
    public void updateUser(User user);
    
    public void deleteUser(int id);
    
    public List<Article> getUserArticles(int id);
    
}
