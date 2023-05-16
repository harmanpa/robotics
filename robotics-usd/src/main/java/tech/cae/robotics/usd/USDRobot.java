package tech.cae.robotics.usd;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tech.cae.robotics.urdf.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class USDRobot {
    public static File tempDir = new File("temp.py");
    public USDRobot(){};
    protected String physicsScene;
    protected String mass;
    protected String mesh;
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
    @Setter(AccessLevel.NONE)
    protected String inertiaCoordinatesArray;
    protected String originXYZ;
    protected String originRPY;
    protected boolean isRigidBody = false;
    protected String collisionMesh;
    protected String collisionOriginXYZ;
    protected String collisionOriginRPY;
    protected List<Visual> visualList;

    public void setInertiaCoordinatesArray(Inertia inertia) {
        this.inertiaCoordinatesArray = Arrays.toString(new Double[]{inertia.getIxx(), inertia.getIxy(),
                inertia.getIxz(), inertia.getIyy(), inertia.getIyz(), inertia.getIzz()});
    }

}
