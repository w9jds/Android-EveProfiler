package com.w9jds.eveprofiler.Classes;

/**
 * Created by Jeremy on 5/23/13.
 */
public class KeysInfo
{
    private String KeyName;
    private String KeyValue;

    KeysInfo(String name, String value)
    {
        this.setKeyName(name);
        this.setKeyValue(value);
    }

    public String getKeyName() { return this.KeyName; }
    public void setKeyName(String KeyName) { this.KeyName = KeyName; }

    public String getKeyValue() { return this.KeyValue; }
    public void setKeyValue(String KeyValue) {this.KeyValue = KeyValue; }

}
