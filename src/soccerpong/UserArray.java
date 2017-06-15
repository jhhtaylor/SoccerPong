/*  Jonathan Taylor 
 Grade 12TK
 Mrs. Firman
 PAT 2016
 "Soccer Pong!"

 */
package soccerpong;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UserArray
{

    private User[] UArr = new User[100];
    private int size = 0;

    public UserArray()
    {
        try
        {
            Scanner scFile = new Scanner(new File("Users.txt"));
            String line;
            String u = "", p = "";

            while (scFile.hasNext())
            {
                //decrypts text file line by line and places it into an array
                line = BitShifter.decrypt(scFile.nextLine());

                try (Scanner sc = new Scanner(line).useDelimiter("#####p"))
                {

                    u = sc.next();
                    p = sc.next();
                    sc.close();

                }

                UArr[size] = new User(u, p);

                size++;
                System.out.println(size);//check if array is filling
            }
        } catch (FileNotFoundException f)
        {
            System.out.println("Could not find Users.txt file. Please try again");
        }
        System.out.println("Full array at size: " + size);//full array
    }

    public int getSize()
    {
        return size;
    }

    public boolean searchForUser(String u, String p)
    {
        int i = 0;

        boolean foundUser = false;

        while (i < size)
        {
            if (UArr[i].getUsername().equals(u) && UArr[i].getPassword().equals(p))
            {

                foundUser = true;
                break;
            }
            i++;
        }
        return foundUser;

    }

}
