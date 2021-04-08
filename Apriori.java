//-----------------------------------------------------------
//  Purpose:     Implementation of Apriori Algorithm for determining C-Section Frequency
//  Implemented: Patrick Kwok
//  UA ID      : 010917833
//-----------------------------------------------------------

import java.io.*;
import java.util.*;
public class Apriori {

	public static void main(String[] args) {
		
		GenerateApriori apriori = new GenerateApriori();
        apriori.process();
    }
	
	//-----------------------------------------------------------------------------
	// Class Name   : Generate Apriori
	// Purpose      : generate Apriori itemsets
	//-----------------------------------------------------------------------------
	static class GenerateApriori
	{
	    Vector<String> candidates=new Vector<String>();     //declare candidates
	    String configFile="config.txt"; 					//configuration file - configure apriori
	    String dataFile="caesarian.txt"; 					//transaction file - datasets of Caesarian
	    int numAttributes; 								    //number of attributes, delivery time, blood pressure, heart problem, caesarian
	    int numData; 								        //number of pregnant woman being observed
	    double minSup; 										//minimum support
	    String oneVal[]; 									//array of value per column that will be treated as a '1'
	    String itemSep = " "; 								//to seperate attributes

	    //-----------------------------------------------------------------------------
	    // Method Name  : process
	    // Purpose      : Generate the apriori itemsets
	    //-----------------------------------------------------------------------------
	    public void process()
	    {
	        int itemsetNumber=0; //the current itemset the program looking at
	        
	        System.out.println("Configuration File: " + configFile);
	        System.out.println("Data File         : " + dataFile);
	        
	        try
	        {
	             FileInputStream file_in = new FileInputStream(configFile);
	             BufferedReader data_in = new BufferedReader(new InputStreamReader(file_in));
	             
	             //number of attributes, 4.  1.delivery time, 2.Blood Pressure, 3.Heart Problem, 4.Caesarian (c-section)
	             numAttributes=Integer.valueOf(data_in.readLine()).intValue();

	             //number of transactions
	             numData=Integer.valueOf(data_in.readLine()).intValue();

	             //minimum suppot
	             minSup=(Double.valueOf(data_in.readLine()).doubleValue());

	             //output config info to the user
	             System.out.print("Configuration     : "+numAttributes+" attributes, "+numData+" women, ");
	             System.out.println("minsup = "+minSup+"%");
	             System.out.println();
	             minSup/=100.0;
	             
	             
	            //start looking and treat as 1
	            oneVal = new String[numAttributes];
	            for(int i=0; i<oneVal.length; i++)
	            	oneVal[i]="1";

	        }

	        catch(IOException e)
	        {
	            System.out.println(e);
	        }

	        System.out.println("Apriori algorithm:\n");

	        do
	        {
	        	//if(candidates.size()>1)
	        	
	            //increase the itemset
	            itemsetNumber++;

	            //generate the candidates
	            generateCandidates(itemsetNumber);

	            //determine frequent itemsets
	            calculateFrequentItemsets(itemsetNumber);
	            if(candidates.size()!=0)
	            {
	                System.out.println("Frequent " + itemsetNumber + "-itemsets");
	                System.out.println(candidates);
	            }
	            
	        //prevent reading database again
	        }while(candidates.size()>1);
	    }

	    //-----------------------------------------------------------------------------
	    // Method Name  : generateCandidates
	    // Purpose      : Generate all possible candidates for the n itemsets (stored in vectors)
	    //-----------------------------------------------------------------------------
	    private void generateCandidates(int n)
	    {
	        Vector<String> tempCandidates = new Vector<String>(); //temporary candidate
	        String str1, str2; 									  //strings that will be used for comparisons
	        StringTokenizer st1, st2; 							  //string tokenizers for comparisons (chopping)

	        if(n==1)
	        {
	            for(int i=1; i<=numAttributes; i++)
	            {
	                tempCandidates.add(Integer.toString(i));
	                //tempCandidates.add.toString(i);
	            }
	        }
	        else if(n==2)
	        {
	            //add each itemset from the previous frequent itemsets together
	            for(int i=0; i<candidates.size(); i++)
	            {
	                st1 = new StringTokenizer(candidates.get(i));
	                str1 = st1.nextToken();
	                for(int j=i+1; j<candidates.size(); j++)
	                {
	                    st2 = new StringTokenizer(candidates.elementAt(j));
	                    str2 = st2.nextToken();
	                    tempCandidates.add(str1 + " " + str2);
	                }
	            }
	        }
	        else
	        {
	            //for each itemset, do compare  to the next item candidates
	            for(int i=0; i<candidates.size(); i++)
	            {
	                for(int j=i+1; j<candidates.size(); j++)
	                {
	                    //String
	                    str1 = new String();
	                    str2 = new String();
	                    
	                    //Tokenizer
	                    st1 = new StringTokenizer(candidates.get(i));
	                    st2 = new StringTokenizer(candidates.get(j));

	                    //make a string of the first n-2 tokens of the strings
	                    for(int s=0; s < n-2; s++)
	                    {
	                        str1 = str1 + " " + st1.nextToken();
	                        str2 = str2 + " " + st2.nextToken();
	                    }

	                    if(str2.compareToIgnoreCase(str1)==0)
	                        tempCandidates.add((str1 + " " + st1.nextToken() + " " + st2.nextToken()).trim());
	                }
	            }
	        }
	        
	        candidates.clear();
	        
	        //set the new ones
	        candidates = new Vector<String>(tempCandidates);
	        tempCandidates.clear();
	    }

	    //-----------------------------------------------------------------------------
	    // Method Name  : calculateFrequentItemsets
	    // Purpose      : Determine which candidates are frequent
	    //-----------------------------------------------------------------------------
	    private void calculateFrequentItemsets(int n)
	    {
	        Vector<String> frequentCandidates = new Vector<String>(); //vector the frequent candidates
	        														  // to collect frequent datasets
	        
	        //local variables for loading data file
	        FileInputStream file_in;
	        BufferedReader data_in;
	        
	        StringTokenizer st, stFile;
	        
	        boolean match; //boolean to check if candidate matches
	        
	        boolean trans[] = new boolean[numAttributes]; //array that hold datasets
	        int count[] = new int[candidates.size()]; //number of matches

	        try
	        {	        	
	                //load the data file
	                file_in = new FileInputStream(dataFile);
	                data_in = new BufferedReader(new InputStreamReader(file_in));

	                //for each transaction
	                for(int i=0; i<numData; i++)
	                {
	                    stFile = new StringTokenizer(data_in.readLine(), itemSep);
	                    
	                    //put the contents of the line into array
	                    for(int j=0; j<numAttributes; j++)
	                    {
	                        trans[j]=(stFile.nextToken().compareToIgnoreCase(oneVal[j])==0); //if it is not a 0, assign the value to true
	                    }

	                    //check each candidate
	                    for(int c=0; c<candidates.size(); c++)
	                    {
	                        match = false; //reset match to false
	                        
	                        //tokenize the candidate so that we know what items need to be present for a match
	                        st = new StringTokenizer(candidates.get(c));
	                        
	                        //check each item in the itemset to see if it is present in the transaction
	                        while(st.hasMoreTokens())
	                        {
	                            match = (trans[Integer.valueOf(st.nextToken())-1]);
	                            if(!match)
	                                break;
	                        }
	                        if(match) //if it matches, increase the count
	                            count[c]++;
	                    }
	                }
	                for(int i=0; i<candidates.size(); i++)
	                {
	                    if((count[i]/(float)numData)>=minSup)
	                    {
	                        frequentCandidates.add(candidates.get(i));
	                    }
	                }
	        }
	        
	        catch(IOException e)
	        {
	            System.out.println(e);
	        }
	        
	        candidates.clear();
	        
	        candidates = new Vector<String>(frequentCandidates);
	        frequentCandidates.clear();
	    }
	}	
}
