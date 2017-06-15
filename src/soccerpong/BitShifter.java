/*  Jonathan Taylor 
    Grade 12TK
    Mrs. Firman
    PAT 2016
    "Soccer Pong!"

 */

package soccerpong;

//this class does the encryption for the Users.txt file when the UserArray class
//read it from a text file and when a new user is created
public class BitShifter
{

    public static String encrypt(String key)
    {
        String result = "";
        int l = key.length();
        char ch;
        
        for(int i = 0; i < l; i++)
        {
            
            ch = key.charAt(i);
            ch += 1;
            result += ch;
            
        }
        
        return result;
    }
    
    public static String decrypt(String key)
    {
        String result = "";
        int l = key.length();
        char ch;
        
        for(int i = 0; i < l; i++)
        {
            
            ch = key.charAt(i);
            ch -= 1;
            result += ch;
            
        }
        
        return result;
    }
}    

