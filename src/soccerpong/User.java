/*  Jonathan Taylor 
    Grade 12TK
    Mrs. Firman
    PAT 2016
    "Soccer Pong!"

 */

package soccerpong;

public class User
{
    private String username = "";
    private String password = "";
    
    public User(String u,String p)
    {
        username = u;
        password = p;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return  "username: " + username + "\npassword: " + password + '}';
    }
    
    
    
}

