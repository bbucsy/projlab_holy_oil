package hu.holyoil;

/**
 * Az objektumok egyéni nevének kiírásáért felelős interfész.
 */
public interface IIdentifiable {
    /**
     * Minden objektumot egyedileg azonosít.
     * @return String: az egyed azonosítója
     */
    String GetId();

}
