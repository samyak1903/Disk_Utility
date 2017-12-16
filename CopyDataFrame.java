import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;


public class CopyDataFrame extends JFrame implements ActionListener
{
    JButton b1,b2,b3;
    JLabel l1,l2;
    JTextField t1;
    Container c;
    JFileChooser fc;

    public CopyDataFrame()
    {
        c = getContentPane();
        c.setLayout(null);

        l1 = new JLabel("Copy Data to All Removables",SwingConstants.CENTER);
        l1.setFont(new Font("Serif",Font.BOLD,36));
        l1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        l2 = new JLabel("Selected File / Folder : ");

        t1 = new JTextField(100);
        t1.setEditable(false);

        b1 = new JButton("Select File");
        b1.addActionListener(this);
        b2 = new JButton("Select Folder");
        b2.addActionListener(this);
        b3 = new JButton("Paste");
        b3.addActionListener(this);

		l1.setBounds(50,30,500,50);
		l2.setBounds(70,130,200,30);
		t1.setBounds(70,170,450,30);

		b1.setBounds(100,270,100,40);
		b2.setBounds(250,270,120,40);
		b3.setBounds(420,270,100,40);

		c.add(l1);
		c.add(l2);
		c.add(t1);
		c.add(b1);
		c.add(b2);
		c.add(b3);

		fc = new JFileChooser(".");

		setSize(600,400);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae)
    {
		if(ae.getSource()==b1)
		{
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				File f = fc.getSelectedFile();
				t1.setText(f.getAbsolutePath());
			}
		}
		if(ae.getSource()==b2)
		{
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				File f = fc.getSelectedFile();
				t1.setText(f.getAbsolutePath());
			}
		}
		if(ae.getSource()==b3)
		{
			if(t1.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"Please Select a File");
				return;
			}
			File f = new File(t1.getText());
			if(!f.exists())
			{
				JOptionPane.showMessageDialog(this,"File or Directory does not exist");
				return;
			}
			copyData();
		}
	}

	void copyData()
	{
		String disks[] = UtilityFunctions.getDrives("Removable");
		File inputFile = new File(t1.getText());
		String message="";

		if(inputFile.isFile())
		{
			int count = 0;
			for(String disk : disks)
			{
				try
				{
					File outputFile = new File(disk+inputFile.getName());
					UtilityFunctions.copyFile(inputFile,outputFile);
					message += "Success : "+outputFile.getAbsolutePath()+"\n";
					count++;
				}
				catch(Exception e)
				{
					message += "Error copying to : "+disk+"\n";
				}
			}
			message += "Copied successfully to : "+count+" Drives";
			JOptionPane.showMessageDialog(this,message);
		}
		else if(inputFile.isDirectory())
		{
			int count = 0;
			for(String disk : disks)
			{
				try
				{
					File outputFile = new File(disk+inputFile.getName());
					UtilityFunctions.copyDirectory(inputFile,outputFile);
					message += "Success : "+outputFile.getAbsolutePath()+"\n";
					count++;
				}
				catch(Exception e)
				{
					message += "Error copying to : "+disk+"\n";
				}
			}
			message += "Copied successfully to : "+count+" Drives";
			JOptionPane.showMessageDialog(this,message);
		}
	}

    public static void main(String[] args)
    {
        new CopyDataFrame();
    }
}