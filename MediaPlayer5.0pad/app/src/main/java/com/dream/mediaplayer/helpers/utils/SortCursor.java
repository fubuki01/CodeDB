package com.dream.mediaplayer.helpers.utils;

import java.text.Collator;  
import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Comparator;  
import java.util.Locale;


  
import android.database.Cursor;  
import android.database.CursorWrapper;  
import android.provider.BaseColumns;
import android.util.Log;
  
public class SortCursor extends CursorWrapper{  
	private Cursor mCursor;  
	private ArrayList<SortEntry> sortList = new ArrayList<SortEntry>();  
	private int mPos = 0;  
	
	private static final String FORMAT = "[a-zA-Z]";
	
	private CharacterParser characterParser;
      
    public class SortEntry {  
        public String key;  
        public int order;  
        public boolean isSelect;
        public long baseColumns_ID;
    }  
      
    @SuppressWarnings("rawtypes")  
    private Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);  
      
    @SuppressWarnings("unchecked")    
    public Comparator<SortEntry> comparator = new Comparator<SortEntry>(){        
        @Override  
        public int compare(SortEntry entry1, SortEntry entry2) {
//        	Log.e("SortCursor", "entry1.key = "+entry1.key+", entry2.key = "+entry2.key);
            return cmp.compare(entry1.key, entry2.key);        
//        	return entry1.key.compareToIgnoreCase(entry2.key);
        }     
    };  
  
    public SortCursor(Cursor cursor,String columnName) {          
        super(cursor);  
        // TODO Auto-generated constructor stub  
        
        characterParser = CharacterParser.getInstance();
        
        long time = System.currentTimeMillis();
        Log.i("SortCursor", "start----------- time = "+time+", cursor.getCount() = "+cursor.getCount());  
        
        mCursor = cursor;  
        if(mCursor != null && mCursor.getCount() > 0) {  
            int i = 0;  
            int column = cursor.getColumnIndexOrThrow(columnName);  
            for(mCursor.moveToFirst();!mCursor.isAfterLast();mCursor.moveToNext(),i++){  
//                String formatFirstname = PinyinUtils.getFirstSpell(cursor.getString(column));
                
                String pinyin = characterParser.getSelling(cursor.getString(column));
    			String formatFirstname = pinyin.substring(0, 1);
                
//                Log.i("SortCursor", "start1---11----formatFirstname "+formatFirstname);  
                if (!formatFirstname.matches(FORMAT)) {
                	formatFirstname = "#";
    			} else {
    				formatFirstname =formatFirstname.toUpperCase(Locale.CHINA);
    			}
//                Log.i("SortCursor", "start1---22----formatFirstname "+formatFirstname);  
                
                SortEntry sortKey = new SortEntry();  
                sortKey.key = formatFirstname;
                sortKey.order = i;  
                sortKey.isSelect = false;
                sortKey.baseColumns_ID = -1;
                sortList.add(sortKey);  
            }  
        }  
        
        Log.i("SortCursor", "start1-------1----time = "+(System.currentTimeMillis()-time));  
        //排序  
        Collections.sort(sortList, comparator);  
        Log.i("SortCursor", "start1-------2----time = "+(System.currentTimeMillis()-time));  
        
//        for (int i = 0; i < sortList.size(); i++) {
//        	Log.i("SortCursor", "start1-------sortList.get("+i+").key = "+sortList.get(i).key); 
//        }
    }  
    
    public SortCursor(Cursor cursor,String columnName, String baseColumns_ID) {          
        super(cursor);  
        // TODO Auto-generated constructor stub  
        
        characterParser = CharacterParser.getInstance();
        
        long time = System.currentTimeMillis();
        Log.i("SortCursor", "start----------- time = "+time+", cursor.getCount() = "+cursor.getCount());  
        
        mCursor = cursor;  
        if(mCursor != null && mCursor.getCount() > 0) {  
            int i = 0;  
            int column = cursor.getColumnIndexOrThrow(columnName);  
            int columnIndex = cursor.getColumnIndexOrThrow(baseColumns_ID);
            for(mCursor.moveToFirst();!mCursor.isAfterLast();mCursor.moveToNext(),i++){  
//                String formatFirstname = PinyinUtils.getFirstSpell(cursor.getString(column));
                
                String pinyin = characterParser.getSelling(cursor.getString(column));
    			String formatFirstname = pinyin.substring(0, 1);
                
//                Log.i("SortCursor", "start1---11----formatFirstname "+formatFirstname);  
                if (!formatFirstname.matches(FORMAT)) {
                	formatFirstname = "#";
    			} else {
    				formatFirstname =formatFirstname.toUpperCase(Locale.CHINA);
    			}
//                Log.i("SortCursor", "start1---22----formatFirstname "+formatFirstname);  
                
                SortEntry sortKey = new SortEntry();  
                sortKey.key = formatFirstname;
                sortKey.order = i;  
                sortKey.isSelect = false;
                sortKey.baseColumns_ID = cursor.getLong(columnIndex);
                sortList.add(sortKey);  
            }  
        }  
        
        Log.i("SortCursor", "start1-------1----time = "+(System.currentTimeMillis()-time));  
        //排序  
        Collections.sort(sortList, comparator);  
        Log.i("SortCursor", "start1-------2----time = "+(System.currentTimeMillis()-time));  
        
//        for (int i = 0; i < sortList.size(); i++) {
//        	Log.i("SortCursor", "start1-------sortList.get("+i+").key = "+sortList.get(i).key); 
//        }
    }  
    
    public ArrayList<SortEntry> getSortList() {
    	return sortList;
    }
      
    public boolean moveToPosition(int position)  
    {  
        if(position >= 0 && position < sortList.size()){  
            mPos = position;  
            int order = sortList.get(position).order;  
            return mCursor.moveToPosition(order);  
        }  
        if(position < 0){  
            mPos = -1;  
        }  
        if(position >= sortList.size()){  
            mPos = sortList.size();  
        }  
        return mCursor.moveToPosition(position);          
    }  
      
    public boolean moveToFirst() {        
        return moveToPosition(0);  
    }  
      
    public boolean moveToLast(){  
        return moveToPosition(getCount() - 1);  
    }  
      
    public boolean moveToNext() {                 
        return moveToPosition(mPos+1);  
    }  
      
    public boolean moveToPrevious() {         
        return moveToPosition(mPos-1);  
    }  
      
    public boolean move(int offset) {         
        return moveToPosition(mPos + offset);  
    }  
      
    public int getPosition() {        
        return mPos;  
    }  
}  
