# File-allocation

The aim of this repository is to simulate the allocation and de-allocation of files and folders using : 

1- Contiguous Allocation Technique (using Best Fit algorithm).

2- Indexed Allocation Technique.

In Contiguous Allocation Techinque : Each file occupies a set of contiguous blocks on the disk and each dirctory contains files and its start and length.

Advantages : 
- Best performance in both sequential and direct access.

Disadvantages :
- Dynamic storage-allocation problem – external fragmentation.
- Files cannot grow.  


In Indexed Allocation Technique : Each file has its own index block, which is an array of disk-block addresses and The directory contains the address of the index block.

Advantages : 
- No external fragmentation – any free block on the disk can satisfy a request for more space.

Disadvantages :
- Suffer from wasted space – pointer overhead of mostly empty index blocks.
