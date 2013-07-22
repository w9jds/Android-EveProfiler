package com.w9jds.eveprofiler.Objects.Server;

import java.util.Date;

/**
 * Created by Jeremy on 7/21/13.
 **/
public class ServerStatus
{
    private Date currentTime;
    private Boolean serverOpen;
    private Double onlinePlayers;
    private Date cachedUntil;

    public Double getonlinePlayers() { return onlinePlayers; }
    public void setonlinePlayers(String onlinePlayers) { this.onlinePlayers = Double.parseDouble(onlinePlayers); }

    public Boolean getserverOpen() { return serverOpen; }
    public void setserverOpen(String serverOpen) { this.serverOpen = Boolean.parseBoolean(serverOpen); }

    public Date getCurrentTime() { return currentTime; }
    public void setCurrentTime(String CurrentTime)
    {
        String[] split1 = CurrentTime.split(" ");
        String[] currentDate = split1[0].split("-");
        String[] currentTime = split1[1].split(":");

        Date Cashed = new Date();
        Cashed.setYear(Integer.parseInt(currentDate[0]));
        Cashed.setMonth(Integer.parseInt(currentDate[1]));
        Cashed.setDate(Integer.parseInt(currentDate[2]));
        Cashed.setHours(Integer.parseInt(currentTime[0]));
        Cashed.setMinutes(Integer.parseInt(currentTime[1]));
        Cashed.setSeconds(Integer.parseInt(currentTime[2]));

        this.currentTime = Cashed;
    }

    public Date getCachedUntil() { return cachedUntil; }
    public void setCachedUntil(String CachedUntil)
    {
        String[] split1 = CachedUntil.split(" ");
        String[] CashedDate = split1[0].split("-");
        String[] CashedTime = split1[1].split(":");

        Date Cashed = new Date();
        Cashed.setYear(Integer.parseInt(CashedDate[0]));
        Cashed.setMonth(Integer.parseInt(CashedDate[1]));
        Cashed.setDate(Integer.parseInt(CashedDate[2]));
        Cashed.setHours(Integer.parseInt(CashedTime[0]));
        Cashed.setMinutes(Integer.parseInt(CashedTime[1]));
        Cashed.setSeconds(Integer.parseInt(CashedTime[2]));

        this.cachedUntil = Cashed;
    }

}
