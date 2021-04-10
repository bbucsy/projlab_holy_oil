package hu.holyoil.commandhandler.statecommand;

import hu.holyoil.IIdentifiable;
import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.controller.GameController;
import hu.holyoil.controller.GameState;
import hu.holyoil.controller.SunController;
import hu.holyoil.repository.NeighbourBaseRepository;
import hu.holyoil.repository.PlayerStorageBaseRepository;
import hu.holyoil.repository.ResourceBaseRepository;
import hu.holyoil.repository.SpaceshipBaseRepository;

import java.util.ArrayList;

public class StateCommandHandler implements ICommandHandler {
    @Override
    public boolean Handle(String command) {
        System.out.println("-------- STATE --------");

        ArrayList<IIdentifiable> toWrite = new ArrayList<>();

        toWrite.addAll(NeighbourBaseRepository.GetInstance().GetAll());
        toWrite.addAll(PlayerStorageBaseRepository.GetInstance().GetAll());
        toWrite.addAll(ResourceBaseRepository.GetInstance().GetAll());
        toWrite.addAll(SpaceshipBaseRepository.GetInstance().GetAll());
        toWrite.add(SunController.GetInstance());

        for (IIdentifiable iIdentifiable: toWrite) {
            System.out.println(iIdentifiable.toString());
        }

        GameState state = GameController.GetInstance().GetGameState();
        if (state != GameState.RUNNING) {
            System.out.println(state.toString());
        }
        
        System.out.println("------ STATE END ------");
        return true;
    }
}
