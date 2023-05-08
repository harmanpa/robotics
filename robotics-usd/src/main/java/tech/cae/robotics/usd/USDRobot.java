package tech.cae.robotics.usd;

import jep.Interpreter;
import jep.SubInterpreter;
import tech.cae.robotics.urdf.*;

import java.io.File;

public class USDRobot {
//    private final Interpreter interpreter = new SubInterpreter();

    public static File tempDir = new File("temp.py");

    public USDRobot(){};
    protected String physicsScene;
    protected String meshAttribute;
    protected String joint;
    protected String parentJoint;
    protected String childJoint;
    protected String sphereAttribute;
    //Prim is rigid body ???
    protected String childPrim;
    protected String lowerLimit;
    protected String upperLimit;
    protected String XYZAxis;
    protected String RPYAxis;
    protected String jointAxis;
    protected String inertiaAttribute;
    protected String massAttribute;
    protected Double[] inertiaCoordinatesArray;
    protected String origin;
    protected boolean isRigidBody = false;


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

    public void setSphereAttribute() {
        this.sphereAttribute = String.valueOf(0.02);
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

    public void setMass(Mass mass) {
        this.massAttribute = String.valueOf(mass.getValue());
    }

    public String getMass() {
        return massAttribute;
    }

    public String getParentJoint() {
        return parentJoint;
    }

    public void setParentJoint(String parentJoint) {
        this.parentJoint = parentJoint;
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

    public Double[] getInertiaCoordinatesArray() {
        return inertiaCoordinatesArray;
    }

    public void setInertiaCoordinatesArray(Inertia inertia) {
        this.inertiaCoordinatesArray = new Double[]{inertia.getIxx(), inertia.getIxy(),
                inertia.getIxz(), inertia.getIyy(), inertia.getIyz(), inertia.getIzz()};;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(Pose origin) {
        this.origin = String.valueOf(origin);
    }

    public String getChildJoint() {
        return childJoint;
    }

    public void setChildJoint(String childJoint) {
        this.childJoint = childJoint;
    }

    public String getJointAxis() {
        return jointAxis;
    }

    public void setJointAxis(String jointAxis) {
        this.jointAxis = jointAxis;
    }

    public boolean isRigidBody() {
        return isRigidBody;
    }

    public void setRigidBody(boolean rigidBody) {
        isRigidBody = rigidBody;
    }
}
