package hu.holyoil.commandhandler.explodeasteroidcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;

/**
 * Az `explode_asteroid [ids]` parancs megvalósítása.
 */
public class ExplodeAsteroidCommandHandler implements ICommandHandler {
    /**
     * A kért aszteroidákat felrobbantja.
     *
     * @param command feldolgozandó parancs
     * @return parancs lefutásának sikeressége
     */
    @Override
    public boolean Handle(String command) {
        String[] commandParams = command.split(" ");
        if (commandParams.length < 2) {
            System.out.println("Invalid number of arguments");
            return false;
        }
        for (int i = 1; i < commandParams.length; i++) {
            Asteroid asteroid = AsteroidRepository.GetInstance().Get(commandParams[i]);
            if (asteroid == null) {
                System.out.println("No Asteroid exists with id: " + commandParams[i]);
                return false;
            }
            asteroid.Explode();
        }
        return true;
    }
}
