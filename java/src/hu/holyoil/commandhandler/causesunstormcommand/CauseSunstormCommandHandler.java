package hu.holyoil.commandhandler.causesunstormcommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.neighbour.Asteroid;
import hu.holyoil.repository.AsteroidRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * Az cause_sunstorm parancs megvalósítása.
 */
public class CauseSunstormCommandHandler implements ICommandHandler {
    /**
     * A kért aszteroidákon napvihart hív.
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

        List<Asteroid> asteroids = new LinkedList<>();

        boolean correctCommand = true;
        for (int i = 1; i < commandParams.length; ++i) {
            Asteroid asteroid = AsteroidRepository.GetInstance().Get(commandParams[i]);
            if (asteroid == null) {
                System.out.println("No Asteroid exists with id: " + commandParams[i]);
                correctCommand = false;
                break;
            }
            asteroids.add(asteroid);
        }

        if (correctCommand) {
            asteroids.forEach(Asteroid::ReactToSunstorm);
            return true;
        }
        else
            return false;
    }
}
