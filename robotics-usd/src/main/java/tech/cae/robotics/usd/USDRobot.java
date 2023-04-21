package tech.cae.robotics.usd;

import jep.Interpreter;
import jep.SubInterpreter;
import tech.cae.robotics.urdf.*;

public class USDRobot {

    // should have variables with python actions assign:
    // @SomeAnnotation("stage = Usd.Stage.CreateNew("fileName.usd")")
    // String stage;

    private final Interpreter interpreter = new SubInterpreter();

    private final Mass mass;
    private final Inertia inertia;
    private final Sphere sphere;

    public USDRobot(Mass mass, Inertia inertia, Sphere sphere) {
        this.mass = mass;
        this.inertia = inertia;
        this.sphere = sphere;
        Double[] inertiaCoordinatesArray = new Double[]{inertia.getIxx(), inertia.getIxy(),
                inertia.getIxz(), inertia.getIyy(), inertia.getIyz(), inertia.getIzz()};
    }
    protected String stage;
    protected String physicsScene;
    protected String mesh;
    protected String joint;
    protected String sphereAttribute;
    //Prim is rigid body ???
    protected String prim;
    protected String inertiaAttribute;
    protected String massAttribute;

}
