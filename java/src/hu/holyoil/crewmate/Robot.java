package hu.holyoil.crewmate;

public class Robot extends AbstractCrewmate {
    @Override
    public void Die() {
        System.out.println("I am robot " + this.toString() + " and I just died");
    }

    @Override
    public void ReactToAsteroidExplosion() {
        System.out.println("I am robot " + this.toString() + " and my asteroid just exploded");
    }
}
