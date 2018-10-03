/*
Garik Arutyunyan, Ishwor Ghimire
Danniel Kennedy
Problem Solving II
October 10, 2016
Mini Game Project
*/

//Add libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Minesweeper extends JFrame implements ActionListener, MouseListener
{
   //Variables
   private String playerName1=null;
   private String playerName2=null;
   
   //JFrame
   JFrame frame=new JFrame();
   JFrame frame1=frame;
   
   //Count
   int count=0;
   
   //JButtons
   JButton start=new JButton("Start game!");
   
   //JLabels
   JLabel player1Label=new JLabel("Player 1 Name: ");
   JLabel player2Label=new JLabel("Player 2 Name: ");
   
   //Flag game over
   boolean gameOver=false;
   
   //Player turn
   int playerTurn=1;
   
   //Jlabels for victory
   JLabel player1Win=new JLabel(new ImageIcon("crown.png","Crown"));
   JLabel player2Win=new JLabel(new ImageIcon("crown.png","Crown"));
   
   //JMenuBar JMenuItems //Menus
   JMenuBar jmb=new JMenuBar();
   JMenu file=new JMenu("File");
   JMenu about=new JMenu("About");
   JMenuItem exit=new JMenuItem("Exit");
   JMenuItem restart=new JMenuItem("Restart");
   JMenuItem aboutProgram=new JMenuItem("About Program");
   JMenuItem help=new JMenuItem("Help");
   
   //JTextfields
   JTextField player1Field=new JTextField(10);
   JTextField player2Field=new JTextField(10);
   
   //ArrayList
   ArrayList<JPanel> panels=new ArrayList<>();
   ArrayList<JLabel> labels=new ArrayList<>();
   
   //JPanels
   JPanel player1Panel=new JPanel();
   JPanel player2Panel=new JPanel();
   JPanel grid;
   
   //Player scores
   int player1Score=0;
   int player2Score=0;
   
   //JLabel player score label
   JLabel player1ScoreLabel;
   JLabel player2ScoreLabel;
   
   //Array
   int[] bombs=new int[64];
      
   //JPanel
   JLabel box=null;
   
   //First Frame
   public Minesweeper()
   {
       //Set title, size, layout
      setTitle("Minesweeper");
      
      //Set border layout
      setLayout(new BorderLayout());
      
      //Set size
      setSize(500,500);
      
      //Location
      setLocationRelativeTo(null);
      
      //Add action listener for enter button
      start.addActionListener(this);
      
      //JPanel
      JPanel info=new JPanel();
      info.setLayout(new GridLayout(2,2));
      info.add(player1Label);
      info.add(player1Field);
      info.add(player2Label);
      info.add(player2Field);
      
      //Add image to center and button enter to south
      add(info,BorderLayout.CENTER);
      add(new JLabel(new ImageIcon("icon.png","Logo")),BorderLayout.WEST);
      add(start,BorderLayout.SOUTH);
      
      //Pack
      pack();
      
      //Set default value for close and the frame visiblity to visible
      setVisible(true);
      setDefaultCloseOperation(HIDE_ON_CLOSE);
   }//end constructor Minesweeper
   
   //Second frame
   public void Minesweeper2()
   {
      //Remove first frame
      super.dispose();
   
      //Make it full screen
      frame.setSize(500,500);
      frame.setTitle("Minesweeper");
      frame.setLocationRelativeTo(null);
      frame.setLayout(new BorderLayout());
      
      //Add jmenubar, jmenus to jmenubar, and add jmenuitems to jmenus
      frame.setJMenuBar(jmb);
      jmb.add(file);
      jmb.add(about);
      file.add(restart);
      file.add(exit);
      about.add(help);
      about.add(aboutProgram);
      
      //Add action listeners to each menuitems
      restart.addActionListener(this);
      exit.addActionListener(this);
      help.addActionListener(this);
      aboutProgram.addActionListener(this);
      
      //JPanel
      grid=new JPanel();
      grid.setLayout(new GridLayout(8,8));
      grid.setBackground(Color.BLACK);
      
      //Make grid border white
      grid.setBorder(BorderFactory.createLineBorder(Color.WHITE,4));
      
      //Loop for values in array list
      for(int i=0; i<64; i++)
      {
         //Random class to generate random numbers
         Random rand=new Random();
         double value;
         value=rand.nextDouble();
         
         //Check values and assign numbers for each, assign 1 if no match was found
         if(value>0.10&&value<0.24)
         {
            bombs[i]=1;
         }
         else if(value>0.25&&value<0.5)
         {
            bombs[i]=2;
         }
         else if(value>0.51&&value<0.80)
         {
            bombs[i]=3;
         }
         else
         {
            bombs[i]=0;
         }
      }
      
      //Loop for creating jpanels and adding to array list
      for(int i=0; i<64; i++)
      {
         //JPanel
         JPanel panel=new JPanel();
         
         //Make each jpanel to have white border
         panel.setBorder(BorderFactory.createLineBorder(Color.WHITE,4));
         
         //Assign icon if value is 1, 2 -2, 3- 4, otherwise 1 point.
         if(bombs[i]==1)
         {
            box=new JLabel();
            box.setIcon(new ImageIcon("bomb.png","Bomb"));
         }
         else if(bombs[i]==2)
         {
            box=new JLabel("2");
            box.setForeground (Color.orange);
         }
         else if(bombs[i]==3)
         {
            box=new JLabel("4");
            box.setForeground (Color.cyan);
         }
         else
         {
            box=new JLabel("1");
            box.setForeground (Color.green);
         }
         //Make each font bold and 25pt
         box.setFont(new Font (box.getName(), Font.BOLD , 25));
         
         //Give name to each panel
         panel.setName(i+"");
         
         //box.setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
         panel.setBackground(Color.BLACK);
         
         //Make it invisbile and add to panel
         box.setVisible(false);
         panel.add(box);
         
         //Add panel and label to arraylists labels and panels
         labels.add(box);
         panels.add(panel);
      }
      
      //Lopp for each panel to add mouselisteners and add them to grid panel that has gridlayout
      for(JPanel loop: panels)
      {
         loop.addMouseListener(this);
         grid.add(loop);
      }
      
      //Who will start first?
      Random rand=new Random();
      double playerTurnRandom=rand.nextDouble();
      
      //If below 0.5 player 1 will start if above 0.51 player 2 will start
      if(playerTurnRandom<0.5)
      {
         playerTurn=1;
      }
      else
      {
         playerTurn=2;
      }
      
      //Left and right panels for player names
      
      //Make jlabels
      JLabel scoreLabel=new JLabel("Score: ",SwingConstants.CENTER);
      JLabel scoreLabel1=new JLabel("Score: ",SwingConstants.CENTER);
      JLabel nameLabel=new JLabel(playerName1,SwingConstants.CENTER);
      JLabel nameLabel1=new JLabel(playerName2,SwingConstants.CENTER);

      //Assign the label that contains the score to the center and others to north
      player1ScoreLabel=new JLabel(player1Score+"",SwingConstants.CENTER);
      player2ScoreLabel=new JLabel(player2Score+"",SwingConstants.CENTER);
      player1ScoreLabel.setVerticalAlignment(SwingConstants.NORTH);
      player2ScoreLabel.setVerticalAlignment(SwingConstants.NORTH);
      
      //Make each font 25pt and bold
      scoreLabel.setFont(new Font (scoreLabel.getName(), Font.BOLD , 25));
      scoreLabel1.setFont(new Font (scoreLabel.getName(), Font.BOLD , 25));
      nameLabel.setFont(new Font (scoreLabel.getName(), Font.BOLD , 25));
      nameLabel1.setFont(new Font (scoreLabel.getName(), Font.BOLD , 25));
      player1ScoreLabel.setFont(new Font (scoreLabel.getName(), Font.BOLD , 25));
      player2ScoreLabel.setFont(new Font (scoreLabel.getName(), Font.BOLD , 25));
      player1ScoreLabel.setForeground(Color.black);
      player2ScoreLabel.setForeground(Color.black);
      scoreLabel.setForeground(Color.black);
      scoreLabel1.setForeground(Color.black);
      nameLabel.setForeground(Color.black);
      nameLabel1.setForeground(Color.black);

      //Make Left and right Jpanel to grid layout
      player1Panel.setLayout(new GridLayout(4,0));
      player2Panel.setLayout(new GridLayout(4,0));

      //Make the vitory image invisible
      player1Win.setVisible(false);
      player2Win.setVisible(false);
      
      //Add jlabels to panel
      player1Panel.add(nameLabel);
      player2Panel.add(nameLabel1);
      player1Panel.add(player1Win);
      player2Panel.add(player2Win);
      player1Panel.add(scoreLabel);
      player2Panel.add(scoreLabel1);
      player1Panel.add(player1ScoreLabel);
      player2Panel.add(player2ScoreLabel);
      
      //Add all JPanels to frame and give their position
      frame.add(grid,BorderLayout.CENTER);
      frame.add(player1Panel,BorderLayout.WEST);
      frame.add(player2Panel,BorderLayout.EAST);
      
      //Notifies which player will start and changes the color of right and left panels
      if(playerTurn==1)
      {
         JOptionPane.showMessageDialog(null,"Coin was thrown! Player "+playerName1+" will turn first","Player 1 turn",JOptionPane.INFORMATION_MESSAGE);
         player1Panel.setBackground(Color.BLUE);
         player2Panel.setBackground(Color.WHITE);
      }
      else if(playerTurn==2)
      {
         JOptionPane.showMessageDialog(null,"Coin was thrown! Player "+playerName2+" will turn first" ,"Player 2 turn",JOptionPane.INFORMATION_MESSAGE);
         player1Panel.setBackground(Color.WHITE);
         player2Panel.setBackground(Color.RED);
      }
      
      //Set the frame visible and close operation is to exit on close
      frame.setVisible(true);
      frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   
   public static void main(String[] args)
   {
      new Minesweeper();
   }//end main
   
   public void actionPerformed(ActionEvent ae)
   {
      //If user clicked enter button then open second frame
      if(ae.getSource()==start)
      {
         //Gets text for each playerName
         playerName1=player1Field.getText();
         playerName2=player2Field.getText();
         
         //Starts another frame
         Minesweeper2();
      }
      //Restart
      else if(ae.getSource()==restart)
      {
         //Call restart method
         restart();
      }
      //Exit
      else if(ae.getSource()==exit)
      {
         //Close the program
         System.exit(0);
      }
      //Help
      else if(ae.getSource()==help)
      {
         //Show message
         JOptionPane.showMessageDialog(null,"The winner is the one who has the highest score.\nThe game stops until one of player will hit bomb.\n1 - 1 Score.\n2 - 2 Score\n4 - 4 Score", "Help" ,JOptionPane.INFORMATION_MESSAGE);
      }
      //About program
      else if(ae.getSource()==aboutProgram)
      {
         //Show message about creators
         JOptionPane.showMessageDialog(null,"Version 1.0\nAuthors: Garik Arutyunyan, Ishwor Ghimire\nDate created: 10/15/2016\n© Minesweeper | Google", "About Program" ,JOptionPane.INFORMATION_MESSAGE);
      }
   }//end actionPerfromed
   
   /**
   @param e Accepts mouseEvent object
   */
   public void mousePressed(MouseEvent e) 
   {
      //Gets the label and converts 
      JPanel label=(JPanel)e.getSource();
      String boxNumber=label.getName();
      int number=Integer.parseInt(boxNumber);
      labels.get(number).setVisible(true);
      
      //Count for each mouse listener
      count++;
      
      //If it is player 1 turn then work on his action and give turn to second player
      if(playerTurn==1)
      {
         if(bombs[number]==0)
         {
            player1ScoreLabel.setText((++player1Score)+""); //Update score
         }
         else if(bombs[number]==1)
         {
            gameOver=true; //Flag for stop the game
         }
         else if(bombs[number]==2)
         {
            player1Score=player1Score+2;
            player1ScoreLabel.setText((player1Score)+""); //Update score
         }
         else if(bombs[number]==3)
         {
            player1Score=player1Score+4;
            player1ScoreLabel.setText((player1Score)+""); //Update score
         }
         
         playerTurn=2;
         
         //Changes bg color
         player1Panel.setBackground(Color.WHITE);
         player2Panel.setBackground(Color.RED);
      }
      else if(playerTurn==2)
      {
         if(bombs[number]==0)
         {
            player2ScoreLabel.setText((++player2Score)+""); //Update score
         }
         else if(bombs[number]==1)
         {
            gameOver=true; //Flag for stop the game
         }
         else if(bombs[number]==2)
         {
            player2Score=player2Score+2;
            player2ScoreLabel.setText((player2Score)+""); //Update score
         }
         else if(bombs[number]==3)
         {
            player2Score=player2Score+4;
            player2ScoreLabel.setText((player2Score)+""); //Update score
         }
         
         playerTurn=1;
         
         //Changes bg color
         player1Panel.setBackground(Color.BLUE);
         player2Panel.setBackground(Color.WHITE);
      }
      
      //Removes mouse action from panel that was pressed
      panels.get(number).removeMouseListener(this);
      
      //If the count is 64 then automaticaly stop game(If no bombs were there)
      if(count==64)
      {
         gameOver=true;
      }
      
      //If it is game over then show who won(highest score) if the scores are tie then say tie
      if(gameOver==true)
      {
         if(player1Score>player2Score)
         {
            JOptionPane.showMessageDialog(null,playerName1+" Won!");
            player1Win.setVisible(true);
         }
         else if(player2Score>player1Score)
         {
            JOptionPane.showMessageDialog(null,playerName2+" Won!");
            player2Win.setVisible(true);
         }
         else if(player2Score==player1Score)
         {
            JOptionPane.showMessageDialog(null,"Tie!");
         }
         
         //Change both background color to white
         player1Panel.setBackground(Color.WHITE);
         player2Panel.setBackground(Color.WHITE);
         
         //Remove all mouselisteners
         for(JPanel loop: panels)
         {
            loop.removeMouseListener(this);
         }
         
         //Make all labels visible
         for(JLabel loop1: labels)
         {
            loop1.setVisible(true);
         }
      }
   }
   
   public void mouseExited(MouseEvent e) {}
   public void mouseEntered(MouseEvent e) {}
   public void mouseReleased(MouseEvent e) {}
   public void mouseClicked(MouseEvent e) {}
   
   //Restart method
   public void restart()
   {
      //Start-over all v ariables
      gameOver=false;
      count=0;
      player1Score=0;
      player2Score=0;
      
      //Make all scores to 0
      player1ScoreLabel.setText((player1Score)+"");
      player2ScoreLabel.setText((player2Score)+"");
      
      //Hide victory icons
      player1Win.setVisible(false);
      player2Win.setVisible(false);
      
      //Make sure all mouselisteners were removed
      for(JPanel loop: panels)
      {
         loop.removeMouseListener(this);
      }

      //Loop for values in array list
      for(int i=0; i<64; i++)
      {
         Random rand=new Random();
         double value;
         
         value=rand.nextDouble();
            
         if(value>0.17&&value<0.24)
         {
            bombs[i]=1;
         }
         else if(value>0.25&&value<0.5)
         {
            bombs[i]=2;
         }
         else if(value>0.51&&value<0.80)
         {
            bombs[i]=3;
         }
         else
         {
            bombs[i]=0;
         }
      }
      
      //Remove all stuff in each label in labels arraylist
      for(JLabel loop1: labels)
      {
         loop1.setIcon(null);
         loop1.setText(null);
      }
      
      //Control variable
      int i=0;
      
      //Loop for remodifying texts and adding icons in jlabels
       for(JLabel loop1: labels)
       {
         if(bombs[i]==1)
         {
            loop1.setIcon(new ImageIcon("bomb.png","Bomb"));
            loop1.setForeground (Color.black);
            i++;
         }
         else if(bombs[i]==2)
         {
            loop1.setText("2");
            loop1.setForeground (Color.orange);
            i++;
         }
         else if(bombs[i]==3)
         {
            loop1.setText("4");
            loop1.setForeground (Color.cyan);
            i++;
         }
         else
         {
            loop1.setText("1");
            loop1.setForeground (Color.green);
            i++;
         }
         loop1.setVisible(false); //Make the label invisible
      }
      
      //Add mouselistener to each panel in panels arraylist
      for(JPanel loop: panels)
      {
         loop.addMouseListener(this);
      }
      
      //Who will start first?
      Random rand=new Random();
      double playerTurnRandom=rand.nextDouble();
      
      if(playerTurnRandom<0.5)
      {
         playerTurn=1;
      }
      else
      {
         playerTurn=2;
      }
      
      if(playerTurn==1)
      {
         JOptionPane.showMessageDialog(null,"Coin was thrown! Player "+playerName1+" will turn first","Player 1 turn",JOptionPane.INFORMATION_MESSAGE);
         player1Panel.setBackground(Color.BLUE);
         player2Panel.setBackground(Color.WHITE);
      }
      else if(playerTurn==2)
      {
         JOptionPane.showMessageDialog(null,"Coin was thrown! Player "+playerName2+" will turn first" ,"Player 2 turn",JOptionPane.INFORMATION_MESSAGE);
         player1Panel.setBackground(Color.WHITE);
         player2Panel.setBackground(Color.RED);
      }
   }
}//end class Minesweeper