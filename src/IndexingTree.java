import java.util.HashMap;
import java.util.Map;


public class IndexingTree {
	IndexingNode root;
	int diskArray[];
	
	IndexingTree(int x)
	{ 
		diskArray = new int[x];
		for(int i = 0 ; i < x ; i++)
		{
			diskArray[i] =  -1;
		}
		root = new IndexingNode();
		root.name = "root";
		root.value = -1;
	}
	
	private IndexingNode search(IndexingNode parent,String name)
	{
		for(int i = 0 ; i < parent.files.size() ; i++)
		{
			if(parent.files.get(i).name.equals(name))
			{
				return parent.files.get(i);
			}
		}
		return null;	
	}
	
	
	///if the path is correct return the node before last node as the last node is not inserted so far.
	private IndexingNode searchAll(String name)
	{
		String[] arr = name.split("/");
		IndexingNode curr = root;
		for(int i = 0 ; i < arr.length-2 ; i++)
		{
			curr = search(curr,arr[i+1]);
			if(curr == null)
				return null;
		}
		return curr;
	}
	
	private IndexingNode getLastNode(String path,String name)
	{
		IndexingNode curr = searchAll(path);
		for(int i = 0 ; i < curr.files.size() ; i++)
		{
			if(curr.files.get(i).name.equals(name))
			{
				return curr.files.get(i);
			}
		}
		return null;
	}
	
	private boolean isExist(String path,String name)
	{
		IndexingNode curr = searchAll(path);
		if(curr!=null)
		{
			for(int i = 0 ; i < curr.files.size() ; i++)
			{
				if(curr.files.get(i).name.equals(name))
				{
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	public void createFolder(String path)
	{
		String arr[] = path.split("/");
		if(!(arr[0].equals(" root")))
		{
			System.out.println("path is not correct Your base directory must be root -- cant create file");
			return;
		}
		if(!isExist(path,arr[arr.length-1]))
		{
			IndexingNode beforeLastNode = searchAll(path);
			IndexingNode newNode = new IndexingNode();
			newNode.name = arr[arr.length-1];
			newNode.value = -1;
			beforeLastNode.files.add(newNode);
			System.out.println("Folder is created succefully");
		}
		else
		{
			System.out.println("File is already exist");
		}
	}
	
	private int freeSpace()
	{
		int num =0 ;
		for(int i = 0 ; i < diskArray.length ; i++)
		{
			if(diskArray[i] == -1)
				num++;
		}
		return num;
	}

	public void createFile(String path)
	{
		String arr[] = path.split("/");
		String arr2[] = arr[arr.length-1].split(" ");
		int value = Integer.parseInt(arr2[1]);
		if(!isExist(path,arr2[0]))
		{
			IndexingNode beforeLastNode = searchAll(path);
			if(beforeLastNode == null)
			{
				System.out.println("This path is wrong!");
				return;
			}
			int size = value;
			int first = 0;
			int space = freeSpace();
			if(space >= size)
			{
				int i = 0;
				while(size > 0)
				{
					if(diskArray[i] == -1)
					{
						if(size == value)
						{
							first = i;
						}
						diskArray[i] = first;
						size--;
					}
					i++;
				}
				IndexingNode newNode = new IndexingNode();
				newNode.name = arr2[0];
				newNode.value = first;
				beforeLastNode.files.add(newNode);
				System.out.println("File created successfully.");
			}
			else
			{
				System.out.println("No Space available!.");
			}	
		}
		else
		{
			System.out.println("already exist.");
		}	
	}
	
	public void deleteAll(String path)
	{
		String arr[] = path.split("/");
		if(isExist(path,arr[arr.length-1]))
		{
			IndexingNode curr = getLastNode(path,arr[arr.length-1]);
			delete(curr);
			IndexingNode beforeLast = searchAll(path);
			for(int i = 0 ; i < beforeLast.files.size() ; i++)
			{
				if(beforeLast.files.get(i) == curr)
				{
					beforeLast.files.remove(i);
					System.out.println("removed successfully.");
					return;
				}
			}
		}
		System.out.println("Path is not correct to remove");
	}
	
	public void delete(IndexingNode node)
	{
		if (node.files.size()!=0)
		{
			for (int i = 0; i< node.files.size(); i++)
			{
			  	delete(node.files.get(i));
			}
		}
		else
		{
			for(int i = 0; i < diskArray.length ; i++)
			{
				if(diskArray[i] == node.value)
				{
					diskArray[i] = -1;
				}
			}
		}
	}
	
	public void displayDiskStatus()
	{
		int empty = 0,allocated = 0;
		for(int i = 0; i < diskArray.length ; i++)
		{
			if(diskArray[i] == -1)
				empty++;
			else
				allocated++;
		}
		System.out.println("Empty locations number : " + empty);
		System.out.println("The Empty Blocks : ");
		System.out.print("[");
		for(int i = 0; i < diskArray.length; i++)
		{
			if(diskArray[i] == -1)
				System.out.print(i);
			if(i+1 != diskArray.length)
				System.out.print(",");
		}
		System.out.println("]");
		
		System.out.println("\nallocated locations number : " + allocated);
		System.out.println("The Allocated Blocks : ");
		System.out.print("[");
		for(int i = 0; i < diskArray.length; i++)
		{
			if(diskArray[i] != -1)
			{
				System.out.print(i);
				
				if(i+1 != diskArray.length)
					System.out.print(",");
			}
		}
		System.out.println("]");
	}
	private int space = 0;
	public void displayDiskStructure(IndexingNode node)
	{
		if(node.files.size() > 0)
		{
			for (int i = 0; i< node.files.size(); i++)
			{
				space++;
				for(int j = 0; j < space; j++)
					System.out.print(" ");
				System.out.println(node.files.get(i).name);
				displayDiskStructure(node.files.get(i));
			}
			space--;
		}
		else
			space--;
	}	
}
