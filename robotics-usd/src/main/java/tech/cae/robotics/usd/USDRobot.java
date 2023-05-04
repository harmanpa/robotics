package tech.cae.robotics.usd;

import jep.Interpreter;
import jep.SubInterpreter;
import tech.cae.robotics.urdf.*;

import java.io.File;

public class USDRobot {

    // should have variables with python actions assign:
    // @SomeAnnotation("stage = Usd.Stage.CreateNew("fileName.usd")")
    // String stage;

    private final Interpreter interpreter = new SubInterpreter();

    public static File tempDir = new File("temp.py");

    private Mass mass;
    private Inertia inertia;
    private Sphere sphere;
    private Mesh mesh;
    private String meshPath;

    public USDRobot(Mass mass, Inertia inertia, Sphere sphere, Mesh mesh) {
        this.mass = mass;
        this.inertia = inertia;
        this.sphere = sphere;
        this.mesh = mesh;
        Double[] inertiaCoordinatesArray = new Double[]{inertia.getIxx(), inertia.getIxy(),
                inertia.getIxz(), inertia.getIyy(), inertia.getIyz(), inertia.getIzz()};
    }

    public USDRobot(){};
    protected String physicsScene;
    protected String meshAttribute;
    protected String joint;
    protected String parentJoint;
    protected String sphereAttribute;
    //Prim is rigid body ???
    protected String childPrim;
    protected String lowerLimit;
    protected String upperLimit;
    protected String limit;
    protected String XYZAxis;
    protected String RPYAxis;
    protected String inertiaAttribute;
    protected String massAttribute;


    public void setChildPrim(String childPrim) {
        this.childPrim = childPrim;
    }

    public String getChildPrim() {
        return childPrim;
    }

    public void setPhysicsScene(String physicsScene) {
         this.physicsScene = physicsScene;
    }

    public String getPhysicsScene() {
        return physicsScene;
    }

    public void setMesh(String meshAttribute) {
        this.meshAttribute = meshAttribute;
    }

    public String getMesh() {
        return meshAttribute;
    }

    public void setJoint(String joint) {
        this.joint = joint;
    }

    public String getJoint() {
        return joint;
    }

    public void setSphereAttribute(Sphere sphere) {
        this.sphereAttribute = String.valueOf(sphere.getRadius());
    }

    public String getSphereAttribute() {
        return sphereAttribute;
    }

    public String getInertiaAttribute() {
        return inertiaAttribute;
    }

    public void setInertiaAttribute(String inertiaAttribute) {
        this.inertiaAttribute = inertiaAttribute;
    }

    public void setMassAttribute(Mass mass) {
        this.massAttribute = String.valueOf(mass.getValue());
    }

    public String getMassAttribute() {
        return massAttribute;
    }

    public String getParentJoint() {
        return parentJoint;
    }

    public void setParentJoint(String parentJoint) {
        this.parentJoint = parentJoint;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    public String getXYZAxis() {
        return XYZAxis;
    }

    public void setXYZAxis(String XYZAxis) {
        this.XYZAxis = XYZAxis;
    }

    public String getRPYAxis() {
        return RPYAxis;
    }

    public void setRPYAxis(String RPYAxis) {
        this.RPYAxis = RPYAxis;
    }
}
