import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContTree {
	ContNode root;
	boolean diskArray[];
	private ArrayList<String> pathes = new ArrayList<String>();
	private ArrayList<String> endpoints = new ArrayList<String>();
	private boolean bool = false;
	
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
			if(bool)
				System.out.println("path is not correct Your base directory must be root -- cant create folder");
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
			pathes.add(path);
			if(bool)
				System.out.println("Folder is created succefully");
		}
		else
		{
			if(bool)
				System.out.println("Folder is already exist");
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
		if(!(arr[0].equals(" root")))
		{
			if(bool)
				System.out.println("path is not correct Your base directory must be root -- cant create file");
			return;
		}
		String arr2[] = arr[arr.length-1].split(" ");
		int length = Integer.parseInt(arr2[1]);
		if(!isExist(path,arr2[0]))
		{
			ContNode beforeLastNode = searchAll(path);
			if(beforeLastNode == null)
			{
				if(bool)
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
				pathes.add(path);
				if(bool)
					System.out.println("File is created succefully!");
			}
			else
			{
				if(bool)
					System.out.println("No Space Available.");
			}	
		}
		else
		{
			if(bool)
				System.out.println("already exist.");
		}	
	}
		

	public void deleteAll(String path)
	{
		String arr[] = path.split("/");
		if(isExist(path,arr[arr.length-1]))
		{
			ContNode curr = getLastNode(path,arr[arr.length-1]);
			setEndPoint();
			delete(curr);
			endpoints.clear();
			ContNode beforeLast = searchAll(path);
			for(int i = 0 ; i < beforeLast.files.size() ; i++)
			{
				if(beforeLast.files.get(i) == curr)
				{
					beforeLast.files.remove(i);
					if(bool)
						System.out.println("removed successfully.");
					return;
				}
			}
		}
		if(bool)
			System.out.println("Path is not correct to remove");
		
	}
	
	private void delete(ContNode node)
	{
		for(int i = 0 ; i < endpoints.size() ; i++)
		{
			if(endpoints.get(i).equals(node.name))
			{
				pathes.remove(i);
				endpoints.remove(i);
				break;
			}
		}
		if (node.files.size()!=0)
		{
			for (int i = 0; i< node.files.size(); i++)
			{
			  	delete(node.files.get(i));
			}
		}
		else
		{
			for(int i = node.start; i < node.start + node.length; i++)
				diskArray[i] = false;
		}
	}
	
	public void displayDiskStatus()
	{
		int empty = 0,allocated = 0;
		for(int i = 0; i < diskArray.length ; i++)
		{
			if(diskArray[i])
				allocated++;
			else
				empty++;
			
		}
		System.out.println("Empty locations number : " + empty);
		System.out.println("The Empty Blocks : ");
		System.out.print("[");
		for(int i = 0; i < diskArray.length; i++)
		{
			if(diskArray[i] == false)
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
			if(diskArray[i] != false)
			{
				System.out.print(i);
				if(i+1 != diskArray.length)
					System.out.print(",");
			}
		}
		System.out.println("]");
	}
	
	private int space = 0;
	boolean check = true;
	public void displayDiskStructure(ContNode node)
	{
		if(check)
		{
			System.out.println("root");
			check = false;
		}
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
			if(!(node.name.equals("root")))
				space--;	
			else
				check = true;
		}
		else
			space--;
	}
	
	private void setEndPoint()
	{
		for(int i = 0 ; i < pathes.size() ; i++)
		{
			if(pathes.get(i).charAt(pathes.get(i).length()-2) == ' ')
			{
				String arr[] = pathes.get(i).split("/");
				String arr2[] = arr[arr.length-1].split(" ");
				endpoints.add(arr2[0]);
			}
			else
			{
				String arr[] = pathes.get(i).split("/");
				endpoints.add(arr[arr.length-1]);
			}
		}
	}
	
	public void addToVFSFile() throws IOException
	{
		FileWriter out = new FileWriter("E:/eclipse projects/os file allocation/src/Virtual_File_System_by_contiguous.txt");
		for(int i = 0 ; i < pathes.size() ; i++)
		{
			out.write(pathes.get(i)+"\n");
		}
		out.close();
	}
	
	public void loadData() throws IOException
	{
		ArrayList<String> tmp = new ArrayList<String>(); 
		File file = new File("E:/eclipse projects/os file allocation/src/Virtual_File_System_by_contiguous.txt");
		if(file.exists())
		{
			FileReader fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);	 
		    String line;
		    while((line = br.readLine()) != null)
		    {
		    	tmp.add(line);
		    }
		    br.close();
		    fr.close();
			for(int i = 0 ; i < tmp.size() ; i++)
			{
				if(tmp.get(i).charAt(tmp.get(i).length()-2) == ' ')
				{
					createFile(tmp.get(i));
				}
				else
				{
					createFolder(tmp.get(i));
				}
			}
		}
		bool = true;
	}
}
	


