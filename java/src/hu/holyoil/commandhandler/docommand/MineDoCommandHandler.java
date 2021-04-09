package hu.holyoil.commandhandler.docommand;

import hu.holyoil.commandhandler.ICommandHandler;
import hu.holyoil.crewmate.IMiner;
import hu.holyoil.repository.MinerRepository;

public class MineDoCommandHandler implements ICommandHandler {
    @Override
    public boolean Handle(String command) {

        String[] commandParams = command.split(" ");

        IMiner iMiner = MinerRepository.GetInstance().Get(commandParams[1]);

        if (iMiner == null) {

            System.out.println("No miner exists with id: " + commandParams[1]);
            return false;

        }

        iMiner.Mine();

        return true;
    }
}
