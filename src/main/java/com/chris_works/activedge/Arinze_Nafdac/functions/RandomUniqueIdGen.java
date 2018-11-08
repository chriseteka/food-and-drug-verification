package com.chris_works.activedge.Arinze_Nafdac.functions;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class RandomUniqueIdGen {
	
//	ArrayList<String> productIdList = new ArrayList<String>();
//
//	public final String TWELVE_DIGIT_PRODUCT_ID() {
//		
//		//What if it is a TimeStamp: farooq
//		
//		String FIRST_FOUR = "", SECOND_FOUR = "", THIRD_FOUR = "", FORTH_FOUR = "";
//		
//		final List<String> NATURAL_NUMBERS = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"); 
//		
//		 
//		 //GENERATES AND SAVES 10 UNIQUE 12-DIGIT PRODUCT ID TO AN ARRAYLIST
//		 for(int i = 1; i <= 10; i++)
//		 {
//	        for (int I = 1; I <= 1; I++) {
//	        	Collections.shuffle(NATURAL_NUMBERS);
//	        	List<String> subList = NATURAL_NUMBERS.subList(I, I + 5);
//	        	FIRST_FOUR = subList.get(I)+subList.get(I + 1)+subList.get(I + 2)+subList.get(I + 3);
//	        }
//	        for (int J = 1; J <= 1; J++) {
//	        	Collections.shuffle(NATURAL_NUMBERS);
//	        	List<String> subList = NATURAL_NUMBERS.subList(J, J + 5);
//	        	SECOND_FOUR = subList.get(J)+subList.get(J + 1)+subList.get(J + 2)+subList.get(J + 3);
//	        }
//	        for (int K = 1; K <= 1; K++) {
//	        	Collections.shuffle(NATURAL_NUMBERS);
//	        	List<String> subList = NATURAL_NUMBERS.subList(K, K + 5);
//	        	THIRD_FOUR = subList.get(K)+subList.get(K + 1)+subList.get(K + 2)+subList.get(K + 3);
//	        }
//	        for (int L = 1; L <= 1; L++) {
//	        	Collections.shuffle(NATURAL_NUMBERS);
//	        	List<String> subList = NATURAL_NUMBERS.subList(L, L + 5);
//	        	FORTH_FOUR = subList.get(L)+subList.get(L + 1)+subList.get(L + 2)+subList.get(L + 3);
//	        }
//	        
//	        String id = FIRST_FOUR +"-"+ SECOND_FOUR +"-"+ THIRD_FOUR +"-"+ FORTH_FOUR;
//	        productIdList.add(new String(id));
//		 }
//		 
//		 //SHUFFLES THE ARRAYLIST AND PRINT OUT ALL THE PRODUCT ID IN IT
//	        Collections.shuffle(productIdList);
////	        for (int i = 1; i < 10; i++) {
////	            System.out.println(productIdList.get(i));
////	        }
//		return null;
//	}
//
//    public String pickIdBySerialNum(int num)
//    {
//    	TWELVE_DIGIT_PRODUCT_ID();
//    	return productIdList.get(num);
//    }
    
    //THIS FORMATS THE NEWLY COLLECTED TIME STAMP AND FORMATS IT TO ####-####-####-####
    public static String formatString(String m) {
    		String formattedM = m.substring(0, 4).concat("-").concat(m.substring(4, 8)
    				.concat("-").concat(m.substring(8, 12).concat("-").concat(m.substring(12, 16))));
    	return formattedM;
    }
    
    //TRYING OUT A UNIQUE ID FROM TIME STAMPS
	private static final AtomicLong LAST_TIME_MS = new AtomicLong();
	
	public static String uniqueCurrentTimeMS() {
	    long now = System.currentTimeMillis();
	    while(true) {
	        long lastTime = LAST_TIME_MS.get();
	        if (lastTime >= now) {
	            now = lastTime+1;
	            String SIXTEEN_DIGIT_ID = String.format("%016d", now);
	            formatString(SIXTEEN_DIGIT_ID);
	        }
	        if (LAST_TIME_MS.compareAndSet(lastTime, now)) {
	            String SIXTEEN_DIGIT_ID = String.format("%016d", now);
	            formatString(SIXTEEN_DIGIT_ID);
	            return SIXTEEN_DIGIT_ID;
	        }
	    }
	}
}
