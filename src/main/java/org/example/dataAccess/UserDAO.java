package org.example.dataAccess;

import org.example.businessLogic.User;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAO extends Serializator<List<User>> implements Serializable {
    public UserDAO() {
        super("src/main/resources/users.ser");
    }
    public boolean exists(List<User> users, String userName){
       List<User> list =  users.stream().filter((User user) -> user.getUserName().equals(userName)).collect(Collectors.toList());
       if(list.isEmpty())
        return false;
       else
           return true;
    }
     public boolean isPasswordCorrect(List<User> users, String userName, String password){
         List<User> list =  users.stream().filter((User user) -> user.getUserName().equals(userName)).collect(Collectors.toList());
         if(password.equals(list.get(0).getPassword())){
             return true;
         }else
             return false;
     }
     public String getUserType(List<User> users, String userName){
         List<User> list =  users.stream().filter((User user) -> user.getUserName().equals(userName)).collect(Collectors.toList());
        return list.get(0).getType();
     }
}
