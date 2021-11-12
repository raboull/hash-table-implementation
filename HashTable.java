import java.lang.String;//import for access to hashCode() function

public class HashTable {

    private final int CAPACITY = 17;//capacity jas specified in the assignment description
    private int filledSlots;
    private String[] hashTable;

    //the default constructor
    public HashTable(){

        this.filledSlots = 0;
        this.hashTable = new String[CAPACITY];
    }

    //The following function computes a key value using the linear probing approach.
    public int computeHashedKey(String key, int i){
        int hashedKey = (key.hashCode()+i)%CAPACITY;
        if (hashedKey < 0)
            hashedKey = hashedKey+CAPACITY;       
        return hashedKey;
    } 

    //This function returns true if the HashTable is empty and returns false otherwise.
    public boolean empty(){

        if (filledSlots == 0)
            return true;
        else 
            return false;
    }

    //This function returs true if the HashHatble is full and returns false otherwise.
    public boolean full(){

        if (filledSlots == CAPACITY)
            return true;
        else
            return false;
    }

    //This function inserts they passed key value into the Hash Table.
    public void insert(String key){
        //Throw RuntimeException when the user attempts to inset into a full hash table.
        if (this.full() == true)
            throw new RuntimeException("Table is full and key <"+key+"> cannot be inserted.");

        //Note: initially I wanted to implement a hash table such that we could only insert unique elements,
        //but the professor said to match the implementation with the text book and therefore allow for
        //multiples of elements to be inserted.

        //initialize the i counter for linear probing
        int i = 0;
        Integer insertInd = null;
        while(i<CAPACITY){
            insertInd = computeHashedKey(key, i);
            if(this.hashTable[insertInd] == null || this.hashTable[insertInd] == "DELETED"){
                this.hashTable[insertInd] = key;
                this.filledSlots = this.filledSlots+1;
                break;
            }
            i = i+1;
        }
    }

    //This function deletes the passed key from the Hash Table.
    public void delete (String key){
        //Throw a RuntimeException when attemping to delete from an empty Hash Table.
        if (this.empty() == true)
            throw new RuntimeException("Table is currently empty and key <"+key+"> cannot be deleted.");

        //initialize the i counter for linear probing
        int i = 0;
        Integer deleteInd = null;
        boolean found = false;
        do{
            deleteInd = computeHashedKey(key,i);
            if (this.hashTable[deleteInd] == key){
                hashTable[deleteInd] = "DELETED";//When a key is deleted, leave a DELETED marker at the slot it previously occupied.
                found = true;
                this.filledSlots = this.filledSlots - 1;
                break;
            }
            i = i+1;
        }while(i<CAPACITY && this.hashTable[deleteInd]!=null);
        
        //Throw a RuntimeException or print a message to the user when a slot with the key to delete was not found
        //The prof said that our code should be non-op in this case so I commented out the code below.
        //if(found == false){
        //    throw new RuntimeException("The element does not exist, so we cannot delete this element.");
        //}
    }

    //This function returns true if the passed key existsts in the Hash Table, returns false otherwise.
    public boolean member(String key){

        //initialize the i counter for linear probing
        int i = 0;
        boolean found = false;
        Integer currentInd = null;
        do{
            currentInd = computeHashedKey(key,i);
            if (this.hashTable[currentInd] == key){
                found = true;
                break;
            }
            i = i+1;
        }while(i<CAPACITY && this.hashTable[currentInd]!=null);

        return found;
    }

    //This funciton prints the contents of the HashTable with a format specified in A3.
    public String toString(){
        String s = "";

        for (int i=0; i<this.CAPACITY; i++){
            if (this.hashTable[i] != null && this.hashTable[i] != "DELETED")
                s = s+i+":\""+this.hashTable[i]+"\"\n";
            else if(this.hashTable[i] == "DELETED")
                s = s+i+":deleted"+"\n";
        }
        return s;
    }
}
