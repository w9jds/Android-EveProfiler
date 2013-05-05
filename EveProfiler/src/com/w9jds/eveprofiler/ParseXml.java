package com.w9jds.eveprofiler;

import java.util.ArrayList;

public class ParseXml 
{

	public ArrayList<ArrayList<String>> ParseCharacterList(String response)
	{
		ArrayList<ArrayList<String>> AllCharacters = new ArrayList<ArrayList<String>>();

		String[] split = response.split("<");
		ArrayList<String> characterLines = new ArrayList<String>();
		
		for (int i = 0; i < split.length; i++)
		{
			if (split[i].startsWith("row ") == true)
			{
				characterLines.add(split[i]);
			}
		}
		
		for (int i = 0; i < characterLines.size(); i++)
		{
			String[] character = characterLines.get(i).split("row name=\"|\" characterID=\"|\" corporationName=\"|\" corporationID=\"|\" />");
			AllCharacters.add(new ArrayList<String>());
			for (int j = 1; j < character.length-1; j++)
			{
				AllCharacters.get(i).add(character[j]);
			}
		}

		return AllCharacters;	
		
	}
	
	public ArrayList<String> ParseCharacterSheet(String response)
	{
		ArrayList<String> Character = new ArrayList<String>();
		
		
		
		
		
		return Character;
		
	}

}
