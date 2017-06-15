/*  Jonathan Taylor 
 Grade 12TK
 Mrs. Firman
 PAT 2016
 "Soccer Pong!"

 */
package soccerpong;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SwingPong
{

    public static void main(String[] args) throws IOException
    {

        //create a new JFrame instance called frame, requires String title
        JFrame frame = new JFrame("Soccer Pong!");
        //set the icon for frame
        frame.setIconImage(ImageIO.read((new File("Soccerball.png"))));
        //set panel to close when the exit button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //create a new borderLayout instance that the JFrame will obide by
        frame.setLayout(new BorderLayout());
        //set the JFrame to not be resizable
        frame.setResizable(false);
        //create new pong panel and add it to the JFrame frame
        PongPanel pongPanel = new PongPanel();
        //add pongpanel to the frame
        frame.add(pongPanel, BorderLayout.CENTER);
        //set the size and visibility of JFrame
        frame.setSize(500, 500);
        //sets frame in middle of screen
        frame.setLocationRelativeTo(null);
        //makes frame visible
        frame.setVisible(true);

    }

}
