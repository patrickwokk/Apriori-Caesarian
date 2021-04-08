# Apriori-Caesarian

During pregnancy, it is vital for women to have a good health status to prevent any delivery problems during birthing. Therefore, In this project, I will implement an Apriori algorithm that will detect the frequency of woman that has a caesarian section also have heart problem, abnormal blood pressure (high or low), and also unnatural delivery time during birthing. This dataset is obtained from the UCI database website and it contains the health status of 80 women during birthing. The dataset is in the form of matrix. The following information describes the matrix from the dataset.

@attribute Delivery time {0, 1} – 0: normal, 1: abnormal (premature or late comer)
@attribute Blood pressure {0, 1} – 0: normal, 1: abnormal (low/high blood pressure)
@attrubute Heart problem {0, 1} – 0: apt, 1: inept
@attribute Caesarian {0, 1} – 0: no, 1: yes

The algorithm that I Implemented contains 3 methods—process, generate and calculate. Process method is the overall function that will call two of the other method. The Process method will be called by the main to properly run the algorithm. Inside this method, it calls “generateCandidates” method & “calculateCandidates” method. This will then produce the frequent itemset. “generateCandidates” method will generate length (k+1) candidate itemset from length k frequent itemset. It stores the item in a vector due to its flexibility nature (growing automatically). From there, “calculateCandidates” will simply just match all the itemset and produce frequent candidates until there is no more left candidates.

This program takes in two files, which are configuration file and the data file from the UCI that is mentioned above. The configuration file allows users to tell the program how many attributes, transaction, and minimum support that is required. In this case, the transaction is treated as the data of 80 women during pregnancy. The minimum support will be important as it is the one that will produce interesting results from this program. Image below shows the raw data before being cleaned. As can be seen from this image, it also has age record of every 80 women during pregnancy. However, the age is not suitable for this project. Hence, developer must clean the data before mining.
