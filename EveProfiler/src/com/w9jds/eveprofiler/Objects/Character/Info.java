package com.w9jds.eveprofiler.Objects.Character;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jeremy on 6/6/13.
 */
public class Info implements Serializable {

    private static final long serialVersionUID = 0L;

    public String name;
    public String race;
    public String bloodline;
    public double accountBalance;
    public double skillPoints;
    public String shipName;
    public String shipTypeID;
    public String shipTypeName;
    public String corporationID;
    public String corporation;
    public String corporationDate;
    public String allianceID;
    public String alliance;
    public String allianceDate;
    public String lastKnownLocation;
    public double securityStatus;
    //employment history here
    public Date cachedUntil;

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

    public double getAccountBalance() { return accountBalance; }
    public void setAccountBalance(double AccountBalance) { this.accountBalance = AccountBalance; }

    public String getAllianceDate() { return allianceDate; }
    public void setAllianceDate(String allianceDate) { this.allianceDate = allianceDate; }

    public String getCorporationDate() { return corporationDate; }
    public void setCorporationDate(String corporationDate) { this.corporationDate = corporationDate; }

    public double getSkillPoints() { return skillPoints; }
    public void setSkillPoints(double SkillPoints) { this.skillPoints = SkillPoints; }

    public String getLastKnownLocation() { return this.lastKnownLocation; }
    public void setLastKnownLocation(String LastKnownLocation) { this.lastKnownLocation = LastKnownLocation; }

    public String getShipName() { return this.shipName; }
    public void setShipName(String shipName) { this.shipName = shipName; }

    public String getshipTypeID() { return this.shipTypeID; }
    public void setShipTypeID(String shipTypeID) { this.shipTypeID = shipTypeID; }

    public String getShipTypeName() { return this.shipTypeName; }
    public void setShipTypeName(String shipTypeName) { this.shipTypeName = shipTypeName; }

    public double getSecurityStatus() { return securityStatus; }
    public void setSecurityStatus(double SecStatus) { this.securityStatus = SecStatus; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCorporation() { return corporation; }
    public void setCorporation(String corporationName) { this.corporation = corporationName; }

    public String getCorporationID() { return corporationID; }
    public void setCorporationID(String corporationID) { this.corporationID = corporationID; }

    public String getRace() { return race; }
    public void setRace(String Race) { this.race = Race; }

    public String getBloodLine() { return bloodline; }
    public void setBloodLine(String BloodLine) { this.bloodline = BloodLine; }

    public String getAlliance() { return alliance; }
    public void setAlliance(String allianceName) { this.alliance = allianceName; }

    public String getAllianceID() { return allianceID; }
    public void setAllianceID(String allianceID) { this.allianceID = allianceID; }
}
