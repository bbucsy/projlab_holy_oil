package hu.holyoil.view;

/**
 * Amikor egy játékos köre véget ér, ez az interface frissíti az összes panelen megjelenő adatot, hogy az mind a
 * játékban lévő settlerhez tartozó információ legyen.
 */
public interface IViewComponent {
    /**
     * Változáskor (kör vége) meghívódó függvény, amely az éppen betöltött telepesre szabja a UI elemeit.
     */
    void UpdateComponent();
}
