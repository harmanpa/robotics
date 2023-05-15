package tech.cae.robotics.usd;

import tech.cae.robotics.urdf.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class USDRobot {
    public static File tempDir = new File("temp.py");
    public USDRobot(){};
    protected String physicsScene;
    protected String meshAttribute;
    protected String joint;
    protected String parentJoint;
    protected String childJoint;
    protected String sphereAttribute;
    protected String childPrim;
    protected String lowerLimit;
    protected String upperLimit;
    protected String XYZAxis;
    protected String RPYAxis;
    protected String jointAxis;
    protected String inertiaAttribute;
    protected String massAttribute;
    protected String inertiaCoordinatesArray;
    protected String originXYZ;
    protected String originRPY;
    protected boolean isRigidBody = false;
    protected String collisionMesh;
    protected String collisionOriginXYZ;
    protected String collisionOriginRPY;
    protected String visualOriginXYZ;
    protected String visualOriginRPY;
    protected String colorAttribute;
    protected List<Visual> visualList;


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

    public void setSphereAttribute(String sphereAttribute) {
        this.sphereAttribute = sphereAttribute;
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

    public String getInertiaCoordinatesArray() {
        return inertiaCoordinatesArray;
    }

    public void setInertiaCoordinatesArray(Inertia inertia) {
        this.inertiaCoordinatesArray = Arrays.toString(new Double[]{inertia.getIxx(), inertia.getIxy(),
                inertia.getIxz(), inertia.getIyy(), inertia.getIyz(), inertia.getIzz()});
    }

    public String getOriginXYZ() {
        return originXYZ;
    }

    public void setOriginXYZ(String originXYZ) {
        this.originXYZ = originXYZ;
    }

    public String getOriginRPY() {
        return originRPY;
    }

    public void setOriginRPY(String originRPY) {
        this.originRPY = originRPY;
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

    public String getCollisionMesh() {
        return collisionMesh;
    }

    public void setCollisionMesh(String collisionMesh) {
        this.collisionMesh = collisionMesh;
    }

    public String getColorAttribute() {
        return colorAttribute;
    }

    public void setColorAttribute(String colorAttribute) {
        this.colorAttribute = colorAttribute;
    }

    public String getCollisionOriginXYZ() {
        return collisionOriginXYZ;
    }

    public void setCollisionOriginXYZ(String collisionOriginXYZ) {
        this.collisionOriginXYZ = collisionOriginXYZ;
    }

    public String getCollisionOriginRPY() {
        return collisionOriginRPY;
    }

    public void setCollisionOriginRPY(String collisionOriginRPY) {
        this.collisionOriginRPY = collisionOriginRPY;
    }

    public String getVisualOriginXYZ() {
        return visualOriginXYZ;
    }

    public void setVisualOriginXYZ(String visualOriginXYZ) {
        this.visualOriginXYZ = visualOriginXYZ;
    }

    public String getVisualOriginRPY() {
        return visualOriginRPY;
    }

    public void setVisualOriginRPY(String visualOriginRPY) {
        this.visualOriginRPY = visualOriginRPY;
    }

    public List<Visual> getVisualList() {
        return visualList;
    }

    public void setVisualList(List<Visual> visualList) {
        this.visualList = visualList;
    }
}
