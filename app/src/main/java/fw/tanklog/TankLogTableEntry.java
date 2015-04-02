package fw.tanklog;

/**
 * Created by Frank on 02.04.2015.
 */
public class TankLogTableEntry {

    private String m_Date;
    private String m_Mileage;
    private String m_Fuel;

    public TankLogTableEntry() {

    }

    public TankLogTableEntry(String date, String mileage, String fuel) {
        m_Date = date;
        m_Mileage = mileage;
        m_Fuel = fuel;
    }

    public String getDate() {
        return this.m_Date;
    }

    public void setDate(String date) {
        this.m_Date = date;
    }

    public String getMileage() {
        return this.m_Mileage;
    }

    public void setMileage(String mileage) {
        this.m_Mileage = mileage;
    }

    public String getFuel() {
        return this.m_Fuel;
    }

    public void setFuel(String fuel) {
        this.m_Fuel = fuel;
    }
}
