package com.w9jds.eveprofiler;

import java.util.ArrayList;

public class ParseXml {

	public void parse(String response)
	{
		String[] str = response.split("<row |=\"|\" ");
		
		for(int i = 0; i < str.length; i++)
		{
			if (str[i-1].contains("name,characterID,corporationName,corporationID") == true)
				ArrayList<CharacterInfo> Characters = new ArrayList<CharacterInfo>();
					Characters.add(new CharacterInfo);
			
		}
		
		
		System.out.println(response);
		
		
	}
	
	
	
	
}
