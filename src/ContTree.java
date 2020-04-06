import java.util.HashMap;
import java.util.Map;

public class ContTree {
	ContNode root;
	boolean diskArray[];
	
	ContTree(int x)
	{
		diskArray = new boolean[x];
		for(int i = 0 ; i < x ; i++)
		{
			diskArray[i] =  false;
		}
		root = new ContNode();
		root.name = "root";
		root.start = 0;
		root.length = 0;
	}
	
	private ContNode search(ContNode parent,String name)
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
	private ContNode searchAll(String name)
	{
		String[] arr = name.split("/");
		ContNode curr = root;
		for(int i = 0 ; i < arr.length-2 ; i++)
		{
			curr = search(curr,arr[i+1]);
			if(curr == null)
				return null;
		}
		return curr;
	}
	
	private ContNode getLastNode(String path,String name)
	{
		ContNode curr = searchAll(path);
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
		ContNode curr = searchAll(path);
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
			ContNode beforeLastNode = searchAll(path);
			ContNode newNode = new ContNode();
			newNode.name = arr[arr.length-1];
			newNode.start = 0;
			newNode.length = 0;
			beforeLastNode.files.add(newNode);
		}
		else
		{
			System.out.println("File is already exist");
		}
	}
	
	
	
	private Map<Integer ,Integer> getFreeSpaces()
	{
		 Map<Integer,Integer> hm =  new HashMap<Integer,Integer>();
		 int count = 0;
		 for(int i = 0 ; i < diskArray.length ; i++)
		 {
			 if(!diskArray[i])
			 {
				count++;
				if(i!=diskArray.length-1)
				{
					if(diskArray[i+1])
					{
						 hm.put(i + 1 - count ,count);
						 count = 0;
					}
				}
				else
				{
					hm.put(i + 1 - count,count);
				}
			 }
		 }
		 return hm;
	}
	
	public void createFile(String path)
	{
		String arr[] = path.split("/");
		String arr2[] = arr[arr.length-1].split(" ");
		int length = Integer.parseInt(arr2[1]);
		if(!isExist(path,arr2[0]))
		{
			ContNode beforeLastNode = searchAll(path);
			if(beforeLastNode == null)
			{
				System.out.println("This path is wrong!");
				return;
			}
			Map<Integer ,Integer> mp = getFreeSpaces();

			//get first small
			int small = 0;
			int start = 0;
			boolean hasSpace = false;
			for (Map.Entry<Integer,Integer> entry : mp.entrySet())  
			{
				if(entry.getValue() == length)
				{
					start = entry.getKey();
					hasSpace= true;
					break;
				}
				else if(!hasSpace && entry.getValue() > length)
				{
					hasSpace = true;
					small = entry.getValue();
					start = entry.getKey();
				}
				else if(entry.getValue() > length && entry.getValue() < small)
				{
					small = entry.getValue();
					start = entry.getKey();
				}
			}
			if(hasSpace)
			{
				for(int i = start ; i < start + length ; i++)
				{
					diskArray[i] = true;
				}
				ContNode newNode = new ContNode();
				newNode.start = start;
				newNode.length = length;
				newNode.name = arr2[0];
				beforeLastNode.files.add(newNode);
			}
			else
			{
				System.out.println("No Space Available.");
			}	
		}
		else
		{
			System.out.println("File is already exist.");
		}	
	}
		
	String path2 = "";
	 public void find(ContNode node,String name)
	 {
	            if (node.files.size()!=0)
	            {
	                for (int i = 0; i< node.files.size(); i++)
	                {
                      deleteFile(path2+"/"+node.files.get(i).name,name);
	                  find(node.files.get(i),node.files.get(i).name);
	                }
	            }
	}


	public void deleteFolder(String path)
	{
		String arr[] = path.split("/");
		if(isExist(path,arr[arr.length-1]))
		{
			path2 = path;
			ContNode beforeLastNode = searchAll(path);
			if(beforeLastNode == null)
			{
				System.out.println("This path is wrong!");
				return;
			}
			ContNode lastNode = getLastNode(path,arr[arr.length-1]);
			find(lastNode,lastNode.name);
		}
		System.out.println("The path is not correct to delete!");
	}
	
	public void deleteFile(String path,String name)
	{
		String arr[] = path.split("/");
		if(isExist(path,name))
		{
			ContNode beforeLastNode = searchAll(path);
			if(beforeLastNode == null)
			{
				System.out.println("This path is wrong!");
				return;
			}
			ContNode lastNode = getLastNode(path,name);
			for(int i = lastNode.start ; i < lastNode.start + lastNode.length  ; i++)
			{
				diskArray[i] = false;
			}
			for(int i = 0 ; i < beforeLastNode.files.size() ; i++)
			{
				if(beforeLastNode.files.get(i) == lastNode)
				{
					beforeLastNode.files.remove(i);
					return;
				}
			}
		 }
	}
}
	


