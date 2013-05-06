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
	
	public ArrayList<ArrayList<String>> ParseCharacterSheet(String response)
	{
		ArrayList<ArrayList<String>> Character = new ArrayList<ArrayList<String>>();
		String[] split = response.split("<");
		
		for (int i = 0; i < split.length; i++)
		{
			if (split[i].startsWith("willpower") == true || split[i].startsWith("perception") == true || split[i].startsWith("charisma") == true || split[i].startsWith("memory") == true || split[i].startsWith("intelligence") == true || split[i].startsWith("DoB") == true || split[i].startsWith("race") == true || split[i].startsWith("bloodLine") == true || split[i].startsWith("ancestry") == true || split[i].startsWith("cloneName") == true || split[i].startsWith("cloneSkillPoints") == true || split[i].startsWith("balance") == true || split[i].startsWith("allianceName") == true || split[i].startsWith("allianceID") == true)
			{ 
				String[] change = split[i].split(">");
				Character.add(new ArrayList<String>());
				Character.get(Character.size()-1).add(change[0]);
				Character.get(Character.size()-1).add(change[1]);
			}
			
		}
		
		
		return Character;
		
	}

}
