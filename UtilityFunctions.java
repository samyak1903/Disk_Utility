import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.filechooser.FileSystemView;

public class UtilityFunctions
{
    public static String[] getDrives(String type)
    {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File[] roots = fsv.getRoots();
		if (roots.length == 1)
		{
			roots = roots[0].listFiles()[0].listFiles();
			for(File file : roots)
			{
				//System.out.println(file.getAbsolutePath());
			}
		}
		else
		{
			System.out.println("I guess you're not on Windows");
			return null;
		}

		ArrayList foundDrives = new ArrayList();
		for (int i = 0; i < roots.length; i++)
		{
			if(type.equalsIgnoreCase("All"))
			{
				foundDrives.add(roots[i]);
				System.out.println(roots[i]);
			}
			else
			{
				if (fsv.isDrive(roots[i]))
				{
					if (fsv.getSystemTypeDescription(roots[i]).indexOf(type) != -1)
					{
						foundDrives.add(roots[i]);
						//System.out.println(roots[i].getAbsolutePath());
					}
				}
			}
		}

		int size = foundDrives.size();

		String[] arr = new String[size];
		int index = 0;

		for(Object o : foundDrives)
		{
			arr[index] = o.toString();
			index++;
		}
		//arr[index]="D:";
		return arr;
	}

	public static boolean copyFile(File inputFile,File outputFile)
	{

		boolean flag = false;
		try
		{

			FileInputStream in = new FileInputStream(inputFile);
			FileOutputStream out = new FileOutputStream(outputFile);
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1)
			{
				out.write(buffer, 0, bytesRead);
			}
			in.close();
			out.close();

			flag = true;
		}catch(Exception e){}
		return flag;
	}

	public static void copyDirectory(File inputDir, File outputDir)
	{
		outputDir.mkdir();
		String[] children = inputDir.list();

		for(int p=0;p<children.length;p++)
		{
			//System.out.println(children[p]);
			File f = new File(inputDir.getAbsolutePath()+"/"+children[p]);
			File f1 = new File(outputDir.getAbsolutePath()+"/"+children[p]);
			if(f.isDirectory())
				copyDirectory(f,f1);
			else
				copyFile(f,f1);
		}
	}
}
