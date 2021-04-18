package hu.holyoil.commandhandler;

/**
 * Interfész a megvalósítandó parancsok megvalósítására, lekezelésére, iránymutatás.
 */
public interface ICommandHandler {

    /**
     * Fogja a bejövő parancsot és feldolgozza.
     * @param command feldolgozandó parancs sora
     * @return feldolgozás sikeressége
     */
    boolean Handle(String command);

}
