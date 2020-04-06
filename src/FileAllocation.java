import java.util.Map;
import java.util.Scanner;
public class FileAllocation {
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		String method = in.next();
		if(method.equals("contiguos"))
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
				else if(command.equals("DeleteFolder"))
				{
					c.deleteFolder(name);
				}
				for(int i = 0 ; i < c.diskArray.length ; i++)
				System.out.print(c.diskArray[i]+" ");
			}
		}
	}
}
