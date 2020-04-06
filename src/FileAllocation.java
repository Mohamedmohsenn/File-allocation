import java.util.Scanner;
public class FileAllocation {
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		String method = in.next();
		if(method.equals("contiguous"))
		{
			ContTree c = new ContTree(10);
			while(true)
			{
				String command = in.next();
				String name = in.nextLine();
				if(command.equals("CreateFile"))
				{
					c.createFile(name);
				}
				else if(command.equals("CreateFolder"))
				{
					c.createFolder(name);
				}
				else if(command.equals("DeleteFolder") || command.equals("DeleteFile"))
				{
					c.deleteAll(name);
				}
				else if(command.equals("DisplayDiskStatus"))
				{
					c.displayDiskStatus();
				}
				else if(command.equals("DisplayDiskStructure"))
				{
					System.out.println("root");
					c.displayDiskStructure(c.root);
				}
				else
				{
					System.out.println("Wrong command");
				}
			}
		}
		else if(method.equals("indexing"))
		{
			IndexingTree c = new IndexingTree(10);
			while(true)
			{
				String command = in.next();
				String name = in.nextLine();
				if(command.equals("CreateFile"))
				{
					c.createFile(name);
				}
				else if(command.equals("CreateFolder"))
				{
					c.createFolder(name);
				}
				else if(command.equals("DeleteFolder") || command.equals("DeleteFile"))
				{
					c.deleteAll(name);
				}
				else if(command.equals("DisplayDiskStatus"))
				{
					c.displayDiskStatus();
				}
				else if(command.equals("DisplayDiskStructure"))
				{
					System.out.println("root");
					c.displayDiskStructure(c.root);
				}
				else
				{
					System.out.println("Wrong command");
				}
				
			}
		}
		else
		{
			System.out.println("Wrong command");
		}
	}
}
